package domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Account {
    private long pk;
    private String id;
    private String password;
    private String name;
    private Set<PetPlant> myPets = new HashSet<>();
    private String token;

    public static class Builder{
        private long pk;
        private String id;
        private String password;
        private String token;

        private Builder(String id, String password) {
            this.pk = -1;
            this.id = id;
            this.password = password;
        }

        public Builder pk(long value){
            pk = value;
            return this;
        }

        public Builder token(String value){
            token = value;
            return this;
        }

        public Account build(){
            return new Account(this);
        }
    }

    public static Builder builder(String id, String password){
        return new Builder(id, password);
    }

    public Account(){}
    private Account(Builder builder){
        pk = builder.pk;
        id = builder.id;
        password = builder.password;
        token = builder.token;
    }

    public boolean checkPassword(String pw){
        return password.equals(pw);
    }

    public void changePassword(String pw){
        password = pw;
    }

    public String getToken(){
        return token;
    }

    @Override
    public String toString() {
        return "Account{" +
                "pk=" + pk +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return pk == account.pk && Objects.equals(id, account.id) && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, id, password);
    }
}
