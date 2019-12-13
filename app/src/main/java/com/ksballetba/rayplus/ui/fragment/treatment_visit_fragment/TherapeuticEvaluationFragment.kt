package com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_therapeutic_evaluation.*

/**
 * A simple [Fragment] subclass.
 */
class TherapeuticEvaluationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_therapeutic_evaluation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        cl_therapeutic_evaluation.setOnClickListener {
            XPopup.Builder(context).asCenterList(
                "疗效评价",
                arrayOf("完全缓解(CR)", "部分缓解(PR)", "疾病稳定(SD)", "疾病进展(PD)")
            ) { pos, text ->
                tv_therapeutic_evaluation.text = text
            }.show()
        }
    }
}
