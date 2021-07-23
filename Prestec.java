/** @file Prestec.java
 @brief Classe Prestec
 */

/**
 * @class Prestec
 * @author Albert Corominas Mariscot (u1960480)
 * @brief Modul funcional que serveix per encapsular les funcions de préstec monetaris entre jugadors, contindra: el
 * jugador que necessita els diners, el jugador que vol donar els diners, el percentatge de benefici, la quantitat del
 * préstec i el torn en que s'haurà de tornar el préstec amb el benefici.
 */

public class Prestec implements Comparable<Prestec> {
    double a_diners; ///< Diners pel quals es fa el prestec
    double a_percentatge; ///< Percentatge de retorns de diners
    int a_torns; ///< Torns efectius per tornar el prestec amb percentatge
    Jugador a_donant; ///< Donant dels diners que se li ha de tornar el prestec

    /**
     * @pre diners>0 && percentatge>=0 && torns>0
     * @post Es crea un objecte prestec, amb el jugador que ha donat els diners, amb els diners que li ha donat, amb el
     * percentatge demanat i el torns que passaran fins tornar els diners.
     */
    Prestec(double diners, double percentatge, int torns, Jugador j){
        a_diners = diners;
        a_percentatge = percentatge;
        a_torns = torns;
        a_donant = j;
    }

    /**
     * @pre True
     * @post Retorna els diners que s’hauran de tornar amb el percentatge aplicat.
     */
    public double dinersTornar(){
        return a_diners*(a_percentatge/100);
    }

    /**
     * @pre True
     * @post Resta un torn el prestec per mantenirlo actualitzat.
     */
    public void pasarTorn(){
        a_torns--;
    }

    /**
     * @pre True
     * @post Retorna true si s'ha de pagar el prestec aquest torn, false en cas contrari.
     */
    public boolean tocaPagar(){
        return a_torns==0;
    }

    /**
     * @pre True
     * @post Retorna el jugador que se li ha de tornar els diners.
     */
    public Jugador donant(){
        return a_donant;
    }

    /**
     * @pre True
     * @post Retorna true si els dos prestecs son identics, false en cas contrari.
     */
    public boolean equals(Prestec p){
        return (a_diners==p.a_diners && a_percentatge==p.a_percentatge && a_torns==p.a_torns);
    }

    /**
     * @pre True
     * @post Retorna 0 si els dos prestecs son iguals, -1 si el actual caduca mes aviat que p o 1 si el actual caduca
     * mes tard que p.
     */
    @Override
    public int compareTo(Prestec o) {
        if(a_torns>o.a_torns){
            return -1;
        } else if(a_torns<o.a_torns){
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * @pre True
     * @post Retorna una descrició del prestec.
     */
    public String toString(int i){
        String s = "Toca pagar el prestec de " + dinersTornar() + "€ al Jugador " + a_donant;
        if(a_torns == 0){
            s += " en aquest torn";
        } else {
            s += " d'aqui " + a_torns + " torns";
        }
        return s;
    }
}
