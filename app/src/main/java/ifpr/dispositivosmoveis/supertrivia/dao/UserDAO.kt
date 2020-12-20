package ifpr.dispositivosmoveis.supertrivia.dao

import ifpr.dispositivosmoveis.supertrivia.models.BaseResponse
import ifpr.dispositivosmoveis.supertrivia.models.User
import ifpr.dispositivosmoveis.supertrivia.network.services.UserService
import ifpr.dispositivosmoveis.supertrivia.util.Factories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDAO {
    private val retrofit = Factories.getApplicationRetrofit()
    private val service = retrofit.create(UserService::class.java)

    fun authenticate(
        username: String,
        password: String,
        finished: (user: BaseResponse<User>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        service.authenticate(username, password).enqueue(object : Callback<BaseResponse<User>> {
            override fun onResponse(call: Call<BaseResponse<User>>, response: Response<BaseResponse<User>>) {
                val user = response.body()!!

                finished(user)

                //onError(Exception())
            }

            override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {
                onError(t)
            }
        })
    }
}