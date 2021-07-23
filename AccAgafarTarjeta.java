/**
 * @class AccAgafarTarjeta
 * @brief Implementació del algoritme d'una acció que permet als jugadors agafar una tarjeta de la pila
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;
import java.util.List;

public class AccAgafarTarjeta implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post Crea una acció per agafar una tarjeta de la sort */
    public AccAgafarTarjeta(Impresora imp){
        a_impresora = imp;
    }

    /** @brief El jugador agafa una tarjeta de la sort de la pila.
    @pre j1!=null && c!=null && b!=null,t!=null
    @post  El jugador obté una tarjeta de la sort. */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        TarjetaSort ts; // Treure carta de la pila de tarjetes
        List<String> valids = new ArrayList<String>();
        String resposta;
        valids.add("si"); valids.add("no");

        ts = t.treureTarjeta();
        if(ts==null) {
            a_impresora.imprimir("No queden tarjetes de la sort a la pila.");
            return;
        }else if(ts.esPosposable()) {
            // Es posposable, vols usarla igualment?
            a_impresora.imprimir("Vols utilitzar la carta? (si/no)");
            resposta = j1.entrarString(true, valids);
            if (resposta.equals("no")) {
                // Guardar tarjeta a les cartes del jugador
                j1.afeguirTarjeta(ts);
                return;
            }
        }
        ts.utilitza(j1,c,jugs,b,t);
        t.tornarTarjeta(ts);

    }

    /** @pre ---
    @post Retorna un avis de que el jugador pot agafar una tarjeta de la sort */
    @Override
    public String pregunta() {
        return "Agafa una Tarjeta de la Sort de la pila de tarjetes.";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "AGAFAR_TARJETA";
    }

    /** @pre ---
    @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Agafar Tarjeta Sort";
    }
}
