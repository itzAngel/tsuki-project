package com.upn.final_project;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.upn.final_project.entidad.Carrito;
import com.upn.final_project.entidad.Producto;
import com.upn.final_project.fragmentos.AddProductoFragment;
import com.upn.final_project.fragmentos.CarritoFragment;
import com.upn.final_project.fragmentos.ProductoFragment;
import com.upn.final_project.modelo.DaoCarrito;
import com.upn.final_project.modelo.DaoProducto;
import com.upn.final_project.modelo.DaoUsuario;

import java.util.List;

public class AdaptadorCarroCompras extends RecyclerView.Adapter<AdaptadorCarroCompras.ProductosViewHolder> {

    Context context;
    List<Carrito> carroCompra;
    TextView tvTotal;
    double total = 0;

    public AdaptadorCarroCompras(Context context, List<Carrito> carroCompra, TextView tvTotal) {
        this.context = context;
        this.carroCompra = carroCompra;
        this.tvTotal = tvTotal;
        for(int i = 0 ; i < carroCompra.size() ; i++) {
            total = total + Double.parseDouble(""+carroCompra.get(i).getTotal_carrito());
        }
        tvTotal.setText(""+total);
    }

    @NonNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_carro_compras, null, false);
        return new ProductosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductosViewHolder productosViewHolder, final int i) {
        DaoProducto daoProducto = new DaoProducto(AdaptadorCarroCompras.this.context);
        daoProducto.abrirBaseDatos();
        Producto producto = daoProducto.cargarPorId(carroCompra.get(i).getId_producto());
        Picasso.get().load("https://3.bp.blogspot.com/-n8RS6JPF0o8/WUVu0llOafI/AAAAAAAADm0/00mLPcRjTI4gV82tGQI86NYoadNrkLrZACLcBGAs/s1600/IMG_0749.jpg").into(productosViewHolder.imageProducto);
        productosViewHolder.tvNomProducto.setText(producto.getTipo_producto());
        productosViewHolder.tvDescripcion.setText(producto.getProducto());
        productosViewHolder.tvPrecio.setText(""+producto.getPrecio());
        productosViewHolder.filaEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ventana = new AlertDialog.Builder(context);
                ventana.setTitle("Confimacion de eliminar");
                ventana.setMessage("¿Desea eliminar el producto del carrito ?");
                ventana.setNegativeButton("NO", null);
                ventana.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DaoCarrito daoCarrito = new DaoCarrito(context);
                        daoCarrito.abrirBaseDatos();
                        String mensaje = daoCarrito.eliminar(carroCompra.get(i).getId_carrito());
                        AlertDialog.Builder v2 = new AlertDialog.Builder(context);
                        v2.setTitle("Mensaje Informativo");
                        v2.setMessage(mensaje);
                        v2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new CarritoFragment())
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

    @Override
    public int getItemCount() {
        return carroCompra.size();
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomProducto, tvDescripcion, tvPrecio;
        ImageView imageProducto,filaEliminar;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProducto = itemView.findViewById((R.id.tvImgProducto));
            tvNomProducto = itemView.findViewById(R.id.tvNomProducto);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            filaEliminar = itemView.findViewById(R.id.filaEliminar);
        }
    }
}
