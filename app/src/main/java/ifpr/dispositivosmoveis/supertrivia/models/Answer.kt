package ifpr.dispositivosmoveis.supertrivia.models

data class Answer(
    var description: String,
    var order: Number
) {
    override fun equals(other: Any?) = other is Answer && this.order == other.order
}