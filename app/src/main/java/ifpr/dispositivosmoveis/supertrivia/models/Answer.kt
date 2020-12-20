package ifpr.dispositivosmoveis.supertrivia.models

data class Answer(
    var description: String,
    var order: Number
) {
    var id: Long? = null

    override fun equals(other: Any?) = other is User && this.id == other.id
}