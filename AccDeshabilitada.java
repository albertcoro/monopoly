/**
 * @class AccOfertaCompra
 * @brief Implementació del algoritme d'una acció qualsevol deshabilitada que no fa res
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.List;

public class AccDeshabilitada implements Accio {
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
     @post Crea una acció deshabilitada. */
    AccDeshabilitada(Impresora imp){
        a_impresora = imp;
    }

    /** @brief No fa res
     @pre ---
     @post No fa res */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t) {
        a_impresora.imprimir("La funció d'aquesta casella està deshabilitada en aquesta partida.");
    }

    /** @pre ---
     @post Retorna una avis de que l'acció esta deshabilitada */
    @Override
    public String pregunta() {
        return "La acció esta deshabilitada";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "DESHABILITAT";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Acció deshabilitada";
    }
}
