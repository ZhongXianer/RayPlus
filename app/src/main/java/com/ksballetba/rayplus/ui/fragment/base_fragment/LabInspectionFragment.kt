package com.ksballetba.rayplus.ui.fragment.base_fragment


import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.adapter.LabInspectionAdapter
import com.ksballetba.rayplus.ui.adapter.ProjectsAdapter
import com.lxj.xpopup.XPopup
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_demographics.*
import kotlinx.android.synthetic.main.fragment_lab_inspection.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class LabInspectionFragment : Fragment() {

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
        initRecyclerView()
    }

    private fun initUI(){
        cl_sampling_date.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$year-$monthOfYear-$dayOfMonth"
                    tv_sampling_date.text = date
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(parentFragmentManager, "采样日期")
        }
    }

    private fun initRecyclerView(){
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
        val bloodRoutineAdapter = LabInspectionAdapter(R.layout.item_lab_inspection, listOf("Hb(g/L)","RBC_B(×10¹²/L)","WBC(×10^9/L)","Plt(×10^9/L)","PT(S)"))
        val urineRoutineAdapter = LabInspectionAdapter(R.layout.item_lab_inspection, listOf("白细胞(个/HP)","红细胞(个/HP)","尿蛋白(＋/－)"))
        val bloodBiochemistryAdapter = LabInspectionAdapter(R.layout.item_lab_inspection, listOf("ALT(IU/L)","AST(IU/L)","TBIL(umol/1)","DBIL(umol/1)","ALB(g/L)","Cr(umol/L)","BUN(mmol/1)","Glu(mmol/L)"))
        val tumorMarkerAdapter = LabInspectionAdapter(R.layout.item_lab_inspection, listOf("CEA(ng/ml)","SCC(U/ml)","NSE(u/ml)"))
        bloodRoutineAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        urineRoutineAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        bloodBiochemistryAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        tumorMarkerAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rv_blood_routine.adapter = bloodRoutineAdapter
        rv_urine_routine.adapter = urineRoutineAdapter
        rv_blood_biochemistry.adapter = bloodBiochemistryAdapter
        rv_tumor_marker.adapter = tumorMarkerAdapter
        bloodRoutineAdapter.setOnItemClickListener { adapter, view, position ->
            XPopup.Builder(context).asInputConfirm(view.findViewById<TextView>(R.id.tv_lab_inspection_title).text.toString(),"请输入监测值"){
                view.findViewById<TextView>(R.id.tv_lab_inspection).text = it
            }.show()
        }
        urineRoutineAdapter.setOnItemClickListener { adapter, view, position ->
            if(position<2){
                XPopup.Builder(context).asInputConfirm(view.findViewById<TextView>(R.id.tv_lab_inspection_title).text.toString(),"请输入监测值"){
                    view.findViewById<TextView>(R.id.tv_lab_inspection).text = it
                }.show()
            }else{
                XPopup.Builder(context).asCenterList("尿蛋白", arrayOf("+", "-")) { _, text ->
                    view.findViewById<TextView>(R.id.tv_lab_inspection).text = text
                }.show()
            }
        }
        bloodBiochemistryAdapter.setOnItemClickListener { adapter, view, position ->
            XPopup.Builder(context).asInputConfirm(view.findViewById<TextView>(R.id.tv_lab_inspection_title).text.toString(),"请输入监测值"){
                view.findViewById<TextView>(R.id.tv_lab_inspection).text = it
            }.show()
        }
        tumorMarkerAdapter.setOnItemClickListener { adapter, view, position ->
            XPopup.Builder(context).asInputConfirm(view.findViewById<TextView>(R.id.tv_lab_inspection_title).text.toString(),"请输入监测值"){
                view.findViewById<TextView>(R.id.tv_lab_inspection).text = it
            }.show()
        }
    }
}
