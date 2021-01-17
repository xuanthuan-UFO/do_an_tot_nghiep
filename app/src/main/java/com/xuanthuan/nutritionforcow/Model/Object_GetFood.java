package com.xuanthuan.nutritionforcow.Model;

public class Object_GetFood {
    int index;
    String name;
    int kg;

    public Object_GetFood(int index, String name, int kg) {
        this.index = index;
        this.name = name;
        this.kg = kg;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }
}
