package com.webaddicted.newhiltproject.view.home

import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowHomeItemBinding
import com.webaddicted.newhiltproject.view.base.BaseAdapter
import java.util.*

class HomeAdapter(private val itemList: List<String>, val clickListener: (String) -> Unit) :
      BaseAdapter(R.layout.row_home_item) {
      private lateinit var mBinding: RowHomeItemBinding

      override fun getListSize(): Int {
            return itemList.size
          }


      override fun onBindTo(binding: ViewDataBinding, position: Int) {
            mBinding = binding as RowHomeItemBinding
            val item = itemList[position]
            mBinding.menuItemTv.text = item
            val drawableName = "menu_${
              item.lowercase(Locale.getDefault())
              .replace(" ", "_")
              .replace("-","")
              .replace("&","")
              .replace("(","")
              .replace(")","")}"
            try {
              val drawableId = mContext.resources.getIdentifier(drawableName, "drawable", mContext.packageName)
              val drawable = ContextCompat.getDrawable(mContext, drawableId)
              drawable?.setTint(ContextCompat.getColor(mContext, R.color.appBlue) )
              mBinding.menuIcon.setImageDrawable(drawable)
            }catch(e : Exception) {
              val drawable = ContextCompat.getDrawable(mContext, R.drawable.logo)
              mBinding.menuIcon.setImageDrawable(drawable)
            }
            mBinding.root.setOnClickListener {
              clickListener(item)
            }
          }
}