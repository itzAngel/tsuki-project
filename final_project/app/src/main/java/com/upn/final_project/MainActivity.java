package com.upn.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.upn.final_project.R;
import com.upn.final_project.fragmentos.BebidasFragment;
import com.upn.final_project.fragmentos.CarritoFragment;
import com.upn.final_project.fragmentos.HomeFragment;
import com.upn.final_project.fragmentos.PostresFragment;
import com.upn.final_project.fragmentos.TortasFragment;
import com.upn.final_project.fragmentos.TortasPersonalizadasFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toogle = new ActionBarDrawerToggle(this,drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.home){
            mostrarFragmento(new HomeFragment());
            item.setChecked(true);
        }
        if(item.getItemId()==R.id.tortas){
            mostrarFragmento(new TortasFragment());
            item.setChecked(true);
        }
        if(item.getItemId()==R.id.tortas_personalizadas){
            mostrarFragmento(new TortasPersonalizadasFragment());
            item.setChecked(true);
        }
        if(item.getItemId()==R.id.postres){
            mostrarFragmento(new PostresFragment());
            item.setChecked(true);
        }
        if(item.getItemId()==R.id.bebidas){
            mostrarFragmento(new BebidasFragment());
            item.setChecked(true);
        }
        if(item.getItemId()==R.id.carrito){
            mostrarFragmento(new CarritoFragment());
            item.setChecked(true);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void mostrarFragmento(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

}