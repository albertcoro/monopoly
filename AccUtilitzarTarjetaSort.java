/**
 * @class AccDonar
 * @brief Implementació del algoritme d'una acció permet a un jugador utilitzar una de les seves tarjetes de la sort
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;
import java.util.List;

public class AccUtilitzarTarjetaSort implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post  Crea una acció per utilitzar una tarjeta de la sort. */
    public AccUtilitzarTarjetaSort(Impresora imp){ a_impresora = imp; }

    /** @brief El jugador utiltza una tarjeta de la sort.
    @pre j1!=null && t!=null
    @post Un jugador utilitza una tarjeta de les seves tarjetes de la sort */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String nomTarjeta;
        List<String> valids = new ArrayList<String>();
        List<TarjetaSort> tarjetes;
        TarjetaSort ts;

        // Obtenir llista de tarjetes de la sort del jugador
        tarjetes = j1.tarjetes();

        // Generem les respostes valides
        for (TarjetaSort tarjeta : tarjetes) {
            valids.add(tarjeta.paraulaClau());
        }

        if(tarjetes.isEmpty()){
            a_impresora.imprimir(j1.nom() + " no té cartes de la sort.");
            return;
        }

        a_impresora.imprimir("Escull una tarjeta de la sort:\n" +
                        valids);

        // Escullim la tarjeta que volem utilitzar
        nomTarjeta = j1.entrarString(true,valids);
        ts = obtTarjetaSort(tarjetes,nomTarjeta);

        // Utilitzem la tarjeta i la retornem a la pila
        ts.utilitza(j1,c,jugs,b,t);
        j1.retirarTarjeta(ts);
        t.tornarTarjeta(ts);


    }

    /** @pre ---
     @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Utilitzar Tarjeta Sort: Escull una tarjeta de la sort per utilitzar";
    }

    /** @pre ---
    @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "USAR_TARJETA";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Utilitzar Tarjeta Sort";
    }

    /** @pre tarjetes.size()>0
    @post Retorna la tarjeta de la sort seleccionada */
    private TarjetaSort obtTarjetaSort(List<TarjetaSort> tarjetes,String clau){
        for (TarjetaSort ts : tarjetes) {
            if (ts.paraulaClau().equals(clau))
                return ts;
        }

        return null;
    }
}
