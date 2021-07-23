/**
* @class AccPagar
* @brief Implementació del algoritme d'una acció que obliga al jugador pagar una quantitat de diners a un altre jugador
* @author Marc Torres Vilagut (u1959683)
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class AccPagar implements Accio{
    private double a_quantitat;     ///< Quantitats de diners que el jugador ha de pagar
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post Crea una acció per pagar una quantitat de diners definida. */
    public AccPagar(Impresora imp, double quantitat){
        a_impresora = imp;
        a_quantitat = quantitat;
    }

    /** @brief El jugador escull a qui vol pagar la  quantitat establerta.
    @pre j1!=null && jugs.size()>1
    @post El jugador paga la quantitat de diners i se la dona a un altre jugador */
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String nomJugador2;
        boolean jugadorTrobat = false;
        List<String> valids = new ArrayList<String>();

        // Creem les respostes vàlides
        for (Jugador jug : jugs) { valids.add(jug.nom()); }
        valids.remove(j1.nom());

        a_impresora.imprimir("Escull jugador: " + valids);

        // Entrar el nom de la jugador a qui vol donar la propietat
        nomJugador2 = j1.entrarString(true,valids);

        // Busquem el jugador fins trobar-lo i li donem la propietat
        Iterator<Jugador> it = jugs.iterator();

        while (it.hasNext() && !jugadorTrobat){
            Jugador j2 = it.next();
            if (j2.nom().equals(nomJugador2)){
                double pagament = Math.min(a_quantitat,j1.diners()); // Per si el jugador té menys diners dels especificats
                a_impresora.imprimir(j1.nom() + " paga " + pagament + " a " + j2.nom());
                jugadorTrobat = true;
                // Donar diners al jugador escollit
                j1.retirarDiners(pagament);
                j2.ingressarDiners(pagament);
            }
        }

        if(j1.diners()<=0)
            desqualificarJugador(j1,jugs,b);
    }

    /** @pre ---
     @post Retorna un avis de la quantitat de diners que perd el jugador */
    @Override
    public String pregunta() {
        return "Pagar: El jugador ha de pagar " + a_quantitat + " de diners a un jugador de la seva elecció.";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "PAGAR";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Pagar Diners";
    }

    /**
     * @pre ---
     * @post Desqualifica a al jugador i donem les seves propietats a la banca. Mostra per pantalla que s'ha desqualificat.
     */
    public void desqualificarJugador(Jugador j,List<Jugador> jugs, Banca b){
        a_impresora.imprimir("S'ha desqualificat el jugador (" + j.nom() + ")");
        for (Terreny terreny : j.prop()) {
            b.afegirTerreny(terreny);
            terreny.canviarPropietari(b);
        }
        jugs.remove(j);
    }

}
