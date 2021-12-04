package com.webaddicted.newhiltproject.view.dashboard

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmTopL3mRetailerBinding
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel

class TopL3MRetailersFragment : BaseFragment(R.layout.frm_top_l3m_retailer) {
    private var l3mRetailersAdapter: L3mRetailersAdapter? = null
    private lateinit var mBinding: FrmTopL3mRetailerBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private var creditList: ArrayList<String>? = null

    companion object {
        val TAG = TopL3MRetailersFragment::class.qualifiedName
        const val BEAT_DATA = "Beat Data"
        fun getInstance(): TopL3MRetailersFragment {
            val bundle = Bundle()
//      bundle.putSerializable(BEAT_DATA, beatRespo)
            val fragment = TopL3MRetailersFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmTopL3mRetailerBinding
//    beatData = arguments?.getSerializable(VisitDetailsFragment.BEAT_DATA) as BeatRespo
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.top_l3m_retailer))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        setUi()
        clickListener()
        creditList = AppConstant.OrderCategory.values().map { it.value } as ArrayList<String>
        setAdapter()
    }


    private fun setUi() {
    }

    private fun clickListener() {
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
//                      result?.data?.strMessage?.let { it1 -> mActivity.showToast(it1) }
//                    }
//                  }
//                })
    }

    private fun setAdapter() {
        l3mRetailersAdapter = L3mRetailersAdapter(creditList)
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
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}