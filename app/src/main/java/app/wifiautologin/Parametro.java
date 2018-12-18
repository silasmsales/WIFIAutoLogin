package app.wifiautologin;

import java.io.Serializable;

public class Parametro implements Serializable {

    private String nome;
    private String valor;
    private String rotulo;
    private String tipo;

    public Parametro(){
    }

    public Parametro(String nome, String valor, String tipo, String rotulo) {
        setNome(nome);
        setValor(valor);
        setTipo(tipo);
        setRotulo(rotulo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRotulo() {
        return this.rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

}
