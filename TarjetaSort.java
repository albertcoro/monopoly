/**
 * @class TarjetaSort
 * @brief Tarjeta que contindra una acció que un jugador podra executar en qualsevol moment si es posposable, o al instant d'agafar-la, si no ho és.
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.List;

public class TarjetaSort {
    private Accio a_comanda;        ///< Acció que guardara una Tarjeta de la sort dins
    private boolean a_posposable;   ///< Valor boolean que indica si la Tarjeta es pot utilitzar més tard de la seva obtenció o, altrament si és d'us inmediat

    /** @pre: --
    @post: Crea una tarjeta de la sort amb una acció. */
    public TarjetaSort(Accio comanda, boolean posposable){
        a_comanda = comanda;
        a_posposable = posposable;
    }

    /** @pre ---
    @post Retorna cert si la acció de la tarjeta de la sort és posposable, fals altrament*/
    public boolean esPosposable(){
        return a_posposable;
    }

    /** @pre: --
    @post:  Executa la comanda de la tarjeta de la sort.*/
    public void utilitza(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        a_comanda.executa(j1,c,jugs,b,t);
    }

    /** @pre ---
     @post Retorna la parula clau amb la que s'identifica l'acció que conté la tarjeta de la sort */
    public String paraulaClau(){
        return a_comanda.paraulaClau();
    }

    /** @pre ---
     @post Retrona cert si les dues tarjetes contenen la mateixa acció, altrament fals */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if(!(o instanceof TarjetaSort))
            return false;
        TarjetaSort aux = (TarjetaSort) o;
        return a_comanda.paraulaClau().equals(aux.paraulaClau());
    }
}
