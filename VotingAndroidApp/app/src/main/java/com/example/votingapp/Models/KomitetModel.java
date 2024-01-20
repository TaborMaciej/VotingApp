package com.example.votingapp.Models;

public class KomitetModel {
    public int ID;
    public String Name = "Name";
    public int Nr_listy = 3;
    public int Img = -1;
    public KomitetModel() { }
    public KomitetModel(String Name_, int Img_, int Nr_) {
        this.Name = Name_;
        this.Img = Img_;
        this.Nr_listy = Nr_;
    }
}
