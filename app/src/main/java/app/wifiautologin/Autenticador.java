package app.wifiautologin;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by silasmsales on 07/12/18.
 */

public class Autenticador extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {

        Perfil perfil;

        if (objects != null)
        {
            perfil = (Perfil) objects[0];
        }else{
            System.err.println(Autenticador.class + " > doInBackground > Nenhuma autenticação válida");
            return null;
        }

        try {

            URL url = perfil.getUrlLogin();

            ListIterator<Parametro> iterator = perfil.getParametros().listIterator();
            Map<String,Object> params = new LinkedHashMap<>();
            Parametro parametro;

            while (iterator.hasNext()){
                parametro = iterator.next();
                params.put(parametro.getNome(), parametro.getValor());
            }

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0)
                    postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

//            for (int c; (c = in.read()) >= 0;)
//                System.out.print((char)c);

        } catch (Exception e) {
            System.err.println(Autenticador.class + " > doInBackground > " + e.toString());
        }

        cancel(false);
        if(isCancelled())
            System.out.println(Autenticador.class + " > doInBackground > Concluído");

        return true;
    }
}
