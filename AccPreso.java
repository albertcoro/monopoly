/**
* @class AccPreso
* @brief Implementació del algoritme d'una acció per empresonar a un jugador
* @author marctorresvilagut
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class AccPreso implements Accio{
    private Impresora a_impresora;    ///< Impresora amb la que farem els outputs de les accions

    /** @pre ---
    @post Crea una acció per enviar un jugador a la presó */
    public AccPreso(Impresora imp){ a_impresora = imp; }
    
    /** @brief El jugador escull a quina preso vol ser empresonat
    @pre j1!=null && t!=null
    @post El jugador es empresonat i es mou a la posicio de la casella de preso que ha escollit. */
    @Override
    public void executa(Jugador j1, Casella c, List<Jugador> jugs, Banca b, Tauler t){
        String nomPreso;                                // Nom de la Preso a la que el Jugador vol anar
        Preso presoAux;                                 // Presons auxiliar
        int posPreso = 0;                               // Preso a la que el Jugador anira
        List<Integer> presons = t.presons();            // Presons del Tauler
        boolean presoTrobada = false;                   // Boolean que determina si s'ha trobat la preso a la que el jugador vol anar.
        List<String> valids = new ArrayList<String>();  // Noms de presons valides per entrar

        while(!presoTrobada) {
            // Creem les respostes vàlides (noms de les presons del tauler)
            t.presons().forEach(p -> valids.add(((Preso) t.casella(p)).nom()));

            a_impresora.imprimir("Escull la presó a la que vols anar: " + valids);

            // Entrar el nom de la propietat que vol donar
            nomPreso = j1.entrarString(true, valids);

            // Obtenir la preso
            presoAux = new Preso(nomPreso);
            Iterator<Integer> it = presons.iterator();

            while (it.hasNext() && !presoTrobada){
                posPreso = it.next();
                if (t.casella(posPreso).equals(presoAux)){
                    presoTrobada = true;
                    t.moureA(j1,posPreso);  // Moure al jugador j1 a la Preso
                    j1.anarPreso();         // Empresonar al jugador j1
                }
            }
        }
    }

    /** @pre ---
     @post Retorna una pregunta relacionada amb les dades d'aquesta acció */
    @Override
    public String pregunta() {
        return "Preso: El jugador anira a una de les presons del tauler.";
    }

    /** @pre ---
     @post Retorna la paraula clau de l'acció */
    @Override
    public String paraulaClau() {
        return "ANAR_PRESO";
    }

    /** @pre ---
     @post Retorna la acció com un string */
    @Override
    public String toString() {
        return "Anar Preso";
    }
}
