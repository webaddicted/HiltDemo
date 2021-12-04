package com.webaddicted.newhiltproject.view.dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.webaddicted.newhiltproject.BuildConfig
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.DialogUpdateAppBinding
import com.webaddicted.newhiltproject.utils.common.DialogUtils
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.view.base.BaseDialog
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel


class UpdateAppDialog : BaseDialog(R.layout.dialog_update_app) {
    private var version: String? = ""
    private var isSkip: Boolean? = true
    private var message: String? = ""
    private lateinit var mBinding: DialogUpdateAppBinding
    private val homeViewModel: HomeViewModel by viewModels()
    companion object {
        val TAG = UpdateAppDialog::class.qualifiedName
        val MESSAGE = "message"
        val IS_SKIP = "isSkip"
        val VERSION = "version"
        fun dialog(message: String?, isSkip:Boolean, version:String?): UpdateAppDialog {
            val dialog = UpdateAppDialog()
            val bundle = Bundle()
            bundle.putString(MESSAGE, message)
            bundle.putBoolean(IS_SKIP, isSkip)
            bundle.putString(VERSION, version)
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as DialogUpdateAppBinding
        message = arguments?.getString(MESSAGE)
        isSkip = arguments?.getBoolean(IS_SKIP)
        version = arguments?.getString(VERSION)
        init()
        clickListener()
    }

    private fun init() {
        mBinding.txtTitle.text = getString(R.string.new_version)
        mBinding.txtMessage.text = message
        mBinding.txtCurrVer.text = String.format(getString(R.string.current_version_value), BuildConfig.VERSION_NAME)
        mBinding.txtNewVer.text = String.format(getString(R.string.new_version_value), version)
    }

    private fun clickListener() {
        if(isSkip==true)
            mBinding.imgClose.visible()
        else mBinding.imgClose.gone()
        mBinding.imgClose.setOnClickListener(this)
        mBinding.btnUpdateNow.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        DialogUtils.fullScreenTransDialogBounds(mActivity, dialog)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.img_close -> {
                dismiss()
            }
            R.id.btn_update_now -> {
                updateApp()
            }
        }
    }
    private fun updateApp() {
        homeViewModel.setUpdateNotifyDays(0L)
        val appPackageName = mActivity.packageName // getPackageName() from Context or Activity object
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }
}