package app.wifiautologin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;

    private MinhasWIFI minhasWIFI = null;
    private static String arquivo = "redes_salvas.obj";
    private AlertDialog.Builder alertDialog;
    private File dir_arquivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_login);
        // Set up the login form.

        dir_arquivos = getApplicationContext().getFilesDir();

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setPositiveButton(R.string.login_success, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
            }
        });
        alertDialog.create();

        minhasWIFI = new MinhasWIFI(criarPerfisUEMASUL(), dir_arquivos);

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        if(getPerfilOnline() != null) {
            mEmailView.setText(getPerfilOnline().getParametros().get(0).getValor());
            mPasswordView.setText(getPerfilOnline().getParametros().get(1).getValor());
            attemptLogin();
        }else {
            System.out.println("onCreate : " + "Impossível saber sua rede WIFI");
        }


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);


        // Store values at the time of the login attempt.
        String auth_user = mEmailView.getText().toString().trim();
        String auth_pass = mPasswordView.getText().toString().trim();




        boolean cancel = false;
        View focusView = null;

        // Check for a valid auth_pass, if the user entered one.
        if (!TextUtils.isEmpty(auth_pass) && !isPasswordValid(auth_pass)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid auth_user address.
        if (TextUtils.isEmpty(auth_user)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(auth_user)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            String a = "a";

            minhasWIFI.getWIFIbyDescricao("UEMASULACAD").getParametros().get(0).setValor(auth_user);
            minhasWIFI.getWIFIbyDescricao("UEMASULACAD").getParametros().get(1).setValor(auth_pass);
            minhasWIFI.getWIFIbyDescricao("UEMASULADM").getParametros().get(0).setValor(auth_user);
            minhasWIFI.getWIFIbyDescricao("UEMASULADM").getParametros().get(1).setValor(auth_pass);
            minhasWIFI.save();


            String b = "b";

            Autenticador autenticador = new Autenticador();
            Perfil perfil = getPerfilOnline();
            autenticador.execute(perfil);

            alertDialog.setMessage("Logado com sucesso na rede " + getWIFIName()).show();

        }
    }

    private Perfil getPerfilOnline(){

        Perfil perfil = null;
        List<Perfil> perfis = minhasWIFI.getPerfis();
        ListIterator <Perfil> iterator = perfis.listIterator();
        String wifi = getWIFIName();

        while (iterator.hasNext()){
            perfil =  iterator.next();
            if(perfil.getDescricao().equals(wifi)) {
                break;
            }else{
                perfil = null;
            }
        }
        return perfil;
    }

    private String getWIFIName(){
        String WIFIName = null;
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null){
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            {

                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//              Versao antiga do android API 20
                if(wifiInfo.getSupplicantState() == SupplicantState.COMPLETED){
                    WIFIName = wifiInfo.getSSID();
                }else{
                    System.out.println("getWIFIName : Não foi possivel obter o nome da rede");
                }
//              Versao antiga do android API 21+
                if (networkInfo.isConnected() && WIFIName.equals("<unknown ssid>")){
                    WIFIName = networkInfo.getExtraInfo().substring(1, networkInfo.getExtraInfo().length()-1);
                }

                System.err.println("getWIFIName : " + WIFIName);
            }else{
                System.out.println("getWIFIName : " + "Não conectado em um WIFI");
            }
        }else{
            System.err.println("getWIFIName : " + "Nenhuma conexão ativa");
        }
        return WIFIName;
    }

    private ArrayList<Perfil> criarPerfisUEMASUL(){
        ArrayList<Perfil> listPerfis = new ArrayList<>();

        try {
            Perfil uemasulacad = new Perfil();
            Perfil uemasuladm = new Perfil();

            uemasulacad.setDescricao("UEMASULACAD");
            uemasuladm.setDescricao("UEMASULADM");

            uemasulacad.setUrlLogin(new URL("http", "10.0.8.1", 8008, "index.php?zone=aluno"));
            uemasulacad.addParametro(new Parametro("auth_user", null, "text"));
            uemasulacad.addParametro(new Parametro("auth_pass", null, "password"));
            uemasulacad.addParametro(new Parametro("accept", "submit", "submit"));

            uemasuladm.setUrlLogin(new URL("http", "10.0.12.1", 8002, "index.php?zone=pfsenseadm"));
            uemasuladm.addParametro(new Parametro("auth_user", null, "text"));
            uemasuladm.addParametro(new Parametro("auth_pass", null, "password"));
            uemasuladm.addParametro(new Parametro("accept", "submit", "submit"));

            listPerfis.add(uemasulacad);
            listPerfis.add(uemasuladm);

        } catch (MalformedURLException e) {
            System.err.println("criarPerfisUEMASUL : " + e.getMessage());
        }

        return listPerfis;
    }

    private boolean isEmailValid(String login) {
        return true;
    }

    private boolean isPasswordValid(String password) {
        return true;
    }

}