package infra.network;

import java.util.Map;

public class Request {
    public enum Method{
        GET, POST
    }

    public String token;
    public Method method;
    public String url;
    public Map<String, Object> data; //TODO : Object -> DTO 인터페이스로 바꿀것
}
