package com.ksballetba.rayplus.ui.fragment.baseline_visit_fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.rayplus.R
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.fragment_first_visit_process.*

/**
 * A simple [Fragment] subclass.
 */
class FirstVisitProcessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_visit_process, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        cl_lesion.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("病灶部位", arrayOf("左上肺", "左下肺", "右上肺", "右中肺", "右下肺")) { _, text ->
                    tv_lesion.text = text
                }.show()
        }
        cl_biopsy_way.setOnClickListener {
            XPopup.Builder(context).asCenterList(
                "活检方式",
                arrayOf("无", "手术", "胸腔镜", "纵膈镜", "经皮肺穿刺", "纤支镜", "E-BUS", "EUS-FNA", "淋巴结活检", "其他")
            ) { pos, text ->
                if (pos < 9) {
                    tv_biopsy_way.text = text
                } else {
                    XPopup.Builder(context).asInputConfirm("活检方式", "请输入活检方式") {
                        tv_biopsy_way.text = it
                    }.show()
                }
            }.show()
        }
        cl_tumor_type.setOnClickListener {
            XPopup.Builder(context).asCenterList(
                "肿瘤病理类型",
                arrayOf("腺癌", "鳞癌", "小细胞肺癌", "大细胞癌", "神经内分泌癌", "肉瘤", "分化差的癌", "混合型癌")
            ) { pos, text ->
                if (pos < 7) {
                    tv_tumor_type.text = text
                } else {
                    XPopup.Builder(context).asInputConfirm("肿瘤病理类型", "请输入混合型癌描述") {
                        tv_tumor_type.text = it
                    }.show()
                }
            }.show()
        }
        cl_genetic_test_sample.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("基因检测标本", arrayOf("无", "外周血", "原发灶组织", "转移灶组织")) { pos, text ->
                    if (pos < 3) {
                        tv_genetic_test_sample.text = text
                    } else {
                        XPopup.Builder(context).asInputConfirm("基因检测标本", "请输入转移灶组织描述") {
                            tv_genetic_test_sample.text = it
                        }.show()
                    }
                }.show()
        }
        cl_genetic_test_way.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("基因检测方法", arrayOf("无", "ARMS", "FISH", "NGS")) { pos, text ->
                    tv_genetic_test_sample.text = text
                }.show()
        }
        cl_PD_L1_expression.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("PD-L1表达", arrayOf("未测", "不详", ">50%", "1%-50%","<1%","阴性")) { pos, text ->
                    tv_PD_L1_expression.text = text
                }.show()
        }
        cl_tumor_mutation_load.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("肿瘤突变负荷(TMB)", arrayOf("未测", "不详", "数量（个突变/Mb）")) { pos, text ->
                    if(pos<3){
                        tv_tumor_mutation_load.text = text
                    }else{
                        XPopup.Builder(context).asInputConfirm("基因检测标本", "请输入转移灶组织描述") {
                            tv_tumor_mutation_load.text = it
                        }.show()
                    }
                }.show()
        }
        cl_microsatellite_instability.setOnClickListener {
            XPopup.Builder(context)
                .asCenterList("微卫星不稳定性(MSI)", arrayOf("未测", "不详", "微卫星稳定性", "微卫星不稳定性")) { pos, text ->
                    tv_microsatellite_instability.text = text
                }.show()
        }
    }
}
