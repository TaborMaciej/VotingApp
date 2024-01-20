package com.example.votingapp.Models;

public class KandydatModel {
    public int ID;
    public String Name = "Name";
    public String Surname = "Surname";
    public int Img = -1;
    public String Desc = "Desc";
    public int Nr_listy = 12;
    public String Okreg = "Okreg";
    public boolean czySenat = false;
    public String Guid = "GUID";
    public int ID_partii = -1;

    public KandydatModel() { }
    public KandydatModel(String Name_, String Surname_, int Img_, String Desc_, int Nr_, String Okreg_, boolean czySenat_, int id_partii)
    {
        this.Name = Name_;
        this.Surname = Surname_;
        this.Img = Img_;
        this.Desc = Desc_;
        this.Nr_listy = Nr_;
        this.Okreg = Okreg_;
        this.czySenat = czySenat_;
        this.ID_partii = id_partii;
    }
}
