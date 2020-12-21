package ifpr.dispositivosmoveis.supertrivia.models

import com.google.gson.annotations.SerializedName

data class FixProblem(
    var status: String,
    var score: Number,
    @SerializedName("correct_answer")
    var correctAnswer: Answer
) {
    fun isCorrect() : Boolean {
        return status != "incorrect"
    }
}