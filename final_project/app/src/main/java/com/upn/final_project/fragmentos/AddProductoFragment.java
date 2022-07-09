package com.upn.final_project.fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.upn.final_project.R;
import com.upn.final_project.entidad.Producto;
import com.upn.final_project.modelo.DaoProducto;

public class AddProductoFragment extends Fragment {

    TextView lblTitulo;
    EditText txtTipo_producto,txtProducto,txtRuta_imagen,txtPrecio,txtComentario;
    Button btnRegistrar;
    Producto producto;
    boolean registra = true;
    int id;

    public AddProductoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_producto, container, false);
        lblTitulo = view.findViewById(R.id.lblTitulo);
        txtTipo_producto = view.findViewById(R.id.txtTipo);
        txtProducto = view.findViewById(R.id.txtNombreproducto);
        txtRuta_imagen = view.findViewById(R.id.txtRuta);
        txtPrecio = view.findViewById(R.id.txtPrecio);
        txtComentario = view.findViewById(R.id.txtComentario);
        btnRegistrar = view.findViewById(R.id.btnAgregarProducto);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(capturarDatos()){
                    DaoProducto dao = new DaoProducto(AddProductoFragment.this.getContext());
                    dao.abrirBaseDatos();
                    String mensaje = "";
                    if(registra){
                        mensaje = dao.registrar(producto);
                    }else{
                        mensaje = dao.modificar(producto);
                    }
                    AlertDialog.Builder ventana = new AlertDialog.Builder(AddProductoFragment.this.getContext());
                    ventana.setTitle("Mensaje Informativo");
                    ventana.setMessage(mensaje);
                    ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            popThisFragment();
                        }
                    });
                    ventana.create().show();
                }
            }
        });
        return view;
    }

    private void popThisFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
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