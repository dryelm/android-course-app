import com.example.myapplication.models.entity.HabitEntity
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class HabitEntitySerializer : JsonSerializer<HabitEntity> {
    override fun serialize(src: HabitEntity, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("count", src.times)
        jsonObject.addProperty("date", src.date)
        jsonObject.addProperty("description", src.description)
        jsonObject.addProperty("frequency", src.days)
        jsonObject.addProperty("priority", priorityToInt(src.priority))
        jsonObject.addProperty("title", src.name)
        jsonObject.addProperty("type", typeToInt(src.type))
        if (src.uid != null) {
            jsonObject.addProperty("uid", src.uid.toString())
        }
        jsonObject.addProperty("color", 0)
        jsonObject.add("done_dates", JsonArray())
        return jsonObject
    }

    private fun priorityToInt(priority: String) =
        when (priority) {
            "Low" -> 0
            "Medium" -> 1
            "High" -> 2
            else -> 1
        }

    private fun typeToInt(type: String) =
        when (type) {
            "Bad" -> 0
            "Good" -> 1
            else -> 0
        }
}

