package infra.network;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {
    public enum Method{
        GET, POST
    }

    public String token;
    public Method method;
    public String url;
    public Map<String, Object> data; //TODO : Object -> DTO 인터페이스로 바꿀것

    public Request(Method method, String url) {
        this.method = method;
        this.url = url;
        data = new HashMap<>();
    }
}
