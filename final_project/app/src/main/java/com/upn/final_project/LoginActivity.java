package com.upn.final_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.net.URI;

public class LoginActivity extends AppCompatActivity {

    ImageView google_img;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageButton btnInstragram, btnFacebook;
    String url ="https://www.instagram.com/tsuki_homemade/";
    String url2="https://www.facebook.com/tsuki_homemade-107558751296293";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnInstragram = findViewById(R.id.btnInstragram);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnInstragram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link= Uri.parse(url);
                Intent i =new Intent(Intent.ACTION_VIEW,link);
                startActivity(i);

            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link= Uri.parse(url2);
                Intent i =new Intent(Intent.ACTION_VIEW,link);
                startActivity(i);
            }
        });
        google_img = findViewById(R.id.google);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);
        google_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    private void SignIn() {

        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                PrincipalActivity();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void PrincipalActivity(){
        finish();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}