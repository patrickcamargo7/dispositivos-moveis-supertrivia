package ifpr.dispositivosmoveis.supertrivia.models

data class FixProblem(
    var status: String,
    var score: Number,
    var correctAnswer: Answer
)