package uz.devtillo.testadminka.model

import uz.devtillo.testadminka.model.test.Test
import java.io.Serializable

data class Subject(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var count: String? = null,
    var author: String? = null,
) : Serializable
