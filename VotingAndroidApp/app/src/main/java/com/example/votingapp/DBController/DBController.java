package com.example.votingapp.DBController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.votingapp.Models.CodeModel;
import com.example.votingapp.Models.KandydatModel;
import com.example.votingapp.Models.KomitetModel;

import java.util.ArrayList;

public class DBController extends SQLiteOpenHelper {
    private static DBController controllerInstance;
    private static SQLiteDatabase dbReference;
    private static final String DATABASE_NAME = "votingDB.db";

    //CODE TABLE
    private static final String TABLE_NAME_CODE = "code_table";
    private static final String[] COLS_NAME_CODE = {
        "ID",
        "Code",
        "wasUsed",
        "IDKanydatSejmu",
        "IDKandydatSenatu"
    };
    private static final String[] COLS_CODE = {
            "INTEGER PRIMARY KEY AUTOINCREMENT",
            "TEXT",
            "INTEGER",
            "TEXT",
            "TEXT"
    };
    //KOMITET TABLE
    private static final String TABLE_NAME_KOMITET = "komitet_table";
    private static final String[] COLS_NAME_KOMITET = {
            "ID",
            "Nazwa",
            "Nrlisty",
            "Img"
    };
    private static final String[] COLS_KOMITET = {
            "TEXT",
            "TEXT",
            "INTEGER",
            "INTEGER"
    };

    //KANDYDAT TABLE
    private static final String TABLE_NAME_KANDYDAT = "kandydat_table";
    private static final String[] COLS_NAME_KANDYDAT = {
            "ID",
            "Imie",
            "Nazwisko",
            "Img",
            "Opis",
            "czy_senat",
            "Nrlisty",
            "IDKomitetu",
            "IDOkregu",
            "Okreg"
    };
    private static final String[] COLS_KANDYDAT= {
            "TEXT",
            "TEXT",
            "TEXT",
            "INTEGER",
            "TEXT",
            "INTEGER",
            "INTEGER",
            "TEXT",
            "TEXT",
            "TEXT"
    };

    public static synchronized DBController getInstance(Context context){
        if(controllerInstance == null)
            controllerInstance = new DBController(context.getApplicationContext());
        return controllerInstance;
    }

    public DBController(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        dbReference = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_CODE + CreateColString(COLS_NAME_CODE, COLS_CODE));
        db.execSQL("create table " + TABLE_NAME_KOMITET + CreateColString(COLS_NAME_KOMITET, COLS_KOMITET));
        db.execSQL("create table " + TABLE_NAME_KANDYDAT + CreateColString(COLS_NAME_KANDYDAT, COLS_KANDYDAT));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CODE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_KOMITET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_KANDYDAT);
        onCreate(db);
    }
    private String CreateColString(String[] name, String[] cols){
        String retString = "(";
        for(int i = 0; i < name.length; i++){
            if(i == name.length - 1)
                retString += name[i] + " " + cols[i];
            else
                retString += name[i] + " " + cols[i] + ",";
        }
        retString += ")";

        return retString;
    }
    public ArrayList<CodeModel>  getSingleCode(String code) {
        Cursor cursor = dbReference.query(TABLE_NAME_CODE, null,
                "Code=?", new String[]{code}, null, null, null);

        ArrayList<CodeModel> lista = new ArrayList<CodeModel>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                CodeModel model = new CodeModel();
                int index;

                index = cursor.getColumnIndex(COLS_NAME_CODE[0]);
                if (index != -1) model.ID = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_CODE[1]);
                if (index != -1) model.Code = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_CODE[2]);
                if (index != -1) model.wasUsed = (cursor.getInt(index) != 0);

                index = cursor.getColumnIndex(COLS_NAME_CODE[3]);
                if (index != -1) model.IDKandydatSejmu = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_CODE[4]);
                if (index != -1) model.IDKandydatSenatu = cursor.getString(index);

                lista.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return lista;
    }
    public void changeCodeSejm(String code, String guidSejm){
        ArrayList<CodeModel> list = getSingleCode(code);
        if(list.size() == 0) return;

        ContentValues x = new ContentValues();
        x.put(COLS_NAME_CODE[3], guidSejm);
        dbReference.update(TABLE_NAME_CODE, x, "Code=?", new String[]{code});
    }
    public void changeCodeSenat(String code, String guidSenat){
        ArrayList<CodeModel> list = getSingleCode(code);
        if(list.size() == 0) return;

        ContentValues x = new ContentValues();
        x.put(COLS_NAME_CODE[4], guidSenat);
        dbReference.update(TABLE_NAME_CODE, x, "Code=?", new String[]{code});
    }

    public long insertCode(CodeModel data){
        ContentValues x = new ContentValues();
        x.put(COLS_NAME_CODE[1], data.Code);
        x.put(COLS_NAME_CODE[2], data.wasUsed);
        return dbReference.insert(TABLE_NAME_CODE, null, x);
    }

    public long insertKandydat(KandydatModel data){
        ContentValues x = new ContentValues();
        x.put(COLS_NAME_KANDYDAT[0], data.ID);
        x.put(COLS_NAME_KANDYDAT[1], data.Imie);
        x.put(COLS_NAME_KANDYDAT[2], data.Nazwisko);
        x.put(COLS_NAME_KANDYDAT[3], data.Zdjecie);
        x.put(COLS_NAME_KANDYDAT[4], data.Opis);
        x.put(COLS_NAME_KANDYDAT[5], data.czySenat);
        x.put(COLS_NAME_KANDYDAT[6], data.Nr_listy);
        x.put(COLS_NAME_KANDYDAT[7], data.IDKomitetu);
        x.put(COLS_NAME_KANDYDAT[8], data.IDokreg);
        x.put(COLS_NAME_KANDYDAT[9], data.Okreg);
        return dbReference.insert(TABLE_NAME_KANDYDAT, null, x);
    }

    public long insertKomitet(KomitetModel data){
        ContentValues x = new ContentValues();
        x.put(COLS_NAME_KOMITET[0], data.ID);
        x.put(COLS_NAME_KOMITET[1], data.Nazwa);
        x.put(COLS_NAME_KOMITET[2], data.Nrlisty);
        x.put(COLS_NAME_KOMITET[3], data.LogoNazwa);
        return dbReference.insert(TABLE_NAME_KOMITET, null, x);
    }

    public void clearBeforeSychronizing(){
        deleteKandydaci();
        deleteKomitety();
    }
    public int deleteKomitety(){
        return dbReference.delete(TABLE_NAME_KOMITET, "1", null);
    }
    public int deleteKandydaci(){
        return dbReference.delete(TABLE_NAME_KANDYDAT, "1", null);
    }
    public void useCode(String code){
        ContentValues x = new ContentValues();
        x.put(COLS_NAME_CODE[1], code);
        x.put(COLS_NAME_CODE[2], 1);

        dbReference.update(TABLE_NAME_CODE, x, "Code=?", new String[]{ code });
    }
    public ArrayList<CodeModel> getCodes() {
        Cursor cursor = dbReference.query(TABLE_NAME_CODE, null, null, null,
                null, null, null);

        ArrayList<CodeModel> lista = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                CodeModel model = new CodeModel();
                int index;

                index = cursor.getColumnIndex(COLS_NAME_CODE[0]);
                if (index != -1) model.ID = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_CODE[1]);
                if (index != -1) model.Code = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_CODE[2]);
                if (index != -1) model.wasUsed = (cursor.getInt(index) != 0);

                lista.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return lista;
    }

    public ArrayList<KandydatModel> getKandydaci() {
        Cursor cursor = dbReference.query(TABLE_NAME_KANDYDAT, null, null, null,
                null, null, null);

        ArrayList<KandydatModel> lista = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                KandydatModel model = new KandydatModel();
                int index;

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[0]);
                if (index != -1) model.ID = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[1]);
                if (index != -1) model.Imie = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[2]);
                if (index != -1) model.Nazwisko = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[3]);
                if (index != -1) model.Zdjecie = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[4]);
                if (index != -1) model.Opis  = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[5]);
                if (index != -1) model.czySenat = (cursor.getInt(index) != 0);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[6]);
                if (index != -1) model.Nr_listy = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[7]);
                if (index != -1) model.IDKomitetu = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[8]);
                if (index != -1) model.IDokreg = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[9]);
                if (index != -1) model.Okreg = cursor.getString(index);

                lista.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return lista;
    }

    public ArrayList<KandydatModel> getKandydaciSenat(String ID_partii){
        Cursor cursor = dbReference.query(TABLE_NAME_KANDYDAT, null,
                "IDKomitetu=? and czy_senat=?", new String[]{ID_partii, "1"}, null, null, null);

        ArrayList<KandydatModel> lista = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                KandydatModel model = new KandydatModel();
                int index;

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[0]);
                if (index != -1) model.ID = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[1]);
                if (index != -1) model.Imie = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[2]);
                if (index != -1) model.Nazwisko = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[3]);
                if (index != -1) model.Zdjecie = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[4]);
                if (index != -1) model.Opis  = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[5]);
                if (index != -1) model.czySenat = (cursor.getInt(index) != 0);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[6]);
                if (index != -1) model.Nr_listy = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[7]);
                if (index != -1) model.IDKomitetu = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[8]);
                if (index != -1) model.IDokreg = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[9]);
                if (index != -1) model.Okreg = cursor.getString(index);

                lista.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return lista;
    }
    public ArrayList<KandydatModel> getKandydaciSejm(String ID_partii){
        Cursor cursor = dbReference.query(TABLE_NAME_KANDYDAT, null,
                "IDKomitetu=? and czy_senat=?", new String[]{ID_partii, "0"}, null, null, null);

        ArrayList<KandydatModel> lista = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                KandydatModel model = new KandydatModel();
                int index;

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[0]);
                if (index != -1) model.ID = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[1]);
                if (index != -1) model.Imie = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[2]);
                if (index != -1) model.Nazwisko = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[3]);
                if (index != -1) model.Zdjecie = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[4]);
                if (index != -1) model.Opis  = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[5]);
                if (index != -1) model.czySenat = (cursor.getInt(index) != 0);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[6]);
                if (index != -1) model.Nr_listy = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[7]);
                if (index != -1) model.IDKomitetu = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[8]);
                if (index != -1) model.IDokreg = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[9]);
                if (index != -1) model.Okreg = cursor.getString(index);

                lista.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return lista;
    }
    public ArrayList<KomitetModel> getKomitety() {
        Cursor cursor = dbReference.query(TABLE_NAME_KOMITET, null, null, null,
                null, null, null);

        ArrayList<KomitetModel> lista = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                KomitetModel model = new KomitetModel();
                int index;

                index = cursor.getColumnIndex(COLS_NAME_KOMITET[0]);
                if (index != -1) model.ID = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KOMITET[1]);
                if (index != -1) model.Nazwa = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KOMITET[2]);
                if (index != -1) model.Nrlisty = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KOMITET[3]);
                if (index != -1) model.LogoNazwa = cursor.getInt(index);

                lista.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return lista;
    }
}