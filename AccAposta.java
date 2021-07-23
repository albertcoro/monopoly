/**
 * @class AccAposta
 * @brief Implementació del algoritme d'una acció que permet al jugador apostar
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.List;
import java.util.ArrayList;

public class AccAposta implements Accio{
    private static final double N_PROBABILITATS = 36.0;     ///< Variacio amb repetició dels valors de llançar 2 daus (6^2)
    private static final int SUMA_DAU_MAXIMA = 12;          ///< Valor màxim al que es pot d'aposta
    private static final int SUMA_DAU_MINIMA = 3;           ///< Valor minim al que es pot d'aposta
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post Crea una acció per apostar */
    public AccAposta(Impresora imp){
        a_impresora = imp;
    }

    /** @brief El jugador fa una aposta
    @pre j1!=null
    @post El jugador rep una quantitat de diners relativa a la aposta que ha fet si la guanya */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        // Creem les respostes vàlides (si/no)
        List<String> valids= new ArrayList<String>();
        valids.add("si"); valids.add("no");

        String ferAccio = j1.entrarString(true,valids).toLowerCase();

        if(ferAccio.equals("si")) {
            a_impresora.imprimir("Entra quantitat de diners que vols apostar i tria la tirada per la que apostes: ");

            // Si el jugador vol fer l'aposta entra els valors demanats.
            double aposta = j1.entrarDouble(1,j1.diners(),true,true);
            int numApostat = j1.entrarInt(SUMA_DAU_MINIMA,SUMA_DAU_MAXIMA,true,true);
            int[] tirada = j1.tirarDaus();
            int sumaDaus = tirada[0] + tirada[1];

            j1.retirarDiners(aposta);
            double res = apostar(aposta, numApostat, sumaDaus);


            a_impresora.imprimir("\nTirada: " + sumaDaus+
                                "\nResultat Aposta: " + res);
            j1.ingressarDiners(res);
        } else{
            a_impresora.imprimir("El jugador no aposta.");
        }

    }

    /** @pre ---
    @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Apostar: Vols Apostar (si/no)?";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "APOSTAR";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Apostar";
    }

    /* @brief: Aposta una quantitat de diners a un numero i retorna el resultat.
    @pre: numAposta >=3 && numAposta<=12, i tirada>1 && tirada<=13
    @post: Retorna el resultat de l'aposta */
    private double apostar(double aposta, int numApostat, int tirada){
        if(tirada >= numApostat){
            double res = (aposta*(1+10*calculaProbabilitat(numApostat)));
            return Math.floor(res);
        }
        else
            return 0;
    }

    /* @brief: Calcula la probabilitat de no obtenir un numero al tirar dos daus.
    @pre: numApsota >=3 && numAposta<=12
    @post: Retorna el la probabilitat de que no surti el numero apostat al tirar dos daus. */
    private double calculaProbabilitat(int numAposta){
        int comb = 0;   // Nombre de combinacions de tirades amb les que obtenim un nombre igual o superior a numAposta
        for(int i=1;i<=6;i++){
            for(int j=1;j<=6;j++){
                if((i+j)>=numAposta)
                    comb++;
            }
        }
        return (N_PROBABILITATS-comb)/N_PROBABILITATS;
    }


}
