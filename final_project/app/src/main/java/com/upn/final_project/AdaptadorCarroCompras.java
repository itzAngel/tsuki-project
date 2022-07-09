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

import java.util.List;

public class AdaptadorCarroCompras extends RecyclerView.Adapter<AdaptadorCarroCompras.ProductosViewHolder> {

    Context context;
    List<Carrito> carroCompra;
    TextView tvsubTotal,tvEnvio,tvTotal;
    double subtotal=0,envio = 10,total = 0;

    public AdaptadorCarroCompras(Context context, List<Carrito> carroCompra, TextView tvsubTotal, TextView tvenvio, TextView tvTotal, Usuario usuario) {
        this.context = context;
        this.carroCompra = carroCompra;
        this.tvsubTotal = tvsubTotal;
        this.tvEnvio = tvEnvio;
        this.tvTotal = tvTotal;
        for(int i = 0 ; i < carroCompra.size() ; i++) {
            subtotal = subtotal + Double.parseDouble(""+carroCompra.get(i).getTotal_carrito());
        }
        tvsubTotal.setText(""+subtotal);
        tvenvio.setText(""+envio);
        total=subtotal+envio;
        tvTotal.setText(""+total);

        DaoPedido daoPedido = new DaoPedido(this.context);
        daoPedido.abrirBaseDatos();
        Pedido pedido = daoPedido.inicializaPedidoDeUsuario(usuario);
        pedido.setSub_total(subtotal);
        pedido.setEnvio(envio);
        pedido.setTotal(total);
        daoPedido.modificar(pedido);
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
        Picasso.get().load(producto.getRuta_imagen()).into(productosViewHolder.imageProducto);
        productosViewHolder.tvNomProducto.setText(producto.getTipo_producto());
        productosViewHolder.tvDescripcion.setText(producto.getProducto());
        productosViewHolder.tvCantidad.setText(""+carroCompra.get(i).getCantidad());
        productosViewHolder.tvPrecio.setText(""+carroCompra.get(i).getTotal_carrito());
        productosViewHolder.agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoCarrito daoCarrito = new DaoCarrito(context);
                daoCarrito.abrirBaseDatos();
                daoCarrito.agregarCarrito(carroCompra.get(i),producto);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new CarritoFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        });
        productosViewHolder.quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoCarrito daoCarrito = new DaoCarrito(context);
                daoCarrito.abrirBaseDatos();
                daoCarrito.quitarCarrito(carroCompra.get(i),producto);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new CarritoFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carroCompra.size();
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomProducto, tvDescripcion,tvCantidad, tvPrecio;
        ImageView imageProducto,agregar,quitar;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProducto = itemView.findViewById((R.id.tvImgProducto));
            tvNomProducto = itemView.findViewById(R.id.tvNomProducto);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            agregar = itemView.findViewById(R.id.agregarCarrito);
            quitar = itemView.findViewById(R.id.quitarCarrito);
        }
    }
}
