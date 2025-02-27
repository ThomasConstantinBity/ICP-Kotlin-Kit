package com.bity.icp_kotlin_kit.domain.model.request

import com.bity.icp_kotlin_kit.util.DEFAULT_POLLING_SECONDS_TIMEOUT
import com.bity.icp_kotlin_kit.util.DEFAULT_POLLING_SECONDS_WAIT

class PollingValues(
    val durationSeconds: Long = DEFAULT_POLLING_SECONDS_TIMEOUT,
    val waitDurationSeconds: Long = DEFAULT_POLLING_SECONDS_WAIT
)