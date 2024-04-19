package com.zaidi.lib

data class DialogModel(
    var isDialogEnable: Boolean? = true,
    var dialogBackgroundColor: Int? = null,
    var dialogTxtColor: Int? = null,
    var dialogTxtTitle: String? = null,
    var dialogTxtDescription: String? = null,
    var dialogCancelBg: Int? = null,
    var dialogSettingBg: Int? = null
)
