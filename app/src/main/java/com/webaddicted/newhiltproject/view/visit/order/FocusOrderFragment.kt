package com.webaddicted.newhiltproject.view.visit.order

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmOrderBinding
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import java.util.*

class FocusOrderFragment : BaseFragment(R.layout.frm_order) {
    private var orderList: ArrayList<String>? = null
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var mBinding: FrmOrderBinding
//      private val orderViewModel: OrderViewModel by activityViewModels()

    companion object {
        val TAG = FocusOrderFragment::class.qualifiedName
        val BEAT_DATA = "Beat Data"
        fun getInstance(): FocusOrderFragment {
            val bundle = Bundle()
            val fragment = FocusOrderFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmOrderBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.focus_order))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        orderList =
            AppConstant.VisitDetailsItem.values().map { it.name } as ArrayList<String>
        setUi()
        clickListener()
    }

    private fun setUi() {
        isOrderableProduct()
        orderAdapter =
            OrderAdapter(orderList, {
                if (!it) {
                    mBinding.loadingTyreIv.gone()
                } else mBinding.loadingTyreIv.visible()
            }, {
                if (it > 0) mBinding.errorTv.gone() else mBinding.errorTv.visible()
            })
        mBinding.itemRv.run {
            layoutManager = LinearLayoutManager(
                mActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = orderAdapter
        }
    }

    private fun isOrderableProduct() {
        //    isOrderable false then loose product not show in list
        orderList =
            orderList?.filter { s -> s == "Deep" } as ArrayList<String>?
    }

    private fun clickListener() {
        mBinding.btnNext.setOnClickListener(this)
        mBinding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val text = mBinding.edtSearch.text.toString()
                    .lowercase(Locale.getDefault())
                orderAdapter.filter(text)
            }


            override fun afterTextChanged(editable: Editable) {
            }
        })
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_next -> navigateScreen(Top15OrderFragment.TAG)
        }
    }

//      private fun getAllOrder() {
//            if (orderViewModel.allOrderRespo.value?.data?.isSuccess == true) {
//                  orderList = getFocusOrder(orderViewModel.allOrderRespo.value?.data?.respDetails)
//                  if (orderList != null && orderList?.size!! > 0) {
//                    setUi()
//                  } else {
//                    mBinding.errorTv.visible()
//                  }
//                  setUi()
//                  return
//                }
//            orderViewModel.allOrderList(beatData?.subDealerId,beatData?.inventoryLocation)
//            orderViewModel.allOrderRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<OrderRespo>>? ->
//                    if (isFailure) getAllOrder()
//                    else {
//                      orderList = getFocusOrder(it.data?.respDetails)
//                      if (orderList != null && orderList?.size!! > 0) {
//                        setUi()
//                      } else {
//                        mBinding.errorTv.visible()
//                      }
//                    }
//                  }
//                })
//          }


//      private fun updateTotalProduct() {
//            var totalAmt = 0.0
//            var size = 0
//            for (item in orderViewModel.invOrderDetails) {
//                  if (item.parentSKU == "") {
//                    totalAmt += (item.sellRate?.toDouble()
//                      ?.let { item.orderQty?.toDouble()?.times(it) }!!)
//                    size++
//                  }
//                }
//            mBinding.txtTotalItem.text =
//              getString(R.string.total_selected_product_value, size.toString())
//            mBinding.txtAmt.text = GlobalUtils.getCurrencyFormat(
//              mActivity, totalAmt.toString()
//            )?.split(".")?.get(0)
//
//          }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            Top15OrderFragment.TAG -> frm = Top15OrderFragment.getInstance()
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }


}
