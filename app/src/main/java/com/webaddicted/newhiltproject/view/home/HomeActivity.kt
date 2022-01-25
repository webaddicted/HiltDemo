package com.webaddicted.newhiltproject.view.home

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.webaddicted.newhiltproject.BuildConfig
import com.webaddicted.newhiltproject.BuildConfig.VERSION_CODE
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.data.model.common.CommonRespo
import com.webaddicted.newhiltproject.data.model.home.UserInfoRespo
import com.webaddicted.newhiltproject.databinding.ActivityHomeBinding
import com.webaddicted.newhiltproject.utils.apiutils.ApiResponse
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseActivity
import com.webaddicted.newhiltproject.view.ecatalogue.ECatalogueHomeFragment
import com.webaddicted.newhiltproject.view.pricelist.PriceVisibilityFragment
import com.webaddicted.newhiltproject.view.splash.WelcomeActivity
import com.webaddicted.newhiltproject.view.subdealer.GeneralInfoFragment
import com.webaddicted.newhiltproject.view.visit.beat.VisitFragment
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel
import java.util.*

class HomeActivity : BaseActivity(R.layout.activity_home) {
    private lateinit var drawerAdapter: DrawerAdapter
    private var showPlaceholder: Boolean = false
    private var disableBack: Boolean = false
    private var showHomeIcon: Boolean = false
    private lateinit var actionbar: ActionBar

    private lateinit var mBinding: ActivityHomeBinding
    private var fragment: Fragment? = null
    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        val TAG = HomeActivity::class.qualifiedName
        fun newIntent(activity: Activity) {
            activity.startActivity(Intent(activity, HomeActivity::class.java))
        }

        fun newClearLogin(activity: Activity?) {
            val intent = Intent(activity, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as ActivityHomeBinding
        setSupportActionBar(mBinding.appbar.toolbar)
        actionbar = supportActionBar!!
//    mBinding.appbar.homeContent.viewModel = homeViewModel
        initUi()
        clickListener()
//    homeViewModel.userInfoRespo.observe(this, userObserver)
//         initializing navigation menu
//    setUpNavigationView()
//    if (savedInstanceState == null) {
//      navItemIndex = 0
//      CURRENT_TAG = TAG_HOME
//      loadHomeFragment()
//    }
    }

    private fun initUi() {
        setActionBarBackIcon(false)
        setActionBarTitle(AppConstant.MenuItem.HOME.value)
        invalidateOptionsMenu()
        val data = AppConstant.MenuItem.values().map { it.value }
        drawerAdapter = DrawerAdapter(data) { menuItem: String -> onMenuItemClicked(menuItem) }
        mBinding.menuRv.run {
            addItemDecoration(
                androidx.recyclerview.widget.DividerItemDecoration(
                    this@HomeActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = drawerAdapter
        }
        onMenuItemClicked(AppConstant.MenuItem.HOME.value)
        getUserInfo()
        invalidateOptionsMenu()
        setAppVersionName()
    }

    private fun clickListener() {
        mBinding.logoutTv.setOnClickListener(this)
    }

    fun setActionBarTitle(title: String, show: Boolean = true) {
        mBinding.appbar.toolbar.visibility = View.VISIBLE
        mBinding.appbar.txtTitle.text = title
//    if (show) {
//      mBinding.appbar.homeContent.titleTv.visibility = View.VISIBLE
//    } else {
//      mBinding.appbar.homeContent.titleTv.visibility = View.GONE
//    }
    }

    fun setActionBarBackIcon(isBack: Boolean) {
        showHomeIcon = false
        disableBack = false
        showPlaceholder = true
        invalidateOptionsMenu()
        if (isBack) {
            actionbar.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            }
        } else {
            actionbar.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_menu)
            }
        }
    }

    private fun onMenuItemClicked(item: String) {
        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        fragment = null
        supportFragmentManager.popBackStack(
            AppConstant.BACK_STACK_ROOT_TAG,
            androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        when (item) {
            AppConstant.MenuItem.HOME.value -> fragment = HomeFragment.getInstance(Bundle())
            AppConstant.MenuItem.NEW_OUTLET.value -> fragment =
                GeneralInfoFragment.getInstance(Bundle())
            AppConstant.MenuItem.VISIT.value -> fragment = VisitFragment.getInstance(Bundle())
            AppConstant.MenuItem.PRICE_VISIBILITY.value -> fragment =
                PriceVisibilityFragment.getInstance(Bundle())
            AppConstant.MenuItem.E_CATALOGUE.value -> fragment =
                ECatalogueHomeFragment.getInstance(Bundle())

        }
        if (fragment != null) {
            mBinding.appbar.txtTitle.text = item
            navigateFragment(R.id.container, fragment!!, false)
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.logoutTv -> {
                mBinding.appbar.homeContent.loadingTyreIv.also {
                    it.visible()
                    Glide.with(this).load(R.raw.loader).into(DrawableImageViewTarget(it))
                }
                homeViewModel.clearPref()
                WelcomeActivity.newClearLogin(this)
            }
        }
    }

    private fun setAppVersionName() {
        var appVersionStr = "Version: ${BuildConfig.VERSION_NAME}"
        if (BuildConfig.DEBUG) {
            appVersionStr = "$appVersionStr--$VERSION_CODE \n ${BuildConfig.BASE_URL}"
        }
        mBinding.appVersionTv.text = appVersionStr
    }

    private fun getUserInfo() {
        homeViewModel.userInfoApi()
        homeViewModel.userInfoRespo.observe(this, {
            handleApiRespo(
                it,
                mBinding.appbar.homeContent.loadingTyreIv
            ) { isFailure: Boolean, result: ApiResponse<CommonRespo<UserInfoRespo>>? ->
                if (isFailure) getUserInfo()
                else {
                    homeViewModel.setPrefUserInfo(it.data?.respDetails)
                    Log.d(
                        TAG,
                        "User Info : ${it.data?.respDetails?.Name} ${homeViewModel.getPrefUserInfo().Email}"
                    )
                }
            }
        })
    }


    fun lockDrawer(isLock: Boolean) {
        if (isLock) {
            mBinding.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            mBinding.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    private fun getNoOfStackActivities(): Int {
        val mngr = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mngr.appTasks[0].taskInfo.numActivities
        } else {
            mngr.getRunningTasks(10)[0].numActivities
        }
    }

    fun popBackStack() {
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
//        Utility.hideKeyboardFrom(this, currentFocus)
//        if (showPlaceholderRight) {
//          //do nothing
//        } else
                if (!showHomeIcon) {
                    when {
                        supportFragmentManager.backStackEntryCount > 0 ||
                                (supportFragmentManager.findFragmentById(R.id.container) != null &&
                                        supportFragmentManager.findFragmentById(R.id.container)?.childFragmentManager?.backStackEntryCount ?: 0 > 1) -> {

                            onBackPressed()
                        }
                        else -> mBinding.drawerLayout.openDrawer(GravityCompat.START)
                    }
                } else {
                    popBackStack()
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (!disableBack) {
            when {
                supportFragmentManager.findFragmentById(R.id.container) != null -> {
                    when {
                        supportFragmentManager.findFragmentById(R.id.container)?.childFragmentManager?.backStackEntryCount ?: 0 > 1 -> {
                            supportFragmentManager.findFragmentById(R.id.container)?.childFragmentManager?.popBackStack()
                        }
                        supportFragmentManager.backStackEntryCount > 0 -> {
                            supportFragmentManager.popBackStack()
                        }
                        else -> {
                            if (getNoOfStackActivities() > 1 || isAllowExitApp) {
                                super.onBackPressed()
                            } else {
                                showToast(getString(R.string.press_back_again_to_exit))
                                isAllowExitApp = true
                            }
                        }
                    }
                }
            }
        }
    }

}