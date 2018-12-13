package app.wifiautologin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MinhasWIFI implements Serializable {

    private List<Perfil> perfis;

    public MinhasWIFI(){
        this.perfis = new ArrayList<Perfil>();
    }

    public MinhasWIFI(List <Perfil> perfis){
        this.perfis = perfis;
    }

    public List<Perfil> getPerfis(){
        return this.perfis;
    }


    public void addPerfil(Perfil perfil){
        this.perfis.add(perfil);
    }

    public Perfil getWIFIbyDescricao(String descricao){

        Perfil perfil = null;
        ListIterator<Perfil> iterator = perfis.listIterator();
        while (iterator.hasNext()){
            perfil = iterator.next();
            if (perfil.getDescricao() == descricao)
                break;
        }
        return perfil;
    }
}
