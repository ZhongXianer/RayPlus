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
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.adapter.LabInspectionAdapter
import com.ksballetba.rayplus.ui.adapter.ProjectsAdapter
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment
import com.ksballetba.rayplus.util.getBaseVisitViewModel
import com.ksballetba.rayplus.util.parseLabInspectionRank
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
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-${monthOfYear+1}-$dayOfMonth"
                    tv_sampling_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(parentFragmentManager, "采样日期")
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
                it.hbVal.toString()
            (mBloodRoutineAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.hbRank ?: 0
            (mBloodRoutineAdapter.getViewByPosition(1, R.id.tv_lab_inspection) as TextView).text =
                it.rBCBVal.toString()
            (mBloodRoutineAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.rBCBRank ?: 0
            (mBloodRoutineAdapter.getViewByPosition(2, R.id.tv_lab_inspection) as TextView).text =
                it.wBCBVal.toString()
            (mBloodRoutineAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.wBCBRank ?: 0
            (mBloodRoutineAdapter.getViewByPosition(3, R.id.tv_lab_inspection) as TextView).text =
                it.pltVal.toString()
            (mBloodRoutineAdapter.getViewByPosition(
                3,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.pltRank ?: 0
            (mBloodRoutineAdapter.getViewByPosition(4, R.id.tv_lab_inspection) as TextView).text =
                it.pTVal.toString()
            (mBloodRoutineAdapter.getViewByPosition(
                4,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.pTRank ?: 0
            (mUrineRoutineAdapter.getViewByPosition(0, R.id.tv_lab_inspection) as TextView).text =
                it.wBCPVal.toString()
            (mUrineRoutineAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.wBCPRank ?: 0
            (mUrineRoutineAdapter.getViewByPosition(1, R.id.tv_lab_inspection) as TextView).text =
                it.rBCPVal.toString()
            (mUrineRoutineAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.rBCPRank ?: 0
            (mUrineRoutineAdapter.getViewByPosition(2, R.id.tv_lab_inspection) as TextView).text =
                if (it.pROVal == 1) "+" else "-"
            (mUrineRoutineAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.pRORank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                0,
                R.id.tv_lab_inspection
            ) as TextView).text = it.aLTVal.toString()
            (mBloodBiochemistryAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.aLTRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                1,
                R.id.tv_lab_inspection
            ) as TextView).text = it.aSTVal.toString()
            (mBloodBiochemistryAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.aSTRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                2,
                R.id.tv_lab_inspection
            ) as TextView).text = it.tBILVal.toString()
            (mBloodBiochemistryAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.tBILRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                3,
                R.id.tv_lab_inspection
            ) as TextView).text = it.dBILVal.toString()
            (mBloodBiochemistryAdapter.getViewByPosition(
                3,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.dBILRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                4,
                R.id.tv_lab_inspection
            ) as TextView).text = it.aLBVal.toString()
            (mBloodBiochemistryAdapter.getViewByPosition(
                4,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.aLBRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                5,
                R.id.tv_lab_inspection
            ) as TextView).text = it.crVal.toString()
            (mBloodBiochemistryAdapter.getViewByPosition(
                5,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.crRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                6,
                R.id.tv_lab_inspection
            ) as TextView).text = it.bUNVal.toString()
            (mBloodBiochemistryAdapter.getViewByPosition(
                6,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.bUNRank ?: 0
            (mBloodBiochemistryAdapter.getViewByPosition(
                7,
                R.id.tv_lab_inspection
            ) as TextView).text = it.gluVal.toString()
            (mBloodBiochemistryAdapter.getViewByPosition(
                7,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.gluRank ?: 0
            (mTumorMarkerAdapter.getViewByPosition(0, R.id.tv_lab_inspection) as TextView).text =
                it.cEAVal.toString()
            (mTumorMarkerAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.cEARank ?: 0
            (mTumorMarkerAdapter.getViewByPosition(1, R.id.tv_lab_inspection) as TextView).text =
                it.sCCVal.toString()
            (mTumorMarkerAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.sCCRank ?: 0
            (mTumorMarkerAdapter.getViewByPosition(2, R.id.tv_lab_inspection) as TextView).text =
                it.nSEVal.toString()
            (mTumorMarkerAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex = it.nSERank ?: 0
        })
    }

    private fun saveData() {
        val time = tv_sampling_date.text.toString()
        val hbVal = (mBloodRoutineAdapter.getViewByPosition(
            0,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val hbRank = parseLabInspectionRank(
            (mBloodRoutineAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val rBCBVal = (mBloodRoutineAdapter.getViewByPosition(
            1,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val rBCBRank = parseLabInspectionRank(
            (mBloodRoutineAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val wBCBVal = (mBloodRoutineAdapter.getViewByPosition(
            2,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val wBCBRank = parseLabInspectionRank(
            (mBloodRoutineAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val pltVal = (mBloodRoutineAdapter.getViewByPosition(
            3,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val pltRank = parseLabInspectionRank(
            (mBloodRoutineAdapter.getViewByPosition(
                3,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val pTVal = (mBloodRoutineAdapter.getViewByPosition(
            4,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val pTRank = parseLabInspectionRank(
            (mBloodRoutineAdapter.getViewByPosition(
                4,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val wBCPVal = (mUrineRoutineAdapter.getViewByPosition(
            0,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val wBCPRank = parseLabInspectionRank(
            (mUrineRoutineAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val rBCPVal = (mUrineRoutineAdapter.getViewByPosition(
            1,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
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
        ) 1 else 2
        val pRORank = parseLabInspectionRank(
            (mUrineRoutineAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val aLTVal = (mBloodBiochemistryAdapter.getViewByPosition(
            0,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val aLTRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val aSTVal = (mBloodBiochemistryAdapter.getViewByPosition(
            1,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val aSTRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val tBILVal = (mBloodBiochemistryAdapter.getViewByPosition(
            2,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val tBILRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                2,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val dBILVal = (mBloodBiochemistryAdapter.getViewByPosition(
            3,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val dBILRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                3,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val aLBVal = (mBloodBiochemistryAdapter.getViewByPosition(
            4,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val aLBRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                4,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val crVal = (mBloodBiochemistryAdapter.getViewByPosition(
            5,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val crRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                5,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val bUNVal = (mBloodBiochemistryAdapter.getViewByPosition(
            6,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val bUNRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                6,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val gluVal = (mBloodBiochemistryAdapter.getViewByPosition(
            7,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val gluRank = parseLabInspectionRank(
            (mBloodBiochemistryAdapter.getViewByPosition(
                7,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val cEAVal = (mTumorMarkerAdapter.getViewByPosition(
            0,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val cEARank = parseLabInspectionRank(
            (mTumorMarkerAdapter.getViewByPosition(
                0,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val sCCVal = (mTumorMarkerAdapter.getViewByPosition(
            1,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
        val sCCRank = parseLabInspectionRank(
            (mTumorMarkerAdapter.getViewByPosition(
                1,
                R.id.ns_lab_inspection
            ) as NiceSpinner).selectedIndex
        )
        val nSEVal = (mTumorMarkerAdapter.getViewByPosition(
            2,
            R.id.tv_lab_inspection
        ) as TextView).text.toString().toIntOrNull()
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
            listOf("Hb(g/L)", "RBC_B(×10¹²/L)", "WBC(×10^9/L)", "Plt(×10^9/L)", "PT(S)")
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
