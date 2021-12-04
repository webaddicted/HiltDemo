package com.webaddicted.newhiltproject.view.ecatalogue

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmEcatalogueTyreDetailsBinding
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel

class ECatalogueTyreDetailsFragment : BaseFragment(R.layout.frm_ecatalogue_tyre_details) {

    private lateinit var mBinding: FrmEcatalogueTyreDetailsBinding
    private val catalogueViewModel: HomeViewModel by viewModels()

    companion object {
        val TAG = ECatalogueTyreDetailsFragment::class.qualifiedName
        const val REQUESTMAP = "RequestMap"
        fun getInstance(): ECatalogueTyreDetailsFragment {
            val fragment = ECatalogueTyreDetailsFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }



    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmEcatalogueTyreDetailsBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.ecatalogue))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        clickListener()
        getData()
        setUpData()
    }

    private fun clickListener() {
        mBinding.playCv.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.playCv -> {
            }
        }
    }

    private fun getData() {
//        catalogueViewModel.requestCompareTyres(requestPattern = arrayListOf(requestPattern))
//        catalogueViewModel.compareTypeRespo.observe(this, {
//            handleApiRespo(
//                it,
//                mBinding.loadingTyreIv
//            ) { isFailure: Boolean, result: ApiResponse<CommonListRespo<CompareTyreResponse>>? ->
//                if (isFailure) getData()
//                else {
//                    tyreRes = it.data?.respDetails
//                    setUpData()
//                }
//
//            }
//        })
    }

    private fun setUpData() {
        mBinding.taglineCv.visibility = View.VISIBLE
        mBinding.taglineTv.text = "TagLine"
        mBinding.patterNameTv.text = "PatternName"
        mBinding.descTv.text = "Description"
        mBinding.playCv.visibility = View.VISIBLE
    }


    private fun navigateScreen(tag: String?, videoURL: String, patternName: String) {
//        var frm: Fragment? = null
//        when (tag) {
//            YoutubeFragment.TAG -> frm = YoutubeFragment.getInstance(videoURL, patternName)
//        }
//        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}
