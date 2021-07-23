/**
 * @class AccDestruirHotel
 * @brief Implementació del algoritme d'una acció amb la que un jugador destrueix un hotel d'una de les seves propietats
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccDestruirHotel implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post  Crea una acció per treure un hotel d'una propietat. */
    public AccDestruirHotel(Impresora imp){
        a_impresora = imp;
    }

    /** @brief El jugador escull treure un hotel d'una de les seves propietat
    @pre j1!=null
    @post Hotel tret d'una propietat del jugador j1. */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String nomPropietat;
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

        // Fem la cerca del terreny
        boolean terrenyTrobat = false;
        Iterator<Terreny> itTerr = j1.prop().iterator();

        while(itTerr.hasNext() && !terrenyTrobat){
            Terreny ter = itTerr.next();
            if(ter.equals(propAux)){
                terrenyTrobat = true;

                // Retornem diners al jugador
                double dinersRecuperats = ter.treureHotel();
                if (dinersRecuperats>0)
                    j1.ingressarDiners(dinersRecuperats);
                else
                    a_impresora.imprimir("La propietat no té cap hotel construit (" + ter.obtNHotel() + "/" + ter.obtMaxHotels() + ")");

            }
        }
    }

    /** @pre ---
     @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Destruir Hotel";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "DESTRUIR_HOTEL";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Destruir Hotel";
    }
}
