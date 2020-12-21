package ifpr.dispositivosmoveis.supertrivia.network.services

import ifpr.dispositivosmoveis.supertrivia.models.BaseResponse
import ifpr.dispositivosmoveis.supertrivia.models.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("users")
    @Headers("Content-Type: application/json")
    fun insert(@Body person: User): Call<BaseResponse<User>>

    @POST("auth")
    @Headers("Content-Type: application/json")
    fun authenticate(@Query("email") username: String, @Query("password") password: String): Call<BaseResponse<User>>
}