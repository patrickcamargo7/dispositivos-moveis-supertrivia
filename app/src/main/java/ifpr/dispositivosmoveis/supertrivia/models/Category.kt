package ifpr.dispositivosmoveis.supertrivia.models

data class Category(
    var name: String,
    var id: Number
) {
    override fun equals(other: Any?) = other is Category && this.id == other.id
}