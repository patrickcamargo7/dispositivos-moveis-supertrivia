package ifpr.dispositivosmoveis.supertrivia.models

data class Problem(
    var question: String,
    var difficulty: String,
    var category: Category,
    var answers: List<Answer>
) {
    var id: Long? = null

    override fun equals(other: Any?) = other is User && this.id == other.id
}