package com.webaddicted.newhiltproject.view.splash

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.BuildConfig
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.Test
import com.webaddicted.newhiltproject.databinding.FrmUserTypeBinding
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

class UserTypeFragment : BaseFragment(R.layout.frm_user_type) {
    private lateinit var mBinding: FrmUserTypeBinding

    companion object {
        val TAG = UserTypeFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): UserTypeFragment {
            val fragment = UserTypeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmUserTypeBinding
        initUi()
        clickListener()
    }

    private fun initUi() {
//        val userInfo = (activity as SplashActivity).getUserInfo()
//        if(userInfo!=null)
//        LoginActivity.newIntent(mActivity)
    }

    private fun clickListener() {
        mBinding.salesmanBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.salesmanBtn -> {
//                val baseUrl = BuildConfig.BASE_URL.dropLast(1)
//                val baseUrl = "https://dev2021-hilt.cs5.force.com/hiltforce/"
//               val baseUrl = "https://proddmsuat-hilt.cs57.force.com/hiltforce/"

                HomeActivity.newIntent(mActivity)
                Log.d("TAG","Is Balanced : ")

            }
        }
    }


}