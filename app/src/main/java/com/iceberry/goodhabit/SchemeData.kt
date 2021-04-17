package com.iceberry.goodhabit

import com.haibin.calendarview.Calendar
import java.io.Serializable

/**
 * author: Iceberry
 * email:qddwork@outlook.com
 * date: 2021/4/17
 * desc:
 *
 */
data class SchemeData(
    val versionCode: Int,
    val serializeTimeMillis: Long,
    val mSchemeDates: MutableMap<String, Calendar>
) : Serializable
