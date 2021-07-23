/**
 * @class TestTerreny
 * @brief Petit test del funcionament d'algunes de les accions del joc
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.*;

public class TestAccions {

    public static void main(String[] args) {
        Impresora imp = new Impresora();

        Jugador j1 = new Huma(1,"Albert",imp);
        Jugador j2 = new Huma(2,"Marc",imp);

        List<Jugador> jugadors = new ArrayList<Jugador>();
        jugadors.add(j1); jugadors.add(j2);

        j1.ingressarDiners(10000); j2.ingressarDiners(5000);

        List<Double> pLloger = new ArrayList<Double>();
        pLloger.add(50.0);
        pLloger.add(100.0);
        pLloger.add(150.0);
        pLloger.add(200.0);
        pLloger.add(350.0);

        Terreny t1 = new TerrenyCasesIHotel("CarrerCreu",600.0,0.0,"no",pLloger,0,null,imp,"si",4,50, true, 400, 500);
        Preso p1 = new Preso("Preso1");

        Map<Integer,Casella> caselles = new LinkedHashMap<>();
        List<Integer> presons = new ArrayList<Integer>();

        caselles.put(1,t1); caselles.put(2,p1);
        presons.add(2);

        Map<Jugador,Integer> jugadorsTauler = new HashMap<Jugador,Integer>();
        jugadorsTauler.put(j1,1); jugadorsTauler.put(j2,2);

        Tauler tauler = new Tauler(caselles, presons, jugadorsTauler,null);

        Banca b = new Banca();
        b.afegirTerreny(t1);
        t1.canviarPropietari(b);

        imp.imprimir("\n****** TEST ACCIONS ******");
        mostrarJugadors(jugadors,imp);

        imp.imprimir("\n****** ACCIO: COBRAR PREMI 1 ******");
        Accio premi = new AccCobrarPremi(imp,1000,true,true);

        imp.imprimir("\n"+premi.pregunta());
        premi.executa(j1,t1,jugadors,b,tauler);
        mostrarJugadors(jugadors,imp);


        imp.imprimir("\n****** ACCIO: COBRAR PREMI 2 ******");

        imp.imprimir("\n"+premi.pregunta());
        premi.executa(j2,t1,jugadors,b,tauler);
        mostrarJugadors(jugadors,imp);

        imp.imprimir("\n****** ACCIO: MULTA ******");
        Accio multa = new AccMulta(imp,1000);

        imp.imprimir("\n"+multa.pregunta());
        multa.executa(j1,t1,jugadors,b,tauler);
        mostrarJugadors(jugadors,imp);;

        imp.imprimir("\n****** ACCIO: ANAR PRESO ******");
        Accio empresonar = new AccPreso(imp);
        imp.imprimir("\n"+empresonar.pregunta());
        empresonar.executa(j1,t1,jugadors,b,tauler);


        imp.imprimir("\n****** ACCIO: SORTIR PRESO  ******");
        Accio sortirPreso = new AccSortirPreso(imp);
        imp.imprimir("\n"+sortirPreso.pregunta());
        sortirPreso.executa(j1,t1,jugadors,b,tauler);

        imp.imprimir("\n****** ACCIO: APOSTA ******");
        Accio aposta = new AccAposta(imp);
        imp.imprimir("\n"+aposta.pregunta());
        aposta.executa(j1,t1,jugadors,b,tauler);
        mostrarJugadors(jugadors,imp);

        imp.imprimir("\n****** ACCIO: PAGAR LLOGUER ******");
        Accio lloguer = new AccPagarLloguer(imp);
        imp.imprimir("\n"+lloguer.pregunta());
        lloguer.executa(j2,t1,jugadors,b,tauler);
        mostrarJugadors(jugadors,imp);


        imp.imprimir("\n****** FI D'EXECUCIÓ ******");
    }

    private static void mostrarJugadors(List<Jugador> jugs, Impresora imp){
        imp.imprimir("\n**** Jugadors ****");
        for (Jugador jug : jugs) {
            imp.imprimir(jug.nom() + ": " + jug.diners() + "\n" + jug.prop());
        }
    }
}
