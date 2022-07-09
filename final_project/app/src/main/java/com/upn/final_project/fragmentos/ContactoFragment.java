package com.upn.final_project.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

   Button btnUbicacion;

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
        return view;
    }
}