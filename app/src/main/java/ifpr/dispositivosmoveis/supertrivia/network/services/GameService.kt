package ifpr.dispositivosmoveis.supertrivia.network.services

import ifpr.dispositivosmoveis.supertrivia.models.BaseResponse
import ifpr.dispositivosmoveis.supertrivia.models.FixProblem
import ifpr.dispositivosmoveis.supertrivia.models.Game
import ifpr.dispositivosmoveis.supertrivia.models.Problem
import retrofit2.Call
import retrofit2.http.*

interface GameService {
    @GET("games")
    @Headers("Content-Type: application/json")
    fun findOrCreate(
        @Query("difficulty") difficulty: String?,
        @Query("category_id") categoryId: Number?,
        @Header("Authorization") token: String
    ): Call<BaseResponse<Game>>

    @DELETE("games")
    @Headers("Content-Type: application/json")
    fun finish(@Header("Authorization") token: String): Call<BaseResponse<Game>>

    @GET("problems/next")
    @Headers("Content-Type: application/json")
    fun nextProblem(@Header("Authorization") token: String): Call<BaseResponse<Problem>>

    @GET("problems/view")
    @Headers("Content-Type: application/json")
    fun currentProblem(@Header("Authorization") token: String): Call<BaseResponse<Problem>>

    @POST("problems/answer")
    @Headers("Content-Type: application/json")
    fun sendAnswer(
        @Query("answer") answerId: Number,
        @Header("Authorization") token: String
    ): Call<BaseResponse<FixProblem>>
}