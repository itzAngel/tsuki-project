package com.upn.final_project.fragmentos;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;
import com.upn.final_project.AdaptadorCarroCompras;
import com.upn.final_project.MainActivity;
import com.upn.final_project.R;
import com.upn.final_project.entidad.Usuario;
import com.upn.final_project.modelo.DaoUsuario;

import java.util.ArrayList;
import java.util.List;

public class CuentaFragment extends Fragment {
    public TextView name, mail;
    ImageView imagV;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount account;
    public CuentaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this.getContext(),gso);
        account = GoogleSignIn.getLastSignedInAccount(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_cuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.name);
        mail = view.findViewById(R.id.mail);
        imagV= (ImageView) view.findViewById(R.id.avatar);
        if(account!=null){
            String Name = account.getDisplayName();
            String Mail = account.getEmail();
            Uri photo = account.getPhotoUrl();
            Picasso.get().load(photo).into(imagV);
            name.setText(Name);
            mail.setText(Mail);

        }
    }
}