package com.upn.final_project.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.upn.final_project.entidad.Carrito;
import com.upn.final_project.entidad.Mascota;
import com.upn.final_project.entidad.Usuario;
import com.upn.final_project.util.BaseDatos;

import java.util.ArrayList;
import java.util.List;

public class DaoUsuario {
    BaseDatos baseDatos;
    SQLiteDatabase database;
    Context context;

    public DaoUsuario(Context context) {
        this.baseDatos = new BaseDatos(context);
        this.context = context;
    }

    public void abrirBaseDatos(){
        database = baseDatos.getWritableDatabase();
    }

    public String registrar(Usuario usuario){
        String respuesta = "";
        try{
            ContentValues values = new ContentValues();
            values.put("nombre", usuario.getNombre());
            values.put("celular", usuario.getCelular());
            values.put("direccion", usuario.getDireccion());
            values.put("email", usuario.getEmail());
            long resultado = database.insert("usuarios", null, values);
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

    public String modificar(Usuario usuario){
        String respuesta = "";
        try{
            ContentValues values = new ContentValues();
            values.put("nombre", usuario.getNombre());
            values.put("celular", usuario.getCelular());
            values.put("direccion", usuario.getDireccion());
            values.put("email", usuario.getEmail());
            long resultado = database.update("usuarios", values, "id="+usuario.getId_usuario(), null);
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
            long resultado = database.delete("usuarios", "id="+id,null);
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

    public List<Usuario> cargar(){
        List<Usuario> lista = new ArrayList<>();
        try{
            Cursor c = database.rawQuery("SELECT * FROM usuarios",null);
            while (c.moveToNext()){
                lista.add(new Usuario(c.getInt(0),c.getString(1), c.getInt(2), c.getString(3), c.getString(4)));
            }
        }catch (Exception e){
            Log.d("===>", e.toString());
        }
        return lista;
    }

    public Usuario cargarporEmail(String email){
        Usuario user = new Usuario();
        user.setEmail("fff");
        try{
            Cursor c = database.rawQuery("SELECT * FROM usuarios where email like '%" + email +"%'",null);
            while (c.moveToNext()){
                user = new Usuario(c.getInt(0),c.getString(1), c.getInt(2), c.getString(3), c.getString(4));
            }
        }catch (Exception e){
            Log.d("===>", e.toString());
        }
        return user;
    }
}
