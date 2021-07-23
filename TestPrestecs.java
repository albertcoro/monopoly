public class TestPrestecs {
    public static void main(String[] args){
        Impresora imp = new Impresora();
        Jugador j1, j2;
        j1 = new Huma(1,"Albert",imp);
        j2 = new Huma(2, "Marc",imp);
        j1.ingressarDiners(4000);
        j2.ingressarDiners(2000);
        Prestec p = new Prestec(2000, 20, 4, j2);
        j1.afeguirPrestec(p);
        j1.pagarPrestec();
    }
}
