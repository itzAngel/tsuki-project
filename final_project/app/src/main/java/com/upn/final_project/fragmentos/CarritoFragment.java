package com.upn.final_project.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.upn.final_project.AdaptadorCarroCompras;
import com.upn.final_project.AdaptadorProductos;
import com.upn.final_project.CarroCompra;
import com.upn.final_project.R;
import com.upn.final_project.entidad.Carrito;
import com.upn.final_project.entidad.Producto;
import com.upn.final_project.entidad.Usuario;
import com.upn.final_project.modelo.DaoCarrito;
import com.upn.final_project.modelo.DaoProducto;
import com.upn.final_project.modelo.DaoUsuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarritoFragment extends Fragment {

    List<Carrito> carroCompras;

    AdaptadorCarroCompras adaptador;

    RecyclerView rvListaCarro;
    TextView tvTotal;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //para recuperar los datos de otro fragment
        mostrarCarrito();
        rvListaCarro = view.findViewById(R.id.rvListaCarro);
        rvListaCarro.setLayoutManager(new GridLayoutManager(CarritoFragment.this.getContext(), 1));
        tvTotal = view.findViewById(R.id.tvTotal);

        adaptador = new AdaptadorCarroCompras(CarritoFragment.this.getContext(), carroCompras, tvTotal);
        rvListaCarro.setAdapter(adaptador);

    }

    private void mostrarCarrito(){
        DaoCarrito daoCarrito = new DaoCarrito(CarritoFragment.this.getContext());
        daoCarrito.abrirBaseDatos();
        carroCompras = daoCarrito.cargarPorUsuario(usuario);
    }
}