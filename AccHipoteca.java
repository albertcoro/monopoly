import java.util.List;

/**
 * @class AccOfertaCompra
 * @brief Implementació del algoritme d'una acció per demanar una hipoteca i
 * @author Marc Torres Vilagut (u1959683)
 */

public class AccHipoteca implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
     @post Crea una acció per hipotecar i deshipotecar una propietat. */
    public AccHipoteca(Impresora imp){a_impresora=imp;}

    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t) {
        a_impresora.imprimir("Funcions HIPOTECAR i DESHIPOTECAR deshabilitades.");
    }

    @Override
    public String pregunta() {
        return "Hipoteca o deshipoteca una propietat.";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció. */
    @Override
    public String paraulaClau() {
        return "HIPOTECAR_DESHIPOTECAR";
    }
}
