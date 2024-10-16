package bunny.mybatis;

import java.util.HashMap;

public class FieldType {

    private static final HashMap<String, String> MAP = new HashMap<>();

    static {
        MAP.put("String", "'");
        MAP.put("Boolean", "");
        MAP.put("Character", "'");
        MAP.put("Byte", "");
        MAP.put("Short", "");
        MAP.put("Integer", "");
        MAP.put("Long", "");
        MAP.put("Float", "");
        MAP.put("Double", "");
        MAP.put("BigDecimal", "");
        MAP.put("Date", "'");
        MAP.put("Timestamp", "'");
        MAP.put("LocalDate", "'");
        MAP.put("LocalTime", "'");
        MAP.put("LocalDateTime", "'");
    }

    public static boolean isNormalType(String typeName) {
        return MAP.containsKey(typeName);
    }

    public static String getDeepStartType(String typeName) {
        if (typeName == null || typeName.isEmpty()) {
            return typeName;
        }

        int i = typeName.indexOf("<");
        return i < 0 ? typeName : typeName.substring(0, i);
    }

    public static String getDefaultValue(String key) {
        return MAP.get(key) == null ? MAP.get("String") : MAP.get(key);
    }
}
