package com.xiaoshanghai.nancang.bean

data class getBaiDUTokenBean(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: String,
    val session_key: String,
    val session_secret: String
)