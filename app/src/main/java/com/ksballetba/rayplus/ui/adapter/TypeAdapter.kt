package com.ksballetba.rayplus.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.sampleData.Level0TypeBean
import com.ksballetba.rayplus.data.bean.sampleData.Level1TypeBean

class TypeAdapter(data: List<MultiItemEntity>) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(ITEM_FIRST_LEVEL, R.layout.item_select_type)
        addItemType(ITEM_SECOND_LEVEL, R.layout.item_type_name)
    }

    companion object {
        public val ITEM_FIRST_LEVEL = 1
        public val ITEM_SECOND_LEVEL = 2
    }

    @SuppressLint("ResourceType")
    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when (helper.itemViewType) {
            ITEM_FIRST_LEVEL -> {
                val level0TypeBean = item as Level0TypeBean
                helper.setText(R.id.sl_type_title, level0TypeBean.type)
                    .itemView.setOnClickListener {
                        val position = helper.adapterPosition
                        if (level0TypeBean.isExpanded) {
                            collapse(position)
                        } else expand(position)
                    }
            }
            ITEM_SECOND_LEVEL -> {
                val level1TypeBean = item as Level1TypeBean
                helper.setText(R.id.sl_type_text, level1TypeBean.typeName)
                if (!level1TypeBean.isSelected)
                    helper.setBackgroundColor(R.id.sl_type_text, Color.parseColor("#fafafa"))
                else helper.setBackgroundColor(R.id.sl_type_text, Color.parseColor("#94D3E6"))
                helper.addOnClickListener(R.id.sl_type_text)
            }
        }
    }
}