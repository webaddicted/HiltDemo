package com.webaddicted.newhiltproject.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.DialogChangeNumberBinding
import com.webaddicted.newhiltproject.utils.common.DialogUtils
import com.webaddicted.newhiltproject.utils.common.ValidationHelper
import com.webaddicted.newhiltproject.view.base.BaseDialog

class ChangeNumberDialog : BaseDialog(R.layout.dialog_change_number) {
    private lateinit var listener: ClickListener
    private var mobileNo: String? = ""
    private lateinit var mBinding: DialogChangeNumberBinding

    companion object {
        val TAG = ChangeNumberDialog::class.qualifiedName
        val MOBILE_NO = "mobile_no"

        fun dialog(mobileNo: String): ChangeNumberDialog {
            val dialog = ChangeNumberDialog()
            val bundle = Bundle()
            bundle.putString(MOBILE_NO, mobileNo)
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as DialogChangeNumberBinding
        mobileNo = arguments?.getString(MOBILE_NO)
        init()
        clickListener()
    }

    private fun init() {
    }

    private fun clickListener() {
        mBinding.imgClose.setOnClickListener(this)
        mBinding.btnSubmit.setOnClickListener(this)

    }
    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.img_close -> {
                dismiss()
            }
            R.id.btn_submit -> {
                var isValid = true
                if (!ValidationHelper.validateMobileNo(mBinding.edtMobile, mBinding.inputMobile)) isValid = false
                if(isValid) {
                    listener.itemClick(mBinding.edtMobile.text.toString(),targetRequestCode)
                    dismiss()
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        DialogUtils.fullScreenTransDialogBounds(mActivity, dialog)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parentFrag = targetFragment
        if (parentFrag is ClickListener) {
            listener = parentFrag
        } else {
            throw RuntimeException("$context must implement ClickListener")
        }
    }
    interface ClickListener {
        fun itemClick(item: String, targetRequestCode: Int)
    }
}

