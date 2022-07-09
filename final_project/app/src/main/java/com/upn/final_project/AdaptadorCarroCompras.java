package com.upn.final_project;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.upn.final_project.entidad.Carrito;
import com.upn.final_project.entidad.Producto;
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
        Picasso.get().load(producto.getRuta_imagen()).into(productosViewHolder.imageProducto);
        productosViewHolder.tvNomProducto.setText(producto.getTipo_producto());
        productosViewHolder.tvDescripcion.setText(producto.getProducto());
        productosViewHolder.tvPrecio.setText(""+producto.getPrecio());
    }

    @Override
    public int getItemCount() {
        return carroCompra.size();
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomProducto, tvDescripcion, tvPrecio;
        ImageView imageProducto;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProducto = itemView.findViewById((R.id.tvImgProducto));
            tvNomProducto = itemView.findViewById(R.id.tvNomProducto);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
        }
    }
}
