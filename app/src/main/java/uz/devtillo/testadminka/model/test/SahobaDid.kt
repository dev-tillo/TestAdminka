package uz.devtillo.testadminka.model.test

import java.io.Serializable

data class SahobaDid(
    var id: Int = 0,
    var what_did_he: String = "",
    var description: String = "",
    var author: String = "",
) : Serializable