/**
 * @class AccAnarA
 * @brief Implementació del algoritme d'una acció per fer que un jugador vagi a una posició concreta del tauler.
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.List;

public class AccAnarA implements Accio{
    private int a_posicio; ///< Posició del tauler a la que el jugador serà enviat
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre pos>0
    @post  Crea una acció per anar a una posició del tauler */
    public AccAnarA(Impresora imp, int pos){
        a_impresora = imp;
        a_posicio = pos;
    }

    /** @brief El jugador j1 viatja a una posició del tauler
    @pre j1!=null && c!=null && t!=null
    @post El jugador j1 es mou a la posició del tauler indicada i executa l'accio corresponent a la casella on cau */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        // Moguem al jugador a la posició indicada i obtenim les opcions que té
        t.moureA(j1,a_posicio);
        Casella c1 = t.casella(a_posicio);
        Accio a = c1.opcions();

        a_impresora.imprimir("El jugador es mou a la casella: "+c1.toString());
        a_impresora.imprimir(a.pregunta());
        a.executa(j1,c1,jugs,b,t);
    }

    /** @pre ---
    @post Retorna un string avisant de que el jugador serà mogut a una posició concreta del tauler */
    @Override
    public String pregunta() {
        return "El jugador es moura a la posició " + a_posicio + " del tauler.";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "ANAR";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Anar A";
    }
}
