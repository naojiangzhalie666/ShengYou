package com.xiaoshanghai.nancang.net.bean

 data class GetAppPayBuyTicketBean(
    val `data`: Data,
    val msg: String,
    val status: Int,
    val tiemstamp: Int,
    val url: String
)

data class Data(
    val orderString: String
)