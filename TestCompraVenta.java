import java.util.*;

public class TestCompraVenta {
    public static void main(String[] args) {
        Impresora imp = new Impresora();

        Jugador j1 = new Huma(1,"TestSubject",imp);
        Jugador j2 = new Huma(2,"Albert",imp);
        Jugador j3 = new Huma(3,"Marc",imp);
        Jugador j4 = new Huma(4,"Player4",imp);

        List<Jugador> jugadors = new ArrayList<Jugador>();
        jugadors.add(j1); jugadors.add(j2);
        jugadors.add(j3); jugadors.add(j4);
        j1.ingressarDiners(10000); j2.ingressarDiners(5000);
        j3.ingressarDiners(10000); j4.ingressarDiners(5000);

        List<Double> pLloger = new ArrayList<Double>();
        pLloger.add(50.0); pLloger.add(100.0);
        pLloger.add(150.0); pLloger.add(200.0);
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
        j1.afegirTerreny(t1); t1.canviarPropietari(j1);

        System.out.println("\n****** ACCIO: OFERTA COMPRA ******");
        Accio ofertaC = new AccOfertaCompra(imp);

        System.out.println("\n"+ofertaC.pregunta());
        ofertaC.executa(j2,t1,jugadors,b,tauler);
        mostrarDiners(jugadors);

        System.out.println("\n****** ACCIO: OFERTA VENTA ******");
        Accio ofertaV = new AccOfertaVenta(imp);

        System.out.println("\n"+ofertaV.pregunta());
        ofertaV.executa(j2,t1,jugadors,b,tauler);
        mostrarDiners(jugadors);
    }

    private static void mostrarDiners(List<Jugador> jugadors){
        for (Jugador jugador : jugadors) {
            System.out.println("Diners de " + jugador.nom() + ": " + jugador.diners());
        }
    }

}
