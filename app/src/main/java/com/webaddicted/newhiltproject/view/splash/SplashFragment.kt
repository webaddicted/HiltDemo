package com.webaddicted.newhiltproject.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.data.model.UserModel
import com.webaddicted.newhiltproject.databinding.FrmSplashBinding
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.frm_splash) {
      private lateinit var userInfo: UserModel
      private lateinit var mBinding: FrmSplashBinding
      val homeVM: HomeViewModel by viewModels()

      companion object {
            val TAG = SplashFragment::class.qualifiedName
            fun getInstance(bundle: Bundle): SplashFragment {
                  val fragment = SplashFragment()
                  fragment.arguments = bundle
                  return fragment
                }
          }

      override fun onBindTo(binding: ViewDataBinding) {
            mBinding = binding as FrmSplashBinding
            init()
          }

      private fun init() {
            Glide.with(this).load(R.raw.loader).into(DrawableImageViewTarget(mBinding.loadingTyreIv))
            userInfo = homeVM.getPrefUserInfo()
            Handler(Looper.getMainLooper()).postDelayed({
                  Log.d(TAG, "Test : ${userInfo.Email}")
                  if (userInfo.Email != null && userInfo.Email?.isNotEmpty()!!)
                      HomeActivity.newClearLogin(mActivity)
                  else navigateScreen(UserTypeFragment.TAG)
                }, AppConstant.SPLASH_DELAY)
          }

      private fun navigateScreen(tag: String?) {
            var frm: Fragment? = null
            when (tag) {
                  UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
                }
            if (frm != null) navigateFragment(R.id.container, frm, false)
          }
}