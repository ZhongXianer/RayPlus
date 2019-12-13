package com.ksballetba.rayplus.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.survival_visit_activity.SurvivalVisitActivity
import kotlinx.android.synthetic.main.fragment_survival_visit.*

/**
 * A simple [Fragment] subclass.
 */
class SurvivalVisitFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survival_visit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        fab_add_survival_visit.setOnClickListener {
            startActivity(Intent(activity,SurvivalVisitActivity::class.java))
        }
    }
}
