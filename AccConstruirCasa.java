/**
 * @class AccConstruirCasa
 * @brief Implementació del algoritme d'una acció amb la que un jugador construieix cases a una de les seves propietats
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccConstruirCasa implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post  Crea una acció per contruir cases en una propietat. */
    public AccConstruirCasa(Impresora imp){
        a_impresora = imp;
    }

    /* @brief El jugador escull contruir cases a una de les seves propietat
    @pre j1!=null.
    @post Cases construides a una propietat del jugador j1. */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String nomPropietat;
        int nCases;
        Terreny propAux;
        List<String> valids= new ArrayList<String>();

        // Generem les respostes valides
        for (Terreny terreny : j1.prop()) { valids.add(terreny.obtNom()); }

        if(valids.isEmpty()) {
            a_impresora.imprimir("El jugador " + j1.nom() + " no té propietats.");
            return;
        }

        a_impresora.imprimir("Escull una de les teves propietats (nom): \n" + valids);

        nomPropietat = j1.entrarString(true,valids);
        propAux = new Terreny(nomPropietat);

        boolean terrenyTrobat = false;
        Iterator<Terreny> itTerr = j1.prop().iterator();

        while(itTerr.hasNext() && !terrenyTrobat){
            Terreny ter = itTerr.next();
            if(ter.equals(propAux)){
                terrenyTrobat = true;
                a_impresora.imprimir("Quantes cases vols construir?");
                nCases = j1.entrarInt(0,ter.obtMaxCases(),true,true);
                double preuConstruccio = ter.obtPreuCasa()*nCases;

                // Comprovem que el jugador tingui suficients diners.
                if (j1.diners()>preuConstruccio) {
                    boolean teAgrupacio;
                    if (!ter.obtAgrupacio().equals("no"))
                        teAgrupacio = false; // obtenir si el jugador j té tota l'agrupació de caselles
                    else
                        teAgrupacio = false;
                    if(ter.construirCasa(nCases,teAgrupacio))
                        j1.retirarDiners(preuConstruccio);
                    else
                        a_impresora.imprimir("No es pot construir. Màxim nombre de cases assolit (" + ter.obtNCases() + "/" + ter.obtMaxCases() + ")");
                }
            }
        }
    }

    /** @pre ---
     @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Construir Cases";
    }
    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "CONSTRUIR_CASA";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Construir Casa";
    }
}
