package com.webaddicted.newhiltproject.view.visit.order

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.data.model.home.BeatRespo
import com.webaddicted.newhiltproject.databinding.FrmOrderBinding
import com.webaddicted.newhiltproject.utils.common.DialogUtils
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.common.GlobalUtils.showToast
import com.webaddicted.newhiltproject.utils.common.gone
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.ApiConstant
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import java.util.*

class OrderSummaryFragment : BaseFragment(R.layout.frm_order) {
    private var totalSelectedProduct: Int = 0
    private var productTotalAmt: Double = 0.0
    private var totalAmt: Double = 0.0
    private var taxAmt: Double = 0.0
    private var orderList: ArrayList<String>? = arrayListOf()
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var mBinding: FrmOrderBinding
//      private val orderViewModel: OrderViewModel by activityViewModels()

    companion object {
        val TAG = OrderSummaryFragment::class.qualifiedName
        const val BEAT_DATA = "Beat Data"
        fun getInstance(): OrderSummaryFragment {
            val bundle = Bundle()
            val fragment = OrderSummaryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmOrderBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.order_summary))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        orderList =
            AppConstant.SelectableLanguage.values().map { it.name } as ArrayList<String>
        setUi()
        clickListener()
        mBinding.btnNext.text = getString(R.string.submit)
        checkBillAmount()
    }

    private fun setUi() {
        orderAdapter =
            OrderAdapter(orderList, {
                if (!it) {
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
        mBinding.errorTv.setOnClickListener(this)
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
            R.id.errorTv -> {

            }
            R.id.btn_next -> {
                DialogUtils.getDialogInstance(
                    mActivity,
                    getString(R.string.submit_order),
                    getString(R.string.are_you_sure_you_want_to_confirm_order),
                    getString(R.string.order_now),
                    getString(R.string.cancel),
                    { dialog, which ->

                        val fragmentManager: FragmentManager? = fragmentManager
//                            fragmentManager?.getBackStackEntryAt(fragmentManager.backStackEntryCount - 4)?.id?.let { fragmentManager.popBackStack(it, FragmentManager.POP_BACK_STACK_INCLUSIVE) }
                        fragmentManager?.popBackStack (FocusOrderFragment::class.java.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    },
                    { dialog, which -> dialog.dismiss() })
            }
        }
    }

    private fun checkBillAmount() {
        productTotalAmt = 12000.0
        taxAmt = 8000.0
        totalAmt = 20000.0
        productTotalAmt = String.format("%.2f", productTotalAmt).toDouble()
        taxAmt = String.format("%.2f", taxAmt).toDouble()
        totalAmt = String.format("%.2f", totalAmt).toDouble()

        mBinding.txtProdItem.text = getString(
            R.string.total_product_value,
            "2"
        )
        mBinding.txtProdItem.visible()
        mBinding.txtProdAmt.visible()
        mBinding.txtTaxItem.visible()
        mBinding.txtTaxAmt.visible()
        mBinding.txtTotalItem.visible()
        mBinding.txtAmt.visible()
        GlobalUtils.logPrint(
            msg =
            " productTotalAmt $productTotalAmt taxAmt $taxAmt totalAmt $totalAmt"
        )
        mBinding.txtProdItem.text =
            getString(R.string.total_product_value, totalSelectedProduct.toString())
//      mBinding.txtProdAmt.text =
//        getString(R.string.rupee_symbol_amt, productTotalAmt.toString())
        mBinding.txtProdAmt.text = GlobalUtils.getCurrencyFormat(
            mActivity, productTotalAmt.toString()
        )?.split(".")?.get(0)
        mBinding.txtTaxItem.text = getString(R.string.tax_value)
//      mBinding.txtTaxAmt.text = getString(R.string.rupee_symbol_amt, taxAmt.toString())
        mBinding.txtTaxAmt.text = GlobalUtils.getCurrencyFormat(
            mActivity, taxAmt.toString()
        )?.split(".")?.get(0)
        mBinding.txtTotalItem.text =
            getString(R.string.total_amt_value, totalSelectedProduct.toString())
//      mBinding.txtAmt.text = getString(R.string.rupee_symbol_amt, totalAmt.toString())
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
