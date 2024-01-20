package com.example.votingapp.DBController;

import static java.lang.Math.floor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.votingapp.Models.CodeModel;
import com.example.votingapp.Models.KandydatModel;
import com.example.votingapp.Models.KomitetModel;
import com.example.votingapp.Models.UserModel;
import com.example.votingapp.R;

import java.util.ArrayList;
import java.util.List;

public class DBController extends SQLiteOpenHelper {
    private static DBController controllerInstance;
    private static SQLiteDatabase dbReference;
    private static final String DATABASE_NAME = "votingDB.db";

    //CODE TABLE
    private static final String TABLE_NAME_CODE = "code_table";
    private static final String[] COLS_NAME_CODE = {
        "ID",
        "Name",
        "wasUsed"
    };
    private static final String[] COLS_CODE = {
            "INTEGER PRIMARY KEY AUTOINCREMENT",
            "TEXT",
            "INTEGER"
    };

    //USER TABLE
    private static final String TABLE_NAME_USER = "user_table";
    private static final String[] COLS_NAME_USER = {
            "ID",
            "login",
            "password"
    };
    private static final String[] COLS_USER = {
            "INTEGER PRIMARY KEY AUTOINCREMENT",
            "TEXT",
            "TEXT"
    };

    //KOMITET TABLE
    private static final String TABLE_NAME_KOMITET = "komitet_table";
    private static final String[] COLS_NAME_KOMITET = {
            "ID",
            "Name",
            "Nr_listy",
            "Img"
    };
    private static final String[] COLS_KOMITET = {
            "INTEGER PRIMARY KEY AUTOINCREMENT",
            "TEXT",
            "INTEGER",
            "INTEGER"
    };

    //KANDYDAT TABLE
    private static final String TABLE_NAME_KANDYDAT = "kandydat_table";
    private static final String[] COLS_NAME_KANDYDAT = {
            "ID",
            "Name",
            "Surname",
            "Img",
            "Desc",
            "Nr_listy",
            "okreg",
            "czy_senat",
            "ID_webserver",
            "ID_partii"
    };
    private static final String[] COLS_KANDYDAT= {
            "INTEGER PRIMARY KEY AUTOINCREMENT",
            "TEXT",
            "TEXT",
            "INTEGER",
            "TEXT",
            "INTEGER",
            "TEXT",
            "INTEGER",
            "TEXT",
            "INTEGER"
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
        db.execSQL("create table " + TABLE_NAME_USER + CreateColString(COLS_NAME_USER, COLS_USER));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CODE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_KOMITET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_KANDYDAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
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

    public long insertUser(UserModel data){
        ContentValues x = new ContentValues();
        x.put(COLS_NAME_USER[1], data.login);
        x.put(COLS_NAME_USER[2], data.password);
        return dbReference.insert(TABLE_NAME_USER, null, x);
    }

    public long insertCode(CodeModel data){
        ContentValues x = new ContentValues();
        x.put(COLS_NAME_CODE[1], data.Name);
        x.put(COLS_NAME_CODE[2], data.wasUsed);
        return dbReference.insert(TABLE_NAME_CODE, null, x);
    }

    public long insertKandydat(KandydatModel data){
        ContentValues x = new ContentValues();
        x.put(COLS_NAME_KANDYDAT[1], data.Name);
        x.put(COLS_NAME_KANDYDAT[2], data.Surname);
        x.put(COLS_NAME_KANDYDAT[3], data.Img);
        x.put(COLS_NAME_KANDYDAT[4], data.Desc);
        x.put(COLS_NAME_KANDYDAT[5], data.Nr_listy);
        x.put(COLS_NAME_KANDYDAT[6], data.Okreg);
        x.put(COLS_NAME_KANDYDAT[7], data.czySenat);
        x.put(COLS_NAME_KANDYDAT[8], data.Guid);
        return dbReference.insert(TABLE_NAME_KANDYDAT, null, x);
    }

    public long insertKomitet(KomitetModel data){
        ContentValues x = new ContentValues();
        x.put(COLS_NAME_KOMITET[1], data.Name);
        x.put(COLS_NAME_KOMITET[2], data.Nr_listy);
        x.put(COLS_NAME_KOMITET[3], data.Img);
        return dbReference.insert(TABLE_NAME_KOMITET, null, x);
    }

    public int deleteKandydaci(){
        return dbReference.delete(TABLE_NAME_KANDYDAT, "1", null);
    }
    public void useCode(String code){
        ContentValues x = new ContentValues();
        x.put(COLS_NAME_CODE[1], code);
        x.put(COLS_NAME_CODE[2], 1);

        dbReference.update(TABLE_NAME_CODE, x, "Name=?", new String[]{ code });
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
                if (index != -1) model.Name = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_CODE[2]);
                if (index != -1) model.wasUsed = cursor.getInt(index);

                lista.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return lista;
    }
    public boolean isUserCorrect(String login, String password) {
        Cursor cursor = dbReference.query(TABLE_NAME_USER, null, "login=? AND password=?", new String[] { login, password },
                null, null, null);

        ArrayList<UserModel> lista = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                UserModel model = new UserModel();
                int index;

                index = cursor.getColumnIndex(COLS_NAME_USER[0]);
                if (index != -1) model.ID = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_USER[1]);
                if (index != -1) model.login = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_USER[2]);
                if (index != -1) model.password = cursor.getString(index);

                lista.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return lista.size() == 1;
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
                if (index != -1) model.ID = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[1]);
                if (index != -1) model.Name = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[2]);
                if (index != -1) model.Surname = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[3]);
                if (index != -1) model.Img = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[4]);
                if (index != -1) model.Desc = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[5]);
                if (index != -1) model.Nr_listy = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[6]);
                if (index != -1) model.Okreg = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[7]);
                if (index != -1) model.czySenat = (cursor.getInt(index) != 0);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[8]);
                if (index != -1) model.Guid = cursor.getString(index);

                lista.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return lista;
    }
    public ArrayList<KandydatModel> getKandydaciSejm(int nrListy){
        Cursor cursor = dbReference.query("", null, null, null, null, null, null);

        ArrayList<KandydatModel> lista = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                KandydatModel model = new KandydatModel();
                int index;

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[0]);
                if (index != -1) model.ID = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[1]);
                if (index != -1) model.Name = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[2]);
                if (index != -1) model.Surname = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[3]);
                if (index != -1) model.Img = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[4]);
                if (index != -1) model.Desc = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[5]);
                if (index != -1) model.Nr_listy = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[6]);
                if (index != -1) model.Okreg = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[7]);
                if (index != -1) model.czySenat = (cursor.getInt(index) != 0);

                index = cursor.getColumnIndex(COLS_NAME_KANDYDAT[8]);
                if (index != -1) model.Guid = cursor.getString(index);

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
                if (index != -1) model.ID = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KOMITET[1]);
                if (index != -1) model.Name = cursor.getString(index);

                index = cursor.getColumnIndex(COLS_NAME_KOMITET[2]);
                if (index != -1) model.Nr_listy = cursor.getInt(index);

                index = cursor.getColumnIndex(COLS_NAME_KOMITET[3]);
                if (index != -1) model.Img = cursor.getInt(index);

                lista.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return lista;
    }
}