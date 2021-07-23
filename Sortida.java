/**
 * @class Sortida
 * @brief Sortida es una casella del tauler única (es a dir només ni ha una). Aquesta casella no es pot comprar i quan un jugador passi per sobre obtindran l'opció de rebre un premi monetari (si esta activat) o obtenir un terreny (si esta activat).
 * @author Marc Torres Vilagut (u1959683)
 */

public class Sortida implements Casella{
    boolean a_actiuPremiTerreny;    ///< Variable que indica si el premi de terreny esta actiu
    boolean a_actiuPremiDiners;     ///< Variable que indica si el premi monetari esta actiu
    Accio a_cobrarPremi;          ///< Acció de obtenir un premi monetari o un terreny

    /** @brief: Crea una casella de sortida a la que se l'indica si estan activats els premis monetaris i/o de terrenys
    @pre: quantitat>0
    @post: Crea una casella de sortida a la que se l'indica si estan activats els premis monetaris i/o de terrenys */
    public Sortida(boolean premiTerreny, boolean premiDiners, double quantitat, Impresora imp){
        a_actiuPremiDiners = premiDiners;
        a_actiuPremiTerreny = premiTerreny;
        a_cobrarPremi = new AccCobrarPremi(imp,quantitat,premiTerreny,premiDiners);
    }

    /** @pre ---
    @post Retorna les opcions de premis que estan disponibles */
    @Override
    public Accio opcions() {
        return a_cobrarPremi;
    }

    /** @pre ---
     @post Retorna l'identificador de la casella */
    @Override
    public String id(){ return "Sd"; }

    /** @brief Retorna la casella de Sortida en format String
    @pre ---
    @post Retrona un string amb les dades de la casella de Sortida */
    @Override
    public String toString() {
        String s = "Casella de Sortida:\n"+
                "\tEl jugador pot escollir entre les opcions de premis disponibles: ";
        if(a_actiuPremiDiners)
            s += "\n\t\t· Premi en diners ";
        if(a_actiuPremiTerreny)
            s += "\n\t\t· Premi en Terreny ";

        return s;
    }
}
