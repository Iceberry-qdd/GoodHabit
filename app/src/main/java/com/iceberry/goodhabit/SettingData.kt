package com.iceberry.goodhabit

/**
 * author: Iceberry
 * email:qddwork@outlook.com
 * date: 2021/4/2
 * desc:
 *
 */
data class SettingData(
    var theme: String,
    var language: String,
    var autoBackup: Boolean,
    var backupLocation: String,
    var importBackupFileLocation: String
) {
    constructor() : this("default", "default", true, "null", "null")
}
