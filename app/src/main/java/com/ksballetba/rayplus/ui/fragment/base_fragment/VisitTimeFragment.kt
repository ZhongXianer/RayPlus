package com.ksballetba.rayplus.ui.fragment.base_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils

import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.VisitTimeBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.BASELINE_CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.util.getBaseVisitViewModel
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_visit_time.*
import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 */
class VisitTimeFragment : Fragment() {

    private lateinit var mOnDateSetListener:DatePickerDialog.OnDateSetListener

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
        initUI()
    }

    private fun initUI(){
        initData()
        loadData()
        mOnDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val date = "$year-${monthOfYear+1}-$dayOfMonth"
                tv_visit_time.text = date
            }
        cl_visit_time.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                mOnDateSetListener,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show(parentFragmentManager,"访视时间")
        }
        fab_save_visit_time.setOnClickListener {
            saveData()
        }
    }

    private fun initData(){
        mSampleId = (arguments as Bundle).getInt(SAMPLE_ID)
        mCycleNumber = (arguments as Bundle).getInt(BASELINE_CYCLE_NUMBER_KEY)
        mViewModel = getBaseVisitViewModel(this)
    }

    private fun loadData(){
        mViewModel.getVisitTime(mSampleId,mCycleNumber).observe(viewLifecycleOwner, Observer {
            tv_visit_time.text = it.cycleTime
        })
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
