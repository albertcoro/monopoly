/** @file Bot.java
 @brief Classe Bot
 */

import java.util.List;

/**
 * @class Bot
 * @author Albert Corominas Mariscot (u1960480)
 * @brief Jugador controlat per ordinador, amb diferents checks per comprovar que compleix amb la estrategia donada.
 */

public class Bot extends Jugador {

    /**
     * @pre True
     * @post Crea un objecte bot amb id, nom i una impresora per mostrar per pantalla
     */
    Bot(int id, String nom, Impresora imp){
        super(id,nom,imp);
    }

    /**
     * @pre True
     * @post Retorna una string aleatori de la llista de strings, si es buida retorna test ja que no es limitada la resposta.
     */
    @Override
    String entrarString(boolean limitat, List<String> valids) {
        if(valids != null){
            int rand = (int) (Math.random()*(valids.size()));
            a_impresora.imprimir(a_nom+"> " + valids.get(rand));
            return valids.get(rand);
        } else {
            a_impresora.imprimir(a_nom+"> test");
            return "test";
        }
    }

    /**
     * @pre True
     * @post Retorna un numero aleatori entre limMinim i limMaxim
     */
    @Override
    int entrarInt(int limMinim, int limMaxim, boolean min, boolean max) {
        int rand = (int) (Math.random()*(limMaxim-limMinim+1)+limMinim);
        a_impresora.imprimir(a_nom+"> " + rand);
        return rand;
    }

    /**
     * @pre True
     * @post Retorna un numero aleatori entre limMinim i limMaxim
     */
    @Override
    double entrarDouble(double limMinim, double limMaxim, boolean min, boolean max) {
        double rand = (Math.random()*(limMaxim-limMinim)+limMinim);
        a_impresora.imprimir(a_nom+"> " + rand);
        return rand;
    }

    /**
     * @pre True
     * @post Retorna true is this i o es el mateix objecte, false en cas contrari
     */
    @Override
    public boolean equals(Object o) {
        if(o == this){
            return true;
        }

        if(!(o instanceof Jugador)){
            return false;
        }

        Jugador j = (Jugador) o;
        return j.a_id == a_id;
    }
}
