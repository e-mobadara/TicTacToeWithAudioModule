package com.example.rick.tictactoe.utils;

import com.google.gson.annotations.SerializedName;

public class AudioFileDTO {

    @SerializedName("id")
    private int id;
    @SerializedName("audioname")
    private String aName;
    @SerializedName("type")
    private String atype;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("langue")
    private String alangue;



    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype;
    }

    public String getAlangue() {
        return alangue;
    }

    public void setAlangue(String alangue) {
        this.alangue = alangue;
    }
}
