package com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.AdjustBodyBean
import com.ksballetba.rayplus.data.bean.treatmentVisitData.TreatmentRecordBodyBean
import com.ksballetba.rayplus.data.bean.treatmentVisitData.TreatmentRecordListBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.treatment_visit_activity.TreatmentRecordActivity
import com.ksballetba.rayplus.ui.adapter.TreatmentRecordAdapter
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.util.getTreatmentVisitViewModel
import com.ksballetba.rayplus.util.parseDefaultContent
import com.ksballetba.rayplus.viewmodel.TreatmentVisitViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_treatment_record.*

/**
 * A simple [Fragment] subclass.
 */
class TreatmentRecordFragment : Fragment() {

    companion object {
        const val TAG = "TreatmentRecordFragment"
        const val TREATMENT_RECORD_BODY = "TREATMENT_RECORD_BODY"
    }

    private lateinit var mViewModel: TreatmentVisitViewModel
    private lateinit var mAdapter: TreatmentRecordAdapter
    var mList = mutableListOf<TreatmentRecordListBean.Data>()
    var mSampleId = 0
    var mCycleNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_treatment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initList()
        loadData()
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(CYCLE_NUMBER_KEY)
        mViewModel = getTreatmentVisitViewModel(this)
    }

    private fun initUI(){
        cl_any_adjustment.setOnClickListener {
            XPopup.Builder(context).asCenterList("治疗中用药剂量有无调整", arrayOf("有","无")) { _, text ->
                tv_any_adjustment.text = text
                if(text == "有"){
                    cl_adjustment_percent.visibility = View.VISIBLE
                    cl_adjustment_reason.visibility = View.VISIBLE
                }else{
                    cl_adjustment_percent.visibility = View.GONE
                    cl_adjustment_reason.visibility = View.GONE
                }
            }.show()
        }
        cl_adjustment_percent.setOnClickListener {
            XPopup.Builder(context).asInputConfirm(getString(R.string.adjustment_percent), "请输入调整百分比") {
                tv_adjustment_percent.text = it
            }.show()
        }
        cl_adjustment_reason.setOnClickListener {
            XPopup.Builder(context).asInputConfirm(getString(R.string.adjustment_reason), "请输入调整原因") {
                tv_adjustment_reason.text = it
            }.show()
        }
        btn_save_adjustment.setOnClickListener {
            val adjustPercent = parseDefaultContent(tv_adjustment_percent.text.toString())
            val adjustReason = parseDefaultContent(tv_adjustment_reason.text.toString())
            val adjustment = if(tv_any_adjustment.text == "有") "1" else "0"
            val adjustBodyBean = AdjustBodyBean(AdjustBodyBean.AdjustmentStatus(adjustPercent,adjustReason,adjustment))
            mViewModel.editAdjustment(mSampleId,mCycleNumber,adjustBodyBean).observe(viewLifecycleOwner,
                Observer {
                    if(it.code==200){
                        ToastUtils.showShort("调整保存成功")
                    }else{
                        ToastUtils.showShort("调整保存失败")
                    }
                })
        }
        fab_treatment_record.setOnClickListener {
            navigateToTreatmentRecordEditPage(mSampleId,mCycleNumber,null)
        }
    }

    private fun initList() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_treatment_record.layoutManager = layoutManager
        mAdapter = TreatmentRecordAdapter(R.layout.item_treatment_record, mList)
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mAdapter.bindToRecyclerView(rv_treatment_record)
        mAdapter.setOnItemClickListener { _, _, position ->
            val treatmentRecord = mList[position]
            val treatmentRecordBody =
                TreatmentRecordBodyBean(
                    treatmentRecord.description,
                    treatmentRecord.endTime,
                    treatmentRecord.medicineName,
                    treatmentRecord.startTime,
                    treatmentRecord.treatmentName,
                    treatmentRecord.treatmentRecordId
                )
            navigateToTreatmentRecordEditPage(
                mSampleId,
                mCycleNumber,
                treatmentRecordBody
            )
        }
        mAdapter.setOnItemChildClickListener { _, _, position ->
            XPopup.Builder(context).asConfirm("信息", "请问是否确认删除") {
                deleteTreatmentRecord(mList[position].treatmentRecordId, position)
            }.show()
        }
    }

    fun loadData() {
        mViewModel.getTreatmentRecordList(mSampleId, mCycleNumber)
            .observe(viewLifecycleOwner, Observer {
                mList = it.toMutableList()
                mAdapter.setNewData(mList)
            })
        mViewModel.getAdjustment(mSampleId,mCycleNumber)
            .observe(viewLifecycleOwner, Observer {
                if(it.data.adjustment == "0"){
                    cl_adjustment_percent.visibility = View.GONE
                    cl_adjustment_reason.visibility = View.GONE
                    tv_any_adjustment.text = "无"
                }else{
                    cl_adjustment_percent.visibility = View.VISIBLE
                    cl_adjustment_reason.visibility = View.VISIBLE
                    tv_any_adjustment.text = "有"
                    tv_adjustment_percent.text = it.data.adjustPercent
                    tv_adjustment_reason.text = it.data.adjustReason
                }
            })
        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
            if (it.status == Status.FAILED) {
                ToastUtils.showShort(it.msg)
                cl_adjustment_percent.visibility = View.GONE
                cl_adjustment_reason.visibility = View.GONE
                tv_any_adjustment.text = "无"
            }
        })
    }

    private fun navigateToTreatmentRecordEditPage(
        sampleId: Int,
        cycleNumber: Int,
        treatmentRecordBody: TreatmentRecordBodyBean?
    ) {
        val intent = Intent(activity, TreatmentRecordActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        intent.putExtra(CYCLE_NUMBER_KEY, cycleNumber)
        if (treatmentRecordBody?.treatmentRecordId != null) {
            intent.putExtra(TREATMENT_RECORD_BODY, treatmentRecordBody)
        }
        startActivity(intent)
    }

    private fun deleteTreatmentRecord(treatmentRecordId: Int, pos: Int) {
        mViewModel.deleteTreatmentRecord(mSampleId, mCycleNumber, treatmentRecordId)
            .observe(viewLifecycleOwner,
                Observer {
                    if (it.code == 200) {
                        ToastUtils.showShort("删除成功")
                        mAdapter.remove(pos)
                    } else {
                        ToastUtils.showShort("删除失败")
                    }
                })
    }
}
