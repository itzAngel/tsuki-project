package com.upn.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.upn.final_project.entidad.Producto;
import com.upn.final_project.entidad.Usuario;
import com.upn.final_project.fragmentos.BebidasFragment;
import com.upn.final_project.fragmentos.CarritoFragment;
import com.upn.final_project.fragmentos.ContactoFragment;
import com.upn.final_project.fragmentos.CuentaFragment;
import com.upn.final_project.fragmentos.HomeFragment;
import com.upn.final_project.fragmentos.PostresFragment;
import com.upn.final_project.fragmentos.ProductoFragment;
import com.upn.final_project.fragmentos.TortasFragment;
import com.upn.final_project.modelo.DaoUsuario;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toogle;

    public TextView name, mail;
    ImageView logout;

    GoogleSignInAccount account;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    boolean valida_logout =true;

    TextView tvCantProductos;
    Button btnVerCarro;
    RecyclerView rvListaProductos;

    List<Producto> listaProductos = new ArrayList<>();
    List<Producto> carroCompras = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            String Name = account.getDisplayName();
            String Mail = account.getEmail();
            DaoUsuario daoUsuario = new DaoUsuario(MainActivity.this);
            daoUsuario.abrirBaseDatos();
            //si es la primera vez que inicia sesion lo agrega a la base de datos
            if(daoUsuario.cargarporEmail(Mail).getEmail().equals("fff")){
                Usuario usuario = new Usuario(Name,999999999,"a",Mail);
                daoUsuario.registrar(usuario);
            }
        }

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

    private boolean SignOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        return valida_logout;
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
        if(item.getItemId()==R.id.productos){
            mostrarFragmento(new ProductoFragment());
            item.setChecked(true);
        }
        if(item.getItemId()==R.id.mi_cuenta){
            mostrarFragmento(new CuentaFragment());
            item.setChecked(true);
        }
        if(item.getItemId()==R.id.contacto){
            mostrarFragmento(new ContactoFragment());
            item.setChecked(true);
        }
        if(item.getItemId()==R.id.signout){
            if(valida_logout){
                String mensaje = "¿Desea salir de la aplicación? ...";
                AlertDialog.Builder ventana = new AlertDialog.Builder(MainActivity.this);
                ventana.setTitle("CERRAR SESIÓN");
                ventana.setMessage(mensaje);
                ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SignOut();
                        finish();
                    }
                });
                ventana.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ventana.create().show();
            }
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

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }
}