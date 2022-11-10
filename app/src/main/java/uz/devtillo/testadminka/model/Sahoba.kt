package uz.devtillo.testadminka.model

import java.io.Serializable

data class Sahoba(
    var id: Int? = null,
    var name: String? = null,
    var born: String? = null,
) : Serializable