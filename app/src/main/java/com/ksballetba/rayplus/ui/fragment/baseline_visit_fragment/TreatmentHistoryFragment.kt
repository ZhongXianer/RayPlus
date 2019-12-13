package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.baseline_visit_activity.TreatmentHistoryActivity
import kotlinx.android.synthetic.main.fragment_treatment_history.*

/**
 * A simple [Fragment] subclass.
 */
class TreatmentHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_treatment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        fab_treatment_history.setOnClickListener {
            startActivity(Intent(activity,
                TreatmentHistoryActivity::class.java))
        }
    }
}
