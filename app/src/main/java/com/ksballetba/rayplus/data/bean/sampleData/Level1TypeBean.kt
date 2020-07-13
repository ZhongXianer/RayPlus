package com.ksballetba.rayplus.data.bean.sampleData

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.ksballetba.rayplus.ui.adapter.TypeAdapter.Companion.ITEM_SECOND_LEVEL

class Level1TypeBean(val typeName: String, val id: Int, var isSelected: Boolean) : MultiItemEntity {
    override fun getItemType(): Int {
        return ITEM_SECOND_LEVEL
    }
}