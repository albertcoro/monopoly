/** @file Huma.java
 @brief Classe Huma
 */

import java.util.List;
import java.util.Scanner;

/**
 * @class Huma
 * @author Albert Corominas Mariscot (u1960480)
 * @brief Jugador controlat per l’usuari, amb diferents checks que el bot, ja que aquests no son automatitzats son
 * controlats per inputs del jugador.
 */

public class Huma extends Jugador {
    Scanner a_entrada; ///< Sistema per mostrar per pantalla missatges de error

    /**
     * @pre True
     * @post Crea un objecte huma amb id, nom i impresora, també li assigna una entrada per teclat.
     */
    Huma(int id, String nom, Impresora imp){
        super(id,nom,imp);
        a_entrada = new Scanner(System.in);
    }

    /**
     * @pre True
     * @post Demana el jugador que entri per teclat un string, fins que coincideixi amb un de valid (si esta activat).
     */
    @Override
    String entrarString(boolean limitat, List<String> valids) {
        String aux;
        System.out.print(a_nom+"> ");
        aux = a_entrada.next();
        boolean acceptable = false;
        while(!acceptable){
            if(!limitat){ // Rangs desactivats cualsevol int es acceptable
                acceptable = true;
            } else {
                acceptable = valids.contains(aux);
            }
            if(!acceptable) {
                System.out.println("ERROR: String no valid! \n Opcions valides:");
                for(String s: valids){
                    System.out.println(" - "+s);
                }
                System.out.print(a_nom+"> ");
                aux = a_entrada.next();
            }
        }
        a_impresora.imprimirFitxerOnly(a_nom+"> "+aux);
        return aux;
    }

    /**
     * @pre True
     * @post Demana el jugador que entri per teclat un int, fins que entri un dintre dels limits (si estan activats).
     */
    @Override
    int entrarInt(int limMinim, int limMaxim, boolean min, boolean max) {
        int aux;
        System.out.print(a_nom+"> ");
        aux = a_entrada.nextInt();
        boolean acceptable = false;
        while(!acceptable){
            if(!min && !max){ // Rangs desactivats cualsevol int es acceptable
                acceptable = true;
            } else {
                if (min && max) { // Ha de estar entre limMinim i limMaxim
                    acceptable = limMinim<=aux && limMaxim>=aux;
                } else if(!min && max){ // Nomes ens importa que sigui mes petit que limMaxim
                    acceptable = limMaxim>=aux;
                } else if(!max && min){ // Nomes ens importa que sigui mes gran que limMinim
                    acceptable = limMinim<=aux;
                }
            }
            if(!acceptable) {
                System.out.println("ERROR: Integer no valid! \n Limits:");
                if(min){
                    System.out.println("El limit inferior es: " + limMinim);
                } else {
                    System.out.println("No hi ha limit inferior.");
                }
                if(max){
                    System.out.println("El limit superior es: " + limMaxim);
                } else {
                    System.out.println("No hi ha limit superior.");
                }
                System.out.print(a_nom+"> ");
                aux = a_entrada.nextInt();
            }
        }
        a_impresora.imprimirFitxerOnly(a_nom+"> "+aux);
        return aux;
    }

    /**
     * @pre True
     * @post Demana el jugador que entri per teclat un double, fins que entri un dintre dels limits (si estan activats).
     */
    @Override
    double entrarDouble(double limMinim, double limMaxim, boolean min, boolean max) {
        double aux;
        System.out.print(a_nom+"> ");
        aux = a_entrada.nextInt();
        boolean acceptable = false;
        while(!acceptable){
            if(!min && !max){ // Rangs desactivats cualsevol int es acceptable
                acceptable = true;
            } else {
                if (min && max) { // Ha de estar entre limMinim i limMaxim
                    acceptable = limMinim<=aux && limMaxim>=aux;
                } else if(!min && max){ // Nomes ens importa que sigui mes petit que limMaxim
                    acceptable = limMaxim>=aux;
                } else if(!max && min){ // Nomes ens importa que sigui mes gran que limMinim
                    acceptable = limMinim<=aux;
                }
            }
            if(!acceptable) {
                System.out.println("ERROR: Double no valid! \n Limits:");
                if(min){
                    System.out.println("El limit inferior es: " + limMinim);
                } else {
                    System.out.println("No hi ha limit inferior.");
                }
                if(max){
                    System.out.println("El limit superior es: " + limMaxim);
                } else {
                    System.out.println("No hi ha limit superior.");
                }
                System.out.print(a_nom+"> ");
                aux = a_entrada.nextInt();
            }
        }
        a_impresora.imprimirFitxerOnly(a_nom+"> "+aux);
        return aux;
    }

    /**
     * @pre True
     * @post Retorna true is this i o es el mateix objecte, false en cas contrari
     */
    @Override
    public boolean equals(Object o) {
        if(o == this){
            return true;
        }

        if(!(o instanceof Jugador)){
            return false;
        }

        Jugador j = (Jugador) o;
        return j.id() == a_id;
    }
}
