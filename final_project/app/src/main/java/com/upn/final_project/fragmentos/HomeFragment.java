package com.upn.final_project.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.upn.final_project.R;

public class HomeFragment extends Fragment {
    ImageButton btnTortas,btnTortasP, btnPostres, btnBebidas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        btnTortas=(ImageButton) view.findViewById(R.id.btnTortas);
        btnTortasP=(ImageButton) view.findViewById(R.id.btnTortasP);
        btnPostres=(ImageButton) view.findViewById(R.id.btnPostres);
        btnBebidas=(ImageButton) view.findViewById(R.id.btnBebidas);
        btnTortas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFragmento(new TortasFragment(),v);
            }
        });
        btnTortasP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFragmento(new TortasPersonalizadasFragment(),v);
            }
        });
        btnPostres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFragmento(new PostresFragment(),v);
            }
        });
        btnBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFragmento(new BebidasFragment(),v);
            }
        });

        return view;
    }

    public void mostrarFragmento(Fragment fragment, View v){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}