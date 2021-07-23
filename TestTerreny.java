/**
 * @class TestTerreny
 * @brief Petit test del funcionament d'un terreny a l'hora de construir i obtenir el lloguer.
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.List;
import java.util.ArrayList;

public class TestTerreny {
    public static void main(String[] args) {
        Impresora imp = new Impresora();

        Accio premi = new AccCobrarPremi(imp,1000.0,true,true);
        
        Jugador j1 = new Huma(1,"Joan", imp);
        List<Jugador> jugadors = new ArrayList<Jugador>();
        jugadors.add(j1);

        List<Double> pLloger = new ArrayList<Double>();
        pLloger.add(50.0);
        pLloger.add(100.0);
        pLloger.add(150.0);
        pLloger.add(200.0);
        pLloger.add(350.0);

        Banca b = new Banca();

        Terreny t1 = new TerrenyCasesIHotel("CarrerCreu",600.0,0.0,"no",pLloger,0,b,imp,"si",4,50, true, 400, 500);
        Terreny t2 = new TerrenyCases("CarrerEmporda", 600.0, 0.0, "no", pLloger, 0, b,imp, "si", 4, 50, false);

        List<Terreny> terrenys = new ArrayList<Terreny>();
        b.afegirTerreny(t1);
        b.afegirTerreny(t2);

        System.out.println("Lloguer terreny " + t2 + ": "+ t2.obtPreuLloguer(false));
        t2.construirCasa(2,false);
        System.out.println("Lloguer terreny " + t2 + ": "+ t2.obtPreuLloguer(false));

        System.out.println("Lloguer terreny " + t1 + ": "+ t1.obtPreuLloguer(false));
        t1.construirCasa(4,false);
        t1.construirHotel();
        System.out.println("Lloguer terreny " + t1 + ": "+ t1.obtPreuLloguer(false));



        premi.executa(j1,t1,jugadors,b,null);

        System.out.println("Jugador " + j1.nom() + ": " + j1.diners());
        premi.executa(j1,t1,jugadors,b,null);
        System.out.println("*** Premi reclamat ***");
        System.out.println("Jugador " + j1.nom() + ": " + j1.diners());

    }
}
