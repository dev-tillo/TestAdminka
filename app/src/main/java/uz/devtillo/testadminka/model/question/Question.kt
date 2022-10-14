package uz.devtillo.testadminka.model.question

data class Question(
    var id: Int? = null,
    var question: String? = null,
    var answer: String? = null,
    var answer1: String? = null,
    var answer2: String? = null,
    var correct: String? = null
)
