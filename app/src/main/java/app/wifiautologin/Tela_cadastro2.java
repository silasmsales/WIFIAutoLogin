package app.wifiautologin;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Tela_cadastro2 extends AppCompatActivity {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro2_login);

        // Mostra nome do Aplicativo no Topo.
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Mostrar icone no lugar de Texto.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Define qual icone vai aparecer.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

//
//        mEmailView = findViewById(R.id.email);
//        mPasswordView = findViewById(R.id.password);
//
//
//        Toast.makeText(this,mEmailView.getText().toString(),Toast.LENGTH_LONG).show();
//        Toast.makeText(this,mPasswordView.getText().toString(),Toast.LENGTH_LONG).show();
//
//        Log.v("EmailMatricula",mEmailView.getText().toString());
//        Log.v("Senha",mPasswordView.getText().toString());

    }
}
