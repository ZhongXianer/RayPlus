package com.ksballetba.rayplus.util

import android.content.Context
import com.ksballetba.rayplus.data.bean.BaseCheckBean
import com.ksballetba.rayplus.ui.widget.CheckboxPopup
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView

fun asCheckboxList(context:Context?,title:String,data:List<BaseCheckBean>,OnSelectedListener:(data:BaseCheckBean,pos:Int)->Unit,OnConfirmListener:(checkedData:List<BaseCheckBean>)->Unit): BasePopupView {
    return XPopup.Builder(context).asCustom(
        CheckboxPopup(context!!).setData(title, data).setOnSelectedListener(OnSelectedListener).setConfirmListener(OnConfirmListener)
    )
}

fun parseDefaultContent(data:String):String{
    return if(data == "请设置"){
        ""
    }else{
        data
    }
}

fun parseLabInspectionRank(idx:Int):Int?{
    return if(idx==0){
        null
    }else{
        idx
    }
}

fun getResearchCenterList() = arrayOf("同济医院","襄阳市第一人民医院","襄阳市中医医院","孝感市中心医院","荆州中心医院","宜昌市中心医院","恩施州中心医院","十堰市太和医院")

fun getPatientGroupList() = arrayOf("安罗替尼","安罗替尼+TKI","安罗替尼+化疗","安罗替尼+免疫")

fun getSexList() = arrayOf("男","女")

fun getDegreeList() = arrayOf("文盲", "小学", "初中", "中专或高中", "大专或本科", "本科以上")

fun getZoneList() = arrayOf("城市", "农村")

fun getRaceList() = arrayOf("白人", "黑人","东方人")

fun getMarriageList() = arrayOf("已婚", "未婚")

fun getVocationList() = arrayOf("脑力劳动者", "体力劳动者","学生","离退休","无业或事业")

fun getBaseIllListInHistory() = arrayOf("无","不详","高血压","冠心病","糖尿病","慢性阻塞性肺疾病","支气管哮喘","肺结核","间质性肺疾病","高脂血症","病毒性肝炎","风湿免疫性疾病","肾脏病","其他，请描述")

fun getClinicalSymptomsList() = arrayOf("咳嗽","咳痰","咳血","发热","胸闷","胸痛","喘气","消瘦","体重下降","其他","不详")

fun getTumorPart() = arrayOf("左上肺", "左下肺", "右上肺", "右中肺", "右下肺")

fun getGeneticTestingMethod() = arrayOf("无", "ARMS", "FISH", "NGS")

fun getGeneMutationType() = arrayOf("未测","不详","无突变","ROS-1","cMET","BRAF","KRAS","Her-2","RET","ERBB2","TP53","EGFR","ALK")

fun getTransferSite() = arrayOf("无","对侧肺门淋巴结","锁骨上淋巴结肺内","肺内","脑","脊柱骨","四肢骨","肝","肾上腺","其他")

fun getPD_L1Expression() = arrayOf("未测", "不详", ">50%", "1%-50%","<1%","阴性")

fun getMSI() = arrayOf("未测", "不详", "微卫星稳定性", "微卫星不稳定性")

fun getImagingEvaluationWayListInHistory() = arrayOf("CT","MRI","超声","X线平片","PET-CT")

fun getTherapeuticEvaluationList() = arrayOf("完全缓解(CR)", "部分缓解(PR)", "疾病稳定(SD)", "疾病进展(PD)")

fun getMainPhysicalSignList() =  arrayOf("高血压", "腹泻", "皮疹","蛋白尿","出血")

fun getAdverseEventMeasure() = arrayOf("剂量不变","减少剂量","暂停用药","停止用药","实验用药已结束")

fun getAdverseEventMedicineMeasure() = arrayOf("继续用药","减少剂量","药物暂停后又恢复","停止用药")

fun getAdverseEventMedicineRelation() = arrayOf("肯定有关","很可能有关","可能有关","可能无关","肯定无关")

fun getAdverseEventSAERecover() = arrayOf("症状消失后无后遗症","症状消失后有后遗症","症状持续")

fun getAdverseEventReportType() = arrayOf("首次报告","随访报告","总结报告")

fun getAdverseEventToxicityClassify() = arrayOf("1级","2级","3级","4级","5级")

fun getAdverseEventSAEState() = arrayOf("死亡","导致住院","延长住院时间","伤残","功能障碍","导致先天畸形","危及生命","怀孕")

fun getSurvivalStatus() = arrayOf("死亡","存活","失联")

fun getInterviewWay() = arrayOf("电话", "门诊", "住院")

fun getOSMethod() =  arrayOf(
    "1.街道办开具死亡证明",
    "2.民政局系统出具死亡证明",
    "3.公安局及派出所出具死亡证明",
    "4.火化证明或公墓数据",
    "5.本院死亡的医疗文件",
    "6.其他医院死亡的医疗文件",
    "7.家属手写证明文件",
    "8.电话随访获知"
)

fun getReasonStopDrug() =  arrayOf(
    "病情进展（出现客观疗效评价的疾病进展或临床症状进展）",
    "不良事件（与试验药物可能存在相关性）",
    "不良事件（与试验药物不存在相关性）",
    "自愿退出（与不良事件无相关性）",
    "违背实验方案",
    "死亡",
    "失联"
)

fun getBestEffect() =
    arrayOf("完全缓解(CR)", "部分缓解(PR)", "疾病稳定(SD)", "疾病进展(SD)", "不能评价(NE)")
