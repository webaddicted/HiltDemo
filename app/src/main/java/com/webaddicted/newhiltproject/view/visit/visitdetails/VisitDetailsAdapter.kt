package com.webaddicted.newhiltproject.view.visit.visitdetails


import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowHomeItemBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showCircleDrawable
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showDrawable
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class VisitDetailsAdapter(
    private val badgeName: String?,
    private val itemList: List<String>?,
    val clickListener: (String) -> Unit
) :
    BaseAdapter(R.layout.row_home_item) {
    private lateinit var mBinding: RowHomeItemBinding

    override fun getListSize(): Int? {
        return itemList?.size
    }

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowHomeItemBinding
        val item = itemList?.get(position)
        mBinding.menuItemTv.text = item
        if (item?.equals(AppConstant.VisitDetailsItem.BADGE.value) == true) {
            mBinding.menuItemTv.text = badgeName?.capitalize()
            mContext.showCircleDrawable(badgeName, mBinding.menuIcon)
        } else
            mContext.showDrawable(item, mBinding.menuIcon)
        if (position == 0 || position == 1) mBinding.imgComplete.visible()
        else mBinding.imgComplete.gone()
        mBinding.root.setOnClickListener {
            clickListener(item.toString())
        }
    }
}
