package com.upn.final_project.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper  {

    public BaseDatos(Context context) {
        super(context, "biblioteca2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE usuarios " +
                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " nombre TEXT NOT NULL, " +
                        " celular INTEGER NOT NULL, " +
                        " direccion TEXT NOT NULL, " +
                        " email TEXT);";
        db.execSQL(query);
        String query2 =
                "CREATE TABLE productos " +
                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " tipo_producto TEXT NOT NULL, " +
                        " producto TEXT NOT NULL, " +
                        " ruta_imagen TEXT, " +
                        " precio DECIMAL NOT NULL, " +
                        " comentario TEXT);";
        db.execSQL(query2);
        String query3 =
                "CREATE TABLE carritos " +
                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " id_pedido INTEGER NOT NULL, " +
                        " id_producto INTEGER NOT NULL, " +
                        " cantidad INTEGER NOT NULL, " +
                        " total_carrito DECIMAL NOT NULL);";
        db.execSQL(query3);
        String query4 =
                "CREATE TABLE pedidos " +
                        " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " id_usuario TEXT NOT NULL, " +
                        " sub_total DECIMAL NOT NULL, " +
                        " envio DECIMAL NOT NULL, " +
                        " total DECIMAL NOT NULL," +
                        " estado TEXT NOT NULL);";
        db.execSQL(query4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
