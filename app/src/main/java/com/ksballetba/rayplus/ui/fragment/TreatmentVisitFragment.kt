package com.ksballetba.rayplus.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.ui.activity.SampleActivity
import com.ksballetba.rayplus.ui.activity.TreatmentVisitDetailActivity
import com.ksballetba.rayplus.ui.adapter.VisitsAdapter
import kotlinx.android.synthetic.main.fragment_treatment_visit.*

/**
 * A simple [Fragment] subclass.
 */
class TreatmentVisitFragment : Fragment() {

    private lateinit var mVisitsAdapter: VisitsAdapter
    var mVisitList = mutableListOf<String>("访视2","访视3")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_treatment_visit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_treatment_visit.layoutManager = layoutManager
        mVisitsAdapter = VisitsAdapter(R.layout.item_treatment_visit,mVisitList)
        mVisitsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rv_treatment_visit.adapter = mVisitsAdapter
        mVisitsAdapter.setOnItemClickListener { _, _, position ->
            navigateToDetailPage(mVisitList[position])
        }
    }

    private fun navigateToDetailPage(treatmentName:String){
        val intent = Intent(activity,TreatmentVisitDetailActivity::class.java)
        intent.putExtra("treatment_name",treatmentName)
        activity?.startActivity(intent)
    }
}
