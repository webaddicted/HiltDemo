package com.webaddicted.newhiltproject.view.visit.order

import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowCollectionBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class CollectionAdapter(
    private val itemList: ArrayList<String>?,
    val clickListener: (String?) -> Unit
) :
    BaseAdapter(R.layout.row_collection) {
    private lateinit var mBinding: RowCollectionBinding

    override fun getListSize(): Int? {
        return itemList?.size
    }


    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowCollectionBinding
        val item = itemList?.get(position)
        mBinding.modeTv.text = "Cheque No"
        mBinding.totalAmtTv.text = GlobalUtils.getCurrencyFormat(
            mContext, "15586"
        )//mContext.getString(R.string.rupee_symbol_amt, "15,111")
        if(position==0||position==1){
            mBinding.bankNameTv.visible()
            mBinding.chequeNoTv.visible()
            mBinding.chequeExpireTv.visible()
            mBinding.bankNameTv.text = "Axis Bank"
            mBinding.chequeNoTv.text = "Check No : 21513131313"
            mBinding.chequeExpireTv.text = "Check expiry Date : 25/Dec/2021"
        }else{
            mBinding.bankNameTv.gone()
            mBinding.chequeNoTv.gone()
            mBinding.chequeExpireTv.gone()
        }
        mBinding.root.setOnClickListener {
            item?.let { it1 -> clickListener(it1) }
        }
    }
}
