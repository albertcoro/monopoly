/** @file Banca.java
 @brief Classe Banca
 */

import java.util.List;

/**
 * @class Banca
 * @author Albert Corominas Mariscot (u1960480)
 * @brief La banca serveix per guardar totes les propietats que no tinguin propietat de jugadors i també aquelles
 * propietats que no pugin ser propietat de ningu (Exemple: Casella de sortida, Casella de sort, Casella de aposta,
 * etc.)
 */

public class Banca extends Propietari {

    /**
     * @pre True
     * @post Crea un objecte Banca buit
     */
    Banca(){
        super();
    }

    /**
     * @pre True
     * @post Passa la llista de terrenys de la banca
     */
    void terrenys(List<Terreny> t){
        a_terr = t;
    }

    /**
     * @pre !a_terr.exist(t)
     * @post Afagueix un terreny t a la llista de terrenys
     */
    public void afegirTerreny(Terreny t){
        a_terr.add(t);
    }
}
