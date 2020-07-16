package com.ksballetba.rayplus.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ksballetba.rayplus.R
import com.ksballetba.rayplus.data.bean.baseData.ImagingEvaluationFileListBean
import com.ksballetba.rayplus.network.RetrofitClient.Companion.BASE_URL

class ImagingEvaluationFileAdapter(
    layoutResId: Int, data: List<ImagingEvaluationFileListBean.Data?>
) : BaseQuickAdapter<ImagingEvaluationFileListBean.Data?, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: ImagingEvaluationFileListBean.Data?) {
        helper.setText(R.id.imaging_evaluation_file_name, "文件名:${item?.fileName}")
            .setText(R.id.imaging_evaluation_create_time, "创建日期:${item?.fileCreateTime}")
            .setText(R.id.imaging_evaluation_file_size, "文件大小:${item?.fileSize}")
            .addOnClickListener(R.id.iv_delete_item_imaging_evaluation_file)
        val path = "${BASE_URL}p1/api/static/tempFiles${item?.filePath?.substring(1)}"
        val imageView: ImageView = helper.getView(R.id.imaging_evaluation_file_picture)
        Glide.with(mContext).load(path).into(imageView)
    }
}