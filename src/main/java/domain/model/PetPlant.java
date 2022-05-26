package domain.model;

import java.util.Date;

public class PetPlant{
    private int pk;
    private int plantID;
    private String petName;
    private Date firstMetDay;
    private Byte[] petImg;

    private PetPlant(Builder builder){
        this.pk = builder.pk;
        this.plantID = builder.plantID;
        this.petName = builder.petName;
        this.firstMetDay = builder.firstMetDay;
        this.petImg = builder.petImg;
    }

    public static class Builder{
        private int pk = -1;
        private int plantID;
        private String petName;
        private Date firstMetDay;
        private Byte[] petImg;

        public Builder(int plantID, String petName, Date firstMetDay){
            this.plantID = plantID;
            this.petName = petName;
            this.firstMetDay = firstMetDay;
        }

        public Builder id(int pk){
            this.pk = pk;
            return this;
        }

        public Builder petName(String petName){
            this.petName = petName;
            return this;
        }

        public Builder firstMetDay(Date firstMetDay){
            this.firstMetDay = firstMetDay;
            return this;
        }

        public Builder petImg(Byte[] petImg){
            this.petImg = petImg;
            return this;
        }

        public PetPlant build(){
            validate();
            return new PetPlant(this);
        }

        private void validate() throws IllegalArgumentException{
            validate(petName);
        }

        private void validate(String petName){
            if(petName.length() > 5){
                throw new IllegalArgumentException("반려식물 이름은 5글자까지 가능합니다.");
            }
        }
    }

    public static Builder builder(int plantID, String petName, Date firstMetDay) {
        return new Builder(plantID, petName, firstMetDay);
    }

    public int getPk() {
        return pk;
    }

    public int getPlantID() {
        return plantID;
    }

    public String getPetName() {
        return petName;
    }

    public Date getFirstMetDay() {
        return firstMetDay;
    }

    public Byte[] getPetImg() { return petImg; }
    public void setPk(int pk) {
        this.pk = pk;
    }

    public void setPlantID(int plantID) {
        this.plantID = plantID;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setFirstMetDay(Date firstMetDay) {
        this.firstMetDay = firstMetDay;
    }

    public void setPetImg(Byte[] petImg) {
        this.petImg = petImg;
    }

    @Override
    public String toString() {
        return "PetPlant{" +
                "pk=" + pk +
                ", plantID=" + plantID +
                ", petName='" + petName + '\'' +
                ", firstMetDay=" + firstMetDay +
                '}';
    }
}
