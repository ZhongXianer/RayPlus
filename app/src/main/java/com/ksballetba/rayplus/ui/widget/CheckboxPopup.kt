package com.ksballetba.rayplus.ui.widget

import android.content.Context
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apkfuns.logutils.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.lxj.xpopup.core.CenterPopupView
import kotlinx.android.synthetic.main.widget_popup_checkbox.view.*

class CheckboxPopup(context: Context) : CenterPopupView(context), View.OnClickListener {

    companion object{
        const val TAG = "CheckboxPopup"
    }

    private var title: String? = null
    var data = mutableListOf<String>()
    var checkedPositions = mutableListOf<Int>()
    private lateinit var onSelectedListener: (text:String,pos:Int)->Unit
    private lateinit var confirmListener: (selectedData:List<String>)->Unit

    override fun getImplLayoutId(): Int {
        return R.layout.widget_popup_checkbox
    }

    override fun initPopupContent() {
        super.initPopupContent()
        if (title.isNullOrBlank()) {
            tv_title?.visibility = View.GONE
        } else {
            tv_title?.text = title
        }
        tv_cancel.setOnClickListener(this)
        tv_confirm.setOnClickListener(this)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_popup.layoutManager = layoutManager
        val adapter = CheckboxPopupAdapter(R.layout.item_popup_checkbox, data)
        adapter.setOnItemClickListener { _, view, position ->
            val checkbox = view.findViewById<CheckBox>(R.id.cb_popup_checkbox)
            checkbox.isChecked = !checkbox.isChecked
            if(checkbox.isChecked){
                checkedPositions.add(position)
                onSelectedListener(data[position],position)
            }else{
                checkedPositions.remove(position)
            }
        }
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rv_popup.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_cancel -> {
                dismiss()
            }
            tv_confirm -> {
                val selectedData = mutableListOf<String>()
                if(checkedPositions.size>0){
                    for(p in checkedPositions){
                        if(p<data.size-1){
                            selectedData.add(data[p])
                        }
                    }
                }
                confirmListener(selectedData)
                dismiss()
            }
        }
    }

    override fun getMaxHeight(): Int {
        return ScreenUtils.getAppScreenHeight() - 400
    }

    fun setData(
        title: String,
        data: List<String>,
        checkedPositions: List<Int>
    ): CheckboxPopup {
        this.title = title
        this.data = data.toMutableList()
        this.checkedPositions.addAll(checkedPositions)
        return this
    }

    fun setConfirmListener(
        confirmListener: (selectedData:List<String>)->Unit
    ): CheckboxPopup {
        this.confirmListener = confirmListener
        return this
    }

    fun setOnSelectedListener(onSelectedListener:(text:String,pos:Int)->Unit):CheckboxPopup{
        this.onSelectedListener = onSelectedListener
        return this
    }
}

class CheckboxPopupAdapter(layoutResId: Int, data: List<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

    companion object {
        const val TAG = "CheckboxPopupAdapter"
    }

    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_popup_checkbox, item)
    }
}