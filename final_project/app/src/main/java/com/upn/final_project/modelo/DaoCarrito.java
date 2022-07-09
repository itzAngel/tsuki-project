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

public class DaoCarrito {
    BaseDatos baseDatos;
    SQLiteDatabase database;
    Context context;

    public DaoCarrito(Context context) {
        this.baseDatos = new BaseDatos(context);
        this.context = context;
    }

    public void abrirBaseDatos(){
        database = baseDatos.getWritableDatabase();
    }

    public String registrar(Carrito carrito){
        String respuesta = "";
        try{
            ContentValues values = new ContentValues();
            values.put("id_pedido", carrito.getId_pedido());
            values.put("id_producto", carrito.getId_producto());
            values.put("cantidad", carrito.getCantidad());
            values.put("total_carrito", carrito.getTotal_carrito());
            long resultado = database.insert("carritos", null, values);
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

    public String modificar(Carrito carrito){
        String respuesta = "";
        try{
            ContentValues values = new ContentValues();
            values.put("id_pedido", carrito.getId_pedido());
            values.put("id_producto", carrito.getId_producto());
            values.put("cantidad", carrito.getCantidad());
            values.put("total_carrito", carrito.getTotal_carrito());
            long resultado = database.update("carritos", values, "id="+carrito.getId_carrito(), null);
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
            long resultado = database.delete("carritos", "id="+id,null);
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

    public String eliminarPorPedido(int idPedido){
        String respuesta = "";
        try {
            long resultado = database.delete("carritos", "id_pedido="+idPedido,null);
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

    public List<Carrito> cargar(){
        List<Carrito> lista = new ArrayList<>();
        try{
            Cursor c = database.rawQuery("SELECT * FROM carritos",null);
            while (c.moveToNext()){
                lista.add(new Carrito(c.getInt(0),c.getInt(1), c.getInt(2), c.getInt(3), c.getDouble(4)));
            }
        }catch (Exception e){
            Log.d("===>", e.toString());
        }
        return lista;
    }

    public List<Carrito> cargarPorUsuario(Usuario usuario){
        List<Carrito> lista = new ArrayList<>();
        try{
            Cursor c = database.rawQuery("SELECT c.* FROM carritos c inner join pedidos p on c.id_pedido=p.id where p.id_usuario=" + usuario.getId_usuario(),null);
            while (c.moveToNext()){
                lista.add(new Carrito(c.getInt(0),c.getInt(1), c.getInt(2), c.getInt(3), c.getDouble(4)));
            }
        }catch (Exception e){
            Log.d("===>", e.toString());
        }
        return lista;
    }

    public void registrarListaCarrito(List<Carrito> lista, Pedido pedido){
        eliminarPorPedido(pedido.getId_pedido());
        for (Carrito carrito:lista) {
            registrar(carrito);
        }
    }
}
