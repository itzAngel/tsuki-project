package com.upn.final_project.fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.upn.final_project.R;

public class TortasPersonalizadasFragment extends Fragment {

    Button btnEnviar;

    private void popThisFragment(View v){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new HomeFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public TortasPersonalizadasFragment() {
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
        View view = inflater.inflate(R.layout.fragment_tortas_personalizadas,container,false);
        btnEnviar=(Button) view.findViewById(R.id.btnEnviarPersonalizado);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ventana = new AlertDialog.Builder(TortasPersonalizadasFragment.this.getContext());
                ventana.setTitle("Mensaje Informativo");
                ventana.setMessage("Su pedido de torta personalizada se envio correctamente");
                ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popThisFragment(v);
                    }
                });
                ventana.create().show();
            }
        });
        return view;
    }
}