package com.ksballetba.rayplus.util

import android.content.Context
import com.ksballetba.rayplus.ui.widget.CheckboxPopup
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView

fun asCheckboxList(context:Context?,title:String,data:List<String>,checkedPositions:List<Int>,OnSelectedListener:(text:String,pos:Int)->Unit,OnConfirmListener:(selectedData:List<String>)->Unit): BasePopupView {
    return XPopup.Builder(context).asCustom(
        CheckboxPopup(context!!).setData(title, data, checkedPositions).setOnSelectedListener(OnSelectedListener).setConfirmListener(OnConfirmListener)
    )
}