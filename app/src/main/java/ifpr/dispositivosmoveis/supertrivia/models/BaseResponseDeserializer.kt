package ifpr.dispositivosmoveis.supertrivia.models

import com.google.gson.*
import java.lang.Exception
import java.lang.reflect.Type

class BaseResponseDeserializer : JsonDeserializer<BaseResponse<*>> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BaseResponse<*>? {
        val content = json.asJsonObject["data"]
        val data = content.asJsonObject;
        val key = data.keySet().first()

        try {
            val obj = data.asJsonObject[key]
            json.asJsonObject.remove("data")
            json.asJsonObject.add("data", obj)
            return Gson().fromJson(json, typeOfT);
        } catch (e: Exception) {
            try {
                val obj = data.getAsJsonArray(key)
                json.asJsonObject.remove("data")
                json.asJsonObject.add("data", obj)
                return Gson().fromJson(json, typeOfT);
            } catch (e: Exception) {
            }
        }
        return Gson().fromJson(json, typeOfT);
    }
}