package app.wifiautologin;


import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by silasmsales on 10/12/18.
 */

public class Perfil implements Serializable{

    private String  descricao;
    private URL     urlLogin;
    private URL     urlLogout;
    private List <Parametro> parametros = new ArrayList<Parametro>();

    public void addParametro(Parametro parametro){
        getParametros().add(parametro);
    }

    public List<Parametro> getParametros() {
        return parametros;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public URL getUrlLogin() {
        return urlLogin;
    }

    public void setUrlLogin(URL urlLogin) {
        this.urlLogin = urlLogin;
    }

    public URL getUrlLogout() {
        return urlLogout;
    }

    public void setUrlLogout(URL urlLogout) {
        this.urlLogout = urlLogout;
    }


}
