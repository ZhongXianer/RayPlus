package com.ksballetba.rayplus.data.bean.sampleData

data class SampleSelectBodyBean(
    var code: Int,
    var `data`: List<Data>,
    var msg: String
) {
    data class Data(
        var id: Int,
        var name: String
    )
}