package com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.treatment_visit_activity.AdverseEventActivity
import kotlinx.android.synthetic.main.fragment_adverse_event.*

/**
 * A simple [Fragment] subclass.
 */
class AdverseEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adverse_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI(){
        fab_add_adverse_event.setOnClickListener {
            startActivity(Intent(activity,AdverseEventActivity::class.java))
        }
    }
}
