/**
* @class AccRebre
* @brief Implementació del algoritme d'una acció de rebre un terreny d'un altre jugador
* @author Marc Torres Vilagut (u1959683)
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class AccRebre implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post Crea una acció per rebre una priopietat d'un altre jugador */
    public AccRebre(Impresora imp){ a_impresora = imp; }

    /** @brief El jugador escull una priopietat d'un altre jugador
    @pre j1!=null && jugs.size()>1
    @post El jugador obté la priopietat escollida de l'altre jugador  */
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String nomPropietat;
        Terreny propietatAux, propietat = null;
        int iProp;
        List<String> valids = new ArrayList<String>();
        boolean trobat = false;

        // Construim el rang de valors valids
        for (Jugador jug : jugs) {
            if(!jug.equals(j1))
                jug.prop().forEach(ter -> valids.add(ter.obtNom()));

        }
        System.out.println("Propietats: " + valids);

        // Si no hi han propietats valides (els altres jugadors no tenen més propietats), no fer res
        if(valids.size()<1){
            return;
        }

        while(propietat==null) {
            // No entrem un rang de valids per que és massa gran i hauriem de calcular-lo 2 cops per iteració
            nomPropietat = j1.entrarString(true,valids);
            propietatAux = new Terreny(nomPropietat);

            Iterator<Jugador> itJugadors = jugs.iterator();
            while (itJugadors.hasNext() && !trobat){
                Jugador j = itJugadors.next();
                if ((iProp = j.teTerreny(propietatAux))!=-1){
                    trobat = true;
                    propietat = j.treureTerreny(iProp); // Retornar el terreny i assignar-lo a Terreny propietat
                    j1.afegirTerreny(propietat);
                    propietat.canviarPropietari(j1);
                }
            }
        }

        // System.out.println("[Temporal] "+j1.nom()+" rep una propietat escollida d'un altre jugador."); // Temporal per enseñar el funcionament de les accions al torn
    }

    /** @pre ---
     @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Rebre: Escull una propietat (nom) d'un altre jugador .";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "REBRE";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Rebre Propietat";
    }
}
