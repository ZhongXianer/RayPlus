package com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.treatment_visit_activity.TreatmentRecordActivity
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_treatment_record.*

/**
 * A simple [Fragment] subclass.
 */
class TreatmentRecordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_treatment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        cl_any_adjustment.setOnClickListener {
            XPopup.Builder(context).asCenterList("治疗中用药剂量有无调整", arrayOf("有","无")) { pos, text ->
                tv_any_adjustment.text = text
            }.show()
        }
        fab_treatment_record.setOnClickListener {
            startActivity(Intent(activity,TreatmentRecordActivity::class.java))
        }
    }
}
