/**
* @class AccMulta
* @brief Implementació del algoritme d'una acció que multa a un jugador 
* @author Marc Torres Vilagut (u1959683)
*/

import java.util.ArrayList;
import java.util.List;

public class AccMulta implements Accio{
    private double a_quantitat;     ///< Quantitats de diners que el jugador ha de pagar
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post Crea una acció per pagar amb una multa de diners definida */
    public AccMulta(Impresora imp, double quantitat){
        a_impresora = imp;
        a_quantitat = quantitat;
    }

    /* @brief El jugador rep un terreny de la banca com a recompensa
    @pre j1!=null
    @post El jugador paga la multa */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        a_impresora.imprimir(j1.nom() + " paga " + a_quantitat + " de multa");
        j1.retirarDiners(a_quantitat);

        if(j1.diners()<=0)
            desqualificarJugador(j1,jugs,b);
    }

    /** @pre ---
     @post Retorna un avis del diners que perd el jugador */
    @Override
    public String pregunta() {
        return "Multa: El jugador ha de pagar " + a_quantitat + " de multa";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "MULTA";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Pagar Multa";
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
