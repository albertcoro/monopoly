/** @file Tauler.java
 @brief Classe Tauler
 */

import java.util.List;
import java.util.Map;

/**
 * @class Tauler
 * @author Albert Corominas Mariscot (u1960480)
 * @brief Contenidor compost de caselles de diferents tipus, els tipus de les caselles, la seva disposició i freqüència
 * es veurà determinat per les característiques que entrem en el constructor.
 */

public class Tauler {

    Map<Integer,Casella> a_caselles; ///< Caselles del tauler
    List<Integer> a_presons; ///< Presons del tauler
    Map<Jugador,Integer> a_jugadors; ///< Mapa de Jugadors amb la casella en la que estan
    List<TarjetaSort> a_tarjeta; ///< Lista de tarjetes de la sort

    /**
     * @pre caselles.size()>0 && preso.size()>0 && jug.size()>0 && tarj.size()>0
     * @post Crea un tauler amb el numero de caselles totals en el tauler, numero de caselles de aposta, numero de
     * caselles de Anar a preso i numero de caselles de preso.
     */
    Tauler(Map<Integer,Casella> caselles, List<Integer> preso, Map<Jugador, Integer> jug, List<TarjetaSort> tarj){
        a_caselles = caselles;
        a_presons = preso;
        a_jugadors = jug;
        a_tarjeta = tarj;
    }

    /**
     * @pre n>0 && n<=CasellaMax
     * @post Retorna la casella que hi ha a la posicio n.
     */
    Casella casella(int n){
        return a_caselles.get(n);
    }

    /**
     * @pre True
     * @post Retorna la llista de presons.
     */
    List<Integer> presons(){
        return a_presons;
    }

    /**
     * @pre True
     * @post Retorna una tarjeta de la sort aleatoria i la treu del pilo de tarjetes.
     */
    TarjetaSort treureTarjeta(){
        int rand = (int) (Math.random()*(a_tarjeta.size()));
        return a_tarjeta.remove(rand);
    }

    /**
     * @pre True
     * @post Es torna la tarjeta a la pila i es fica al final de tot
     */
    void tornarTarjeta(TarjetaSort t){
        a_tarjeta.add(t);
    }

    /**
     * @pre True
     * @post Retorna true si jugador j te tota la agrupació
     */
    boolean comprovarAgrup(String agrup, Jugador j){
        if(agrup.equals("no")){
            return false;
        } else {
            for (int i = 0; i < a_caselles.size(); i++) {
                Casella c = a_caselles.get(i);
                if (c instanceof Terreny) {
                    Terreny t = (Terreny) c;
                    if (t.obtPropietari() != j && agrup.equals(t.obtAgrupacio())) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * @pre i!=0
     * @post Mou el jugador j, i caselles (si es positiu endevant i si es negatiu enrere), retorna la posicio actual
     */
    int moure(Jugador j, int i){
        int cas = a_jugadors.get(j);
        int diferencia;
        Integer mov;
        if(i>0){ //Moviment endevant
            if(cas+i>a_caselles.size()){ //Passa del limit de caselles
                diferencia = (cas+i+1)-a_caselles.size();
                mov = diferencia;
            } else { //No passa del limit de caselles
                mov = cas+i;
            }
        } else { //Moviment enrere
            if(cas+i<1){ //Passa del limit de caselles
                diferencia = a_caselles.size()-(cas+i);
                mov = diferencia;
            } else { //No Passa del limit de caselles
                mov = cas+i;
            }
        }
        a_jugadors.put(j,mov);
        return mov;
    }

    /**
     * @pre i>=0
     * @post Mou el jugador j a la casella i
     */
    void moureA(Jugador j, int i){
        a_jugadors.put(j,i);
    }
}
