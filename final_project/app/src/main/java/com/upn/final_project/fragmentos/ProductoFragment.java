package com.upn.final_project.fragmentos;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upn.final_project.AdaptadorCarroCompras;
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
    FloatingActionButton btnNuevo;
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
        btnNuevo = view.findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Context myContext = v.getContext();
                SharedPreferences.Editor editor = activity.getSharedPreferences("DeviceToken", MODE_PRIVATE).edit();
                editor.clear();
                editor.putString("pid","agrega");
                editor.apply();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new AddProductoFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        });
        DaoProducto daoProducto = new DaoProducto(ProductoFragment.this.getContext());
        daoProducto.abrirBaseDatos();
        listaProductos = daoProducto.cargar();

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