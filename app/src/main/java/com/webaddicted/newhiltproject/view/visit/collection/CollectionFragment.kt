package com.webaddicted.newhiltproject.view.visit.order

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.FrmCollectionBinding
import com.webaddicted.newhiltproject.utils.common.*
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseFragment
import com.webaddicted.newhiltproject.view.home.HomeActivity
import com.webaddicted.newhiltproject.viewmodel.HomeViewModel

class CollectionFragment : BaseFragment(R.layout.frm_collection) {
    private var branch: String? = ""
    private var bankAccNumber: String? = ""
    private var bankInfo: ArrayList<String>? = null
    private lateinit var collectionAdapter: CollectionAdapter
    private lateinit var mBinding: FrmCollectionBinding
    private val visitViewModel: HomeViewModel by viewModels()
    private var collectionReq: ArrayList<String>? = arrayListOf()
    private var isCollectionCash: Boolean = true

    companion object {
        val TAG = CollectionFragment::class.qualifiedName
        fun getInstance(): CollectionFragment {
            val bundle = Bundle()
            val fragment = CollectionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmCollectionBinding
        (mActivity as HomeActivity).setActionBarTitle(getString(R.string.retailer_collection))
        (mActivity as HomeActivity).setActionBarBackIcon(true)
        (mActivity as HomeActivity).lockDrawer(true)
        bankInfo =
            AppConstant.SelectableLanguage.values().map { it.value } as ArrayList<String>
        collectionReq =
            AppConstant.SelectableLanguage.values().map { it.value } as ArrayList<String>
        setUi()
        clickListener()
    }

    private fun setUi() {
        collectionAdapter =
            CollectionAdapter(collectionReq) { item: String? -> onItemClicked(item) }
        mBinding.itemRv.run {
            layoutManager = LinearLayoutManager(
                mActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = collectionAdapter
        }
    }

    private fun clickListener() {
        mBinding.btnAdd.setOnClickListener(this)
        mBinding.btnSubmit.setOnClickListener(this)
        mBinding.edtBankName.setOnClickListener(this)
        mBinding.edtBankName.setOnClickListener(this)
        mBinding.edtChequeDate.setOnClickListener(this)

        mBinding.rgCollectionType.setOnCheckedChangeListener { _, checkedId ->
            val radioButton: View = mBinding.root.findViewById(checkedId)
            when (mBinding.rgCollectionType.indexOfChild(radioButton)) {
                0 -> {
                    isCollectionCash = true
                    mBinding.inputBankName.gone()
                    mBinding.inputChequeDate.gone()
                    mBinding.inputChequeNo.gone()
                }
                1 -> {
                    isCollectionCash = false
                    mBinding.inputBankName.visible()
                    mBinding.inputChequeDate.visible()
                    mBinding.inputChequeNo.visible()
                }
            }
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.edt_cheque_date -> {
                GlobalUtils.getDate(mActivity, mBinding.edtChequeDate)
            }
            R.id.edt_bank_name -> {
                if (bankInfo == null) {
                    getString(R.string.bank_details_no_available)
                } else
                    DialogUtils.getSingleChoiceDialog(mActivity,
                        resources.getString(R.string.select_bank_name),
                        bankInfo!!,
                        { dialog, position ->
                            if (position >= 0) {
                                val bankData = bankInfo?.get(position)
//                bankData?.bankName?.let { activity?.showToast(it) }
                                mBinding.edtBankName.setText(bankData)
                                bankAccNumber = bankData.toString()
                                branch = bankData.toString()

                            }
                            dialog.dismiss()
                        },
                        { dialog, position -> dialog.dismiss() })
            }
            R.id.btn_add -> {
                validate()
            }
            R.id.btn_submit -> {
                if (collectionReq?.size!! > 0) activity?.onBackPressed()
                else DialogUtils.showDialog(
                    mActivity,
                    getString(R.string.warning),
                    getString(R.string.please_add_collection_amt)
                )
            }
        }
    }

    private fun validate() {
        mBinding.edtBankName.error = null
        mBinding.edtChequeDate.error = null
        mBinding.edtChequeNo.error = null
        mBinding.edtAmt.error = null
        var isValid = true
        if (!isCollectionCash && !ValidationHelper.validateBlank(
                mBinding.edtBankName,
                mBinding.inputBankName,
                getString(R.string.please_enter_bank_name)
            )
        ) isValid = false
        if (!isCollectionCash && !ValidationHelper.validateBlank(
                mBinding.edtChequeDate,
                mBinding.inputChequeDate,
                getString(R.string.please_select_cheque_date)
            )
        ) isValid = false
        if (!isCollectionCash && !ValidationHelper.validateBlank(
                mBinding.edtChequeNo,
                mBinding.inputChequeNo,
                getString(R.string.please_enter_cheque_no)
            )
        ) isValid = false
        if (!ValidationHelper.validateBlank(
                mBinding.edtAmt,
                mBinding.inputAmt,
                getString(R.string.please_enter_amount)
            )
        ) isValid = false

        if (isValid) {
            mBinding.edtAmt.setText("")
            mBinding.edtBankName.setText("")
            mBinding.edtChequeDate.setText("")
            mBinding.edtChequeNo.setText("")
            collectionAdapter.notifyDataSetChanged()
        }
    }

//      private fun createCollection() {
//            visitViewModel.createCollection(CreateCollectionReq(collectionReq))
//            visitViewModel.createCollectionRespo.observe(this, {
//                  handleApiRespo(
//                    it,
//                    mBinding.loadingTyreIv
//                  ) { isFailure: Boolean, result: ApiResponse<CommonRespo<Boolean>>? ->
//                    if (isFailure) getBankInfo()
//                    else {
//                      if (it.data?.isSuccess == true) {
//                        it.data.strMessage?.let { it1 -> mActivity.showToast(it1) }
//                        mActivity.onBackPressed()
//                      } else {
//                        DialogUtils.showDialog(
//                          mActivity,
//                          getString(R.string.error),
//                          it.data?.strMessage.toString()
//                        )
//
//                      }
//                    }
//                  }
//                })
//          }

    private fun onItemClicked(item: String?) {
        DialogUtils.getDialogInstance(
            mActivity,
            getString(R.string.delete),
            getString(R.string.do_you_really_want),
            getString(R.string.delete),
            getString(R.string.cancel),
            { dialog, position ->
            }
        ) { dialog, position -> }
    }

    private fun navigateScreen(tag: String?) {
        val frm: Fragment? = null
        when (tag) {
//      VisitDetailsFragment.TAG -> frm = VisitDetailsFragment.getInstance(Bundle())
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}