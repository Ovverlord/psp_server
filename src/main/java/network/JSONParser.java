package network;

import com.google.gson.Gson;

public class JSONParser {

    public static String jsonFromObject(Object obj)
    {
        Gson g = new Gson();
        return g.toJson(obj, obj.getClass());
    }

    public static <T> T objectFromJson(String json, Class<T> cls)
    {
        Gson g = new Gson();
        return g.fromJson(json, cls);
    }
}
