package com.upn.final_project.fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.upn.final_project.MapaActivity;
import com.upn.final_project.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ContactoFragment extends Fragment {

   Button btnUbicacion,btnEnviar;

    private void popThisFragment(View v){
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new HomeFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacto,container,false);
        btnUbicacion=(Button) view.findViewById(R.id.btnUbicacion);
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapaActivity.class);
                startActivity(intent);
            }
        });
        btnEnviar=(Button) view.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ventana = new AlertDialog.Builder(ContactoFragment.this.getContext());
                ventana.setTitle("Mensaje Informativo");
                ventana.setMessage("Su formulario se envio correctamente");
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