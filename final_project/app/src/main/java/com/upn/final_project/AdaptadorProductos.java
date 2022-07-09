package com.upn.final_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.upn.final_project.entidad.Carrito;
import com.upn.final_project.entidad.Pedido;
import com.upn.final_project.entidad.Producto;
import com.upn.final_project.entidad.Usuario;
import com.upn.final_project.fragmentos.AddProductoFragment;
import com.upn.final_project.fragmentos.CarritoFragment;
import com.upn.final_project.fragmentos.ProductoFragment;
import com.upn.final_project.modelo.DaoCarrito;
import com.upn.final_project.modelo.DaoPedido;
import com.upn.final_project.modelo.DaoProducto;
import com.upn.final_project.modelo.DaoUsuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ProductosViewHolder> {

    Context context;
    TextView tvCantProductos;
    Button btnVerCarro;
    List<Producto> listaProductos;
    List<Producto> carroCompra;

    //para la sesion del usuario
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    String Mail;

    public AdaptadorProductos(Context context, TextView tvCantProductos, Button btnVerCarro,
                              List<Producto> listaProductos, List<Producto> carroCompra) {
        this.context = context;
        this.tvCantProductos = tvCantProductos;
        this.btnVerCarro = btnVerCarro;
        this.listaProductos = listaProductos;
        this.carroCompra = carroCompra;
    }

    @NonNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_productos, null, false);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this.context,gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this.context);
        if(account!=null){
            Mail = account.getEmail();
        }
        return new ProductosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosViewHolder holder,final int i) {
        holder.tvNomProducto.setText(listaProductos.get(holder.getAdapterPosition()).getTipo_producto());
        holder.tvDescripcion.setText(listaProductos.get(holder.getAdapterPosition()).getProducto());
        holder.tvPrecio.setText(""+listaProductos.get(holder.getAdapterPosition()).getPrecio());
        holder.cbCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCantProductos.setText(""+(Integer.parseInt(tvCantProductos.getText().toString().trim()) + 1));
                carroCompra.add(listaProductos.get(holder.getAdapterPosition()));
            }
        });
        btnVerCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                //cargamos todos los dao que se usan
                DaoUsuario daoUsuario = new DaoUsuario(AdaptadorProductos.this.context);
                daoUsuario.abrirBaseDatos();
                DaoProducto daoProducto = new DaoProducto(AdaptadorProductos.this.context);
                daoProducto.abrirBaseDatos();
                DaoPedido daoPedido = new DaoPedido(AdaptadorProductos.this.context);
                daoPedido.abrirBaseDatos();
                DaoCarrito daoCarrito = new DaoCarrito(AdaptadorProductos.this.context);
                daoCarrito.abrirBaseDatos();

                //cargamos el usuario
                Usuario usuario = daoUsuario.cargarporEmail(Mail);
                Pedido pedido = new Pedido();
                List<Carrito> listaCarrito = new ArrayList<>();
                if(!usuario.getEmail().equals("fff")){//significa que es el usuario activo
                    pedido = daoPedido.inicializaPedidoDeUsuario(usuario);
                    //convertimos la lista de productos en lista de carrito
                    listaCarrito = ponerProductosEnCarrito(carroCompra, pedido);
                }
                //eliminamos la lista anterior y ponemos la lista actual
                daoCarrito.registrarListaCarrito(listaCarrito,pedido);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new CarritoFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        });
        holder.filaEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new AddProductoFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                /*
                intent.putExtra("pid",listaMascotas.get(position).getId()+"");
                intent.putExtra("ptipo",listaMascotas.get(position).getTipo()+"");
                intent.putExtra("pmascota",listaMascotas.get(position).getNombreMascota()+"");
                intent.putExtra("ppeso",listaMascotas.get(position).getPeso()+"");
                intent.putExtra("pedad",listaMascotas.get(position).getEdad()+"");
                intent.putExtra("pdueno",listaMascotas.get(position).getNombreDueno()+"");
                 */
            }
        });
        holder.filaEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ventana = new AlertDialog.Builder(context);
                ventana.setTitle("Confimacion de eliminar");
                ventana.setMessage("¿Desea eliminar la mascota: " + listaProductos.get(i).getProducto() + "?");
                ventana.setNegativeButton("NO", null);
                ventana.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DaoProducto daoProducto = new DaoProducto(context);
                        daoProducto.abrirBaseDatos();
                        String mensaje = daoProducto.eliminar(listaProductos.get(i).getId_producto());
                        AlertDialog.Builder v2 = new AlertDialog.Builder(context);
                        v2.setTitle("Mensaje Informativo");
                        v2.setMessage(mensaje);
                        v2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new ProductoFragment())
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            }
                        });
                        v2.create().show();
                    }
                });
                ventana.create().show();
            }
        });
    }
    public List<Carrito> ponerProductosEnCarrito(List<Producto> listaPro,Pedido pedido){
        List<Carrito> lista = new ArrayList<>();
        for (Producto p:listaPro) {
            Carrito carrito = new Carrito(pedido.getId_pedido(),p.getId_producto(),1,p.getPrecio());
            lista.add(carrito);
        }
        return lista;
    }
    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomProducto, tvDescripcion, tvPrecio;
        ImageButton cbCarro;
        ImageButton filaEditar,filaEliminar;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomProducto = itemView.findViewById(R.id.tvNomProducto);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            cbCarro = itemView.findViewById(R.id.cbCarro);
            filaEditar = itemView.findViewById(R.id.filaEditar);
            filaEliminar = itemView.findViewById(R.id.filaEliminar);
        }
    }
}
