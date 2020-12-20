package ifpr.dispositivosmoveis.supertrivia.network.services

import ifpr.dispositivosmoveis.supertrivia.models.BaseResponse
import ifpr.dispositivosmoveis.supertrivia.models.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CategoryService {
    @GET("categories")
    @Headers("Content-Type: application/json")
    fun findAll(): Call<BaseResponse<List<Category>>>
}