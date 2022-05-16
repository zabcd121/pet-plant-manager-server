package DTO;

public class AccountDTO {
    private long pk;
    private String id;
    private String password;

    public static class Builder{
        private long pk;
        private String id;
        private String password;

        public Builder pk(long value){
            pk = value;
            return this;
        }

        public Builder id(String value){
            id = value;
            return this;
        }

        public Builder password(String value){
            password = value;
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

}
