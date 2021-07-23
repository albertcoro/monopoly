/**
 * @class Acc
 * @brief Implementació del algoritme d'una acció per fer que un jugador ofereixi un preu de venta per una propietat de les seves propietats
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AccOfertaVenta implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
     @post Crea una acció per oferir una propia propietat en venta */
    public AccOfertaVenta(Impresora imp){ a_impresora = imp; }

    /** @brief El jugador escull una de les seves propietat i fa una oferta als altres jugadors.
     @pre j1!=null && jugs.size()>1
     @post Un jugador obté la propietat ofertada */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String nomPropietat;
        double preuInici;
        int i=0, token=0;
        Terreny propietat;
        LinkedList<Jugador> enSubhasta = new LinkedList<Jugador>();
        List<String> valids = new ArrayList<String>();

        // Construim el rang de valors valids
        for (Terreny terreny : j1.prop()) {
            valids.add(terreny.obtNom());
        }
        a_impresora.imprimir("Propietats disponibles: \n"+ valids);
        if(valids.isEmpty()) {
            a_impresora.imprimir("El jugador " + j1.nom() + " no té més propietats per donar.");
            return;
        }
        nomPropietat = j1.entrarString(true,valids);
        propietat = obtenirPropietat(j1,nomPropietat);

        // Creem una llista dels indexs dels jugadors a la llista de llugadors

        i = jugs.indexOf(j1)+1;

        // Cerquem el jugador que no ha volgut comprar el terreny
        while (true){
            if(i>=jugs.size())
                i=0;
            if (jugs.get(i).equals(j1))
                break;
            else
                enSubhasta.add(jugs.get(i));
            i++;
        }

        preuInici = j1.entrarDouble(1,j1.diners()-1,true,true);
        subhasta(propietat,enSubhasta,preuInici);

    }

    /** @pre ---
     @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Oferta Venta: Escull una de les teves propietat (nom). Ofereix un preu de venta i comença una subhasta per la propietat.";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "VENDRE";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Oferir venta";
    }

    /** @pre j1!=null
     @post Retorna el terreny que el jugador escull */
    private Terreny obtenirPropietat(Jugador j1, String nomPropietat){
        Terreny propietatAux = new Terreny(nomPropietat);
        for (Terreny terreny : j1.prop()) {
            if (terreny.equals(propietatAux))
                return terreny;
        }

        return null;
    }

    /** @pre jugs.size()>0 && jugs.size()==enSubhasta(size) && t1!=null && token>=0 && preuInici>0
     @post El jugador que ha fet l'oferta més alta rep el terreny. Si no hi han ofertas el terreny continua pertanyent al jugador. */
    private void subhasta(Terreny t1, LinkedList<Jugador> enSubhasta, double preuInici) {
        double dinersMinims = preuInici;    // Oferta minima de diners per la subhasta
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
