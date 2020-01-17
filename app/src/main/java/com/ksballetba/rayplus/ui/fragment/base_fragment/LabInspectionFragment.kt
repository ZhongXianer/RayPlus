package com.ksballetba.rayplus.ui.fragment.base_fragment


import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.LabInspectionBodyBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.adapter.LabInspectionAdapter
import com.ksballetba.rayplus.ui.adapter.ProjectsAdapter
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment
import com.ksballetba.rayplus.util.getBaseVisitViewModel
import com.ksballetba.rayplus.util.parseLabInspectionRank
import com.ksballetba.rayplus.util.showDatePickerDialog
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_lab_inspection.*
import org.angmarch.views.NiceSpinner
import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 */
class LabInspectionFragment : Fragment() {

    lateinit var mViewModel: BaseVisitViewModel

    var mSampleId = 0
    var mCycleNumber = 0

    private lateinit var mBloodBiochemistryAdapter: LabInspectionAdapter
    private lateinit var mUrineRoutineAdapter: LabInspectionAdapter
    private lateinit var mBloodRoutineAdapter: LabInspectionAdapter
    private lateinit var mTumorMarkerAdapter: LabInspectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab_inspection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
        initRecyclerView()
        loadData()
    }

    private fun initUI() {
        cl_sampling_date.setOnClickListener {
            showDatePickerDialog(tv_sampling_date,parentFragmentManager)
        }
        fab_save_lab_inspection.setOnClickListener {
            saveData()
        }
    }

    private fun initData() {
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(BaselineVisitFragment.CYCLE_NUMBER_KEY)
        mViewModel = getBaseVisitViewModel(this)
    }

    private fun loadData() {
        mViewModel.getLabInspection(mSampleId, mCycleNumber).observe(viewLifecycleOwner, Observer {
            tv_sampling_date.text = it.time
            (mBloodRoutineAdapter.getViewByPosition(0, R.id.tv_lab_inspection) as TextView).text =
                "${it.hbVal?:""}"
            (mBloodRoutineAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.hbRank ?: 0
            (mBloodRoutineAdapter.getViewByPosition(1, R.id.tv_lab_inspection) as TextView).text =
                "${it.rBCBVal?:""}"
            (mBloodRoutineAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.rBCBRank ?: 0
            (mBloodRoutineAdapter.getViewByPosition(2, R.id.tv_lab_inspection) as TextView).text =
                "${it.wBCBVal?:""}"
            (mBloodRoutineAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.wBCBRank ?: 0
            (mBloodRoutineAdapter.getViewByPosition(3, R.id.tv_lab_inspection) as TextView).text =
                "${it.pltVal?:""}"
            (mBloodRoutineAdapter.getViewByPosition(
                3,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.pltRank ?: 0
            (mBloodRoutineAdapter.getViewByPosition(4, R.id.tv_lab_inspection) as TextView).text =
                "${it.pTVal?:""}"
            (mBloodRoutineAdapter.getViewByPosition(
                4,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.pTRank ?: 0
            (mUrineRoutineAdapter.getViewByPosition(0, R.id.tv_lab_inspection) as TextView).text =
                "${it.wBCPVal?:""}"
            (mUrineRoutineAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.wBCPRank ?: 0
            (mUrineRoutineAdapter.getViewByPosition(1, R.id.tv_lab_inspection) as TextView).text =
                "${it.rBCPVal?:""}"
            (mUrineRoutineAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.rBCPRank ?: 0
            (mUrineRoutineAdapter.getViewByPosition(2, R.id.tv_lab_inspection) as TextView).text =
                if (it.pROVal == 1.0f) "+" else "-"
            (mUrineRoutineAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.pRORank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                0,
                R.id.tv_lab_inspection
            ) as TextView).text = "${it.aLTVal?:""}"
            (mBloodBiochemistryAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.aLTRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                1,
                R.id.tv_lab_inspection
            ) as TextView).text = "${it.aSTVal?:""}"
            (mBloodBiochemistryAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.aSTRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                2,
                R.id.tv_lab_inspection
            ) as TextView).text = "${it.tBILVal?:""}"
            (mBloodBiochemistryAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.tBILRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                3,
                R.id.tv_lab_inspection
            ) as TextView).text = "${it.dBILVal?:""}"
            (mBloodBiochemistryAdapter.getViewByPosition(
                3,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.dBILRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                4,
                R.id.tv_lab_inspection
            ) as TextView).text = "${it.aLBVal?:""}"
            (mBloodBiochemistryAdapter.getViewByPosition(
                4,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.aLBRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                5,
                R.id.tv_lab_inspection
            ) as TextView).text = "${it.crVal?:""}"
            (mBloodBiochemistryAdapter.getViewByPosition(
                5,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.crRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                6,
                R.id.tv_lab_inspection
            ) as TextView).text = "${it.bUNVal?:""}"
            (mBloodBiochemistryAdapter.getViewByPosition(
                6,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.bUNRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                7,
                R.id.tv_lab_inspection
            ) as TextView).text = "${it.gluVal?:""}"
            (mBloodBiochemistryAdapter.getViewByPosition(
                7,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.gluRank ?: 0
            (mTumorMarkerAdapter.getViewByPosition(0, R.id.tv_lab_inspection) as TextView).text =
                "${it.cEAVal?:""}"
            (mTumorMarkerAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.cEARank ?: 0
            (mTumorMarkerAdapter.getViewByPosition(1, R.id.tv_lab_inspection) as TextView).text =
                "${it.sCCVal?:""}"
            (mTumorMarkerAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.sCCRank ?: 0
            (mTumorMarkerAdapter.getViewByPosition(2, R.id.tv_lab_inspection) as TextView).text =
                "${it.nSEVal?:""}"
            (mTumorMarkerAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.nSERank ?: 0
        })
//        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
//            if(it.status == Status.FAILED){
//                ToastUtils.showShort(it.msg)
//            }
//        })
    }

    private fun saveData() {
        val time = tv_sampling_date.text.toString()
        val hbVal = (mBloodRoutineAdapter.getViewByPosition(
            0,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val hbRank = parseLabInspectionRank(
            (mBloodRoutineAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val rBCBVal = (mBloodRoutineAdapter.getViewByPosition(
            1,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val rBCBRank = parseLabInspectionRank(
            (mBloodRoutineAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val wBCBVal = (mBloodRoutineAdapter.getViewByPosition(
            2,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val wBCBRank = parseLabInspectionRank(
            (mBloodRoutineAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val pltVal = (mBloodRoutineAdapter.getViewByPosition(
            3,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val pltRank = parseLabInspectionRank(
            (mBloodRoutineAdapter.getViewByPosition(
                3,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val pTVal = (mBloodRoutineAdapter.getViewByPosition(
            4,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val pTRank = parseLabInspectionRank(
            (mBloodRoutineAdapter.getViewByPosition(
                4,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val wBCPVal = (mUrineRoutineAdapter.getViewByPosition(
            0,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val wBCPRank = parseLabInspectionRank(
            (mUrineRoutineAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val rBCPVal = (mUrineRoutineAdapter.getViewByPosition(
            1,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val rBCPRank = parseLabInspectionRank(
            (mUrineRoutineAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val pROVal = if ((mUrineRoutineAdapter.getViewByPosition(
                2,
                R.id.tv_lab_inspection
            ) as TextView).text == "+"
        ) 1f else 2f
        val pRORank = parseLabInspectionRank(
            (mUrineRoutineAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val aLTVal = (mBloodBiochemistryAdapter.getViewByPosition(
            0,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val aLTRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val aSTVal = (mBloodBiochemistryAdapter.getViewByPosition(
            1,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val aSTRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val tBILVal = (mBloodBiochemistryAdapter.getViewByPosition(
            2,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val tBILRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val dBILVal = (mBloodBiochemistryAdapter.getViewByPosition(
            3,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val dBILRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                3,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val aLBVal = (mBloodBiochemistryAdapter.getViewByPosition(
            4,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val aLBRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                4,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val crVal = (mBloodBiochemistryAdapter.getViewByPosition(
            5,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val crRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                5,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val bUNVal = (mBloodBiochemistryAdapter.getViewByPosition(
            6,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val bUNRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                6,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val gluVal = (mBloodBiochemistryAdapter.getViewByPosition(
            7,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val gluRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                7,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val cEAVal = (mTumorMarkerAdapter.getViewByPosition(
            0,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val cEARank = parseLabInspectionRank(
            (mTumorMarkerAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val sCCVal = (mTumorMarkerAdapter.getViewByPosition(
            1,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val sCCRank = parseLabInspectionRank(
            (mTumorMarkerAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val nSEVal = (mTumorMarkerAdapter.getViewByPosition(
            2,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toFloatOrNull()
        val nSERank = parseLabInspectionRank(
            (mTumorMarkerAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val labInspectionBodyBean = LabInspectionBodyBean(
            aLBRank,
            aLBVal,
            aLTRank,
            aLTVal,
            aSTRank,
            aSTVal,
            bUNRank,
            bUNVal,
            cEARank,
            cEAVal,
            crRank,
            crVal,
            dBILRank,
            dBILVal,
            gluRank,
            gluVal,
            hbRank,
            hbVal,
            nSERank,
            nSEVal,
            pRORank,
            pROVal,
            pTRank,
            pTVal,
            pltRank,
            pltVal,
            rBCBRank,
            rBCBVal,
            rBCPRank,
            rBCPVal,
            sCCRank,
            sCCVal,
            tBILRank,
            tBILVal,
            time,
            wBCBRank,
            wBCBVal,
            wBCPRank,
            wBCPVal
        )
        mViewModel.editLabInspection(mSampleId,mCycleNumber,labInspectionBodyBean).observe(viewLifecycleOwner,
            Observer {
                if(it.code==200){
                    ToastUtils.showShort("实验室检查表单修改成功")
                }else{
                    ToastUtils.showShort("实验室检查表单修改失败")
                }
            })
    }

    private fun initRecyclerView() {
        val bloodRoutineLayoutManager = LinearLayoutManager(context)
        bloodRoutineLayoutManager.orientation = RecyclerView.VERTICAL
        val urineRoutineLayoutManager = LinearLayoutManager(context)
        urineRoutineLayoutManager.orientation = RecyclerView.VERTICAL
        val bloodBiochemistryLayoutManager = LinearLayoutManager(context)
        bloodBiochemistryLayoutManager.orientation = RecyclerView.VERTICAL
        val tumorMarkerLayoutManager = LinearLayoutManager(context)
        tumorMarkerLayoutManager.orientation = RecyclerView.VERTICAL
        rv_blood_routine.layoutManager = bloodRoutineLayoutManager
        rv_urine_routine.layoutManager = urineRoutineLayoutManager
        rv_blood_biochemistry.layoutManager = bloodBiochemistryLayoutManager
        rv_tumor_marker.layoutManager = tumorMarkerLayoutManager
        mBloodRoutineAdapter = LabInspectionAdapter(
            R.layout.item_lab_inspection,
            listOf("Hb(g/L)", "RBC_B(×10^12/L)", "WBC(×10^9/L)", "Plt(×10^9/L)", "PT(S)")
        )
        mUrineRoutineAdapter = LabInspectionAdapter(
            R.layout.item_lab_inspection,
            listOf("白细胞(个/HP)", "红细胞(个/HP)", "尿蛋白(＋/－)")
        )
        mBloodBiochemistryAdapter = LabInspectionAdapter(
            R.layout.item_lab_inspection,
            listOf(
                "ALT(IU/L)",
                "AST(IU/L)",
                "TBIL(umol/1)",
                "DBIL(umol/1)",
                "ALB(g/L)",
                "Cr(umol/L)",
                "BUN(mmol/1)",
                "Glu(mmol/L)"
            )
        )
        mTumorMarkerAdapter = LabInspectionAdapter(
            R.layout.item_lab_inspection,
            listOf("CEA(ng/ml)", "SCC(U/ml)", "NSE(u/ml)")
        )
        mBloodRoutineAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mUrineRoutineAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mBloodBiochemistryAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mTumorMarkerAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mBloodRoutineAdapter.bindToRecyclerView(rv_blood_routine)
        mUrineRoutineAdapter.bindToRecyclerView(rv_urine_routine)
        mBloodBiochemistryAdapter.bindToRecyclerView(rv_blood_biochemistry)
        mTumorMarkerAdapter.bindToRecyclerView(rv_tumor_marker)
        mBloodRoutineAdapter.setOnItemClickListener { adapter, view, position ->
            XPopup.Builder(context).asInputConfirm(
                view.findViewById<TextView>(R.id.tv_lab_inspection_title).text.toString(),
                "请输入监测值"
            ) {
                view.findViewById<TextView>(R.id.tv_lab_inspection).text = it
            }.show()
        }
        mUrineRoutineAdapter.setOnItemClickListener { adapter, view, position ->
            if (position < 2) {
                XPopup.Builder(context).asInputConfirm(
                    view.findViewById<TextView>(R.id.tv_lab_inspection_title).text.toString(),
                    "请输入监测值"
                ) {
                    view.findViewById<TextView>(R.id.tv_lab_inspection).text = it
                }.show()
            } else {
                XPopup.Builder(context).asCenterList("尿蛋白", arrayOf("+", "-")) { _, text ->
                    view.findViewById<TextView>(R.id.tv_lab_inspection).text = text
                }.show()
            }
        }
        mBloodBiochemistryAdapter.setOnItemClickListener { adapter, view, position ->
            XPopup.Builder(context).asInputConfirm(
                view.findViewById<TextView>(R.id.tv_lab_inspection_title).text.toString(),
                "请输入监测值"
            ) {
                view.findViewById<TextView>(R.id.tv_lab_inspection).text = it
            }.show()
        }
        mTumorMarkerAdapter.setOnItemClickListener { adapter, view, position ->
            XPopup.Builder(context).asInputConfirm(
                view.findViewById<TextView>(R.id.tv_lab_inspection_title).text.toString(),
                "请输入监测值"
            ) {
                view.findViewById<TextView>(R.id.tv_lab_inspection).text = it
            }.show()
        }
    }
}
