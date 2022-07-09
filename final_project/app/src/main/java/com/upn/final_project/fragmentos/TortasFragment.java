package com.upn.final_project.fragmentos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upn.final_project.AdaptadorProductos;
import com.upn.final_project.R;
import com.upn.final_project.entidad.Carrito;
import com.upn.final_project.entidad.Pedido;
import com.upn.final_project.entidad.Producto;
import com.upn.final_project.entidad.Usuario;
import com.upn.final_project.modelo.DaoCarrito;
import com.upn.final_project.modelo.DaoPedido;
import com.upn.final_project.modelo.DaoProducto;
import com.upn.final_project.modelo.DaoUsuario;

import java.util.ArrayList;
import java.util.List;

public class TortasFragment extends Fragment {

    ImageView imgTorta1,imgTorta2,imgTorta3,imgTorta4,imgTorta5,imgTorta6;
    List<Producto> carroCompra = new ArrayList<>();
    //para la sesion del usuario
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    String Mail;
    FloatingActionButton btnVerCarro;
    public TortasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this.getContext(),gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this.getContext());
        if(account!=null){
            Mail = account.getEmail();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tortas,container,false);
        imgTorta1=(ImageView) view.findViewById(R.id.imgTorta1);
        imgTorta2=(ImageView) view.findViewById(R.id.imgTorta2);
        imgTorta3=(ImageView) view.findViewById(R.id.imgTorta3);
        imgTorta4=(ImageView) view.findViewById(R.id.imgTorta4);
        imgTorta5=(ImageView) view.findViewById(R.id.imgTorta5);
        imgTorta6=(ImageView) view.findViewById(R.id.imgTorta6);
        btnVerCarro = view.findViewById(R.id.btnVerCarroTortas);
        btnVerCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                //cargamos todos los dao que se usan
                DaoUsuario daoUsuario = new DaoUsuario(v.getContext());
                daoUsuario.abrirBaseDatos();
                DaoProducto daoProducto = new DaoProducto(v.getContext());
                daoProducto.abrirBaseDatos();
                DaoPedido daoPedido = new DaoPedido(v.getContext());
                daoPedido.abrirBaseDatos();
                DaoCarrito daoCarrito = new DaoCarrito(v.getContext());
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

        DaoProducto daoProducto = new DaoProducto(TortasFragment.this.getContext());
        daoProducto.abrirBaseDatos();
        imgTorta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = daoProducto.cargarPorId(1);
                agregarCarrito(v, producto);
                carroCompra.add(producto);
            }
        });
        imgTorta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = daoProducto.cargarPorId(2);
                agregarCarrito(v, producto);
                carroCompra.add(producto);
            }
        });
        imgTorta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = daoProducto.cargarPorId(3);
                agregarCarrito(v, producto);
                carroCompra.add(producto);
            }
        });
        imgTorta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = daoProducto.cargarPorId(4);
                agregarCarrito(v, producto);
                carroCompra.add(producto);
            }
        });
        imgTorta5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = daoProducto.cargarPorId(5);
                agregarCarrito(v, producto);
                carroCompra.add(producto);
            }
        });
        imgTorta6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = daoProducto.cargarPorId(6);
                agregarCarrito(v, producto);
                carroCompra.add(producto);
            }
        });
        return view;

    }

    public List<Carrito> ponerProductosEnCarrito(List<Producto> listaPro,Pedido pedido){
        List<Carrito> lista = new ArrayList<>();
        for (Producto p:listaPro) {
            Carrito carrito = new Carrito(pedido.getId_pedido(),p.getId_producto(),1,p.getPrecio());
            lista.add(carrito);
        }
        return lista;
    }

    public void agregarCarrito(View v, Producto p){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        //cargamos todos los dao que se usan
        DaoUsuario daoUsuario = new DaoUsuario(TortasFragment.this.getContext());
        daoUsuario.abrirBaseDatos();
        DaoPedido daoPedido = new DaoPedido(TortasFragment.this.getContext());
        daoPedido.abrirBaseDatos();
        DaoCarrito daoCarrito = new DaoCarrito(TortasFragment.this.getContext());
        daoCarrito.abrirBaseDatos();
        //cargamos el usuario
        Usuario usuario = daoUsuario.cargarporEmail(Mail);
        Pedido pedido = new Pedido();
        if(!usuario.getEmail().equals("fff")){//significa que es el usuario activo
            pedido = daoPedido.inicializaPedidoDeUsuario(usuario);
            //convertimos la lista de productos en lista de carrito
            Carrito carrito = new Carrito(pedido.getId_pedido(),p.getId_producto(),1,p.getPrecio());
            daoCarrito.agregarCarrito(carrito,p);
        }
        Toast.makeText(activity, "Se agregó el producto: "+p.getProducto()+ " al carrito", Toast.LENGTH_SHORT).show();
    }
}