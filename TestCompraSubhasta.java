import java.util.ArrayList;
import java.util.List;

public class TestCompraSubhasta {
    public static void main(String[] args) {
        Impresora imp = new Impresora();

        AccComprarTerreny acCompraT = new AccComprarTerreny(imp);

        Jugador j1 = new Huma(1,"TestSubject",imp);
        Jugador j2 = new Huma(2,"Albert",imp);
        Jugador j3 = new Huma(3,"Marc",imp);
        Jugador j4 = new Huma(3,"Player4",imp);

        List<Jugador> jugadors = new ArrayList<Jugador>();
        jugadors.add(j1); jugadors.add(j2); jugadors.add(j3); jugadors.add(j4);

        jugadors.forEach(j -> j.ingressarDiners(10000));

        List<Double> pLloger = new ArrayList<Double>();
        pLloger.add(50.0); pLloger.add(100.0);
        pLloger.add(150.0); pLloger.add(200.0);
        pLloger.add(350.0);

        Terreny t1 = new TerrenyCasesIHotel("CarrerCreu",600.0,0.0,"no",pLloger,0,null,imp,"si",4,50, true, 400, 500);

        System.out.println(acCompraT.pregunta());
        acCompraT.executa(j1,t1,jugadors,null,null);

        System.out.print("Resultats: \n");
        mostrarJugadors(jugadors);
    }

    private static void mostrarJugadors(List<Jugador> jugs){
        System.out.println("\n**** Jugadors ****");
        for (Jugador jug : jugs) {
            System.out.println(jug.nom() + ": " + jug.diners() + "\n" + jug.prop());
        }
    }
}
