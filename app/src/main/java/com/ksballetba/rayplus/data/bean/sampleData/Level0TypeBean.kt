package com.ksballetba.rayplus.data.bean.sampleData

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.ksballetba.rayplus.ui.adapter.TypeAdapter.Companion.ITEM_FIRST_LEVEL

class Level0TypeBean(val type: String) : AbstractExpandableItem<Level1TypeBean>(),MultiItemEntity {
    override fun getLevel(): Int {
        return 0
    }

    override fun getItemType(): Int {
        return ITEM_FIRST_LEVEL
    }
}