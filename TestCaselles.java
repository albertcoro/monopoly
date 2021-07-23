/**
 * @class TestTerreny
 * @brief Petit test del funcionament d'algunes caselles i les accions que contenen
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.*;

public class TestCaselles {
    public static void main(String[] args) {
        Impresora imp = new Impresora();

        /*** CREEM ELS JUGADORS ***/
        Jugador j1 = new Huma(1,"Albert",imp);
        Jugador j2 = new Huma(2,"Marc",imp);
        j1.ingressarDiners(1500.0); j2.ingressarDiners(2000.0);

        List<Jugador> jugadors = new ArrayList<Jugador>();
        jugadors.add(j1);
        jugadors.add(j2);

        /*** CREEM EL/S TERRENY/S ***/
        List<Double> pLloger = new ArrayList<Double>();
        pLloger.add(50.0);
        pLloger.add(100.0);
        pLloger.add(150.0);
        pLloger.add(200.0);
        pLloger.add(350.0);

        Banca b = new Banca();

        Terreny t1 = new TerrenyCasesIHotel("CarrerCreu",600.0,0.0,"no",pLloger,0,b,imp,"si",4,50, true, 400, 500);

        t1.canviarPropietari(j1);
        j1.afegirTerreny(t1);

        /*** MOSTREM JUGADORS ***/
        System.out.println("Jugador " + j1.nom() + ": " + j1.diners());
        System.out.println("Jugador " + j2.nom() + ": " + j2.diners()+"\n");

        /*** TESTS D'OPCIONS DE CASELLES  ***/
        Accio comanda;

        /*** CASELLA D'APOSTA ***/
        System.out.println("\n****** CASELLA: APOSTA ******");
        Casella ap = new Aposta(imp);
        comanda = ap.opcions();

        System.out.println(comanda.pregunta());
        comanda.executa(j1,t1,jugadors,null,null);
        mostrarJugadors(jugadors);

        /*** CASELLA DE COMANDA DIRECTA: MULTA ***/
        System.out.println("\n****** CASELLA: MULTA ******");
        Casella cd1 = new ComandaDirecta(new AccMulta(imp,1000.0));
        comanda = cd1.opcions();

        System.out.println(comanda.pregunta());
        comanda.executa(j1,t1,jugadors,null,null);
        mostrarJugadors(jugadors);

        /*** CASELLA DE COMANDA DIRECTA: DONAR ***/
        System.out.println("\n****** CASELLA: DONAR ******");
        Casella cd2 = new ComandaDirecta(new AccDonar(imp));
        comanda = cd2.opcions();

        System.out.println(comanda.pregunta());
        comanda.executa(j1,t1,jugadors,null,null);
        mostrarJugadors(jugadors);

        /*** CASELLA DE COMANDA DIRECTA: REBRE ***/
        System.out.println("\n****** CASELLA: REBRE ******");
        Casella cd3 = new ComandaDirecta(new AccRebre(imp));
        comanda = cd3.opcions();

        System.out.println(comanda.pregunta());
        comanda.executa(j1,t1,jugadors,null,null);
        mostrarJugadors(jugadors);

        /*** CASELLA DE COMANDA DIRECTA: PAGAR ***/
        System.out.println("\n****** CASELLA: PAGAR ******");
        Casella cd4 = new ComandaDirecta(new AccPagar(imp,777.0));
        comanda = cd4.opcions();

        System.out.println(comanda.pregunta());
        comanda.executa(j2,t1,jugadors,null,null);
        mostrarJugadors(jugadors);


    }

    private static void mostrarJugadors(List<Jugador> jugs){
        System.out.println("\n**** Jugadors ****");
        for (Jugador jug : jugs) {
            System.out.println(jug.nom() + ": " + jug.diners() + "\n" + jug.prop());
        }
    }
}
