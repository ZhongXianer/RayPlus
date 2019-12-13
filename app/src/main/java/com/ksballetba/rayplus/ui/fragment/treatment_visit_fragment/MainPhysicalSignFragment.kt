package com.ksballetba.rayplus.ui.fragment.treatment_visit_fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.treatment_visit_activity.MainPhysicalSignActivity
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_main_physical_sign.*

/**
 * A simple [Fragment] subclass.
 */
class MainPhysicalSignFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_physical_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        fab_main_physical_sign.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    MainPhysicalSignActivity::class.java
                )
            )
        }
        cl_ECOG_score.setOnClickListener {
            XPopup.Builder(context).asInputConfirm("ECOG评分", "请输入ECOG评分") {
                tv_ECOG_score.text = it
            }.show()
        }
    }
}
