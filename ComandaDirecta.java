/**
 * @class ComandaDirecta
 * @brief ComandaDirecta es una casella del tauler que no es pot comprar. Si un jugador cau en aquesta casella se l’hi obliga a executar la comanda que conté aquesta casella.
 * @author Marc Torres Vilagut (u1959683)
 */
import java.util.ArrayList;

public class ComandaDirecta implements Casella{
    private Accio a_comanda;

    /** @pre: --
    @post: Crea una casella de comanda directa que conté una comanda */
    public ComandaDirecta(Accio comanda){
        a_comanda = comanda;
    }

    /** @pre ---
    @post Retorna l'opció de la comanda que guarda aquesta casella */
    @Override
	public Accio opcions() {
        return a_comanda;
	}

    /** @pre ---
     @post Retorna l'identificador de la casella */
    @Override
    public String id(){ return "CD"; }

    /** @brief Retrona la casella de Comanda Directa en format String
     * @pre ---
    @post Retrona un string amb les dades de la casella de Comanda Directa */
    @Override
    public String toString() {
        return  "Casella de Comanda Directa:\n"+
                "   Els jugadors que cauen en aquesta casella han d'executar l'acció: " + a_comanda.toString();
    }

}
