/**
 * @class Aposta
 * @brief Aposta es una casella del tauler que no es pot comprar. Si un jugador cau en aquesta casella pot apostar, a un numero resultant de daus i amb una quantitat.
 * @author Marc Torres Vilagut (u1959683)
 */
import java.util.ArrayList;
import java.util.Collections;

public class Aposta implements Casella{
    private AccAposta a_apostar;       ///< Acció d'apostar

    /** @pre: ---
    @post: Crea una casella d'aposta */
    public Aposta(Impresora imp){ a_apostar = new AccAposta(imp);}

    /** @pre ---
    @post Retorna la opció d'apostar */
    @Override
	public Accio opcions() {
		return a_apostar;
	}

    /** @pre ---
     @post Retorna l'identificador de la casella */
    @Override
    public String id(){ return "A"; }

    /** @brief Retorna la casella Aposta en format String
    @pre ---
    @post Retrona un string amb les dades de la casella Aposta */
    @Override
    public String toString() {
        return  "Casella d'Aposta:\n"+
                "   Pots apostar a una tirada una quantitat de diners que escullis en aquesta casella per la posibilitat d'obtenir un premi";
    }
}
