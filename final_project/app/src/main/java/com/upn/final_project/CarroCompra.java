package com.upn.final_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upn.final_project.entidad.Producto;

import java.util.List;

public class CarroCompra extends AppCompatActivity {

    List<Producto> carroCompras;

    AdaptadorCarroCompras adaptador;

    RecyclerView rvListaCarro;
    TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_carrito);
        getSupportActionBar().hide();

        carroCompras = (List<Producto>) getIntent().getSerializableExtra("CarroCompras");

        rvListaCarro = findViewById(R.id.rvListaCarro);
        rvListaCarro.setLayoutManager(new GridLayoutManager(CarroCompra.this, 1));
        tvTotal = findViewById(R.id.tvTotal);

        adaptador = new AdaptadorCarroCompras(CarroCompra.this, carroCompras, tvTotal);
        rvListaCarro.setAdapter(adaptador);

    }
}
