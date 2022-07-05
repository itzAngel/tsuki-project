package com.upn.final_project.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.upn.final_project.entidad.Mascota;
import com.upn.final_project.util.BaseDatos;

import java.util.ArrayList;
import java.util.List;

public class DaoMascota {
    BaseDatos baseDatos;
    SQLiteDatabase database;
    Context context;

    public DaoMascota(Context context) {
        this.baseDatos = new BaseDatos(context);
        this.context = context;
    }

    public void abrirBaseDatos(){
        database = baseDatos.getWritableDatabase();
    }

    public String registrarMascota(Mascota mascota){
        String respuesta = "";
        try{
            ContentValues values = new ContentValues();
            values.put("tipo", mascota.getTipo());
            values.put("nombreMascota", mascota.getNombreMascota());
            values.put("peso", mascota.getPeso());
            values.put("edad", mascota.getEdad());
            values.put("nombreDueno", mascota.getNombreDueno());
            long resultado = database.insert("mascotas", null, values);
            if(resultado ==-1) {
                respuesta = "Error al insertar";
            }else{
                respuesta = "Se registró correctamente";
            }
        }catch (Exception e){
            Log.d("===>",e.toString());
        }
        return respuesta;
    }

    public String modificarMascota(Mascota mascota){
        String respuesta = "";
        try{
            ContentValues values = new ContentValues();
            values.put("tipo", mascota.getTipo());
            values.put("nombreMascota", mascota.getNombreMascota());
            values.put("peso", mascota.getPeso());
            values.put("edad", mascota.getEdad());
            values.put("nombreDueno", mascota.getNombreDueno());
            long resultado = database.update("mascotas", values, "id="+mascota.getId(), null);
            if(resultado ==-1) {
                respuesta = "Error al actualizar";
            }else{
                respuesta = "Se actualizó correctamente";
            }
        }catch (Exception e){
            Log.d("===>",e.toString());
        }
        return respuesta;
    }

    public String eliminarMascota(int id){
        String respuesta = "";
        try {
            long resultado = database.delete("mascotas", "id="+id,null);
            if(resultado == -1){
                respuesta = "Error al eliminar";
            }else{
                respuesta = "Se eliminó correctamente";
            }
        }catch (Exception e){
            Log.d("===>",e.toString());
        }
        return respuesta;
    }

    public List<Mascota> cargarMascota(){
        List<Mascota> listaMascotas = new ArrayList<>();
        try{
            Cursor c = database.rawQuery("SELECT * FROM mascotas",null);
            while (c.moveToNext()){
                listaMascotas.add(new Mascota(c.getInt(0), c.getString(1), c.getString(2), c.getDouble(3), c.getInt(4), c.getString(5)));
            }
        }catch (Exception e){
            Log.d("===>", e.toString());
        }
        return listaMascotas;
    }
}
