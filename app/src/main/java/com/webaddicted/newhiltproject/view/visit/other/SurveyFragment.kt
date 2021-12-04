package com.webaddicted.newhiltproject.view.visit.other

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmSurveyBinding
import com.webaddicted.newhiltproject.utils.common.ImagePickerHelper
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.splash.UserTypeFragment
import java.io.File

class SurveyFragment : BaseFragment(R.layout.frm_survey) {
    private lateinit var mBinding: FrmSurveyBinding
    private var long: Double = 0.0
    private var lat: Double = 0.0

    companion object {
        val TAG = SurveyFragment::class.qualifiedName
        val LAT = "lat"
        val LONG = "long"
        fun getInstance(lat: Double, long: Double): SurveyFragment {
            val bundle = Bundle()
            bundle.putDouble(LAT, lat)
            bundle.putDouble(LONG, long)
            val fragment = SurveyFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmSurveyBinding
        lat = arguments?.getDouble(LAT, 0.0)!!
        long = arguments?.getDouble(LONG, 0.0)!!
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.survey))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        setUi()
        clickListener()
    }

    private fun setUi() {
        addRadioButton(4)
    }

    private fun clickListener() {
        mBinding.btnSubmit.setOnClickListener(this)
        mBinding.includeImg.imgDocType.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
//    GlobalUtils.logPrint(msg = " Name " + " Id is " + v.id)
        when (v.id) {
            R.id.btn_submit -> {
                validate()
            }
            R.id.img_doc_type -> {
                setImg(mBinding.includeImg.imgDocType)
            }
        }
    }

    fun addRadioButton(number: Int) {
        mBinding.rgQue.orientation = LinearLayout.VERTICAL
        for (i in 1..number) {
            val rdBtn = RadioButton(mActivity)
            rdBtn.id = View.generateViewId()
            rdBtn.text = "Radio " + rdBtn.id
            rdBtn.buttonTintList = ContextCompat.getColorStateList(mActivity, R.color.appBlue)
            rdBtn.setOnClickListener(this)
            mBinding.rgQue.addView(rdBtn)
        }
    }

    private fun setImg(imgView: ImageView) {
        ImagePickerHelper.getImage(
            mActivity,
            ImagePickerHelper.ImgPickerType.CHOOSER_CAMERA_GALLERY,
            object : ImagePickerHelper.ImagePickerListener {
                override fun onSuccess(mFile: File, imageBitmap: Bitmap?) {
//          mActivity.showToast("${mFile.absoluteFile}")
                    if (mFile.exists()) {
                        imgView.setImageBitmap(imageBitmap)
                        imgView.tag = ""
//                        surveyImgUpload(mFile)
                    }
                }
            })
    }

//      private fun getSurvey() {
//            visitViewModel.getSurveyData(beatData?.subDealerId)
//            visitViewModel.getSurveyRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<GetSurveyRespo>>? ->
//                    if (isFailure) getSurvey()
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


    private fun validate() {
    }


//      private fun surveyImgUpload(mFile: File) {
//            val dseCode = GlobalUtils.toRequestBody(beatData?.dseCode.toString())
//            val dseName = GlobalUtils.toRequestBody(beatData?.dseName.toString())
//            val subDealerCode = GlobalUtils.toRequestBody(beatData?.subDealerCode.toString())
//            val subDealerName = GlobalUtils.toRequestBody(beatData?.subDealerName.toString())
//            val latBody = GlobalUtils.toRequestBody(lat.toString())
//            val longBody = GlobalUtils.toRequestBody(long.toString())
//            val fileBody = mFile.asRequestBody("image/*".toMediaTypeOrNull())
//            val body = MultipartBody.Part.createFormData("file", mFile.name, fileBody)
//            val checkInTime = GlobalUtils.toRequestBody(visitViewModel.getOrderInTime(beatData?.subDealerId))
//            val req = SurveyImgUploadReq(
//              dseCode,
//              dseName,
//              subDealerCode,
//              subDealerName,
//              latBody,
//              longBody,
//              body,
//              checkInTime
//            )
//            visitViewModel.surveyImgUpload(req)
//            visitViewModel.surveyImgUploadRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<SurveyImgUploadRespo>? ->
//                    if (isFailure) surveyImgUpload(mFile)
//                    else {
//                      if (result?.data!=null && result.data.size>0) {
//                        mActivity.showToast(result.data[0].conclusion)
//                      } else {
//                          DialogUtils.showDialog(
//                            mActivity,
//                            getString(R.string.error),
//                            getString(R.string.something_went_wrong)
//                          )
//                      }
//                    }
//                  }
//                })
//          }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}