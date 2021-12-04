package com.webaddicted.newhiltproject.view.visit.beat

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.data.model.home.BeatRespo
import com.webaddicted.newhiltproject.databinding.RowVisitBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.makePhoneCall
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.onWhatsAppClick
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showCircleDrawable
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseActivity
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class VisitAdapter(
      private val activity: BaseActivity,
      private val itemList: ArrayList<String>?,
      val clickListener: (String) -> Unit
) :
      BaseAdapter(R.layout.row_visit) {
      private lateinit var mBinding: RowVisitBinding

      override fun getListSize() = itemList?.size


      override fun onBindTo(binding: ViewDataBinding, position: Int) {
            mBinding = binding as RowVisitBinding
            val item = itemList?.get(position)
            if (position == 1||position == 0||position == 2) {
              mBinding.custNameTv.setTextColor(mContext.getColor(R.color.gray_818181))
            } else {
              mBinding.custNameTv.setTextColor(mContext.getColor(R.color.appBlue))
            }
            mBinding.custNameTv.text = "SubDealer Name"
            mBinding.monthlySaleTv.text = "256"
            mBinding.pendingBillTv.text = "5"
            mBinding.mobileTv.text = "9024061407"
            mBinding.pendingAmtTv.text = "28,920"
            mBinding.statusIndicatorVw.backgroundTintList = ContextCompat.getColorStateList(
              mContext,
              if (position == 1||position == 0||position == 2) R.color.green else R.color.red_color
            )
            mBinding.imgLocation.setImageResource(if (position == 1||position == 0||position == 2) R.drawable.ic_location else R.drawable.ic_location_off)
            mBinding.imgKyc.setImageResource(if (position == 1||position == 0||position == 2) R.drawable.ic_kyc else R.drawable.ic_kyc_not)
//    mBinding.imgBadge.setImageResource(if(item.ba!!)R.drawable.ic_location else R.drawable.ic_location_off)
            mContext.showCircleDrawable("logo", mBinding.imgBadge)
            onClickListener(mBinding, mBinding.callIv, position)
            onClickListener(mBinding, mBinding.whatsAppBtn, position)
            onClickListener(mBinding, mBinding.cardView, position)
          }

      override fun getClickEvent(mRowBinding: ViewDataBinding, view: View?, position: Int) {
            super.getClickEvent(mRowBinding, view, position)
            val item = itemList?.get(position)
            when (view?.id) {
              R.id.callIv -> mContext.makePhoneCall("9024061407")
              R.id.whatsAppBtn -> mContext.onWhatsAppClick(activity, "9024061407")
              R.id.card_view -> item?.let { clickListener(it) }
            }
          }
}