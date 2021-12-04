package com.webaddicted.newhiltproject.view.home

import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowMenuItemBinding
import com.webaddicted.newhiltproject.view.base.BaseAdapter
import java.util.*

class DrawerAdapter(private val itemList: List<String>, val clickListener: (String) -> Unit) :
      BaseAdapter(R.layout.row_menu_item) {
      private lateinit var mBinding: RowMenuItemBinding

      override fun getListSize(): Int {
            return itemList.size
          }


      override fun onBindTo(binding: ViewDataBinding, position: Int) {
            mBinding = binding as RowMenuItemBinding
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