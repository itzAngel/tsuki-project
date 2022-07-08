package com.upn.final_project.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.upn.final_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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


        return view;
    }
}