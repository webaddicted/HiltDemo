package com.webaddicted.newhiltproject.view.visit.orderhistory

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmOrderHistoryListBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.common.ValidationHelper
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity

class OrderHistoryListFragment : BaseFragment(R.layout.frm_order_history_list) {
    private var historyList: ArrayList<String>? = null
    private lateinit var orderHistoryAdapter: OrderHistoryListAdapter
    private lateinit var mBinding: FrmOrderHistoryListBinding

    companion object {
        val TAG = OrderHistoryListFragment::class.qualifiedName
        fun getInstance(): OrderHistoryListFragment {
            val bundle = Bundle()
            val fragment = OrderHistoryListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmOrderHistoryListBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.order_history))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        historyList = AppConstant.VisitDetailsItem.values().map { it.name } as java.util.ArrayList<String>
        clickListener()
        setUi()
    }

    private fun clickListener() {
        mBinding.edtFromDate.setOnClickListener(this)
        mBinding.edtToDate.setOnClickListener(this)
        mBinding.filterBtn.setOnClickListener(this)
        mBinding.clearFilterBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.edt_from_date ->
                GlobalUtils.getDate(mActivity, mBinding.edtFromDate)
            R.id.edt_to_date ->
                GlobalUtils.getDate(mActivity, mBinding.edtToDate)
            R.id.filterBtn -> {
                if (validate()) {
                    val fromDate = GlobalUtils.getFormattedDateFromDate(
                        mBinding.edtFromDate.text.toString(),
                        AppConstant.SERVER_DATE_FORMAT, AppConstant.DISPLAY_DATE_FORMAT
                    )
                    val toDate = GlobalUtils.getFormattedDateFromDate(
                        mBinding.edtToDate.text.toString(),
                        AppConstant.SERVER_DATE_FORMAT, AppConstant.DISPLAY_DATE_FORMAT
                    )
                }
            }
            R.id.clearFilterBtn -> {
                mBinding.edtToDate.setText("")
                mBinding.edtFromDate.setText("")
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        if (!ValidationHelper.validateBlank(
                mBinding.edtFromDate,
                mBinding.inputFromDate,
                getString(R.string.select_from_date)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtToDate,
                mBinding.inputToDate,
                getString(R.string.select_to_date)
            )
        ) isValid = false
        return isValid
    }


    private fun setUi() {
        orderHistoryAdapter =
            OrderHistoryListAdapter(historyList) { menuItem: String ->
                onItemClicked(
                    menuItem
                )
            }
        mBinding.itemRv.run {
            layoutManager = LinearLayoutManager(
                mActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = orderHistoryAdapter
        }
        if (historyList == null || historyList?.size == 0) {
//      historyList?.clear()
            mBinding.errorTv.visible()
            return
        }
        mBinding.errorTv.gone()
    }

    private fun onItemClicked(menuItem: String) {
        navigateScreen(OrderHistoryDetailsFragment.TAG, menuItem)
    }

//      private fun getOrderHistory(fDate: String? = null, tDate: String? = null) {
////    if (orderViewModel.appVersionRespo.value?.data?.isSuccess == true) {
////      return
////    }
//            val req = OrderHistoryListReq(beatData?.subDealerCode, fDate, tDate)
//            mBinding.errorTv.gone()
//            orderViewModel.orderHistoryList(req)
//            orderViewModel.orderHistoryListRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<OrderHistoryListRespo>>? ->
//                    if (isFailure) getOrderHistory()
//                    else {
//                      historyList = result?.data?.respDetails
//                      setUi()
//                    }
//                  }
//                })
//          }

    private fun navigateScreen(tag: String?, menuItem: String) {
        var frm: Fragment? = null
        when (tag) {
            OrderHistoryDetailsFragment.TAG -> frm =
                OrderHistoryDetailsFragment.getInstance()
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}