package com.upn.final_project.fragmentos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.upn.final_project.AdaptadorProductos;
import com.upn.final_project.R;
import com.upn.final_project.entidad.Producto;
import com.upn.final_project.modelo.DaoProducto;

import java.util.ArrayList;
import java.util.List;

public class ProductoFragment extends Fragment {

    private int pid = 0;
    private String pTipo_producto = null;
    private String pProducto = null;
    private String pRuta_imagen = null;
    private double pPrecio = 0;
    private String pComentario = null;

    TextView lblTitulo;
    EditText txtTipo_producto,txtProducto,txtRuta_imagen,txtPrecio,txtComentario;
    Button btnRegistrar;
    Producto producto;
    boolean registra = true;
    int id;

    TextView tvCantProductos;
    Button btnVerCarro;
    RecyclerView rvListaProductos;
    AdaptadorProductos adaptador;

    List<Producto> listaProductos = new ArrayList<>();
    List<Producto> carroCompras = new ArrayList<>();


    public ProductoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recibirParametros();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_producto, container, false);
        tvCantProductos = view.findViewById(R.id.tvCantProductos);
        btnVerCarro = view.findViewById(R.id.btnVerCarro);
        rvListaProductos = view.findViewById(R.id.rvListaProductos);
        rvListaProductos.setLayoutManager(new GridLayoutManager(ProductoFragment.this.getContext(), 1));

        listaProductos.add(new Producto(1, "Producto 1", "Descripcion del Producto 1", 50.0));
        listaProductos.add(new Producto(2, "Producto 2", "Descripcion del Producto 2", 80.0));
        listaProductos.add(new Producto(3, "Producto 3", "Descripcion del Producto 3", 40.0));
        listaProductos.add(new Producto(4, "Producto 4", "Descripcion del Producto 4", 20.0));
        listaProductos.add(new Producto(5, "Producto 5", "Descripcion del Producto 5", 56.0));

        adaptador = new AdaptadorProductos(ProductoFragment.this.getContext(), tvCantProductos, btnVerCarro, listaProductos, carroCompras);
        rvListaProductos.setAdapter(adaptador);
        return view;
    }

    private void recibirParametros(){
        if(pid!=0){
            registra = false;
            lblTitulo.setText("Modificar Producto");
            btnRegistrar.setText("Modificar Producto");
            id = pid;
            txtTipo_producto.setText(pTipo_producto);
            txtProducto.setText(pProducto);
            txtRuta_imagen.setText(pRuta_imagen);
            txtPrecio.setText(String.valueOf(pPrecio));
            txtComentario.setText(pComentario);
        }
    }

    private boolean capturarDatos(){
        String tipo_producto = txtTipo_producto.getText().toString();
        String nomproducto = txtProducto.getText().toString();
        String ruta_imagen = txtRuta_imagen.getText().toString();
        String precio = txtPrecio.getText().toString();
        String comentario = txtComentario.getText().toString();
        boolean valida = true;
        if(tipo_producto.equals("")){
            txtTipo_producto.setError("Tipo es obligatorio");
            valida = false;
        }
        if(nomproducto.equals("")){
            txtProducto.setError("Producto es obligatoria");
            valida = false;
        }
        if(ruta_imagen.equals("")){
            txtRuta_imagen.setError("Ruta de imagen es obligatorio");
            valida = false;
        }
        if(precio.equals("")){
            txtPrecio.setError("Precio es obligatoria");
            valida = false;
        }
        if(comentario.equals("")){
            txtComentario.setError("Comentario es obligatorio");
            valida = false;
        }
        if(valida){
            if(registra){
                producto = new Producto(tipo_producto,nomproducto,ruta_imagen,Double.parseDouble(precio),comentario);
            }else{
                producto = new Producto(id,tipo_producto,nomproducto,ruta_imagen,Double.parseDouble(precio),comentario);
            }

        }
        return valida;
    }

}