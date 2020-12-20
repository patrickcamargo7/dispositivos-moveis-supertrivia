package ifpr.dispositivosmoveis.supertrivia.network.services

import ifpr.dispositivosmoveis.supertrivia.models.BaseResponse
import ifpr.dispositivosmoveis.supertrivia.models.Ranking
import retrofit2.Call
import retrofit2.http.*

interface RankingService {
    @GET("ranking")
    @Headers("Content-Type: application/json")
    fun findAll(): Call<BaseResponse<List<Ranking>>>
}