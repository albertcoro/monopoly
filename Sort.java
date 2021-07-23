/**
 * @class Sort
 * @brief Sort es una casella del tauler que no es pot comprar. Si un jugador cau en aquesta casella haura de agafar una tarjeta de sort de la pila de targetes.
 * @author Marc Torres Vilagut (u1959683)
 */

public class Sort implements Casella{

    private AccAgafarTarjeta a_agafarTS;       ///< Acció d'agafar una tarjeta de la sort
    
    /* @brief: Crea una casella de tipus sort
    @pre: --
    @post: Crea una casella de la sort */
    public Sort(Impresora imp){ a_agafarTS = new AccAgafarTarjeta(imp);}

    /* @brief Retorna l'opció d'agafar una tarjeta de la sort
    @pre ---
    @post Retorna l'opció d'agafar una tarjeta de la sort */
    @Override
	public Accio opcions() {
        return a_agafarTS;
	}

    /** @pre ---
     @post Retorna l'identificador de la casella */
    @Override
    public String id(){ return "So"; }

    /** @brief Retorna la casella de Sort en format String
    @pre ---
    @post Retrona un string amb les dades de la casella de Sort */
    @Override
    public String toString() {
        return  "Casella de Sort:\n"+
                "    Els jugadors que cauen en aquesta casella han d'agafar una tarjeta de la sort de la pila";
    }
}
