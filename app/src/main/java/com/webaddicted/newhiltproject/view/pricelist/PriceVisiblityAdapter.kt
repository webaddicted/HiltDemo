package com.webaddicted.newhiltproject.view.pricelist

import android.view.View
import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowPriceVisiblityBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.getCurrencyFormat
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class PriceVisiblityAdapter(
    private val showPostTax: Boolean = false,
    private val itemList: ArrayList<String>?,
    val clickListener: (String) -> Unit
) :
    BaseAdapter(R.layout.row_price_visiblity) {
    private lateinit var mBinding: RowPriceVisiblityBinding

    override fun getListSize() = itemList?.size

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowPriceVisiblityBinding
        val item = itemList?.get(position)
        mBinding.materialDescTv.text = String.format(
            mContext.resources.getString(R.string.value_hyphen_value),
            "Test",
            "Value"
        )
        if (showPostTax) {
            mBinding.npbWithTaxDescTv.text = getCurrencyFormat(mContext, "1531")
            mBinding.npbWithTaxDescTv.visibility = View.VISIBLE
            mBinding.npbWithTaxTv.visibility = View.VISIBLE

            mBinding.flapTv.visibility = View.GONE
            mBinding.flapDescTv.visibility = View.GONE

            mBinding.tubeTv.visibility = View.GONE
            mBinding.tubeDescTv.visibility = View.GONE

            mBinding.tyrePriceTv.visibility = View.GONE
            mBinding.tyrePriceDescTv.visibility = View.GONE

            mBinding.setTv.visibility = View.GONE
            mBinding.setDescTv.visibility = View.GONE
        } else {
            mBinding.tyrePriceDescTv.text = getCurrencyFormat(mContext, "313")
            mBinding.tubeDescTv.text = getCurrencyFormat(mContext, "1315")
            mBinding.flapDescTv.text = getCurrencyFormat(mContext, "888")
            mBinding.setDescTv.text = getCurrencyFormat(mContext, "3153")

            mBinding.setTv.visibility = View.VISIBLE
            mBinding.setDescTv.visibility = View.VISIBLE

            mBinding.npbWithTaxDescTv.visibility = View.GONE
            mBinding.npbWithTaxTv.visibility = View.GONE
        }
//        mBinding.custNameTv.text = item?.subDealerName
//        mBinding.monthlySaleTv.text = item?.monthSales.toString()
//        mBinding.pendingBillTv.text = item?.pendingBillCount.toString()
//        mBinding.mobileTv.text = item?.mobile
//        mBinding.pendingAmtTv.text = item?.pendingAmt.toString()
//        mBinding.statusIndicatorVw.backgroundTintList = ContextCompat.getColorStateList(
//                mContext,
//                if (item?.beatStatus!!) R.color.green else R.color.red_color
//        )
//        mBinding.imgLocation.setImageResource(if (item.locationStatus!!) R.drawable.ic_location else R.drawable.ic_location_off)
//        mBinding.imgKyc.setImageResource(if (item.kycStatus!!) R.drawable.ic_kyc else R.drawable.ic_kyc_not)
////        mBinding.imgBadge.setImageResource(if(item.ba!!)R.drawable.ic_location else R.drawable.ic_location_off)
//        mContext.showCircleDrawable(item.badgeIcon, mBinding.imgBadge)
//        onClickListener(mBinding, mBinding.callIv, position)
//        onClickListener(mBinding, mBinding.whatsAppBtn, position)
//        onClickListener(mBinding, mBinding.cardView, position)
    }

}