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

fun getResearchCenterList() = arrayOf("同济医院","襄阳市第一人民医院","襄阳市中医医院","孝感市中心医院","荆州中心医院","宜昌市中心医院","恩施州中心医院","十堰市太和医院")

fun getPatientGroupList() = arrayOf("安罗替尼","安罗替尼+TKI","安罗替尼+化疗","安罗替尼+免疫")

fun getSexList() = arrayOf("男","女")

fun getDegreeList() = arrayOf("文盲", "小学", "初中", "中专或高中", "大专或本科", "本科以上")

fun getZoneList() = arrayOf("城市", "农村")

fun getRaceList() = arrayOf("白人", "黑人","东方人")

fun getMarriageList() = arrayOf("已婚", "未婚")

fun getVocationList() = arrayOf("脑力劳动者", "体力劳动者","学生","离退休","无业或事业")

fun getBaseIllListInHistory() = arrayOf("无","不详","高血压","冠心病","糖尿病","慢性阻塞性肺疾病","支气管哮喘","肺结核","间质性肺疾病","高脂血症","病毒性肝炎","风湿免疫性疾病","肾脏病","其他，请描述")