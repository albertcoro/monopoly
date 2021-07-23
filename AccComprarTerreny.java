/**
 * @class AccComprarTerreny
 * @brief Implementació del algoritme d'una acció que proposa a un jugador comprar una propietat,
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AccComprarTerreny implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post  Crea una acció per comprar una propietat. */
    public AccComprarTerreny(Impresora imp){
        a_impresora = imp;
    }


    /** @brief El jugador escull comprar o no la propietat i, si es compra, comença la subhasta.
    @pre j1!=null && c!=null && jugs.size()>1 && b!=null && t!=null
    @post Un jugador obté una propietat */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String respotsa;
        List<String> valids= new ArrayList<String>();
        Terreny t1;
        if(c.id().equals("T"))
            t1 = (Terreny) c;
        else {
            a_impresora.imprimir("ERROR: Casella accedida no és un terreny ni un subtipus de terreny.");
            return;
        }

        // Creem les respostes vàlides
        valids.add("si"); valids.add("no");

        // El jugador respon la pregunta.
        respotsa = j1.entrarString(true,valids);

        if (respotsa.equals("si")) { // Vendre propietat a jugador
            double preuVenda = t1.obtPreuVenda();
            if (j1.diners() > preuVenda) {
                t1.canviarPropietari(j1);
                j1.retirarDiners(preuVenda);
            }
        } else if (respotsa.equals("no")) { // resposta == "no"
            // Creem una llista dels indexs dels jugadors a la llista de llugadors
            LinkedList<Jugador> enSubhasta = new LinkedList<Jugador>();
            int i = jugs.indexOf(j1)+1;

            // Cerquem el jugador que no ha volgut comprar el terreny
            while (true){
                if(i>=jugs.size())
                    i=0;
                enSubhasta.add(jugs.get(i));
                if (jugs.get(i).equals(j1))
                    break;
                i++;
            }

            // Iniciem la subhasta
            subhasta(t1, enSubhasta);
        }
    }

    /** @pre ---
    @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Comprar Terreny: Vols comprar el terreny (si/no)? Si no vols comprar començara la subhasta pel terreny.";
    }

    /** @pre ---
    @post retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "COMPRA";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Comprar Terreny";
    }

    /** @pre jugs.size()==enSubhasta.size() && token>=0 && t1!=null
    @post El jugador que ha fet l'oferta més alta rep el terreny. Si no hi han ofertas el terreny continua pertanyent a la banca. */
    private void subhasta(Terreny t1, LinkedList<Jugador> enSubhasta) {
        double dinersMinims = 0;   // Oferta minima de diners per la subhasta
        List<String> valids= new ArrayList<String>();
        String continuar;
        valids.add("si"); valids.add("no");

        a_impresora.imprimir("S'inicia la subhasta per el terreny " + t1.obtNom() + ". Preu original: " + t1.obtPreuVenda());
        while (true){
            Jugador jAux = enSubhasta.poll();

            if(jAux.diners()<dinersMinims){
                a_impresora.imprimir("El jugador " + jAux.nom() + " no te suficients diners i surt de la subhasta.");
                if(enSubhasta.size()==1 && dinersMinims>0) // S'ha fet una oferta i només queda 1 jugador
                    break;
                else if(enSubhasta.size()==0) {
                    a_impresora.imprimir("La propietat " + t1.obtNom() + " no es comprada per ningu.");
                    return;
                }
                continue;
            }

            a_impresora.imprimir("Vols donar una oferta? ");
            continuar = jAux.entrarString(true,valids);

            if(continuar.equals("si")) {
                // Entrem la nova oferta
                dinersMinims = jAux.entrarDouble(dinersMinims, jAux.diners()-1, true, true);
                enSubhasta.add(jAux);
            } else{
                // Treguem l'index del jugador de la llista de participants
                if(enSubhasta.size()==0) { // Ningu vol comprar
                    a_impresora.imprimir("La propietat " + t1.obtNom() + " no es comprada per ningu.");
                    return;
                }
            }
            if(enSubhasta.size()==1 && dinersMinims>0) // S'ha fet una oferta i només queda 1 jugador
                break;
        }
        // Donem el jugador al últim jugador restant
        Jugador jGuanyador = enSubhasta.peek();
        if(jGuanyador.diners()>dinersMinims) {
            a_impresora.imprimir(jGuanyador.nom() + " rep el terreny " + t1.obtNom());
            jGuanyador.retirarDiners(dinersMinims);
            jGuanyador.afegirTerreny(t1);
            t1.canviarPropietari(jGuanyador);
        }
    }
}
