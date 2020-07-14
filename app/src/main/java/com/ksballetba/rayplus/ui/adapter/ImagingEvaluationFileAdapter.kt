package com.ksballetba.rayplus.ui.adapter

import android.content.Context
import android.widget.ImageView
import androidx.annotation.IdRes
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.baseData.ImagingEvaluationFileBodyBean
import com.ksballetba.rayplus.network.RetrofitClient.Companion.PROJECT_BASE_URL
import kotlinx.android.synthetic.main.item_imaging_evaluation_file.view.*

class ImagingEvaluationFileAdapter(
    layoutResId: Int, data: List<ImagingEvaluationFileBodyBean.Data>
) : BaseQuickAdapter<ImagingEvaluationFileBodyBean.Data, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: ImagingEvaluationFileBodyBean.Data?) {
        helper.setText(R.id.imaging_evaluation_file_name, item?.fileName ?: "")
            .setText(R.id.imaging_evaluation_create_time, item?.fileCreateTime ?: "")
            .setText(R.id.imaging_evaluation_file_size, item?.fileSize ?: "")
        val path = "${PROJECT_BASE_URL}p1/api/static/tempFiles${item?.filePath?.substring(1)}"
        val imageView: ImageView = helper.getView(R.id.imaging_evaluation_file_picture)
        Glide.with(mContext).load(path).into(imageView)
    }
}