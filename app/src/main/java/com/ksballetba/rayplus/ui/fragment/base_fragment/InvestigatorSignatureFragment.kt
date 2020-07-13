package com.ksballetba.rayplus.ui.fragment.base_fragment


import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.SampleActivity
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.util.getBaseVisitViewModel
import com.ksballetba.rayplus.util.getUserName
import com.ksballetba.rayplus.util.showDatePickerDialog
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_investigator_signature.*
import retrofit2.http.Url
import java.net.URL
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class InvestigatorSignatureFragment : Fragment() {

    private lateinit var mViewModel: BaseVisitViewModel

    var mSampleId = 0
    var mCycleNumber = 0

    val picturePath = "http://39.106.111.52/p1/api/static/tempFiles"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_investigator_signature, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        if (mCycleNumber == 1)
            getBaselineSignature()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SampleActivity.SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(CYCLE_NUMBER_KEY)
        mViewModel = getBaseVisitViewModel(this)
    }

    private fun getBaselineSignature() {
        mViewModel.getBaselineInvestigatorSignature(mSampleId)
            .observe(viewLifecycleOwner, Observer { it ->
                if (it == null) {
                    signature_status.text = "使用当前账户对基线资料进行签名"
                    account_info.text = "当前账户:${getUserName(this.context!!)}"
                    confirm_signature.isVisible = true
                } else {
                    signature_status.text = "基线资料已签名！"
//                    val url = URL("${picturePath}${it.filePath}")
//                    val bitmap = BitmapFactory.decodeStream(url.openStream())
//                    signature_picture.setImageBitmap(bitmap)
                    confirm_signature.isVisible = false
                }
            })
    }

    private fun initUI() {
        confirm_signature.setOnClickListener() {

        }
    }
}
