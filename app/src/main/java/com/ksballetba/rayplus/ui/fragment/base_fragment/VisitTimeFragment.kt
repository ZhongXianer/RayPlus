package com.ksballetba.rayplus.ui.fragment.base_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.apkfuns.logutils.LogUtils
import com.blankj.utilcode.util.ToastUtils

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.VisitTimeBean
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.activity.TreatmentVisitDetailActivity
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.util.getBaseVisitViewModel
import com.ksballetba.rayplus.util.showDatePickerDialog
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_visit_time.*
import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 */
class VisitTimeFragment : Fragment() {

    companion object{
        const val TAG = "VisitTimeFragment"
    }


    lateinit var mViewModel:BaseVisitViewModel

    var mSampleId = 0
    var mCycleNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visit_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        loadData()
        initUI()
    }

    private fun initUI(){


        cl_visit_time.setOnClickListener {
            showDatePickerDialog(tv_visit_time,parentFragmentManager)
        }
        fab_save_visit_time.setOnClickListener {
            saveData()
        }
        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
            if(it.status == Status.FAILED){
                ToastUtils.showShort(it.msg)
            }
        })
    }

    private fun initData(){
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(CYCLE_NUMBER_KEY)
        mViewModel = getBaseVisitViewModel(this)
    }

    private fun loadData(){
        mViewModel.getVisitTime(mSampleId,mCycleNumber).observe(viewLifecycleOwner, Observer {
            tv_visit_time.text = it.cycleTime
            if(activity is TreatmentVisitDetailActivity){
                val visitTitle = activity?.findViewById<Toolbar>(R.id.tb_treatment_visit)?.title
                val visitTime = it.cycleTime?:""
                activity?.findViewById<Toolbar>(R.id.tb_treatment_visit)?.title = "$visitTitle  $visitTime"
            }
        })
//        mViewModel.getLoadStatus().observe(viewLifecycleOwner, Observer {
//            if(it.status == Status.FAILED){
//                ToastUtils.showShort(it.msg)
//            }
//        })
    }

    private fun saveData(){
        val visitTimeBean = VisitTimeBean(tv_visit_time.text.toString())
        mViewModel.editVisitTime(mSampleId,mCycleNumber,visitTimeBean).observe(viewLifecycleOwner, Observer {
            if(it.code==200){
                ToastUtils.showShort("访视时间修改成功")
            }else{
                ToastUtils.showShort("访视时间修改失败")
            }
        })
    }
}
