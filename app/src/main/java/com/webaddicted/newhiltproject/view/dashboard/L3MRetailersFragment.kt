package com.webaddicted.newhiltproject.view.dashboard

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmL3mRetailerBinding
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel

class L3MRetailersFragment : BaseFragment(R.layout.frm_l3m_retailer) {
    private var retailerList: ArrayList<String>? = null
    private var l3mRetailersAdapter: L3mRetailersAdapter? = null
    private lateinit var mBinding: FrmL3mRetailerBinding
    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        val TAG = L3MRetailersFragment::class.qualifiedName
        const val BEAT_DATA = "Beat Data"
        fun getInstance(): L3MRetailersFragment {
            val bundle = Bundle()
//      bundle.putSerializable(BEAT_DATA, beatRespo)
            val fragment = L3MRetailersFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmL3mRetailerBinding
//    beatData = arguments?.getSerializable(VisitDetailsFragment.BEAT_DATA) as BeatRespo
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.l3m_retailer))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        retailerList = AppConstant.OrderCategory.values().map { it.value } as ArrayList<String>
        setUi()
        clickListener()
        l3mRetailer()
        setAdapter()
    }


    private fun setUi() {
    }

    private fun clickListener() {
        mBinding.nextFabBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.next_fabBtn -> {
                navigateScreen(RetailerCreditWiseFragment.TAG)
            }
        }
    }


    private fun l3mRetailer() {
//            homeViewModel.l3mRetailer("")
//            homeViewModel.l3MRetailerRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<L3MRetailerRespo>>? ->
//                    if (isFailure) l3mRetailer()
//                    else {
//                      retailerList = result?.data?.respDetails
//                      setAdapter()
//                    }
//                  }
//                })
    }

    private fun setAdapter() {
        l3mRetailersAdapter = L3mRetailersAdapter(retailerList)
        mBinding.itemRv.run {
            layoutManager = LinearLayoutManager(
                mActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = l3mRetailersAdapter
        }
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            RetailerCreditWiseFragment.TAG -> frm = RetailerCreditWiseFragment.getInstance()
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}
