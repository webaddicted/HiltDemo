package com.webaddicted.newhiltproject.view.ecatalogue

import android.app.Activity
import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowEcatalogueFilterBinding
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class ECatalogueFilterAdapter(
    private val activity: Activity,
    private var itemList: List<String>,
    val clickListener: (String) -> Unit,
    val compareClickListener: (String, pos: Int) -> Unit
) :
    BaseAdapter(R.layout.row_ecatalogue_filter) {
    private lateinit var mBinding: RowEcatalogueFilterBinding

    override fun getListSize() = itemList.size

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowEcatalogueFilterBinding
        val patternList = itemList[position]
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
