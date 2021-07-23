/** @file Impresora.java
 @brief Classe Impresora
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @class Impresora
 * @author Albert Corominas Mariscot (u1960480)
 * @brief Modul funcional que permet la sortida per pantalla i enviament de text a fitxer de registre.
 */

public class Impresora {

    private File a_fitRegis; ///< Fitxer de registre on guardarem totes les interaccions de text de la partida.

    /**
     * @pre True
     * @post Crea la carpeta ./registre i crea un fitxer nou de regsitre per guardar interaccions
     */
    Impresora(){
        crearCarpetaRegistre();
        crearFitxerRegistre();
    }

    /**
     * @pre True
     * @post Metode que recolecta tot el que s'imprimeix per pantalla i es escrit per pantalla, i ho posa dintre el
     * fitxer de registre de la partida.
     */
    public void imprimir(String s){
        try {
            FileOutputStream oFile = new FileOutputStream(a_fitRegis, true);
            OutputStreamWriter out = new OutputStreamWriter(oFile);
            out.write(s);
            out.write("\n");
            System.out.println(s);
            out.close();
            oFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @pre True
     * @post Metode que guarda exclusivament les entrades de jugadors per que no es mostrin varies vegades per pantalla.
     */
    public void imprimirFitxerOnly(String s){
        try {
            FileOutputStream oFile = new FileOutputStream(a_fitRegis, true);
            OutputStreamWriter out = new OutputStreamWriter(oFile);
            out.write(s);
            out.write("\n");
            out.close();
            oFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @pre True
     * @post Si no existex la carpeta registre per guardar els fitxers de registre de partides.
     */
    private void crearCarpetaRegistre(){
        File directory = new File("./registre");
        if (! directory.exists()){
            directory.mkdir();
        }
    }

    /**
     * @pre True
     * @post Crea un fitxer de registre txt on s'escriu totes les interaccions de la partida, el nom es predefinit com
     * partida i, on i es el numero de partides que portes jugades (comprovant si el fiter de partida existeix)
     */
    private void crearFitxerRegistre(){
        boolean fit;
        try {
            int i = 1;
            String s = "./registre/Partida_"+i+".txt";
            File fitxer = new File(s);
            while(fitxer.exists()){
                i++;
                s = "./registre/Partida_"+i+".txt";
                fitxer = new File(s);
            }
            fit = fitxer.createNewFile();
            a_fitRegis = fitxer;
            if(fit){
                imprimir("S'ha creat un fitxer de registre " + s + " que guardara totes les interaccions de la " +
                        "partida.");
            } else {
                imprimir("No s'ha pogut crear el fitxer de registre");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
