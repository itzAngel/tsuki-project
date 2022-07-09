package com.upn.final_project;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.upn.final_project.entidad.Producto;
import com.upn.final_project.fragmentos.CarritoFragment;
import com.upn.final_project.modelo.DaoProducto;

import java.io.Serializable;
import java.util.List;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ProductosViewHolder> {

    Context context;
    TextView tvCantProductos;
    Button btnVerCarro;
    List<Producto> listaProductos;
    List<Producto> carroCompra;

    public AdaptadorProductos(Context context, TextView tvCantProductos, Button btnVerCarro, List<Producto> listaProductos, List<Producto> carroCompra) {
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
        return new ProductosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, int position) {
        holder.tvNomProducto.setText(listaProductos.get(holder.getAdapterPosition()).getTipo_producto());
        holder.tvDescripcion.setText(listaProductos.get(holder.getAdapterPosition()).getProducto());
        holder.tvPrecio.setText(""+listaProductos.get(holder.getAdapterPosition()).getPrecio());

        holder.cbCarro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(holder.cbCarro.isChecked() == true) {
                    tvCantProductos.setText(""+(Integer.parseInt(tvCantProductos.getText().toString().trim()) + 1));
                    carroCompra.add(listaProductos.get(holder.getAdapterPosition()));
                } else if(holder.cbCarro.isChecked() == false) {
                    tvCantProductos.setText(""+(Integer.parseInt(tvCantProductos.getText().toString().trim()) - 1));
                    carroCompra.remove(listaProductos.get(holder.getAdapterPosition()));
                }
            }
        });

        btnVerCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                DaoProducto daoProducto = new DaoProducto(AdaptadorProductos.this.context);
                daoProducto.abrirBaseDatos();
                String mensaje = "";
                for (Producto p: carroCompra) {
                    daoProducto.registrar(p);
                }
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new CarritoFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomProducto, tvDescripcion, tvPrecio;
        CheckBox cbCarro;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNomProducto = itemView.findViewById(R.id.tvNomProducto);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            cbCarro = itemView.findViewById(R.id.cbCarro);
        }
    }
}
