package ifpr.dispositivosmoveis.supertrivia.util

import com.google.gson.GsonBuilder
import ifpr.dispositivosmoveis.supertrivia.models.BaseResponse
import ifpr.dispositivosmoveis.supertrivia.models.BaseResponseDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class Factories {
    companion object{
        fun getApplicationRetrofit() : Retrofit {
            var gson = GsonBuilder()
                .registerTypeAdapter(BaseResponse::class.java, BaseResponseDeserializer())
                .create()

            return Retrofit.Builder()
                .baseUrl(Environment.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}