package com.example.votingapp.Models;

public class KomitetModel {
    public String ID;
    public String Nazwa = "Name";
    public int Nrlisty = 3;
    public int LogoNazwa = -1;
    public KomitetModel() { }
    public KomitetModel(String Name_, int Img_, int Nr_) {
        this.Nazwa = Name_;
        this.LogoNazwa = Img_;
        this.Nrlisty = Nr_;
    }
}
