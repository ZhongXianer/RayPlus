package com.ksballetba.rayplus.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.SampleQueryBodyBean
import com.ksballetba.rayplus.data.bean.sampleData.*
import com.ksballetba.rayplus.network.Status
import com.ksballetba.rayplus.ui.activity.MainActivity.Companion.PROJECT_ID
import com.ksballetba.rayplus.ui.activity.MainActivity.Companion.RESEARCH_CENTER_ID
import com.ksballetba.rayplus.ui.activity.SampleEditActivity.Companion.REFRESH_LAST_PAGE
import com.ksballetba.rayplus.ui.adapter.SamplesAdapter
import com.ksballetba.rayplus.ui.adapter.TypeAdapter
import com.ksballetba.rayplus.ui.adapter.TypeAdapter.Companion.ITEM_FIRST_LEVEL
import com.ksballetba.rayplus.ui.adapter.TypeAdapter.Companion.ITEM_SECOND_LEVEL
import com.ksballetba.rayplus.util.*
import com.ksballetba.rayplus.viewmodel.SamplesViewModel
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_sample.*
import org.angmarch.views.NiceSpinner
import org.jetbrains.anko.toast

class SampleActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SampleActivity"
        const val SAMPLE_ID = "SAMPLE_ID"
        const val SAMPLE_BODY = "SAMPLE_BODY"
    }

    private var mProjectId = 0
    private var mResearchCenterId = 0

    private lateinit var mViewModel: SamplesViewModel
    private lateinit var mSamplesAdapter: SamplesAdapter
    private lateinit var mTypeAdapter: TypeAdapter
    private var mSampleList = mutableListOf<SampleListBean.Data>()
    private var mTypeList = mutableListOf<MultiItemEntity>()

    private var mSearchType: Int = 0
    private var mSampleQueryBodyBean =
        SampleQueryBodyBean(null, null, null, null, null, null, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        setContentView(R.layout.activity_sample)
        initUI()
        initList()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.action == REFRESH_LAST_PAGE) {
            srl_sample.autoRefresh()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.item_select_samples -> {
                dl_Sample.openDrawer(Gravity.RIGHT)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sample_menu, menu)
        return true
    }


    private fun initUI() {
        setSupportActionBar(tb_sample)
        initRefresh()
        mProjectId = intent.getIntExtra(PROJECT_ID, 0)
        mResearchCenterId = intent.getIntExtra(RESEARCH_CENTER_ID, 0)
        val searchType = mutableListOf("姓名", "身份证号", "编号")
        val spinner = findViewById<NiceSpinner>(R.id.choose_search_type)
        spinner.attachDataSource(searchType)
        spinner.setOnSpinnerItemSelectedListener { _, _, position, _ ->
            mSearchType = position
        }
        search_button.setOnClickListener {
            val searchContent = search_input.text.toString()
            when (mSearchType) {
                0 -> {
                    initPartQueryBodyBean()
                    if (searchContent != "") mSampleQueryBodyBean.name = searchContent
                }
                1 -> {
                    initPartQueryBodyBean()
                    if (searchContent != "") mSampleQueryBodyBean.idCard = searchContent
                }
                2 -> {
                    initPartQueryBodyBean()
                    if (searchContent != "") mSampleQueryBodyBean.patientIds = searchContent
                }
            }
            loadInitial(mSampleQueryBodyBean)
        }

        fab_refresh_sample.setOnClickListener {
            search_input.setText("")
            initSampleQueryBodyBean()
            mTypeList.clear()
            getSearchCenters()
            srl_sample.autoRefresh()
        }

        fab_add_sample.setOnClickListener {
            navigateToSampleEditActivity(-1, null)
        }
    }

    private fun initSampleQueryBodyBean() {
        mSampleQueryBodyBean = SampleQueryBodyBean(null, null, null, null, null, null, null, null)
    }

    private fun initPartQueryBodyBean() {
        mSampleQueryBodyBean.name = null
        mSampleQueryBodyBean.idCard = null
        mSampleQueryBodyBean.patientIds = null
    }

    private fun initList() {
        mViewModel = getSamplesViewModel(this)
        initSampleListView()
        initSampleSelectListView()
        mViewModel.fetchLoadStatus().observe(this, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    srl_sample.autoRefresh()
                }
                Status.SUCCESS -> {
                    srl_sample.finishRefresh()
                }
                Status.FAILED -> {
                    toast(it.msg.toString())
                    Log.d(TAG, it.msg.toString())
                }
            }
        })
        srl_sample.autoRefresh()
        srl_sample.setOnRefreshListener {
            loadInitial(mSampleQueryBodyBean)
        }
        srl_sample.setOnLoadMoreListener {
            loadMore(mSampleQueryBodyBean)
        }
        mTypeList.clear()
        getSearchCenters()
    }

    private fun initSampleListView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_sample.layoutManager = layoutManager
        mSamplesAdapter = SamplesAdapter(R.layout.item_sample, mSampleList)
        mSamplesAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rv_sample.adapter = mSamplesAdapter
        mSamplesAdapter.setOnItemClickListener { _, _, position ->
            navigateToCRFActivity(mSampleList[position].sampleId)
        }
        mSamplesAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.btn_sample_edit -> {
                    if (mSampleList[position].submitStatus != 2) {
                        val sample = mSampleList[position]
                        val sampleBody =
                            SampleEditBodyBean(
                                sample.date,
                                sample.groupId,
                                sample.idNum,
                                sample.inGroupTime,
                                sample.patientIds,
                                sample.patientName,
                                sample.researchCenterId,
                                sample.sampleId,
                                if (sample.sex == "男") 0 else 1,
                                sample.signTime
                            )
                        navigateToSampleEditActivity(mSampleList[position].sampleId, sampleBody)
                    }
                }
                R.id.btn_sample_submit -> {
                    if (mSampleList[position].submitStatus != 2) {
                        XPopup.Builder(this).asConfirm("信息", "请问是否确认提交到总中心") {
                            submitSample(mSampleList[position].sampleId)
                        }.show()
                    }
                }
                R.id.iv_delete_item_sample -> {
                    if (mSampleList[position].submitStatus == 2) {
                        toast("已全部提交，不能删除！")
                    } else {
                        XPopup.Builder(this).asConfirm("信息", "请问是否确认删除") {
                            deleteSample(mSampleList[position].sampleId, position)
                        }.show()
                    }
                }
                R.id.btn_sample_unlock -> {
                    if (judgeUnlockSample() &&
                        (mSampleList[position].submitStatus == 1 || mSampleList[position].submitStatus == 2)
                    ) {
                        XPopup.Builder(this)
                            .asConfirm("信息", "请问是否确认解锁${mSampleList[position].patientIds}号样本") {
                                unlockSample(mSampleList[position].sampleId)
                            }.show()
                    }
                }
            }
        }
    }

    private fun initSampleSelectListView() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        sl_type_list.layoutManager = gridLayoutManager
        sl_type_list.itemAnimator = DefaultItemAnimator()
        mTypeAdapter = TypeAdapter(mTypeList)
        mTypeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        sl_type_list.adapter = mTypeAdapter
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (mTypeAdapter.getItemViewType(position)) {
                    ITEM_FIRST_LEVEL -> {
                        return 2
                    }
                    ITEM_SECOND_LEVEL -> {
                        if (mTypeAdapter.getParentPosition(mTypeList[position]) == 0)
                            return 2
                        return 1
                    }
                }
                return 2
            }
        }
        mTypeAdapter.setOnItemChildClickListener() { adapter, _, position ->
            val itemEntity = mTypeList[position] as Level1TypeBean
            if (!itemEntity.isSelected) {
                mTypeList.forEach {
                    if (it is Level1TypeBean
                        && adapter.getParentPosition(it) == adapter.getParentPosition(itemEntity)
                    )
                        it.isSelected = false
                }
                itemEntity.isSelected = true
                adapter.notifyDataSetChanged()
            }
            val parent = adapter.getParentPosition(itemEntity)
            val level0TypeBean = mTypeList[parent] as Level0TypeBean
            when (level0TypeBean.type) {
                "研究中心" -> {
                    if (itemEntity.id == -1)
                        mSampleQueryBodyBean.researchCenterId = null
                    else mSampleQueryBodyBean.researchCenterId = itemEntity.id
                }
                "患者组别" -> {
                    if (itemEntity.id == -1)
                        mSampleQueryBodyBean.groupId = null
                    else mSampleQueryBodyBean.groupId = itemEntity.id
                }
                "肿瘤病理类型" -> {
                    if (itemEntity.id == -1)
                        mSampleQueryBodyBean.tumorPathologicalType = null
                    else mSampleQueryBodyBean.tumorPathologicalType = itemEntity.typeName
                }
                "性别" -> {
                    if (itemEntity.id == -1)
                        mSampleQueryBodyBean.sex = null
                    else mSampleQueryBodyBean.sex = itemEntity.id
                }
                "样本状态" -> {
                    if (itemEntity.id == -1)
                        mSampleQueryBodyBean.submitStatus = null
                    else mSampleQueryBodyBean.submitStatus = itemEntity.id
                }
            }
            loadInitial(mSampleQueryBodyBean)
        }
    }

    private fun initTypeList() {
        val tumorPathologicalType = Level0TypeBean("肿瘤病理类型")
        tumorPathologicalType.addSubItem(Level1TypeBean("全部", -1, true))
        getTumorPathologicalType().forEach {
            tumorPathologicalType.addSubItem(Level1TypeBean(it, 0, false))
        }
        tumorPathologicalType.addSubItem(Level1TypeBean("混合型癌", 0, false))
        val sex = Level0TypeBean("性别")
        sex.addSubItem(Level1TypeBean("全部", -1, true))
        getSexList().forEach {
            sex.addSubItem(Level1TypeBean(it, getSexList().indexOf(it), false))
        }
        val sampleStatus = Level0TypeBean("样本状态")
        sampleStatus.addSubItem(Level1TypeBean("全部", -1, true))
        getSampleSubmitStatus().forEach {
            sampleStatus.addSubItem(
                Level1TypeBean(
                    it,
                    getSampleSubmitStatus().indexOf(it),
                    false
                )
            )
        }
        mTypeList.add(tumorPathologicalType)
        mTypeList.add(sex)
        mTypeList.add(sampleStatus)
    }

    private fun getSearchCenters() {
        mViewModel.getAllResearchCenter(mProjectId).observe(this, Observer {
            val researchCenters = it
            val level0TypeBean = Level0TypeBean("研究中心")
            level0TypeBean.addSubItem(Level1TypeBean("全部", -1, true))
            researchCenters.forEach { data ->
                val level1TypeBean = Level1TypeBean(data.name, data.id, false)
                level0TypeBean.addSubItem(level1TypeBean)
            }
            mTypeList.add(level0TypeBean)
            getGroupIds()
        })
    }

    private fun getGroupIds() {
        mViewModel.getGroupIds().observe(this, Observer {
            val groups = it
            val level0TypeBean = Level0TypeBean("患者组别")
            level0TypeBean.addSubItem(Level1TypeBean("全部", -1, true))
            groups.forEach { data ->
                val level1TypeBean = Level1TypeBean(data.name, data.id, false)
                level0TypeBean.addSubItem(level1TypeBean)
            }
            mTypeList.add(level0TypeBean)
            initTypeList()
            mTypeAdapter.notifyDataSetChanged()
        })
    }

    private fun loadInitial(sampleQueryBodyBean: SampleQueryBodyBean) {
        mViewModel.fetchData(sampleQueryBodyBean).observe(this, Observer {
            mSampleList = it
            if (mSampleList.size == 0)
                Log.d(TAG, "样本为空")
            mSamplesAdapter.setNewData(mSampleList)
        })
    }

    private fun loadMore(sampleQueryBodyBean: SampleQueryBodyBean) {
        mViewModel.fetchMore(sampleQueryBodyBean).observe(this, Observer {
            mSamplesAdapter.addData(it)
            srl_sample.finishLoadMore()
        })
    }

    private fun navigateToSampleEditActivity(sampleId: Int, sampleBody: SampleEditBodyBean?) {
        val intent = Intent(this, SampleEditActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        if (sampleId > 0) {
            intent.putExtra(SAMPLE_BODY, sampleBody)
        }
        startActivity(intent)
    }

    private fun navigateToCRFActivity(sampleId: Int) {
        val intent = Intent(this, CRFActivity::class.java)
        intent.putExtra(SAMPLE_ID, sampleId)
        startActivity(intent)
    }

    private fun submitSample(sampleId: Int) {
        mViewModel.submitSample(
            SampleSubmitBodyBean(
                sampleId
            )
        ).observe(this, Observer {
            if (it.code == 200) {
                toast("样本提交成功")
                srl_sample.autoRefresh()
            } else if (it.code == 10031 || it.code == 10032) {
                reLogin(this)
            } else {
                toast(it.msg)
            }
        })
    }

    private fun deleteSample(sampleId: Int, pos: Int) {
        mViewModel.deleteSample(sampleId).observe(this, Observer {
            if (it.code == 200) {
                toast("样本删除成功")
                mSamplesAdapter.remove(pos)
            } else if (it.code == 10031 || it.code == 10032) {
                reLogin(this)
            } else {
                toast("样本删除失败")
            }
        })
    }

    private fun unlockSample(sampleId: Int) {
        mViewModel.unlockSample(sampleId).observe(this, Observer {
            if (it.code == 200) {
                toast("样本解锁成功")
                srl_sample.autoRefresh()
            } else if (it.code == 10031 || it.code == 10032) {
                reLogin(this)
            } else {
                toast("样本解锁失败")
            }
        })
    }

    private fun initRefresh() {
        srl_sample.setEnableRefresh(true)
        srl_sample.setEnableAutoLoadMore(true)
        srl_sample.setEnableOverScrollBounce(true)//是否启用越界回弹
        srl_sample.setEnableScrollContentWhenLoaded(true)//是否在加载完成时滚动列表显示新的内容
        srl_sample.setEnableHeaderTranslationContent(true)//是否下拉Header的时候向下平移列表或者内容
        srl_sample.setEnableFooterTranslationContent(true)//是否上拉Footer的时候向上平移列表或者内容
        srl_sample.setEnableLoadMoreWhenContentNotFull(true)
    }
}


