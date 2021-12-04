package com.webaddicted.newhiltproject.view.subdealer

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmOtpBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.ApiConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.dialog.ChangeNumberDialog
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.splash.UserTypeFragment
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel

class OtpFragment : BaseFragment(R.layout.frm_otp), ChangeNumberDialog.ClickListener {
    private lateinit var mBinding: FrmOtpBinding
    var mobileNo = ""
    var timerCount = 0

    companion object {
        val TAG = OtpFragment::class.qualifiedName
        const val SUBDEALER_REQ = "SubDealer Req"
        fun getInstance(req: String?): OtpFragment {
            val bundle = Bundle()
            bundle.putSerializable(SUBDEALER_REQ, req)
            val fragment = OtpFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmOtpBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.verify_otp))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        mobileNo = "9024061407"
        setUi()
        resendOtp()
        clickListener()
        otpRequest(ApiConstant.GENERATE_OTP)
    }

    private fun resendOtp() {
        val color = getColor(mActivity, R.color.appOrange)
        val wordToSpan: Spannable =
            Spannable.Factory.getInstance()
                .newSpannable(getString(R.string.dont_receive) + " Resend")
//    val redColor = ColorStateList(arrayOf(intArrayOf()), intArrayOf(-0x5ef6ff))
//    val highlightSpan = TextAppearanceSpan(null, Typeface.BOLD, -1, redColor, null)
        wordToSpan.setSpan(
//      highlightSpan,
            ForegroundColorSpan(color),
            16,
            23,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        wordToSpan.setSpan(object : ClickableSpan() {
            override fun onClick(v: View) {
                if (timerCount == 0)
                    otpRequest(ApiConstant.RESENT_OTP)
            }
        }, 16, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBinding.txtResent.text = wordToSpan
        mBinding.txtResent.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setUi() {
        val color = getColor(mActivity, R.color.appBlue)
        val msg = getString(R.string.otp_send_desc) + " (+91-$mobileNo) (Change)"
        val wordToSpan: Spannable =
            Spannable.Factory.getInstance()
                .newSpannable(msg)
//    val redColor = ColorStateList(arrayOf(intArrayOf()), intArrayOf(-0x5ef6ff))
//    val highlightSpan = TextAppearanceSpan(null, Typeface.BOLD, -1, redColor, null)
        wordToSpan.setSpan(
            ForegroundColorSpan(color),
            56,
            msg.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        wordToSpan.setSpan(object : ClickableSpan() {
            override fun onClick(v: View) {
                changeNumberDialog()
            }
        }, 56, msg.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBinding.otpNoteTv.text = wordToSpan
        mBinding.otpNoteTv.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun clickListener() {
        mBinding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_submit -> {
                val otp = mBinding.pinview.text.toString().trim()
                if (otp.length == 6)
                    otpRequest(ApiConstant.VALIDATE_OTP, otp = otp)
                else mActivity.showToast(getString(R.string.enter_otp_code))
            }
        }
    }

    private fun otpRequest(reqType: String, otp: String? = "") {
        startTimer()
        HomeActivity.newClearLogin(mActivity)
//            val req = OTPReq(reqType, mobileNo, otp, ApiConstant.SUBDEALER_CHANGES)
//            subDealerViewModel.sendOTP(req)
//            subDealerViewModel.otpRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonRespo<OTPRespo>>? ->
//                    if (isFailure) otpRequest(reqType, otp)
//                    else {
//                      if (it.data?.isSuccess == true) {
//                        if (reqType == ApiConstant.VALIDATE_OTP)
//                          submitData()
//                        else startTimer()
//                      } else {
//                        it.data?.strMessage?.let { it1 -> DialogUtils.showDialog(
//                            mActivity,
//                            getString(R.string.error),
//                            it1
//                          )
//                        }
//                      }
//                    }
//                  }
//                })
    }

    var timer: CountDownTimer? = null
    private fun startTimer() {
        mBinding.txtTimer.visible()
        timer?.cancel()
//    timer?.onFinish()
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
//        GlobalUtils.logPrint(msg = "$millisUntilFinished")
                val sec = millisUntilFinished / 1000
                mBinding.txtTimer.text = "00:" + String.format("%02d", sec)
                timerCount = sec.toInt()
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                timerCount = 0
                mBinding.txtTimer.gone()
            }
        }

        timer?.start()
    }


    override fun itemClick(item: String, targetRequestCode: Int) {
        mobileNo = item
        setUi()
        otpRequest(ApiConstant.GENERATE_OTP)
    }

    private fun changeNumberDialog() {
        val dialog = ChangeNumberDialog.dialog(mobileNo)
        dialog.setTargetFragment(this, 256)
        dialog.show(mActivity.supportFragmentManager, "ChangeNumberDialog")
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
//      ZoomImageFrm.TAG -> frm = ZoomImageFrm.getInstance(bundle)
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}
