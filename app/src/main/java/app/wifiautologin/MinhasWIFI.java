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
import java.util.ListIterator;

public class MinhasWIFI implements Serializable {

    private static final String ARQUIVO = "REDES_SALVAS.OBJ";
    private static FileInputStream fis_redes_salvas;
    private static ObjectInputStream ois_redes_salvas;
    private static FileOutputStream fos_redes_salvas;
    private static ObjectOutputStream oos_redes_salvas;
    private static File DIRETORIO;
    private ArrayList<Perfil> perfis;

    public MinhasWIFI(File diretorio){
        this(new ArrayList<Perfil>(), diretorio);
    }

    protected MinhasWIFI(ArrayList<Perfil> perfis, File diretorio){
        DIRETORIO = diretorio;

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

            File file = new File(DIRETORIO, ARQUIVO);

            fos_redes_salvas = new FileOutputStream(file);
            oos_redes_salvas = new ObjectOutputStream(fos_redes_salvas);
            oos_redes_salvas.writeObject(perfis);
            oos_redes_salvas.close();
            fos_redes_salvas.close();

        } catch (FileNotFoundException e) {
            System.out.println("Erro 0: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean jaExisteRegistro(){
        File file = new File(DIRETORIO, ARQUIVO);
        return file.exists();
    }

    protected ArrayList<Perfil> getPerfis(){
        try {

            File file = new File(DIRETORIO, ARQUIVO);

            fis_redes_salvas = new FileInputStream(file);
            ois_redes_salvas = new ObjectInputStream(fis_redes_salvas);
            perfis = (ArrayList) ois_redes_salvas.readObject();
            ois_redes_salvas.close();
            fis_redes_salvas.close();

        } catch (FileNotFoundException e) {
            System.out.println("Erro 0: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return this.perfis;
    }

    public void addPerfil(Perfil perfil){
        this.perfis.add(perfil);
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

}
