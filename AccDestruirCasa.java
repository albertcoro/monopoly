/**
 * @class AccDestruirCasa
 * @brief Implementació del algoritme d'una acció amb la que un jugador destrueix cases d'una de les seves propietats
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccDestruirCasa implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post  Crea una acció per treure cases d'una propietat. */
    public AccDestruirCasa(Impresora imp){
        a_impresora = imp;
    }

    /* @brief El jugador escull treure cases d'una de les seves propietat.
    @pre j1!=null
    @post Cases tretes d'una propietat del jugador. */
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

        a_impresora.imprimir("Escull una de les teves propietats (nom):\n" + valids);

        nomPropietat = j1.entrarString(true,valids);
        propAux = new Terreny(nomPropietat);

        boolean terrenyTrobat = false;
        Iterator<Terreny> itTerr = j1.prop().iterator();

        while(itTerr.hasNext() && !terrenyTrobat){
            Terreny ter = itTerr.next();
            if(ter.equals(propAux)){
                terrenyTrobat = true;
                a_impresora.imprimir("Quantes cases vols treure?");
                nCases = j1.entrarInt(0,ter.obtMaxCases(),true,true);

                // Retornem diners al jugador
                double dinersRecuperats = ter.treureCasa(nCases);
                if(dinersRecuperats>0)
                    j1.ingressarDiners(dinersRecuperats);
                else
                    a_impresora.imprimir("La propietat no té cap casa construida (" + ter.obtNCases() + "/" + ter.obtMaxCases() + ")");
            }
        }
    }

    @Override
    public String pregunta() {
        return "Destruir Cases";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "DESTRUIR_CASA";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Destruir Casa";
    }
}
