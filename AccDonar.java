/**
* @class AccDonar
* @brief Implementació del algoritme d'una acció que obliga al jugador a donar una de les seves propietats a un altre jugador
* @author Marc Torres Vilagut (u1959683)
*/

import java.util.List;
import java.util.ArrayList;

public class AccDonar implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post  Crea una acció per donar una propietat */
    public AccDonar(Impresora imp){ a_impresora = imp; }

    /** @brief El jugador j1 un escull una de les seves propietats i se la dona a un altre jugador de la seva elecció
    @pre j1!=null && jugs.size()>1
    @post  El jugador dona una propietat seva a un altre jugador*/
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        Terreny propietat;

        propietat = obtPropietat(j1);

        if (propietat==null){
            a_impresora.imprimir("El jugador " + j1.nom() + " no té propietats.");
            return;
        }
        donar(j1,jugs,propietat);
    }

    /** @pre ---
     @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Donar: Escull una de les teves propietats. Escull un jugador per donar-li la propietat (nom).";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "DONAR";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Donar Propietat";
    }

    /** @pre ---
    @post Retorna el terreny que escull el jugador */
    private Terreny obtPropietat(Jugador j1){
        String nomPropietat;
        Terreny propietatAux, propietat = null;
        List<String> valids = new ArrayList<String>();

        // Si el jugador no té propietats retornar res
        if (j1.prop().size()<1)
            return null;

        // Creem les respostes vàlides (noms de les propietats del jugador j1)
        for (Terreny terreny : j1.prop()) { valids.add(terreny.obtNom()); }
        a_impresora.imprimir("Propietats disponibles: \n"+ valids);


        // Entrar el nom de la propietat que vol donar
        nomPropietat = j1.entrarString(true,valids);

        // Obtenir la propietat
        propietatAux = new Terreny(nomPropietat);
        int pos = j1.teTerreny(propietatAux);
        propietat = j1.treureTerreny(pos); // Canviar treureTerreny de void a Terreny per obtenir la propietat

        return propietat;
    }

    /** @pre propietat!=null
    @post Un altre jugador rep la propietat donada */
    private void donar(Jugador j1, List<Jugador> jugs, Terreny propietat){
        String nomJugador2;
        List<String> valids= new ArrayList<String>();

        // Creem les respostes vàlides
        for (Jugador jug : jugs) {
            if(!jug.equals(j1))
                valids.add(jug.nom());
        }

        a_impresora.imprimir("Escull un jugador: " + valids);

        // Entrar el nom de la jugador a qui vol donar la propietat
        nomJugador2 = j1.entrarString(true,valids);

        // Busquem el jugador fins trobar-lo i li donem la propietat

        for (Jugador j2 : jugs) {
            if (j2.nom().equals(nomJugador2)) {
                a_impresora.imprimir(j2.nom() + " rep la propietat " + propietat.obtNom() + " de " + j1.nom());
                j2.afegirTerreny(propietat);
                propietat.canviarPropietari(j2);
                break;
            }
        }

    }

}
