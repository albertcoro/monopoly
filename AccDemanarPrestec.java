/**
 * @class AccDemanarPrestec
 * @brief Implementació del algoritme d'una acció que permet a un jugador demanar un prestec a un altre jugador,
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccDemanarPrestec implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post Crea una acció per demanar prestecs */
    public AccDemanarPrestec(Impresora imp){
        a_impresora = imp;
    }

    /* @brief El jugador demana un prestec
    @pre j1!=null && jugs.size()>1
    @post El jugador obté un prestec si l'accepten, altrament res */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String nomJugador2;
        double dinersPrestec;
        List<String> valids = new ArrayList<String>();

        // Generem les respostes valides (noms dels jugados a qui pots demanar el prestec)
        for (Jugador jug : jugs) {
            if(jug.equals(j1))
                continue;
            valids.add(jug.nom());
        }

        a_impresora.imprimir("A qui vols demanar un prestec?\n"+valids);

        // Escollir jugador
        nomJugador2 = j1.entrarString(true,valids);
        Iterator<Jugador> itJ = jugs.iterator();
        Jugador j2 = null;
        while (itJ.hasNext()){
            j2 = itJ.next();
            if(j2.nom().equals(nomJugador2))
                break;
        }

        // Demanar quanitat de diners pel prestec
        dinersPrestec = j1.entrarDouble(0,0,true,false);

        // Preguntem al jugador a qui se l'hi ha demanat el préstec si vol donar-lo
        ferNegociacio(j1,j2,dinersPrestec);
    }

    /** @pre ---
     @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Demanar Préstec: Escull el jugador a qui vulguis demanar un prestec (nom). Entra la quantitat de diners que necessites.";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "DEMANAR_PRESTEC";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Demanar Prestec";
    }

    /** @pre j1!=null && j2!=null && dinersPrestec>0
    @post El jugador obte un prestec si tots dos accepten les mateixes condicions */
    private void ferNegociacio(Jugador j1, Jugador j2, double dinersPrestec){
        String resposta;
        int percentatge = 0, torns = 0;
        boolean respostaDeJ2 = false;
        List<String> valids = new ArrayList<>();
        valids.add("no"); valids.add("oferta");

        while (true) {
            if(valids.contains("ok"))
                a_impresora.imprimir("Acceptar oferta, denegarla o proposar oferta (ok/no/oferta):");
            else
                a_impresora.imprimir("Fes una oferta o denega el prestec (no/oferta):");

            if(respostaDeJ2)
                resposta = j1.entrarString(true,valids);
            else
                resposta = j2.entrarString(true,valids);

            respostaDeJ2 = !respostaDeJ2; // Canvi de jugador

            if(resposta.equals("oferta")){
                a_impresora.imprimir("Percentatge (numero): "); // Demanar Percentatge
                if(respostaDeJ2)
                    percentatge = j1.entrarInt(0, 100, true, true);
                else
                    percentatge = j2.entrarInt(0, 100, true, true);

                a_impresora.imprimir("Torns  (numero): "); // Demanar numero de torns
                if(respostaDeJ2)
                    torns = j1.entrarInt(0, 0, true, false);
                else
                    torns = j2.entrarInt(0, 0, true, false);

                respostaDeJ2 = !respostaDeJ2; // Canvi de jugador
            } else if(resposta.equals("no")){
                a_impresora.imprimir("Prestec denegat.");
                return;
            } else // "ok"
                break;

            if(!valids.contains("ok"))
                valids.add("ok");
        }

        generarPrestec(j1,j2,dinersPrestec,percentatge,torns);
    }

    /** @pre ---
    @post El jugador obté un prestec de l'altre jugador */
    private void generarPrestec(Jugador j1, Jugador j2, double dinersPrestec, int percentatge, int torns){
        // Intercanvi diners
        j1.ingressarDiners(dinersPrestec);
        j2.retirarDiners(dinersPrestec);

        // Creem prestec i l'afegim als prestecs del jugador j1
        Prestec p = new Prestec(dinersPrestec, percentatge, torns, j2);
        j1.afeguirPrestec(p);
    }
}
