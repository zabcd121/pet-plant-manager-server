package infra.network;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Response implements Serializable {
    public enum StatusCode{
        SUCCESS, FAIL
    }

    public StatusCode statusCode;
    public Map<String, Object> data; //TODO : Object -> DTO interface로 변환

    public Response(StatusCode statusCode) {
        this.statusCode = statusCode;
        data = new HashMap<>();
    }
}
