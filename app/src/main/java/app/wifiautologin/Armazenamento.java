package app.wifiautologin;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Armazenamento {

    private static String MINHAS_REDES = "redes.o";
    private static FileInputStream fis_redes_salvas;
    private static ObjectInputStream ois_redes_salvas;
    private static FileOutputStream fos_redes_salvas;
    private static ObjectOutputStream oos_redes_salvas;

    public static boolean jaExisteRegistro(Context context){
        File file = new File(context.getFilesDir(), MINHAS_REDES);
        if (file.exists())
            System.out.println("jaExisteRegistro > TRUE");
        else
            System.out.println("jaExisteRegistro > FALSE");

        return file.exists();
    }

    public static boolean salvarRedes(MinhasWIFI minhasWIFI, Context context){

        boolean retorno = false;
        System.out.println("salvarRedes > salvando registros");
        try {

            File file = new File(context.getFilesDir(), MINHAS_REDES);

            fos_redes_salvas = new FileOutputStream(file);
            oos_redes_salvas = new ObjectOutputStream(fos_redes_salvas);
            oos_redes_salvas.writeObject(minhasWIFI);
            oos_redes_salvas.close();
            fos_redes_salvas.close();
            System.out.println("salvarRedes > registros salvos : " + minhasWIFI.getPerfis().size());
            retorno  = true;

        } catch (FileNotFoundException e) {
            System.out.println("Erro 0: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public static MinhasWIFI resgastarRedes(Context context) {
        MinhasWIFI minhasWIFI = null;
        System.out.println("resgastarRedes > resgatando registros");

        try {

            File file = new File(context.getFilesDir(), MINHAS_REDES);
            minhasWIFI = null;

            if (file.exists()) {
                fis_redes_salvas = new FileInputStream(file);
                ois_redes_salvas = new ObjectInputStream(fis_redes_salvas);
                minhasWIFI = (MinhasWIFI) ois_redes_salvas.readObject();
                ois_redes_salvas.close();
                fis_redes_salvas.close();
                System.out.println("resgatandoRedes > registros  : " + minhasWIFI.getPerfis().size());
            } else {
                System.out.println("Sem registro cadastrado");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Erro 0: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return minhasWIFI;
    }
}
