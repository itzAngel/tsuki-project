package com.upn.final_project.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.upn.final_project.entidad.Carrito;
import com.upn.final_project.entidad.Mascota;
import com.upn.final_project.entidad.Pedido;
import com.upn.final_project.entidad.Usuario;
import com.upn.final_project.util.BaseDatos;

import java.util.ArrayList;
import java.util.List;

public class DaoPedido {
    BaseDatos baseDatos;
    SQLiteDatabase database;
    Context context;

    public DaoPedido(Context context) {
        this.baseDatos = new BaseDatos(context);
        this.context = context;
    }

    public void abrirBaseDatos(){
        database = baseDatos.getWritableDatabase();
    }

    public String registrar(Pedido pedido){
        String respuesta = "";
        try{
            ContentValues values = new ContentValues();
            values.put("id_usuario", pedido.getId_usuario());
            values.put("sub_total", pedido.getSub_total());
            values.put("envio", pedido.getEnvio());
            values.put("total", pedido.getTotal());
            long resultado = database.insert("pedidos", null, values);
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

    public String modificar(Pedido pedido){
        String respuesta = "";
        try{
            ContentValues values = new ContentValues();
            values.put("id_usuario", pedido.getId_usuario());
            values.put("sub_total", pedido.getSub_total());
            values.put("envio", pedido.getEnvio());
            values.put("total", pedido.getTotal());
            long resultado = database.update("pedidos", values, "id="+pedido.getId_pedido(), null);
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
            long resultado = database.delete("pedidos", "id="+id,null);
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

    public List<Pedido> cargar(){
        List<Pedido> lista = new ArrayList<>();
        try{
            Cursor c = database.rawQuery("SELECT * FROM pedidos",null);
            while (c.moveToNext()){
                lista.add(new Pedido(c.getInt(0),c.getInt(1), c.getInt(2), c.getInt(3), c.getDouble(4)));
            }
        }catch (Exception e){
            Log.d("===>", e.toString());
        }
        return lista;
    }
}
