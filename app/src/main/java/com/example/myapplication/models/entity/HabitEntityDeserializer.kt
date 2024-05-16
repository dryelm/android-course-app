import com.example.myapplication.models.entity.HabitEntity
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class HabitEntityDeserializer : JsonDeserializer<HabitEntity> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): HabitEntity {
        val jsonObject = json.asJsonObject
        return HabitEntity(
            uid = jsonObject.get("uid").asString.toInt(),
            name = jsonObject.get("title").asString,
            description = jsonObject.get("description").asString,
            priority = jsonObject.get("priority").asInt.toString(),
            type = jsonObject.get("type").asInt.toString(),
            days = jsonObject.get("frequency").asInt,
            times = jsonObject.get("count").asInt,
            date = jsonObject.get("date").asInt
        )
    }
}