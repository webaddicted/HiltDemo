package com.webaddicted.newhiltproject.view.ecatalogue

import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.data.model.other.TileModel
import com.webaddicted.newhiltproject.databinding.RowEcatalogueBinding
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class ECatalogueAdapter(
    private val isPassenger: Boolean = false,
    private val itemList: MutableList<TileModel>,
    val clickListener: (TileModel) -> Unit
) :
    BaseAdapter(R.layout.row_ecatalogue) {
    private lateinit var mBinding: RowEcatalogueBinding

    override fun getListSize() = itemList.size

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowEcatalogueBinding
        val item = itemList[position]
        mBinding.tyreTitletv.text = item.tileName
        val drawableId = item.tileIcon
        mBinding.tyreIconIv.setImageDrawable(mContext.getDrawable(drawableId))
        mBinding.cardView.setOnClickListener { clickListener(item) }
    }

}
