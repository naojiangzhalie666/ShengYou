package com.xiaoshanghai.nancang.callback

import com.xiaoshanghai.nancang.net.bean.ReportFoot

interface ReportViewCallback {

    fun reportResult(result: ReportFoot)

}