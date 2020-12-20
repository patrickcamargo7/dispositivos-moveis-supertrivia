package ifpr.dispositivosmoveis.supertrivia.dao

import ifpr.dispositivosmoveis.supertrivia.models.BaseResponse
import ifpr.dispositivosmoveis.supertrivia.models.Category
import ifpr.dispositivosmoveis.supertrivia.network.services.CategoryService
import ifpr.dispositivosmoveis.supertrivia.util.Factories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDAO {
    private val retrofit = Factories.getApplicationRetrofit()
    private val service = retrofit.create(CategoryService::class.java)

    fun findAll(
        finished: (user: BaseResponse<List<Category>>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        service.findAll().enqueue(object : Callback<BaseResponse<List<Category>>> {
            override fun onResponse(call: Call<BaseResponse<List<Category>>>, response: Response<BaseResponse<List<Category>>>) {
                val categories = response.body()!!
                finished(categories)
            }
            override fun onFailure(call: Call<BaseResponse<List<Category>>>, t: Throwable) {
                onError(t)
            }
        })
    }
}