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

import com.upn.final_project.AdaptadorCarroCompras;
import com.upn.final_project.CarroCompra;
import com.upn.final_project.R;
import com.upn.final_project.entidad.Producto;
import com.upn.final_project.modelo.DaoProducto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarritoFragment extends Fragment {

    List<Producto> carroCompras;

    AdaptadorCarroCompras adaptador;

    RecyclerView rvListaCarro;
    TextView tvTotal;

    String aa;

    public CarritoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        getParentFragmentManager().setFragmentResultListener("key", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String aj = bundle.getString("men");
                aa = aj;
                //carroCompras = (List<Producto>) bundle.getSerializable("CarroCompras");
            }
        });
        //para recuperar los datos de otro fragment
        mostrarCarrito();
        rvListaCarro = view.findViewById(R.id.rvListaCarro);
        rvListaCarro.setLayoutManager(new GridLayoutManager(CarritoFragment.this.getContext(), 1));
        tvTotal = view.findViewById(R.id.tvTotal);

        adaptador = new AdaptadorCarroCompras(CarritoFragment.this.getContext(), carroCompras, tvTotal);
        rvListaCarro.setAdapter(adaptador);

    }

    private void mostrarCarrito(){
        DaoProducto daoProducto = new DaoProducto(this.getContext());
        daoProducto.abrirBaseDatos();
        carroCompras = daoProducto.cargar();
    }
}