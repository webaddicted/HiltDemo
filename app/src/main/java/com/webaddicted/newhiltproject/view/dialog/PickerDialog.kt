package com.webaddicted.newhiltproject.view.dialog

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.DialogPickerBinding
import com.webaddicted.newhiltproject.utils.common.DialogUtils
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.view.base.BaseDialog
import java.util.*

class PickerDialog : BaseDialog(R.layout.dialog_picker) {
    private lateinit var listener: ClickListener
    private var mAdapter: PickerAdapter? = null
    private var title: String? = ""
    private var list: ArrayList<String>? = arrayListOf()
    private lateinit var mBinding: DialogPickerBinding

    companion object {
        val TAG = PickerDialog::class.qualifiedName
        const val DATA_LIST = "DataList"
        const val TITLE = "Title"
        fun dialog(title: String, list: ArrayList<String>): PickerDialog {
            val dialog = PickerDialog()
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putStringArrayList(DATA_LIST, list)
            dialog.arguments = bundle
            return dialog
        }
    }

    interface ClickListener {
        fun itemClick(item: String, targetRequestCode: Int)
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as DialogPickerBinding
        title = arguments?.getString(TITLE)
        list = arguments?.getStringArrayList(DATA_LIST)
        init()
        clickListener()
    }

    private fun init() {
        mBinding.txtTitle.text = title
        mAdapter = PickerAdapter(
            list!!,
            { item: String, position: Int -> onItemClicked(item) },
            { if (it > 0) mBinding.noRecordsTv.gone() else mBinding.noRecordsTv.visible() })
        mBinding.rvItem.run {
            layoutManager = LinearLayoutManager(
                mActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                androidx.recyclerview.widget.DividerItemDecoration(
                    mActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = mAdapter
        }
        mBinding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val text = mBinding.edtSearch.text.toString()
                    .lowercase(Locale.getDefault())
                mAdapter?.filter(text)
            }


            override fun afterTextChanged(editable: Editable) {
            }
        })
    }

    private fun onItemClicked(item: String) {
        listener.itemClick(item, targetRequestCode)
        dismiss()
    }

    private fun clickListener() {
        mBinding.btnNext.setOnClickListener(this)
        mBinding.imgCancel.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        DialogUtils.fullScreenTransDialog(mActivity, dialog)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_next -> {
                mActivity.showToast("Click")
            }
            R.id.img_cancel -> {
                dismiss()
            }
        }
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
}
