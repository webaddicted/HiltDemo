package com.webaddicted.newhiltproject.view.subdealer

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmKycDetailsBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.common.ImagePickerHelper
import com.webaddicted.newhiltproject.utils.common.ValidationHelper
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.dialog.PickerDialog
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel
import java.io.File

class KycFragment : BaseFragment(R.layout.frm_kyc_details), PickerDialog.ClickListener {
    private lateinit var data: ArrayList<String>
    private lateinit var mBinding: FrmKycDetailsBinding
    private val subDealerViewModel: HomeViewModel by viewModels()
    private var subDealerPickList: String? = null
    var doc1: Bitmap? = null
    var doc2: Bitmap? = null
    var shopImg: Bitmap? = null

    companion object {
        val TAG = KycFragment::class.qualifiedName
        const val SUB_DEALER = "Sub Dealer"
        const val SUB_DEALER_REQ = "Sub Dealer REQ"
        fun getInstance(item: String?, req: String?): KycFragment {
            val bundle = Bundle()
            bundle.putSerializable(SUB_DEALER, item)

            bundle.putSerializable(SUB_DEALER_REQ, req)
            val fragment = KycFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmKycDetailsBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.kyc_detail))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        data = AppConstant.AccountType.values().map { it.name } as ArrayList<String>
        setUi()
        clickListener()

    }

    private fun setUi() {
        if (doc1 != null)
            mBinding.includeDocType1.imgDocType.setImageBitmap(doc1)
        if (doc2 != null)
            mBinding.includeDocType2.imgDocType.setImageBitmap(doc2)
        if (shopImg != null)
            mBinding.includeShopPhoto.imgDocType.setImageBitmap(shopImg)
    }

    private fun clickListener() {
        mBinding.btnNext.setOnClickListener(this)
        mBinding.edtDocType1.setOnClickListener(this)
        mBinding.edtDocType2.setOnClickListener(this)
        mBinding.includeDocType1.imgDocType.setOnClickListener {
            setImg(
                AppConstant.UploadImageType.DOC_IMG1.value,
                mBinding.includeDocType1.imgDocType,
                mBinding.includeDocType1.loadingTyreIv,
            )
        }
        mBinding.includeDocType2.imgDocType.setOnClickListener {
            setImg(
                AppConstant.UploadImageType.DOC_IMG2.value,
                mBinding.includeDocType2.imgDocType,
                mBinding.includeDocType2.loadingTyreIv,
            )
        }
        mBinding.includeShopPhoto.imgDocType.setOnClickListener {
            setImg(
                AppConstant.UploadImageType.SHOP_IMAGE.value,
                mBinding.includeShopPhoto.imgDocType,
                mBinding.includeShopPhoto.loadingTyreIv,
            )
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_next -> {
                if (validate()) {
                    navigateScreen(CRMInfoFragment.TAG)
                }
            }
            R.id.edt_doc_type1 -> {
                showDialog(
                    getString(R.string.select_doc_type1),
                    data,
                    AppConstant.DialogType.PHOTO_ID_1.value
                )
            }
            R.id.edt_doc_type2 -> {
                showDialog(
                    getString(R.string.select_doc_type2),
                    data,
                    AppConstant.DialogType.PHOTO_ID_2.value
                )
            }
        }
    }

    private fun setImg(imgType: String, imgView: ImageView, loader: ImageView) {
        ImagePickerHelper.getImage(
            mActivity,
            ImagePickerHelper.ImgPickerType.CHOOSER_CAMERA_GALLERY,
            object : ImagePickerHelper.ImagePickerListener {
                override fun onSuccess(mFile: File, imageBitmap: Bitmap?) {
//          mActivity.showToast("${mFile.absoluteFile}")
                    if (mFile.exists()) {
                        imgView.setImageBitmap(imageBitmap)
//                        uploadImage(imgType, mFile.name, imageBitmap, loader, imgView)
                    }
                }
            })
    }

//    private fun uploadImage(
//        imgType: String,
//        fileName: String,
//        imageBitmap: Bitmap?,
//        loader: ImageView,
//        imageView: ImageView
//    ) {
//        setDefaultBitmapNull(imgType)
//        val encodedImage = GlobalUtils.bitmapToString(imageBitmap)
//        val req = UploadDocReq("", fileName, encodedImage)
//        subDealerViewModel.uploadSubdealerDoc(req)
//        subDealerViewModel.uploadDocRespo.observe(this, {
//            handleApiRespo(
//                it,
//                mBinding.loadingTyreIv
//            ) { isFailure: Boolean, result: ApiResponse<CommonRespo<String>>? ->
//                if (isFailure) {
//                    setDefaultBitmapNull(imgType)
//                    uploadImage(imgType, fileName, imageBitmap, loader, imageView)
//                } else {
//                    if (it.data?.isSuccess == true) {
//                        when (imgType) {
//                            AppConstant.UploadImageType.DOC_IMG1.value -> {
//                                doc1 = imageBitmap
//                                subDealerReq?.filePhoto1 = it.data.respDetails
//                            }
//                            AppConstant.UploadImageType.DOC_IMG2.value -> {
//                                doc2 = imageBitmap
//                                subDealerReq?.filePhoto2 = it.data.respDetails
//                            }
//                            AppConstant.UploadImageType.SHOP_IMAGE.value -> {
//                                shopImg = imageBitmap
//                                subDealerReq?.fileShop = it.data.respDetails
//                            }
//                        }
//                    } else {
//                        setDefaultBitmapNull(imgType)
//                        imageView.setImageBitmap(null)
//                        it.data?.strMessage?.let { it1 ->
//                            DialogUtils.showDialog(
//                                mActivity,
//                                getString(R.string.error),
//                                it1
//                            )
//                        }
//                    }
//                }
//            }
//        })
//    }

    private fun setDefaultBitmapNull(imgType: String) {
        when (imgType) {
            AppConstant.UploadImageType.DOC_IMG1.value -> {
                doc1 = null
            }
            AppConstant.UploadImageType.DOC_IMG2.value -> {
                doc2 = null
            }
            AppConstant.UploadImageType.SHOP_IMAGE.value -> {
                shopImg = null
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        if (!ValidationHelper.validateBlank(
                mBinding.edtDocType1,
                mBinding.inputDocType1,
                getString(R.string.select_doc_type2)
            )
        ) isValid = false
//    if (!ValidationHelper.validateBlank(
//        mBinding.edtDocNo1,
//        mBinding.inputDocNo1,
//        getString(R.string.enter_doc_no)
//      )
//    ) isValid = false
        if (mBinding.edtDocType1.text.toString() == AppConstant.PhotoIdType.VOTER_ID.value && !ValidationHelper.validateVoterId(
                mBinding.edtDocNo1,
                mBinding.inputDocNo1
            )
        ) isValid = false
        if (mBinding.edtDocType1.text.toString() == AppConstant.PhotoIdType.AADHAR_CARD.value &&
            !ValidationHelper.validateAadharNo(
                mBinding.edtDocNo1,
                mBinding.inputDocNo1
            )
        ) isValid = false
        if (mBinding.edtDocType1.text.toString() == AppConstant.PhotoIdType.DRIVING_LICENSE.value &&
            !ValidationHelper.validateDL(
                mBinding.edtDocNo1,
                mBinding.inputDocNo1
            )
        ) isValid = false
        if (mBinding.edtDocType1.text.toString() == AppConstant.PhotoIdType.PASSPORT.value &&
            !ValidationHelper.validatePassport(
                mBinding.edtDocNo1,
                mBinding.inputDocNo1
            )
        ) isValid = false

//        if (doc1 == null) {
//            mActivity.showToast(getString(R.string.capture_doc_img))
//            isValid = false
//        }
        if (!ValidationHelper.validateBlank(
                mBinding.edtDocType2,
                mBinding.inputDocType2,
                getString(R.string.select_doc_type2)
            )
        ) isValid = false

        if (mBinding.edtDocType2.text.toString() == AppConstant.PhotoIdType.VOTER_ID.value && !ValidationHelper.validateVoterId(
                mBinding.edtDocNo2,
                mBinding.inputDocNo2
            )
        ) isValid = false
        if (mBinding.edtDocType2.text.toString() == AppConstant.PhotoIdType.AADHAR_CARD.value &&
            !ValidationHelper.validateAadharNo(
                mBinding.edtDocNo2,
                mBinding.inputDocNo2
            )
        ) isValid = false
        if (mBinding.edtDocType2.text.toString() == AppConstant.PhotoIdType.DRIVING_LICENSE.value &&
            !ValidationHelper.validateDL(
                mBinding.edtDocNo2,
                mBinding.inputDocNo2
            )
        ) isValid = false
        if (mBinding.edtDocType2.text.toString() == AppConstant.PhotoIdType.PASSPORT.value &&
            !ValidationHelper.validatePassport(
                mBinding.edtDocNo2,
                mBinding.inputDocNo2
            )
        ) isValid = false

//        if (doc2 == null) {
//            mActivity.showToast(getString(R.string.capture_doc_img))
//            isValid = false
//        }
//        if (shopImg == null) {
//            mActivity.showToast(getString(R.string.capture_shop_img))
//            isValid = false
//        }
        return isValid
    }

    private fun showDialog(title: String, list: ArrayList<String>, reqCode: Int) {
        val dialog = PickerDialog.dialog(title, list)
        dialog.setTargetFragment(this, reqCode)
        dialog.show(mActivity.supportFragmentManager, "PickerDialog")
    }

    override fun itemClick(item: String, targetRequestCode: Int) {
        when (targetRequestCode) {
            AppConstant.DialogType.PHOTO_ID_1.value -> {
                mBinding.edtDocType1.setText(item)
            }
            AppConstant.DialogType.PHOTO_ID_2.value -> {
                mBinding.edtDocType2.setText(item)
            }
        }
    }


    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            CRMInfoFragment.TAG -> frm =
                CRMInfoFragment.getInstance(subDealerPickList, tag)
//      ZoomImageFrm.TAG -> frm = ZoomImageFrm.getInstance(bundle)
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}
