package ifpr.dispositivosmoveis.supertrivia.dao

import ifpr.dispositivosmoveis.supertrivia.models.*
import ifpr.dispositivosmoveis.supertrivia.network.services.GameService
import ifpr.dispositivosmoveis.supertrivia.util.Factories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class GameDAO {
    private val retrofit = Factories.getApplicationRetrofit()
    private val service = retrofit.create(GameService::class.java)

    fun findOrCreate(
        difficulty: String?,
        categoryId: Number?,
        token: String,
        finished: (user: BaseResponse<Game>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        service.findOrCreate(difficulty, categoryId, token).enqueue(object : Callback<BaseResponse<Game>> {
            override fun onResponse(call: Call<BaseResponse<Game>>, response: Response<BaseResponse<Game>>) {
                val game = response.body()!!
                finished(game)
            }
            override fun onFailure(call: Call<BaseResponse<Game>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun findCurrentProblem(
        token: String,
        finished: (user: BaseResponse<Problem>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        service.currentProblem(token).enqueue(object : Callback<BaseResponse<Problem>> {
            override fun onResponse(call: Call<BaseResponse<Problem>>, response: Response<BaseResponse<Problem>>) {
                if (response.isSuccessful) {
                    val problem = response.body()!!
                    finished(problem)
                } else {
                    if (response.code() == 400) {
                        onError(RequireInitGameFailure())
                    }
                }

            }
            override fun onFailure(call: Call<BaseResponse<Problem>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun findNextProblem(
        token: String,
        finished: (user: BaseResponse<Problem>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        service.nextProblem(token).enqueue(object : Callback<BaseResponse<Problem>> {
            override fun onResponse(call: Call<BaseResponse<Problem>>, response: Response<BaseResponse<Problem>>) {
                val game = response.body()!!
                finished(game)
            }
            override fun onFailure(call: Call<BaseResponse<Problem>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun finishGame(
        token: String,
        finished: (user: BaseResponse<Game>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        service.finish(token).enqueue(object : Callback<BaseResponse<Game>> {
            override fun onResponse(call: Call<BaseResponse<Game>>, response: Response<BaseResponse<Game>>) {
                val game = response.body()!!
                finished(game)
            }
            override fun onFailure(call: Call<BaseResponse<Game>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun sendAnswer(
        answerId: Number,
        token: String,
        finished: (user: BaseResponse<FixProblem>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        service.sendAnswer(answerId, token).enqueue(object : Callback<BaseResponse<FixProblem>> {
            override fun onResponse(call: Call<BaseResponse<FixProblem>>, response: Response<BaseResponse<FixProblem>>) {
                val fixProblem = response.body()!!
                finished(fixProblem)
            }
            override fun onFailure(call: Call<BaseResponse<FixProblem>>, t: Throwable) {
                onError(t)
            }
        })
    }
}

class RequireInitGameFailure: Exception();