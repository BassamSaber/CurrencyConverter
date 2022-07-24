package com.samz.convertcurrency.model

import androidx.lifecycle.MutableLiveData

class UIViewResourceLiveData : MutableLiveData<UIViewResource>() {
    fun setValue(
        message: String?,
        type: UIViewTypes = UIViewTypes.DIALOG,
        state: UIViewSTATE = UIViewSTATE.SHOW
    ) {
        value = UIViewResource(message, type, state)
    }
}

data class UIViewResource(
    val message: String?,
    val type: UIViewTypes,
    val state: UIViewSTATE
)

enum class UIViewTypes {
    TOAST,
    DIALOG
}

enum class UIViewSTATE {
    SHOW,
    DISMISS
}