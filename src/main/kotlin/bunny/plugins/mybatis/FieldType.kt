package bunny.plugins.mybatis

object FieldType {
    private val MAP = HashMap<String, String?>()

    init {
        MAP["String"] = "'"
        MAP["Boolean"] = ""
        MAP["Character"] = "'"
        MAP["Byte"] = ""
        MAP["Short"] = ""
        MAP["Integer"] = ""
        MAP["Long"] = ""
        MAP["Float"] = ""
        MAP["Double"] = ""
        MAP["BigDecimal"] = ""
        MAP["Date"] = "'"
        MAP["Timestamp"] = "'"
        MAP["LocalDate"] = "'"
        MAP["LocalTime"] = "'"
        MAP["LocalDateTime"] = "'"
    }

    @JvmStatic
    fun isNormalType(typeName: String): Boolean {
        return MAP.containsKey(typeName)
    }

    fun getDeepStartType(typeName: String?): String? {
        if (typeName == null || typeName.isEmpty()) {
            return typeName
        }

        val i = typeName.indexOf("<")
        return if (i < 0) typeName else typeName.substring(0, i)
    }

    @JvmStatic
    fun getDefaultValue(key: String): String? {
        return if (MAP[key] == null) MAP["String"] else MAP[key]
    }
}
