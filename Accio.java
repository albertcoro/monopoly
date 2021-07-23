/**
* @class Accio
* @brief Interficie amb la declaració de la funció base per les accions del joc
* @author Marc Torres Vilagut (u1959683)
*/

import java.util.List;

public interface Accio{
    /* @brief El jugador executa l'acció definida
    @pre ---
    @post El jugador executa l'acció definida
    @param[in] j1 El jugador que crida l'execució de l'acció.
    @param[in] t1 La casella des d'on j1 executa l'acció.
    @param[in] jugs Llista dels jugadors de la partida.
    @param[in] b Entitat de la banca.
    @param[in] t Tauler de la partida. */
    void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t);

    /** @pre ---
    @post Retorna la pregunta que se li fara al jugador a l'hora d'executar l'acció */
    String pregunta();

    /** @pre ---
    @post retorna la parula clau amb la que s'identifica una acció */
    String paraulaClau();
}


/*
Accions:
    Caselles Generals:
        Apostar(),                      [X] <t>
        Dona(),                         [X] <t>
        Rebre(),                        [X] <t>
        Multa(import),				    [X] <t>
        Cobrar(premi),				    [X]
        Pagar(import),			        [X] <t>
        Dispensar() i Restablir(),	    [-] *
        Preso(),			       	    [X] * <t>
        Sortir_Preso(),		    	    [X] * <t>
        Anar(numCasella),	            [X]

	Terrenys:
        Comprar i Subhasta              [X] :-> Les dues en AccComprarTerreny <t>
        PagarLloguer()                  [X]

    Jugadors:
        CrearPrestec()                  [X] <t>
        OfertaCompra(),		            [X] <t>
        OfertaVenta(),		            [X] <t>
        OfertaIntercanvi(), 		    [ ] *
        ConstruirCases()                [X] <t>
        ConstruirHotel()                [X] <t>
        DestruirCases()                 [X] <t>
        DestruirHotel()                 [X] <t>


    * Accions Opcionals d'implementar
    [X] Accions implamentades
    [-] Accions que no s'implementaran
    <t> Accions testejades
*/