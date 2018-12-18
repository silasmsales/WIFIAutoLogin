package app.wifiautologin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class MinhasWIFI implements Serializable {

    private static FileInputStream fis_redes_salvas;
    private static ObjectInputStream ois_redes_salvas;
    private static FileOutputStream fos_redes_salvas;
    private static ObjectOutputStream oos_redes_salvas;
    private static File arquivo;
    private ArrayList<Perfil> perfis;

    public MinhasWIFI(File arquivo){
        this(new ArrayList<Perfil>(), arquivo);
    }

    protected MinhasWIFI(ArrayList<Perfil> perfis, File arquivo){
        this.arquivo = arquivo;

        if (jaExisteRegistro())
        {
            this.perfis = getPerfis();
        }else{
            this.perfis = perfis;
        }
        save();
    }

    protected void save() {
        try {

            File file = new File(arquivo.toURI());

            fos_redes_salvas = new FileOutputStream(file);
            oos_redes_salvas = new ObjectOutputStream(fos_redes_salvas);
            oos_redes_salvas.writeObject(perfis);
            oos_redes_salvas.close();
            fos_redes_salvas.close();

        } catch (FileNotFoundException e) {
            System.out.println(MinhasWIFI.class + " > save > " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean jaExisteRegistro(){
        File file = new File(arquivo.toURI());
        return file.exists();
    }

    protected ArrayList<Perfil> getPerfis(){
        try {

            File file = new File(arquivo.toURI());

            fis_redes_salvas = new FileInputStream(file);
            ois_redes_salvas = new ObjectInputStream(fis_redes_salvas);
            perfis = (ArrayList) ois_redes_salvas.readObject();
            ois_redes_salvas.close();
            fis_redes_salvas.close();

        } catch (FileNotFoundException e) {
            System.out.println(MinhasWIFI.class + " > getPerfis > " +  e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return this.perfis;
    }

    public void addPerfil(Perfil perfil){

        if(getWIFIbyDescricao(perfil.getDescricao()) != null)
        {
            perfis.remove(getWIFIbyDescricao(perfil.getDescricao()));
        }
        perfis.add(perfil);
        save();
    }

    protected Perfil getWIFIbyDescricao(String descricao){

        Perfil perfil = null;
        ListIterator<Perfil> iterator = perfis.listIterator();

        while (iterator.hasNext())
            if (iterator.next().getDescricao().equals(descricao))
                return iterator.previous();

        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator iterator  = perfis.iterator();
        Parametro parametro;
        Perfil perfil;
        while (iterator.hasNext()){
            perfil = (Perfil) iterator.next();
            stringBuilder.append("\n@" + MinhasWIFI.class + "{\n");
            stringBuilder.append("{ Descrição : " + perfil.getDescricao() + "},\n");
            stringBuilder.append("{ URL Login : " + perfil.getUrlLogin() + "},\n");
            stringBuilder.append("{ URL Login : " + perfil.getUrlLogin() + "},\n");
            stringBuilder.append("{ URL Logout: " + perfil.getUrlLogout() + "},\n");
            Iterator i1 = perfil.getParametros().iterator();
            while (i1.hasNext()){
                parametro = (Parametro) i1.next();
                stringBuilder.append("{ Parâmetros : ");
                stringBuilder.append("\t\t[ "+parametro.getNome()+" ] [ " + parametro.getValor() + "] [ " + parametro.getRotulo() + " ] [ " + parametro.getTipo()+" ] }\n");
            }
            stringBuilder.append("}");
        }
        return stringBuilder.toString();
    }
}
