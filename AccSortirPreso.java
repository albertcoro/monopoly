import java.util.List;

/**
* @class AccSortirPreso
* @brief Implementació del algoritme d'una acció que treu a un jugador de la presó.
* @author Marc Torres Vilagut (u1959683)
*/

public class AccSortirPreso implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post Crea una acció per sortir de la presó*/
    public AccSortirPreso(Impresora imp){ a_impresora = imp; }

    /* @brief El jugador surt de la presó
    @pre j1!=null
    @post El jugador surt de la presó */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        j1.sortirPreso();
        a_impresora.imprimir(j1.nom() + " ha sortit de la preso.");
    }

    /** @pre ---
     @post Retorna un avis de que el jugador ha sortit de la preso */
    @Override
    public String pregunta() {
        return "Sortir Preso: El jugador sortirà de la presó.";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "SORTIR_PRESO";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Sortir Preso";
    }
}
