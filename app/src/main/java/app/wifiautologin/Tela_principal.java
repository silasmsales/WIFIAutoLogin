package app.wifiautologin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tela_principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_principal_login);

        // Mostra nome do Aplicativo no Topo.
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Mostrar icone no lugar de Texto.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Define qual icone vai aparecer.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);




    }
}
