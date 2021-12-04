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
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import java.util.*

class Top15OrderFragment : BaseFragment(R.layout.frm_order) {
    private var orderList: ArrayList<String>? = null
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var mBinding: FrmOrderBinding
//      private val orderViewModel: OrderViewModel by activityViewModels()

    companion object {
        val TAG = Top15OrderFragment::class.qualifiedName
        val BEAT_DATA = "Beat Data"
        fun getInstance(): Top15OrderFragment {
            val bundle = Bundle()
            val fragment = Top15OrderFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmOrderBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.top15_order))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        orderList =
            AppConstant.VisitDetailsItem.values().map { it.name } as ArrayList<String>
        setUi()
        clickListener()
        updateTotalProduct()
    }

    private fun setUi() {
        orderAdapter =
            OrderAdapter(orderList, {
                if (!it) {
                    updateTotalProduct()
                    mBinding.loadingTyreIv.gone()
                } else mBinding.loadingTyreIv.visible()
            }, { if (it > 0) mBinding.errorTv.gone() else mBinding.errorTv.visible() })
        mBinding.itemRv.run {
            layoutManager = LinearLayoutManager(
                mActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = orderAdapter
        }
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
            R.id.btn_next -> navigateScreen(AllOrderFragment.TAG)
        }
    }

//      private fun getAllOrder() {
//            if (orderViewModel.allOrderRespo.value?.data?.isSuccess == true) {
//                  orderList = getTop15Order(orderViewModel.allOrderRespo.value?.data?.respDetails)
//                  if(orderList!=null && orderList?.size!!>0) {
//                    setUi()
//                  }else{
//                    mBinding.errorTv.visible()
//                  }
//                  return
//                }
//
//            orderViewModel.allOrderList(beatData?.subDealerId,beatData?.inventoryLocation)
//            orderViewModel.allOrderRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<OrderRespo>>? ->
//                    if (isFailure) getAllOrder()
//                    else {
//                      orderList = getTop15Order(it.data?.respDetails)
//                      if(orderList!=null && orderList?.size!!>0) {
//                        setUi()
//                      }else{
//                        mBinding.errorTv.visible()
//                      }
//                    }
//                  }
//                })
//          }

    private fun updateTotalProduct() {
        val totalAmt = 12000.0
        val size = 2
        mBinding.txtTotalItem.text =
            getString(R.string.total_selected_product_value, size.toString())
//    mBinding.txtAmt.text = getString(R.string.rupee_symbol_amt, totalAmt.toString())
        mBinding.txtAmt.text = GlobalUtils.getCurrencyFormat(
            mActivity, totalAmt.toString()
        )?.split(".")?.get(0)
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            AllOrderFragment.TAG -> frm = AllOrderFragment.getInstance()
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}