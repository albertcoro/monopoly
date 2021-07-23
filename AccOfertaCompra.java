/**
 * @class AccOfertaCompra
 * @brief Implementació del algoritme d'una acció per fer que un jugador ofereixi un preu de compra per una propietat d'un altre jugador
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;
import java.util.List;

public class AccOfertaCompra implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
     @post Crea una acció per oferir un preu de compra per una propietat. */
    public AccOfertaCompra(Impresora imp){ a_impresora = imp; }

    /** @brief El jugador escull una propietat i fa una oferta de compra al jugador propietari.
     @pre j1!=null && jugs.size()>1
     @post Un jugador obté la propietat ofertada si el propietari accepta, altrament no rep res */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String nomPropietat;
        double preuInici;
        Terreny propietat;
        Jugador j2;
        List<String> valids = new ArrayList<String>();

        // Construim el rang de valors valids
        for (Jugador jug : jugs) {
            if(!jug.equals(j1))
                jug.prop().forEach(ter -> valids.add(ter.obtNom()));
        }
        if(valids.size()<1) {
            a_impresora.imprimir("No tens propietats per vendre.");
            return;
        }

        a_impresora.imprimir("Propietats disponibles: \n"+ valids);

        nomPropietat = j1.entrarString(true,valids);
        propietat = obtenirPropietat(j1,jugs,nomPropietat);
        assert propietat != null;
        j2 = (Jugador) propietat.obtPropietari();
        preuInici = j1.entrarDouble(1,j1.diners()-1,true,true);
        ferNegociacio(j1,j2,propietat,preuInici);
    }

    /** @pre ---
     @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Oferta Compra: Escull una propietat (nom) d'un altre jugador. Ofereix un preu de compra.";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "COMPRAR";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Oferir compra d'una propietat.";
    }

    /** @pre j1!=null && jugadors.size()>1
     @post Obté la propietat escullida pel jugador. */
    private Terreny obtenirPropietat(Jugador j1, List<Jugador> jugadors, String nomPropietat){
        Terreny propietatAux = new Terreny(nomPropietat);

        for (Jugador jugador : jugadors) {
            if (jugador.equals(j1))
                continue;
            for (Terreny terreny : jugador.prop()) {
                if(terreny.equals(propietatAux))
                    return terreny;
            }
        }
        return null;
    }


    /** @pre j1!=null && j2!=null && propietat!=null && preuInicial>0
     @post El jugador obté la propietat si els dos jugadors arriben a un acord, altrament no rep res. */
    private void ferNegociacio(Jugador j1, Jugador j2, Terreny propietat, double preuInicial){
        boolean tornJ1 = false;
        String resposta;
        double preu = preuInicial;
        List<String> acceptables = new ArrayList<String>(); // Respostes generades per personatges Bots
        acceptables.add("ok"); acceptables.add("no");
        while (true) {
            if (tornJ1)
                resposta = j2.entrarString(false, acceptables);
            else
                resposta = j1.entrarString(false, acceptables);
            tornJ1 = !tornJ1;
            if(resposta.equals("ok"))
                break;
            else if(resposta.equals("no")) {
                a_impresora.imprimir("No s'accepta la oferta i la propietat segueix pertenyent a "+ j1.nom());
                return;
            }
            else
                preu = Double.parseDouble(resposta);
        }

        if (j1.diners()>preu) {
            j1.retirarDiners(preu);
            j1.afegirTerreny(propietat);
            j2.ingressarDiners(preu);
            j2.treureTerreny(j2.teTerreny(propietat));
        }

    }
}
