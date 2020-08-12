package com.ksballetba.rayplus.ui.fragment.base_fragment


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.baseData.SignatureRequestBodyBean
import com.ksballetba.rayplus.network.RetrofitClient.Companion.BASE_URL
import com.ksballetba.rayplus.ui.activity.CRFActivity
import com.ksballetba.rayplus.ui.activity.SampleActivity
import com.ksballetba.rayplus.ui.activity.TreatmentVisitDetailActivity
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.ui.fragment.ProjectSummaryFragment.Companion.INSPECTOR
import com.ksballetba.rayplus.ui.fragment.ProjectSummaryFragment.Companion.INVESTIGATOR
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import kotlinx.android.synthetic.main.fragment_investigator_signature.*

/**
 * A simple [Fragment] subclass.
 */
class InvestigatorSignatureFragment : Fragment() {

    private lateinit var mViewModel: BaseVisitViewModel

    var mSampleId = 0
    var mCycleNumber = 0

    private val picturePath = "${BASE_URL}p1/api/static/tempFiles"

    private lateinit var researchCenterMap: Map<Int, String>

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
        getResearchCenters()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SampleActivity.SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(CYCLE_NUMBER_KEY)
        mViewModel = getBaseVisitViewModel(this)
    }

    private fun initUI() {
        confirm_signature.setOnClickListener() {
            when (mCycleNumber) {
                1 -> {
                    mViewModel.addBaselineInvestigatorSignature(
                        mSampleId,
                        SignatureRequestBodyBean(getUserId(this.context!!).toString())
                    ).observe(viewLifecycleOwner, Observer {
                        if (it.code == 200) {
                            ToastUtils.showShort("基线资料签名成功")
                            getBaselineSignature()
                        } else if (it.code == 999)
                            ToastUtils.showShort("服务器出错！")
                        else ToastUtils.showShort(it.msg)
                    })
                }
                INVESTIGATOR -> {
                    mViewModel.addSummaryInvestigatorSignature(
                        mSampleId,
                        SignatureRequestBodyBean(getUserId(this.context!!).toString())
                    ).observe(viewLifecycleOwner, Observer {
                        if (it.code == 200) {
                            ToastUtils.showShort("研究者项目总结签名成功")
                            getSummarySignature()
                        } else if (it.code == 999)
                            ToastUtils.showShort("服务器出错！")
                        else ToastUtils.showShort(it.msg)
                    })
                }
                INSPECTOR -> {
                    mViewModel.addSummaryInspectorSignature(
                        mSampleId,
                        SignatureRequestBodyBean(getUserId(this.context!!).toString())
                    ).observe(viewLifecycleOwner, Observer {
                        if (it.code == 200) {
                            ToastUtils.showShort("监察员项目总结签名成功")
                            getSummarySignature()
                        } else if (it.code == 999)
                            ToastUtils.showShort("服务器出错！")
                        else ToastUtils.showShort(it.msg)
                    })
                }
                else -> {
                    mViewModel.addCycleInvestigatorSignature(
                        mSampleId, mCycleNumber,
                        SignatureRequestBodyBean(getUserId(this.context!!).toString())
                    ).observe(viewLifecycleOwner, Observer {
                        if (it.code == 200) {
                            ToastUtils.showShort("访视${mCycleNumber}签名成功")
                            getCycleSignature()
                        } else if (it.code == 999)
                            ToastUtils.showShort("服务器出错！")
                        else ToastUtils.showShort(it.msg)
                    })
                }
            }
        }
    }

    private fun getResearchCenters() {
        mViewModel.getResearchCenters(getProjectId())
            .observe(viewLifecycleOwner, Observer { result ->
                researchCenterMap = result.associateBy({ it.id }, { it.name })
                when (activity) {
                    is CRFActivity -> {
                        if (mCycleNumber == 1)
                            getBaselineSignature()
                        else
                            getSummarySignature()
                    }
                    is TreatmentVisitDetailActivity -> {
                        getCycleSignature()
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getBaselineSignature() {
        mViewModel.getBaselineInvestigatorSignature(mSampleId)
            .observe(viewLifecycleOwner, Observer {
                if (it == null) {
                    signature_status.text = "使用当前账户对基线资料进行签名"
                    account_info.text = "当前账户:${getUserName(this.context!!)}"
                    research_center_info.text = "所属中心:${getResearchCenterName(this.context!!)}"
                    signature_picture.setImageResource(R.drawable.remind)
                    confirm_signature.isVisible = true
                } else {
                    signature_status.text = "基线资料已签名!"
                    account_info.text = "签名账户名:${it.userName}"
                    research_center_info.text = "所属中心:${researchCenterMap[it.researchCenterId]}"
                    val path = it.filePath?.substring(1)
                    Glide.with(this).load("${picturePath}${path}").into(signature_picture)
                    confirm_signature.isVisible = false
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getCycleSignature() {
        mViewModel.getCycleInvestigatorSignature(mSampleId, mCycleNumber)
            .observe(viewLifecycleOwner,
                Observer {
                    if (it == null) {
                        signature_status.text = "使用当前账户对访视${mCycleNumber}进行签名"
                        account_info.text = "当前账户:${getUserName(this.context!!)}"
                        research_center_info.text = "所属中心:${getResearchCenterName(this.context!!)}"
                        signature_picture.setImageResource(R.drawable.remind)
                        confirm_signature.isVisible = true
                    } else {
                        signature_status.text = "访视${mCycleNumber}已签名!"
                        account_info.text = "签名账户名:${it.userName}"
                        research_center_info.text = "所属中心:${researchCenterMap[it.researchCenterId]}"
                        glidePicture(it.filePath!!)
                        confirm_signature.isVisible = false
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    fun getSummarySignature() {
        mViewModel.getSummarySignature(mSampleId).observe(viewLifecycleOwner, Observer {
            when (mCycleNumber) {
                INVESTIGATOR -> {
                    if (it?.si?.filePath == null) {
                        signature_status.text = "使用当前研究员账户进行项目总结签名"
                        account_info.text = "当前账户:${getUserName(this.context!!)}"
                        research_center_info.text = "所属中心:${getResearchCenterName(this.context!!)}"
                        signature_picture.setImageResource(R.drawable.remind)
                        confirm_signature.isVisible = true
                    } else {
                        signature_status.text = "研究者已对项目总结签名!"
                        account_info.text = "签名账户名:${it.si.userName}"
                        research_center_info.text =
                            "所属中心:${researchCenterMap[it.si.researchCenterId]}"
                        glidePicture(it.si.filePath)
                        confirm_signature.isVisible = false
                    }
                }
                INSPECTOR -> {
                    if (it?.inspector?.filePath == null) {
                        signature_status.text = "使用当前监察员账户进行项目总结签名"
                        account_info.text = "当前账户:${getUserName(this.context!!)}"
                        research_center_info.text = "所属中心:${getResearchCenterName(this.context!!)}"
                        signature_picture.setImageResource(R.drawable.remind)
                        confirm_signature.isVisible = true
                    } else {
                        signature_status.text = "监察员已对项目总结签名!"
                        account_info.text = "签名账户名:${it.inspector.userName}"
                        research_center_info.text =
                            "所属中心:${researchCenterMap[it.inspector.researchCenterId]}"
                        glidePicture(it.inspector.filePath)
                        confirm_signature.isVisible = false
                    }
                }
            }
        })
    }

    private fun glidePicture(filePath: String) {
        val path = filePath.substring(1)
        Glide.with(this).load("${picturePath}${path}").into(signature_picture)
    }
}
