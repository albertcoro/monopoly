import java.util.ArrayList;
import java.util.List;

public class TestCasesIHotels {
    public static void main(String[] args) {
        Impresora imp = new Impresora();

        Jugador j1 = new Huma(1,"Albert",imp);
        Jugador j2 = new Huma(2,"Marc",imp);

        List<Jugador> jugadors = new ArrayList<Jugador>();
        jugadors.add(j1); jugadors.add(j2);

        j1.ingressarDiners(10000); j2.ingressarDiners(5000);

        List<Double> pLloger = new ArrayList<Double>();
        pLloger.add(50.0); pLloger.add(100.0);
        pLloger.add(150.0); pLloger.add(200.0);
        pLloger.add(350.0);

        Terreny t1 = new TerrenyCasesIHotel("CarrerCreu",600.0,0.0,"no",pLloger,0,null,imp,"si",4,50, true, 400, 500);

        j1.afegirTerreny(t1);
        t1.canviarPropietari(j1);

        System.out.println(j1);

        System.out.println("\n****** CASELLA: CONSTRUIR_CASES ******");
        Accio contrCasa = new AccConstruirCasa(imp);

        System.out.println("\n"+contrCasa.pregunta());
        contrCasa.executa(j1,t1,jugadors,null,null);
        System.out.println("Diners de " + j1.nom() + ": " + j1.diners());

        System.out.println("\n****** CASELLA: CONTRUIR_HOTEL ******");
        Accio contrHotel = new AccConstruirHotel(imp);

        System.out.println("\n"+contrHotel.pregunta());
        contrHotel.executa(j1,t1,jugadors,null,null);
        System.out.println("Diners de " + j1.nom() + ": " + j1.diners());

        System.out.println("\n****** CASELLA: DESTRUIR_CASES ******");
        Accio destrCasa = new AccDestruirCasa(imp);

        System.out.println("\n"+destrCasa.pregunta());
        destrCasa.executa(j1,t1,jugadors,null,null);
        System.out.println("Diners de " + j1.nom() + ": " + j1.diners());

        System.out.println("\n****** CASELLA: DESTRUIR_HOTEL ******");
        Accio destrHotel = new AccDestruirHotel(imp);

        System.out.println("\n"+destrHotel.pregunta());
        destrHotel.executa(j1,t1,jugadors,null,null);
        System.out.println("Diners de " + j1.nom() + ": " + j1.diners());
    }
}
