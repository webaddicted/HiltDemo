package com.webaddicted.newhiltproject.view.dialog

import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowPickerBinding
import com.webaddicted.newhiltproject.view.base.BaseAdapter
import java.util.*
import kotlin.collections.ArrayList

class PickerAdapter(private var itemList: ArrayList<String>, val clickListener: (String, Int) -> Unit,
                    val listSizeListener: (Int) -> Unit) :
    BaseAdapter(R.layout.row_picker) {
    private lateinit var mBinding: RowPickerBinding
    private var searchText: String? = null
    private val searchArray: List<String>

    init {
        this.searchArray = ArrayList()
        itemList.let { this.searchArray.addAll(it) }
    }

    override fun getListSize()= itemList.size

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowPickerBinding
        val item = itemList[position]
        mBinding.menuItemTv.text = item
        mBinding.root.setOnClickListener {
            clickListener(item, position)
        }
    }

    fun notifyAdapter(list: ArrayList<String>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    fun filter(textStr: String?) {
        val charText = textStr!!.toLowerCase(Locale.getDefault())
        searchText = charText
        itemList.clear()
        if (charText == null && charText.isBlank()) {
            itemList.addAll(searchArray)
        } else {
            for (wp in searchArray) {
                if (wp.toLowerCase(Locale.getDefault()).contains(charText)) {
                    itemList.add(wp)
                }
                //                else if (wp.toLowerCase(Locale.getDefault()).contains(charText)) {
                //                    mAction.add(wp);
                //                }
            }
        }
        itemList.size.let { listSizeListener(it) }
        notifyDataSetChanged()
    }
}
