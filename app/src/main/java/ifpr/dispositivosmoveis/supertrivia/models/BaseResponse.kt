package ifpr.dispositivosmoveis.supertrivia.models

data class BaseResponse<T>(
    var status: String,
    var data: T
) {
    fun isSuccessfully() : Boolean {
        return status == "success"
    }
}