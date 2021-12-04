package com.webaddicted.newhiltproject.view.dialog


import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.DialogSizeBinding
import com.webaddicted.newhiltproject.utils.common.DialogUtils
import com.webaddicted.newhiltproject.view.base.BaseDialog

class SizeDialog : BaseDialog(R.layout.dialog_size) {

    private lateinit var mBinding: DialogSizeBinding
    private var title: String = ""
    private var sizes: List<String> = ArrayList()
    private lateinit var mAdapter: SizeAdapter
    private lateinit var mManager: androidx.recyclerview.widget.LinearLayoutManager

    companion object {
        val TAG = SizeDialog::class.qualifiedName
        val TITLE = "title"
        val SIZES = "sizes"


        fun dialog(badgeName: String, sizes: ArrayList<String>): SizeDialog {
            val dialog = SizeDialog()
            val bundle = Bundle()
            bundle.putString(TITLE, badgeName)
            bundle.putStringArrayList(SIZES, sizes)
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as DialogSizeBinding
        arguments?.let {
            title = it.getString(TITLE) ?: ""
            sizes = it.getStringArrayList(SIZES) as ArrayList<String>
        }
        init()
        clickListener()
    }

    private fun init() {
        mBinding.topBarTv.text = String.format(getString(R.string.available_sizes_for_value), title)
        mManager = androidx.recyclerview.widget.LinearLayoutManager(mActivity)
        mBinding.sizesRv.layoutManager = mManager
        mAdapter = SizeAdapter(sizes)
        mBinding.sizesRv.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
    }

    private fun clickListener() {
        mBinding.topBarTv.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        DialogUtils.fullScreenTransDialogBounds(mActivity, dialog)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.topBarTv -> {
                dismiss()
            }
        }
    }
}