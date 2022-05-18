package dto;

import java.io.Serializable;

public class MessageDTO implements Serializable {
    private String msg;

    public MessageDTO(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
