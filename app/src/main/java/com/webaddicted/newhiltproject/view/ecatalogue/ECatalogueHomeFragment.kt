package com.webaddicted.newhiltproject.view.ecatalogue

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.data.model.other.TileModel
import com.webaddicted.newhiltproject.databinding.FrmEcatalogueHomeBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity

class ECatalogueHomeFragment : BaseFragment(R.layout.frm_ecatalogue_home) {
    private var catalogueAdapter: ECatalogueAdapter? = null
    private var data: List<String>? = null
    private var spinnerAdapter: ArrayAdapter<String>? = null
    private lateinit var mBinding: FrmEcatalogueHomeBinding
    private var isPassenger: Boolean = true
    private val cardsList = ArrayList<TileModel>(8)
    private var titles = arrayOf("")
    private var icons = intArrayOf()

    companion object {
        val TAG = ECatalogueHomeFragment::class.qualifiedName
        const val BEAT_DATA = "Beat Data"
        fun getInstance(bundle: Bundle): ECatalogueHomeFragment {
            val fragment = ECatalogueHomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmEcatalogueHomeBinding
//        beatData = arguments?.getSerializable(VisitDetailsFragment.BEAT_DATA) as BeatRespo
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.ecatalogue))
        (mActivity as HomeActivity).setActionBarBackIcon(false)
        (mActivity as HomeActivity).lockDrawer(false)
        setUi(AppConstant.SelectableLanguage.ENGLISH.value)
        setAdapter()
        clickListener()
    }

    private fun setAdapter() {
        data = AppConstant.SelectableLanguage.values().map { it.value }
        spinnerAdapter = ArrayAdapter(
            mActivity,
            R.layout.row_spinner_txt,
            data!!
        )
        spinnerAdapter?.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        mBinding.languageSpinner.adapter = spinnerAdapter
    }


//    private fun setViewPager() {
//        tabTitle = AppConstant.ECatalogueTab.values().map { it.value }
//        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(tabTitle?.get(0)))
//        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(tabTitle?.get(1)))
//    }

    private fun clickListener() {
        mBinding.commercialbtn.setOnClickListener(this)
        mBinding.passengerbtn.setOnClickListener(this)

//        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                isPassenger = (tab.position == 0)
//                setUi(AppConstant.SelectableLanguage.ENGLISH.value)
////                viewPager.setCurrentItem(tab.position)
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {}
//            override fun onTabReselected(tab: TabLayout.Tab?) {}
//        })
        mBinding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem: String = parent.getItemAtPosition(position).toString()
                    setUi(selectedItem)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.commercialbtn -> {
                isPassenger = false
                onCommercialButtonClicked()
                setUi(AppConstant.SelectableLanguage.ENGLISH.value)
            }
            R.id.passengerbtn -> {
                isPassenger = true
                onPassengerButtonClicked()
                setUi(AppConstant.SelectableLanguage.ENGLISH.value)
            }
        }
    }

    private fun onPassengerButtonClicked() {
        val commercial = mBinding.commercialbtn
        val passengerView = mBinding.passengerbtn
        GlobalUtils.setBackgroundTintColor(passengerView,R.color.appBlue)
        GlobalUtils.setBackgroundTintColor(commercial,R.color.white)
//        commercial.setBackgroundResource(R.drawable.white_box_rightradius)
        activity?.let { ContextCompat.getColor(it, R.color.appBlue) }
            ?.let { commercial.setTextColor(it) }
//        passengerView.setBackgroundResource(R.drawable.blue_box_leftradius)
        activity?.let { ContextCompat.getColor(it, R.color.white) }
            ?.let { passengerView.setTextColor(it) }
    }

    private fun onCommercialButtonClicked() {
        val commercial = mBinding.commercialbtn
        val passengerView = mBinding.passengerbtn
        GlobalUtils.setBackgroundTintColor(commercial,R.color.appBlue)
        GlobalUtils.setBackgroundTintColor(passengerView,R.color.white)
//        commercial.setBackgroundResource(R.drawable.blue_box_rightradius)

        activity?.let { ContextCompat.getColor(it, R.color.white) }
            ?.let { commercial.setTextColor(it) }
//        passengerView.setBackgroundResource(R.drawable.white_box_leftradius)
        activity?.let { ContextCompat.getColor(it, R.color.appBlue) }
            ?.let { passengerView.setTextColor(it) }
    }

    private fun setUi(item: String) {
        if (isPassenger) onPassengerButtonClicked() else onCommercialButtonClicked()
        titles = when (item) {
            AppConstant.SelectableLanguage.ENGLISH.value -> {
                resources.getStringArray(R.array.english_vehicle_types)
            }
            AppConstant.SelectableLanguage.POLISH.value -> {
                resources.getStringArray(R.array.polish_vehicle_types)
            }
            AppConstant.SelectableLanguage.ITALIAN.value -> {
                resources.getStringArray(R.array.italian_vehicle_types)
            }
            AppConstant.SelectableLanguage.SPANISH.value -> {
                resources.getStringArray(R.array.spanish_vehicle_types)
            }
            else -> resources.getStringArray(R.array.english_vehicle_types)
        }
        icons = intArrayOf(
            R.drawable.catalog_bike, R.drawable.catalog_scooter, R.drawable.catalog_car,
            R.drawable.catalog_suv, R.drawable.catalog_truck,
            R.drawable.catalog_lastmile, R.drawable.catalog_lcv, R.drawable.catalog_lcv
        )
        cardsList.clear()
        enumValues<AppConstant.ECatalogueVehicleType>().forEachIndexed { index, element ->
            val tilesData = TileModel(titles[index], icons[index], element.value)
            cardsList.add(tilesData)
        }
        val passengerList = if (isPassenger) cardsList.subList(0, 4)
        else cardsList.subList(4, cardsList.size)
        catalogueAdapter = ECatalogueAdapter(isPassenger, passengerList)
        { menuItem: TileModel -> navigateScreen(ECatalogueFilterFragment.TAG, menuItem) }
        mBinding.itemRv.run {
            layoutManager = GridLayoutManager(mActivity, 2)
            adapter = catalogueAdapter
        }
    }

    private fun navigateScreen(tag: String?, menuItem: TileModel) {
        var frm: Fragment? = null
        when (tag) {
            ECatalogueFilterFragment.TAG -> frm = ECatalogueFilterFragment.getInstance(menuItem)
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}
