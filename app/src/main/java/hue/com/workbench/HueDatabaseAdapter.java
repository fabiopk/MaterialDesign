package hue.com.workbench;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HueDatabaseAdapter {

    HueHelper hueHelper;

    public HueDatabaseAdapter(Context context) {
        hueHelper = new HueHelper(context);
    }

    public long insertData(String name, String address, byte[] image, String cordinate) {
        SQLiteDatabase db = hueHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HueHelper.NAME, name);
        contentValues.put(HueHelper.ADDRESS, address);
        contentValues.put(HueHelper.IMAGE, image);
        contentValues.put(HueHelper.CORDINATE, cordinate);
        long id = db.insert(HueHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public long insertCordinate(String cordinate) {
        SQLiteDatabase db = hueHelper.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();

        long id = db.insert(HueHelper.TABLE_NAME, null, contentValues1);
        return id;
    }

    public String getAllData() {
        SQLiteDatabase db = hueHelper.getWritableDatabase();
        String[] columns = {HueHelper.UID, HueHelper.NAME, HueHelper.ADDRESS};

        Cursor cursor = db.query(HueHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer bf = new StringBuffer();

        while (cursor.moveToNext()) {
            int ID = cursor.getInt(cursor.getColumnIndex(HueHelper.UID));
            String name = cursor.getString(cursor.getColumnIndex(HueHelper.NAME));
            String address = cursor.getString(cursor.getColumnIndex(HueHelper.ADDRESS));
            bf.append(ID + " " + name + " " + address + "\n");
        }
        return bf.toString();
    }

    public List<Restaurante> getRestaurante() {//retorna os dados do banco

        List<Restaurante> lista = new ArrayList<>();
        Restaurante current;

        SQLiteDatabase db = hueHelper.getWritableDatabase();
        String[] columns = {HueHelper.UID, HueHelper.NAME, HueHelper.ADDRESS, HueHelper.IMAGE, HueHelper.CORDINATE};

        Cursor cursor = db.query(HueHelper.TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(HueHelper.NAME));
            String address = cursor.getString(cursor.getColumnIndex(HueHelper.ADDRESS));
            byte[] image = cursor.getBlob(cursor.getColumnIndex(HueHelper.IMAGE));
            String cordinate = cursor.getString(cursor.getColumnIndex(HueHelper.CORDINATE));
            current = new Restaurante();
            current.name = name;
            current.address = address;
            current.image = image;
            current.cordinate = cordinate;
            lista.add(current);
        }


            return lista;

        }

        static class HueHelper extends SQLiteOpenHelper {


            private static final String DATABASE_NAME = "huedatabase.db";
            private static final String TABLE_NAME = "RESTAURANTES";
            private static final int DATABASE_VERSION = 4;
            private static final String UID = "_id";
            private static final String NAME = "Name";
            private static final String ADDRESS = "Address";
            private static final String IMAGE = "Image";
            private static final String CORDINATE = "Cordinate";

            private static final String CREATE_TABLE =
                    "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " + ADDRESS + " VARCHAR(255), " + CORDINATE + " VARCHAR(255), "+ IMAGE + " BLOB);";
            private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

            public HueHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                try {
                    Log.d("ERRO", CREATE_TABLE);
                    db.execSQL(CREATE_TABLE);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                try {
                    db.execSQL(DROP_TABLE);
                    onCreate(db);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
