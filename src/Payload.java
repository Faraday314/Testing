import java.util.HashMap;
import java.util.Map;

public class Payload {
    private final Map<String, Object> objectMap;
    private final Map<String, Class<?>> classMap;
    private String f;

    public Payload() {
        objectMap = new HashMap<>();
        classMap = new HashMap<>();
    }

    public Payload addItem(String id, Object obj) {
        objectMap.put(id, obj);
        classMap.put(id, obj.getClass());
        return this;
    }

    public <T> T getItem(String id) {
        Object obj = objectMap.get(id);
        Class<T> clazz = (Class<T>) classMap.get(id);
        return clazz.cast(obj);
    }
}