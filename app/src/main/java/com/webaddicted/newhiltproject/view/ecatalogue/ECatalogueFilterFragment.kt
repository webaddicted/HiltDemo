package com.webaddicted.newhiltproject.view.ecatalogue

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.data.model.other.TileModel
import com.webaddicted.newhiltproject.databinding.FrmEcatalogueFilterBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.dialog.CompareDialog
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel

class ECatalogueFilterFragment : BaseFragment(R.layout.frm_ecatalogue_filter),
    AdapterView.OnItemSelectedListener {
    private var list: ArrayList<String>? = null
    private lateinit var mBinding: FrmEcatalogueFilterBinding
    private val catalogueViewModel: HomeViewModel by viewModels()
    private var tileModel: TileModel? = null
    private var mAdapter: ECatalogueFilterAdapter? = null
    private var mManager: GridLayoutManager? = null


    companion object {
        val TAG = ECatalogueFilterFragment::class.qualifiedName
        const val GET_SELECTED_TILE = "Get Tile"
        fun getInstance(tile: TileModel): ECatalogueFilterFragment {
            val fragment = ECatalogueFilterFragment()
            val bundle = Bundle()
            bundle.putSerializable(GET_SELECTED_TILE, tile)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmEcatalogueFilterBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.ecatalogue))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        tileModel = arguments?.getSerializable(GET_SELECTED_TILE) as TileModel
        setUpList()
        setOnClickListeners()
        setOnItemSelectListeners(true)
        //setUpObservers()
        mBinding.ecatFilterRV.isNestedScrollingEnabled = false
        list  = AppConstant.OrderCategory.values().map { it.value } as ArrayList<String>
    }

    private fun setOnClickListeners() {
        mBinding.searchButton.setOnClickListener(this)
        mBinding.filterBtn.setOnClickListener(this)
        mBinding.clearFilterBtn.setOnClickListener(this)
    }

    private fun setOnItemSelectListeners(isEnable: Boolean) {
        try {
            if (isEnable) {
                mBinding.seasonSpinner.onItemSelectedListener = this
                mBinding.vehiclesSp.onItemSelectedListener = this
                mBinding.modelsSp.onItemSelectedListener = this
                mBinding.fitmentSp.onItemSelectedListener = this
                mBinding.haulTypSp.onItemSelectedListener = this
                mBinding.applicationSp.onItemSelectedListener = this
                mBinding.hpSp.onItemSelectedListener = this
                mBinding.needSp.onItemSelectedListener = this
            } else {
                mBinding.seasonSpinner.onItemSelectedListener = null
                mBinding.vehiclesSp.onItemSelectedListener = null
                mBinding.modelsSp.onItemSelectedListener = null
                mBinding.fitmentSp.onItemSelectedListener = null
                mBinding.haulTypSp.onItemSelectedListener = null
                mBinding.applicationSp.onItemSelectedListener = null
                mBinding.hpSp.onItemSelectedListener = null
                mBinding.needSp.onItemSelectedListener = null
            }
        } catch (e: Exception) {
        }
    }

    private fun setUpList() {
        mManager = GridLayoutManager(activity, 2)
        mBinding.ecatFilterRV.layoutManager = mManager
        activity?.baseContext?.let {
            mAdapter = list?.let { it1 ->
                ECatalogueFilterAdapter(mActivity, it1,
                    { item -> onItemClicked(item) }) { item, position ->
                    onCompareClicked(
                        item,
                        position
                    )
                }
            }
        }
        mBinding.ecatFilterRV.adapter = mAdapter
    }

    private fun onItemClicked(item: String) {
        navigateScreen(ECatalogueTyreDetailsFragment.TAG)
    }

    private fun onCompareClicked(item: String, position: Int) {
        showCompareDialog(AppConstant.HomeItem.values().map { it.value } as ArrayList<String>)
    }

    private fun showCompareDialog(result: ArrayList<String>) {
        val fragment = CompareDialog.dialog(result[0], result[1], "Tile Type")
        fragment.setTargetFragment(this, 1)
        fragment.show(requireActivity().supportFragmentManager, "ecat_type_fragment")
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            mBinding.searchButton.id -> {
                activity?.baseContext?.let { GlobalUtils.hideKeyboardFrom(it, v) }
            }
            mBinding.filterBtn.id -> {
            }
            R.id.clearFilterBtn -> {
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view != null && view is TextView) {
            view.setTextColor(ContextCompat.getColor(mActivity, R.color.appBlue))
        }
    }


    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            ECatalogueTyreDetailsFragment.TAG -> frm = ECatalogueTyreDetailsFragment.getInstance()
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}