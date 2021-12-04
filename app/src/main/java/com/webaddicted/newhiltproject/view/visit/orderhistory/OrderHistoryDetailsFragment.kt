package com.webaddicted.newhiltproject.view.visit.orderhistory

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmOrderHistoryDetailBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.dashboard.MTDDashboardFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity

class OrderHistoryDetailsFragment : BaseFragment(R.layout.frm_order_history_detail) {
    private var prodItemList: List<String>? = null
    private var orderItem: String? = null
    private lateinit var orderHistoryAdapter: OrderHistoryDetailAdapter
    private lateinit var mBinding: FrmOrderHistoryDetailBinding

    companion object {
        val TAG = OrderHistoryDetailsFragment::class.qualifiedName

        //            const val ORDER_HISTORYLIST_RESPO = "orderHistoryListRespo"
        fun getInstance(): OrderHistoryDetailsFragment {
            val fragment = OrderHistoryDetailsFragment()
            val bundle = Bundle()
//                  bundle.putSerializable(ORDER_HISTORYLIST_RESPO, orderHistoryListRespo)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmOrderHistoryDetailBinding
//            orderItem = arguments?.getSerializable(ORDER_HISTORYLIST_RESPO) as OrderHistoryListRespo
        (mActivity as HomeActivity).setActionBarTitle("#25863 Customer Name")
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        prodItemList =
            AppConstant.VisitDetailsItem.values().map { it.name } as java.util.ArrayList<String>
        setUi()
        setAdapterUi()
    }

    private fun setAdapterUi() {
        if (prodItemList == null || prodItemList?.size == 0) {
            mBinding.errorTv.visible()
            return
        }
        mBinding.errorTv.gone()
        orderHistoryAdapter =
            OrderHistoryDetailAdapter(prodItemList) { menuItem: String ->
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
    }

    private fun onItemClicked(menuItem: String) {
//    navigateScreen()
    }

    private fun setUi() {
        mBinding.txtDate.text = GlobalUtils.getFormattedDateFromDate(
            "2021-12-10",
            AppConstant.DISPLAY_DATE_FORMAT, AppConstant.SERVER_DATE_FORMAT
        )
        mBinding.txtSubDealerName.text = "Customer Name"
        mBinding.txtRouteName.text = "Route Name"
        mBinding.txtOrderQty.text = "586"
        mBinding.txtTotalPrice.text = GlobalUtils.getCurrencyFormat(mActivity, "258364")
    }


    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            MTDDashboardFragment.TAG -> frm = MTDDashboardFragment.getInstance()
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}
