package com.webaddicted.newhiltproject.view.visit.other

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmFeedbackBinding
import com.webaddicted.newhiltproject.utils.common.ValidationHelper
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.splash.UserTypeFragment

class FeedbackFragment : BaseFragment(R.layout.frm_feedback) {
    private lateinit var mBinding: FrmFeedbackBinding

    companion object {
        val TAG = FeedbackFragment::class.qualifiedName
        fun getInstance(): FeedbackFragment {
            val bundle = Bundle()
            val fragment = FeedbackFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmFeedbackBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.feedback))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        setUi()
        clickListener()
    }


    private fun setUi() {
    }

    private fun clickListener() {
        mBinding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_submit -> {
                if (validate()) {
                }
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        if (!ValidationHelper.validateBlank(
                mBinding.edtTitle,
                mBinding.inputTitle,
                getString(R.string.please_enter_title)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtFeedback,
                mBinding.inputFeedback,
                getString(R.string.please_enter_feedback)
            )
        ) isValid = false
        return isValid
    }

//      private fun submitFeedback() {
//            val req = FeedbackReq(beatData?.subDealerId, mBinding.edtTitle.text.toString(), mBinding.edtFeedback.text.toString())
//            visitViewModel.submitFeedback(req)
//            visitViewModel.submitFeedbackRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonRespo<Boolean>>? ->
//                    if (isFailure) submitFeedback()
//                    else {
//                      if (it.data?.isSuccess == true) {
//                        it.data.strMessage?.let { it1 -> mActivity.showToast(it1) }
//                        activity?.onBackPressed()
//                      } else {
//                        it.data?.strMessage?.let { it1 ->
//                          DialogUtils.showDialog(
//                            mActivity,
//                            getString(R.string.error),
//                            it1
//                          )
//                        }
//                      }
//                    }
//                  }
//                })
//          }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
//      ZoomImageFrm.TAG -> frm = ZoomImageFrm.getInstance(bundle)
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}
