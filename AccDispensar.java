import java.util.List;

/**
 * @class AccOfertaCompra
 * @brief Implementació del algoritme d'una acció per demanar una hipoteca i
 * @author Marc Torres Vilagut (u1959683)
 */

public class AccDispensar implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
     @post Crea una acció per dispensar o retirar dispensa de pagament a un jugador. */
    public AccDispensar(Impresora imp){a_impresora=imp;}

    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t) {
        a_impresora.imprimir("Funcions DISPENSAR i RESTABLIR deshabilitades.");
    }

    @Override
    public String pregunta() {
        return "Dispensa o reestableix el pagament del lloguer a altre jugador";
    }

    @Override
    public String paraulaClau() {
        return "DISPENSAR_RESTABLIR";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Dispensar pagament";
    }
}
