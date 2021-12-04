package com.webaddicted.newhiltproject.view.visit.orderhistory

import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowOrderHistoryListBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class OrderHistoryListAdapter(
      private val itemList: ArrayList<String>?,
      val clickListener: (String) -> Unit
) :
      BaseAdapter(R.layout.row_order_history_list) {
      private lateinit var mBinding: RowOrderHistoryListBinding

      override fun getListSize(): Int? {
            return itemList?.size
          }

      override fun onBindTo(binding: ViewDataBinding, position: Int) {
            mBinding = binding as RowOrderHistoryListBinding
            val item = itemList?.get(position)
            mBinding.txtName.text = "#2586 Customer Name"

            mBinding.txtDate.text = GlobalUtils.getFormattedDateFromDate(
              "2021-11-11",
              AppConstant.DISPLAY_DATE_FORMAT, AppConstant.SERVER_DATE_FORMAT
            )
            mBinding.txtAmount.text = GlobalUtils.getCurrencyFormat(mContext, "25638")
            mBinding.cardView.setOnClickListener { item?.let { it1 -> clickListener(it1) } }
          }
}
