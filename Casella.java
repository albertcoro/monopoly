/**
* @class Casella
* @brief Casella d'un tauler 
* @author Marc Torres Vilagut (u1959683)
*/

import java.util.ArrayList;

public interface Casella {
    /** @pre ---
    @post Retorna les accions que pots/has de fer en una casella */
    Accio opcions();

    /** @pre ---
     @post Retorna l'identificador d'una casella */
    String id();
}
