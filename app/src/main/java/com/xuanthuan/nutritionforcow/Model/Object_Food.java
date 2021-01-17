package com.xuanthuan.nutritionforcow.Model;

public class Object_Food {
    int index;
    String nameFood;
    double dM, cP, mE;
    float kg;

    public Object_Food(double dM, double cP, double mE) {
        this.dM = dM;
        this.cP = cP;
        this.mE = mE;
    }

    public Object_Food(int index, String nameFood, double dM, double cP, double mE,float kg) {
        this.index = index;
        this.nameFood = nameFood;
        this.dM = dM;
        this.cP = cP;
        this.mE = mE;
        this.kg = kg;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getcP() {
        return cP;
    }

    public void setcP(double cP) {
        this.cP = cP;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public double getdM() {
        return dM;
    }

    public void setdM(double dM) {
        this.dM = dM;
    }


    public double getCP() {
        return cP;
    }

    public void setCP(double cP) {
        this.cP = cP;
    }

    public double getmE() {
        return mE;
    }

    public void setmE(double mE) {
        this.mE = mE;
    }

    public float getKg() {
        return kg;
    }

    public void setKg(float kg) {
        this.kg = kg;
    }
}
