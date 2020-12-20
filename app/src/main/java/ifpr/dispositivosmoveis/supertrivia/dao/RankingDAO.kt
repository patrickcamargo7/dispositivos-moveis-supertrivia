package ifpr.dispositivosmoveis.supertrivia.dao

import ifpr.dispositivosmoveis.supertrivia.models.BaseResponse
import ifpr.dispositivosmoveis.supertrivia.models.Ranking
import ifpr.dispositivosmoveis.supertrivia.network.services.RankingService
import ifpr.dispositivosmoveis.supertrivia.util.Factories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankingDAO {
    private val retrofit = Factories.getApplicationRetrofit()
    private val service = retrofit.create(RankingService::class.java)

    fun findAll(
        finished: (user: BaseResponse<List<Ranking>>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        service.findAll().enqueue(object : Callback<BaseResponse<List<Ranking>>> {
            override fun onResponse(call: Call<BaseResponse<List<Ranking>>>, response: Response<BaseResponse<List<Ranking>>>) {
                val ranking = response.body()!!
                finished(ranking)
            }
            override fun onFailure(call: Call<BaseResponse<List<Ranking>>>, t: Throwable) {
                onError(t)
            }
        })
    }
}