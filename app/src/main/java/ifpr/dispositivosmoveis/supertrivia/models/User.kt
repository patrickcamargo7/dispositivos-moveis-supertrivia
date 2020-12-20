package ifpr.dispositivosmoveis.supertrivia.models

data class User(
    var name: String,
    var password: String,
    var email: String,
    var token: String
) {
    var id: Long? = null

    override fun equals(other: Any?) = other is User && this.id == other.id
}