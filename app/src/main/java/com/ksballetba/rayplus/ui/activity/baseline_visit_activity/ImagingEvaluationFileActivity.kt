package com.ksballetba.rayplus.ui.activity.baseline_visit_activity

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.R.layout.empty
import com.ksballetba.rayplus.data.bean.baseData.ImagingEvaluationFileListBean
import com.ksballetba.rayplus.ui.activity.SampleActivity.Companion.SAMPLE_ID
import com.ksballetba.rayplus.ui.adapter.ImagingEvaluationFileAdapter
import com.ksballetba.rayplus.ui.fragment.BaselineVisitFragment.Companion.CYCLE_NUMBER_KEY
import com.ksballetba.rayplus.ui.fragment.base_fragment.ImagingEvaluationFragment.Companion.EVALUATE_ID
import com.ksballetba.rayplus.ui.fragment.base_fragment.ImagingEvaluationFragment.Companion.REFRESH_PAGE
import com.ksballetba.rayplus.util.getBaseVisitViewModel
import com.ksballetba.rayplus.util.getPath
import com.ksballetba.rayplus.viewmodel.BaseVisitViewModel
import com.lxj.xpopup.XPopup
import com.qw.photo.CoCo
import com.qw.photo.callback.SimpleGetImageAdapter
import com.qw.photo.pojo.PickResult
import com.qw.photo.pojo.TakeResult
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_imaging_evaluation_file.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jetbrains.anko.toast
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ImagingEvaluationFileActivity : AppCompatActivity() {

    companion object {
        const val TAG = "takePhotoActivity"
    }

    private lateinit var mViewModel: BaseVisitViewModel

    private var mFileList = mutableListOf<ImagingEvaluationFileListBean.Data?>()
    private lateinit var mImagingEvaluationFileAdapter: ImagingEvaluationFileAdapter

    private var mSampleId = 0
    private var mCycleNumber = 0
    private var mEvaluateId = 0
    private lateinit var mCurrentPage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imaging_evaluation_file)
        initData()
        initUI()
        initList()
    }

    private fun initData() {
        mSampleId = intent.getIntExtra(SAMPLE_ID, 0)
        mCycleNumber = intent.getIntExtra(CYCLE_NUMBER_KEY, 0)
        mEvaluateId = intent.getIntExtra(EVALUATE_ID, 0)
        mCurrentPage = intent.getStringExtra(REFRESH_PAGE)
    }

    private fun initUI() {
        setSupportActionBar(tb_imaging_evaluation_file)
        fab_upload_file.setOnClickListener {
            chooseFile()
        }
    }

    private fun initList() {
        mViewModel = getBaseVisitViewModel(this)
        mImagingEvaluationFileAdapter =
            ImagingEvaluationFileAdapter(R.layout.item_imaging_evaluation_file, mFileList)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        file_list.layoutManager = layoutManager
        file_list.adapter = mImagingEvaluationFileAdapter
        val view = layoutInflater.inflate(empty, null)
        mImagingEvaluationFileAdapter.emptyView = view
        mImagingEvaluationFileAdapter.setOnItemChildClickListener { _, _, position ->
            XPopup.Builder(this).asConfirm("信息", "请问是否删除文件") {
                deleteFile(mFileList[position]?.filePath!!, position)
            }.show()
        }
        getImagingEvaluationFile()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getImagingEvaluationFile() {
        mViewModel.getImagingEvaluationFileList(mSampleId, mCycleNumber, mEvaluateId).observe(this,
            Observer {
                mFileList = it
                mImagingEvaluationFileAdapter.setNewData(mFileList)
            })
    }

    private fun deleteFile(filePath: String, position: Int) {
        mViewModel.deleteImagingEvaluationFile(filePath).observe(this, Observer {
            if (it.code == 200) {
                toast("删除成功")
                mImagingEvaluationFileAdapter.remove(position)
            } else toast(it.msg)
        })
    }

    private fun uploadFile(path: String) {
        val file = File(path)
        val fileRQ = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("file", file.name, fileRQ)
        mViewModel.uploadImagingEvaluationFile(mSampleId, mCycleNumber, mEvaluateId, part)
            .observe(this,
                Observer {
                    if (it.code == 200) {
                        toast("上传成功")
                        getImagingEvaluationFile()
                    } else {
                        toast(it.msg)
                    }
                })
    }

    private fun chooseFile() {
        val choice = arrayOf("拍照", "从文件选择")
        XPopup.Builder(this).asBottomList("选择文件来源", choice) { position, _ ->
            when (position) {
                0 -> {
                    RxPermissions.getInstance(this).request(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).subscribe { t ->
                        if (t!!) {
                            CoCo.with(this@ImagingEvaluationFileActivity)
                                .take(createSDCardFile())
                                .applyWithDispose()
                                .start(object : SimpleGetImageAdapter<TakeResult>() {
                                    override fun onSuccess(data: TakeResult) {
                                        uploadFile(data.targetFile!!.path)
                                    }
                                })
                        } else {
                            toast("没有权限")
                        }
                    }

                }
                1 -> {
                    RxPermissions.getInstance(this).request(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).subscribe { t ->
                        if (t!!) {
                            XPopup.Builder(this).asConfirm("提示", "上传过大图片将无法在移动端显示,请在网页端查看") {
                                CoCo.with(this@ImagingEvaluationFileActivity)
                                    .pick(createSDCardFile())
                                    .apply()
                                    .start(object : SimpleGetImageAdapter<PickResult>() {
                                        override fun onSuccess(data: PickResult) {
                                            try {
                                                val uri = data.originUri
                                                val filePath =
                                                    getPath(this@ImagingEvaluationFileActivity, uri)
                                                uploadFile(filePath!!)
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                        }
                                    })
                            }.show()
                        } else {
                            toast("没有权限")
                        }
                    }
                }
            }
        }.show()
    }

    @Throws(IOException::class)
    private fun createSDCardFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = File(externalCacheDir!!.path + "/" + timeStamp)
        if (!storageDir.exists())
            storageDir.mkdir()
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

}