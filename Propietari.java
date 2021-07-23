/** @file Propietari.java
 @brief Classe Propietari
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @class Propietari
 * @author Albert Corominas Mariscot (u1960480)
 * @brief Classe generica de propietari, per donar pertenyencies a caselles
 */

public class Propietari {
    List<Terreny> a_terr; ///< Llista de terrenys del jugador.

    /**
     * @pre True
     * @post Crea un propietari amb una llista de terrenys buida
     */
    Propietari(){
        a_terr = new ArrayList<Terreny>();
    }

    /**
     * @pre True
     * @post Retorna una llista amb les propietats del propietari actual.
     */
    public List<Terreny> prop(){
        return a_terr;
    }

    /**
     * @pre True
     * @post Afagueix la propietat p a a_prop.
     */
    public void afegirTerreny(Terreny p){
        if(!a_terr.contains(p)){
            a_terr.add(p);
        }
    }

    /**
     * @pre True
     * @post Treu la propietat p de la llista de propeitats prop.
     */
    public Terreny treureTerreny(int index){
        return a_terr.remove(index);
    }

    /**
     * @pre Cert
     * @post Retorna la posició de la propietat dintre el conjunt de propietats, si no l'ha trobat retorna -1.
     */
    public int teTerreny(Terreny p){
        return a_terr.indexOf(p);
    }


}
