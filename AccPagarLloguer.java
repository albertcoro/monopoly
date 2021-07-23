/**
 * @class AccPagar
 * @brief Implementació del algoritme d'una acció que obliga al jugador pagar una lloguer al jugador de la casella de terreny
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.List;

public class AccPagarLloguer implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
     @post Crea una acció per pagar el lloguer d'un terreny. */
    public AccPagarLloguer(Impresora imp){ a_impresora = imp; }

    /** @brief El jugador paga el lloguer del terreny
     @pre j1!=null && c!=null && jugs.size()>1 && t!=null
     @post El jugador paga el lloguer del terreny on ha caigut al jugador propietari */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        boolean teAgrupacio = false;
        Terreny t1;
        if(c.id().equals("T"))
            t1 = (Terreny) c;
        else {
            a_impresora.imprimir("ERROR: Casella accedida no és un terreny ni un subtipus de terreny.");
            return;
        }

        //Si el jugador ha caigut en una casella propia, no ha de pagar lloguer
        if (j1.teTerreny(t1)!=-1)
            return;

        for (Jugador jug : jugs) {
            if (jug.equals(t1.obtPropietari())) {
                teAgrupacio = t.comprovarAgrup(t1.obtAgrupacio(), jug);
                double lloguer = t1.obtPreuLloguer(teAgrupacio);
                double pagament = Math.min(lloguer,j1.diners()); // Per si el jugador té menys diners dels especificats
                a_impresora.imprimir(j1.nom() + " paga " + pagament + " de lloguer a " + jug.nom());
                j1.retirarDiners(pagament);
                jug.ingressarDiners(pagament);
                break;
            }
        }

        if (j1.diners()<=0)
            desqualificarJugador(j1,jugs,b);
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "LLOGUER";
    }

    /** @pre ---
     @post Retorna una avis de que ha de pagar un lloguer */
    @Override
    public String pregunta() {
        return "Pagar Lloguer: El jugador paga un lloguer al jugador propietari del terreny on ha caigut.";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Pagar Lloguer";
    }

    /**
     * @pre ---
     * @post Desqualifica a al jugador i donem les seves propietats a la banca. Mostra per pantalla que s'ha desqualificat.
     */
    public void desqualificarJugador(Jugador j,List<Jugador> jugs, Banca b){
        a_impresora.imprimir("S'ha desqualificat el jugador (" + j.nom() + ")");
        for (Terreny terreny : j.prop()) {
            b.afegirTerreny(terreny);
            terreny.canviarPropietari(b);
        }
        jugs.remove(j);
    }
}
