package com.webaddicted.newhiltproject.view.visit.order

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

class AllOrderFragment : BaseFragment(R.layout.frm_order) {
    private var spinnerAdapter: ArrayAdapter<String>? = null
    private var orderList: ArrayList<String>? = arrayListOf()
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var mBinding: FrmOrderBinding
//      private val orderViewModel: OrderViewModel by activityViewModels()

    companion object {
        val TAG = AllOrderFragment::class.qualifiedName
        fun getInstance(): AllOrderFragment {
            val bundle = Bundle()
            val fragment = AllOrderFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmOrderBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.all_order))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        orderList =
            AppConstant.VisitDetailsItem.values().map { it.name } as ArrayList<String>
        val data = AppConstant.OrderCategory.values().map { it.value }
        spinnerAdapter = ArrayAdapter(
            mActivity,
            R.layout.row_spinner_txt,
            data
        )
        spinnerAdapter?.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        mBinding.categorySpinner.adapter = spinnerAdapter
        setUi()
        clickListener()
        updateTotalProduct()
    }

    private fun setUi() {
        mBinding.edtSearch.hint = getString(R.string.smart_search)
        mBinding.linear.visible()
        if (orderList != null && orderList?.size!! == 0) {
            mBinding.errorTv.visible()
        } else mBinding.errorTv.gone()
        orderAdapter =
            OrderAdapter(orderList, {
                if (!it) {
                    updateTotalProduct()
                    mBinding.loadingTyreIv.gone()

                } else {
                    GlobalUtils.showLoader(mActivity, mBinding.loadingTyreIv)
                }
            },
                { if (it > 0) mBinding.errorTv.gone() else mBinding.errorTv.visible() })
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
        mBinding.errorTv.setOnClickListener(this)
        mBinding.btnNext.setOnClickListener(this)
        mBinding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    mBinding.edtSearch.setText("")
                    val selectedItem: String = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
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
            R.id.errorTv -> {
            }
            R.id.btn_next -> {
                navigateScreen(OrderSummaryFragment.TAG)
            }
        }
    }


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
            OrderSummaryFragment.TAG -> frm = OrderSummaryFragment.getInstance()
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}
