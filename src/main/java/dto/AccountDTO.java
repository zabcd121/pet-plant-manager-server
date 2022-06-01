package dto;

import java.io.Serializable;

public class AccountDTO implements Serializable {
    private long pk;
    private String id;
    private String password;
    private String token;
    private String address;
    private double x;
    private double y;

    public static class Builder{
        private long pk;
        private String id;
        private String password;
        private String token;
        private String address;
        private double x;
        private double y;

        public Builder pk(long value){
            pk = value;
            return this;
        }

        public Builder id(String value){
            id = value;
            return this;
        }

        public Builder address(String value){
            address = value;
            return this;
        }

        public Builder x(double value){
            x = value;
            return this;
        }

        public Builder y(double value){
            y = value;
            return this;
        }

        public Builder password(String value){
            password = value;
            return this;
        }

        public Builder token(String value){
            token = value;
            return this;
        }

        public AccountDTO build(){
            return new AccountDTO(this);
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    public AccountDTO(){}
    private AccountDTO(Builder builder) {
        pk = builder.pk;
        id = builder.id;
        password = builder.password;
        token = builder.token;
    }

    public long getPk() {
        return pk;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getToken(){return token;}

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
