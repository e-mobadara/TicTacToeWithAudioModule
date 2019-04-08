package com.example.rick.tictactoe.utils;

public class AudioFile {
    int id;
    String aPath;
    String aName;
    String atype;
    String alangue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AudioFile(int id, String aName, String atype, String alangue) {
        this.id = id;
        this.aName = aName;
        this.atype = atype;
        this.alangue = alangue;
    }

    @Override
    public String toString() {
        return "AudioFile{" +
                "aPath='" + aPath + '\'' +
                ", aName='" + aName + '\'' +
                ", atype='" + atype + '\'' +
                ", alangue='" + alangue + '\'' +
                '}';
    }

    public String getAuri() {
        return auri;

    }

    public void setAuri(String auri) {
        this.auri = auri;
    }

    String auri;

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

    public String getaPath() {
        return aPath;
    }

    public AudioFile(String aPath, String aName) {
        this.aPath = aPath;
        this.aName = aName;
    }
    public AudioFile(){}

    public void setaPath(String aPath) {
        this.aPath = aPath;
    }
    public String getaName() {
        return aName;
    }
    public void setaName(String aName) {
        this.aName = aName;
    }
}