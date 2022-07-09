package com.upn.final_project.fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upn.final_project.AdaptadorCarroCompras;
import com.upn.final_project.R;
import com.upn.final_project.entidad.Carrito;
import com.upn.final_project.entidad.Usuario;
import com.upn.final_project.modelo.DaoCarrito;
import com.upn.final_project.modelo.DaoPedido;
import com.upn.final_project.modelo.DaoUsuario;

import java.util.List;

public class CarritoFragment extends Fragment {

    List<Carrito> carroCompras;

    AdaptadorCarroCompras adaptador;
    FloatingActionButton btnCrearPedido;
    RecyclerView rvListaCarro;
    TextView tvsubTotal,tvEnvio,tvTotal;

    //para la sesion del usuario
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    String Mail;
    Usuario usuario = new Usuario();

    public CarritoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this.getContext(),gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this.getContext());
        if(account!=null){
            Mail = account.getEmail();
        }
        //cargamos el usuario
        DaoUsuario daoUsuario = new DaoUsuario(this.getContext());
        daoUsuario.abrirBaseDatos();
        usuario = daoUsuario.cargarporEmail(Mail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carrito, container, false);
    }

    private void popThisFragment(View v){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new HomeFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //para recuperar los datos de otro fragment
        mostrarCarrito();
        rvListaCarro = view.findViewById(R.id.rvListaCarro);
        rvListaCarro.setLayoutManager(new GridLayoutManager(CarritoFragment.this.getContext(), 1));
        tvsubTotal = view.findViewById(R.id.tvsubTotal);
        tvEnvio = view.findViewById(R.id.tvEnvio);
        tvTotal = view.findViewById(R.id.tvTotal);
        btnCrearPedido = view.findViewById(R.id.btnCrearPedido);
        btnCrearPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                DaoPedido daoPedido = new DaoPedido(CarritoFragment.this.getContext());
                daoPedido.abrirBaseDatos();
                daoPedido.registrarPedidoActivo(usuario);
                android.app.AlertDialog.Builder ventana = new AlertDialog.Builder(CarritoFragment.this.getContext());
                ventana.setTitle("Mensaje Informativo");
                ventana.setMessage("El pedido se ha agregado correctamente y se esta procesando para su preparacion y envio");
                ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popThisFragment(v);
                    }
                });
                ventana.create().show();
            }
        });
        adaptador = new AdaptadorCarroCompras(CarritoFragment.this.getContext(), carroCompras, tvsubTotal, tvEnvio, tvTotal, usuario);
        rvListaCarro.setAdapter(adaptador);
    }

    private void mostrarCarrito(){
        DaoCarrito daoCarrito = new DaoCarrito(CarritoFragment.this.getContext());
        daoCarrito.abrirBaseDatos();
        carroCompras = daoCarrito.cargarPorUsuario(usuario);
    }
}