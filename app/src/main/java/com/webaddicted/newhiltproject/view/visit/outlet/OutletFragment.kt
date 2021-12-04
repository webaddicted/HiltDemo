package com.webaddicted.newhiltproject.view.visit.outlet

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmOutletBinding
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.view.splash.UserTypeFragment

class OutletFragment : BaseFragment(R.layout.frm_outlet) {
    private var outletData: ArrayList<String>? = null
    private var outletAdapter: OutletDashboardAdapter? = null
    private lateinit var tabTitle: List<String>
    private lateinit var mBinding: FrmOutletBinding

    companion object {
        val TAG = OutletFragment::class.qualifiedName
        fun getInstance(): OutletFragment {
            val bundle = Bundle()
            val fragment = OutletFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmOutletBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.outlet_dashboard))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        outletData =
            AppConstant.VisitDetailsItem.values().map { it.name } as java.util.ArrayList<String>
        setUi()
        clickListener()
        setUpStore()
    }

    private fun setUi() {
        mBinding.txtSubdealerName.text = "Sub Dealer Name"
        tabTitle = AppConstant.OutletDashboardTab.values().map { it.value }
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(tabTitle[0]))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(tabTitle[1]))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(tabTitle[2]))
    }

    private fun clickListener() {
        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    mBinding.itemRv.gone()
                    mBinding.cardView.visible()
                    setUpStore()
                } else {
                    mBinding.itemRv.visible()
                    mBinding.cardView.gone()
                    setAdapter(tabTitle[tab.position])
                }
//        viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setUpStore() {
        mBinding.includeSaleTar.txtSaleTarHdr.text = getString(R.string.qty_sales_target)
        mBinding.includeSaleTar.txtSaleTar.text = "589"

        mBinding.includeSaleVal.txtSaleTarHdr.text = getString(R.string.qtd_sales_value)
        mBinding.includeSaleVal.txtSaleTar.text = "556"

        mBinding.includeL3mavg.txtSaleTarHdr.text = getString(R.string.l3m_avg_sales)
        mBinding.includeL3mavg.txtSaleTar.text = "560"

        mBinding.includeMtdsale.txtSaleTarHdr.text = getString(R.string.mtd_sales_val)
        mBinding.includeMtdsale.txtSaleTar.text = "5"

        mBinding.includeNoBill.txtSaleTarHdr.text = getString(R.string.no_of_bill)
        mBinding.includeNoBill.txtSaleTar.text = "6"

        mBinding.includeLppc.txtSaleTarHdr.text = getString(R.string.lppc)
        mBinding.includeLppc.txtSaleTar.text = "506"

        mBinding.includeCrBill.txtSaleTarHdr.text = getString(R.string.total_cr_bill)
        mBinding.includeCrBill.txtSaleTar.text = "46"

        mBinding.includeCrVal.txtSaleTarHdr.text = getString(R.string.total_cr_value)
        mBinding.includeCrVal.txtSaleTar.text = "38"

        mBinding.includeScheme.txtSaleTarHdr.text = getString(R.string.no_of_scheme)
        mBinding.includeScheme.txtSaleTar.text = "256"

        mBinding.includeLpd.txtSaleTarHdr.text = getString(R.string.retailer_lpd)
        mBinding.includeLpd.txtSaleTar.text = "50"

    }

    private fun setAdapter(selectTab: String) {
        outletAdapter = OutletDashboardAdapter(selectTab, outletData)
        mBinding.itemRv.run {
            layoutManager = LinearLayoutManager(
                mActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = outletAdapter
        }
    }

//      private fun outletDashboard() {
//            visitViewModel.outletDashboard()
//            visitViewModel.outletDashboardRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonRespo<OutletDashboardRespo>>? ->
//                    if (isFailure) outletDashboard()
//                    else {
//                      if (it.data?.isSuccess == true) {
//                        outletData = it.data.respDetails
//                        setUpStore()
//                      } else {
//                        it.data?.strMessage?.let { it1 ->
//                          DialogUtils.showDialog(
//                            mActivity,
//                            getString(R.string.error),
//                            it1
//                          )
//                        }
//                      }
//                    }
//                  }
//                })
//          }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
//      ZoomImageFrm.TAG -> frm = ZoomImageFrm.getInstance(bundle)
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}