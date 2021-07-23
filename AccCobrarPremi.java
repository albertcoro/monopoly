/**
 * @class AccCobrarPremi
 * @brief Implementació del algoritme d'una acció per donar diners o un terreny com a premi al jugador
 * @author Marc Torres Vilagut (u1959683)
 */
import java.util.ArrayList;
import java.util.List;

public class AccCobrarPremi implements Accio{
    boolean a_actiuPremiTerreny;    ///< Variable que indica si el premi de terreny esta actiu
    boolean a_actiuPremiDiners;     ///< Variable que indica si el premi monetari esta actiu
    double a_quantitat;             ///< Quantitats de diners del premi
    private Impresora a_impresora;  ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
     @post Crea una acció per rebre un premi */
    public AccCobrarPremi(Impresora imp, double quantitat, boolean premiTerreny, boolean premiDiners){
        a_actiuPremiDiners = premiDiners;
        a_actiuPremiTerreny = premiTerreny;
        a_impresora = imp;
        a_quantitat = quantitat;
    }

    /** @brief El jugador escull un premi
     @pre j1!=null && b!=null &&
     @post El jugador rep el premi escollit */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String tipusPremi;
        List<String> valids = new ArrayList<String>();

        if(a_actiuPremiDiners)
            valids.add("DINERS");
        if(a_actiuPremiTerreny)
            valids.add("TERRENY");



        while (true) {
            a_impresora.imprimir("Escull un premi: " + valids);
            tipusPremi = j1.entrarString(true,valids);
            if (tipusPremi.equals("DINERS")) {
                premiDiners(j1);
            }
            else { // TERRENY
                if(!premiTerreny(j1, b)) {
                    a_impresora.imprimir("La banca no té més propietats per escollir");
                    continue;
                }
            }
            break;
        }

    }

    /** @pre ---
     @post Retorna una pregunta per que el jugador esculli quin premi vol */
    @Override
    public String pregunta() {
        String s = "Premi: ";
        if(a_actiuPremiDiners)
            s += "\n\tDiners: El jugador rebrà un premi monetari (" + a_quantitat + ")";
        if(a_actiuPremiDiners)
            s += "\n\tTerreny: El jugador rebra una propietat de la banca com a premi.";
        return s;
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "COBRAR_PREMI";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Cobrar Premi";
    }

    /** @pre j1!=null
    @post El jugador obté la quantitat de diners estipulada com a premi monetari. */
    private void premiDiners(Jugador j1){
        a_impresora.imprimir("El jugador " + j1.nom() + " obte " + a_quantitat + " de capital com a premi.");
        j1.ingressarDiners(a_quantitat);
    }


    /** @pre j1!=null && b!=null
    @post El jugador obté una propietat de la banca com a premi de terreny. */
    private boolean premiTerreny(Jugador j1, Banca b){
        String nomPropietat;
        Terreny propietatAux, premi = null;
        List<String> valids = new ArrayList<String>();
        // Creem les respostes vàlides (noms de les propietats de la banca)
        for (Terreny terreny : b.prop()) {
            valids.add(terreny.obtNom());
        }

        if(valids.isEmpty())
            return false;

        while(premi == null) {
            // Entrar el nom de la propietat que vol donar
            a_impresora.imprimir("Propietats disponibles: " + valids);
            a_impresora.imprimir("\nEscull una propietat (nom).");
            nomPropietat = j1.entrarString(true, valids);

            // Obtenir la propietat
            propietatAux = new Terreny(nomPropietat);
            premi = b.treureTerreny(b.teTerreny(propietatAux));
        }

        j1.afegirTerreny(premi);
        premi.canviarPropietari(j1);
        a_impresora.imprimir(j1.nom() + " obté el terreny " + premi.obtNom() + " com a premi.");
        return true;
    }
}
