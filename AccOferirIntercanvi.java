/**
 * @class AccOfertaCompra
 * @brief Implementació del algoritme d'una acció per fer que un jugador ofereixi un intercanvi per una propietat d'un altre jugador
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;
import java.util.List;

public class AccOferirIntercanvi implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
     @post Crea una acció per oferir un intercanvi a un jugador. */
    public AccOferirIntercanvi(Impresora imp){ a_impresora = imp; }

    /** @brief El jugador fa una oferta d'intercanvi de propietats a un altre jugador
     @pre j1!=null, jugs.size>1
     @post Els dos jugadors s'intercanvien propietats si el segon accepta l'oferta, altrament res. */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t) {
        String resposta;
        Terreny t1,t2;
        Jugador j2;
        List<String> valids = new ArrayList<String>();

        t1 = obtPropietatOfertada(j1);
        t2 = obtPropietatDesitjada(j1,jugs);

        j2 = (Jugador) t2.obtPropietari();

        a_impresora.imprimir("Vols canviar " + t2.obtNom() + " per " + t1.obtNom() + "?");
        a_impresora.imprimir(t1.toString());
        a_impresora.imprimir(t2.toString());

        valids.add("si"); valids.add("no");
        resposta = j2.entrarString(true, valids);

        if(resposta.equals("no")){
            a_impresora.imprimir(j2.nom() + " no accepta l'intercanvi");
        }
    }

    /** @pre ---
     @post Retorna un avis amb una descripcio de l'accio */
    @Override
    public String pregunta() {
        return "Intercanvi: Ofereix un intercanvi de propietats a un altre jugador.";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció. */
    @Override
    public String paraulaClau() {
        return "INTERCANVI";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Intercanvi";
    }

    /** @pre j1!=null
    @post Retorna una propietat propia del jugador escollida per ell */
    private Terreny obtPropietatOfertada(Jugador j1){
        String nomPropietat;
        Terreny propietatAux;
        List<String> valids = new ArrayList<String>();
        for (Terreny terreny : j1.prop()) {
            valids.add(terreny.obtNom());
        }
        if(valids.isEmpty()) {
            a_impresora.imprimir("No tens propietats per intercanviar.");
            return null;
        }
        a_impresora.imprimir("Escull una propietat per oferir: \n" + valids);
        nomPropietat = j1.entrarString(true,valids);
        propietatAux = new Terreny(nomPropietat);

        for (Terreny terreny : j1.prop()) {
            if(terreny.equals(propietatAux))
                return terreny;
        }
        return null;
    }


    /** @pre j1!=null && jugadors.size()>1
     @post Retorna la propietat d'un altre jugador que el jugador vol. */
    private Terreny obtPropietatDesitjada(Jugador j1, List<Jugador> jugadors){
        String nomPropietat;
        Terreny propietatAux;
        List<String> valids = new ArrayList<String>();
        for (Jugador jugador : jugadors) {
            if (!jugador.equals(j1))
                continue;
            for (Terreny terreny : jugador.prop())
                valids.add(terreny.obtNom());
        }
        if(valids.isEmpty()) {
            a_impresora.imprimir("No tens propietats per intercanviar.");
            return null;
        }
        a_impresora.imprimir("Escull una propietat per oferir: \n" + valids);
        nomPropietat = j1.entrarString(true,valids);
        propietatAux = new Terreny(nomPropietat);

        for (Jugador jugador : jugadors) {
            if (!jugador.equals(j1)){
                for (Terreny terreny : jugador.prop()) {
                    if(terreny.equals(propietatAux))
                        return terreny;
                }
            }
        }


        return null;
    }
}
