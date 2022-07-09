package com.upn.final_project.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.upn.final_project.entidad.Carrito;
import com.upn.final_project.entidad.Mascota;
import com.upn.final_project.entidad.Producto;
import com.upn.final_project.util.BaseDatos;

import java.util.ArrayList;
import java.util.List;

public class DaoProducto {
    BaseDatos baseDatos;
    SQLiteDatabase database;
    Context context;

    public DaoProducto(Context context) {
        this.baseDatos = new BaseDatos(context);
        this.context = context;
    }

    public void abrirBaseDatos(){
        database = baseDatos.getWritableDatabase();
    }

    public String registrar(Producto producto){
        String respuesta = "";
        try{
            ContentValues values = new ContentValues();
            values.put("tipo_producto", producto.getTipo_producto());
            values.put("producto", producto.getProducto());
            values.put("ruta_imagen", producto.getRuta_imagen());
            values.put("precio", producto.getPrecio());
            values.put("comentario", producto.getComentario());
            long resultado = database.insert("productos", null, values);
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

    public String modificar(Producto producto){
        String respuesta = "";
        try{
            ContentValues values = new ContentValues();
            values.put("tipo_producto", producto.getTipo_producto());
            values.put("producto", producto.getProducto());
            values.put("ruta_imagen", producto.getRuta_imagen());
            values.put("precio", producto.getPrecio());
            values.put("comentario", producto.getComentario());
            long resultado = database.update("productos", values, "id="+producto.getId_producto(), null);
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

    public String eliminar(int id){
        String respuesta = "";
        try {
            long resultado = database.delete("productos", "id="+id,null);
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

    public List<Producto> cargar(){
        List<Producto> lista = new ArrayList<>();
        try{
            Cursor c = database.rawQuery("SELECT * FROM productos",null);
            while (c.moveToNext()){
                lista.add(new Producto(c.getInt(0),c.getString(1), c.getString(2), c.getString(3), c.getDouble(4), c.getString(5)));
            }
        }catch (Exception e){
            Log.d("===>", e.toString());
        }
        return lista;
    }

}
