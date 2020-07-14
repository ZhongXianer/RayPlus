package com.ksballetba.rayplus.ui.activity.baseline_visit_activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.baseData.ImagingEvaluationFileBodyBean
import com.ksballetba.rayplus.ui.adapter.ImagingEvaluationFileAdapter
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import kotlinx.android.synthetic.main.activity_imaging_evaluation_file.*

class ImagingEvaluationFileActivity : AppCompatActivity() {

    private lateinit var mViewModel: BaseVisitViewModel

    private var mFileList = mutableListOf<ImagingEvaluationFileBodyBean.Data?>()
    private lateinit var imagingEvaluationFileAdapter: ImagingEvaluationFileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imaging_evaluation_file)
    }

    private fun initUI() {
        setSupportActionBar(tb_imaging_evaluation_file)
    }
}