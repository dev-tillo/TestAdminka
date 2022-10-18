package uz.devtillo.testadminka.model.test

import uz.devtillo.testadminka.model.question.Question
import java.io.Serializable

data class Test(
    var id: Int = 0,
    var testName: String = "",
    var subId: String = "",
    var count: String = "",
    var level: String = "",
) : Serializable