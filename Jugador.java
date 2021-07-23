/** @file Jugador.java
 @brief Classe Jugador
 */

import java.lang.Math;
import java.util.*;

/**
 * @class Jugador
 * @author Albert Corominas Mariscot (u1960480)
 * @brief  Jugador amb id unic i nom. Pot ser jugador huma o bot.
 */

abstract public class Jugador extends Propietari {

    static int MINDAU = 1; ///< Minim que pot treure en un dau de 6 cares
    static int MAXDAU = 6; ///< Maxim que pot treure en un dau de 6 cares

    protected int a_id; ///< Id identificador de Propietari (1-6).
    protected String a_nom; ///< Nom del Propietari.
    private double a_diners; ///< Diners actual del jugador.
    private boolean a_preso; ///< Boolea que indica si el jugador esta a la preso.
    private Queue<Prestec> a_prestecs; ///< Llista de prectecs que té aquest jugador
    private List<TarjetaSort> a_tarjetaSort; ///< Llista de tarjetes de la sort
    protected Impresora a_impresora; ///< Impresora per entrades i sortides
    private int a_tornsPreso; ///< Torns que porta a la preso el jugador
    private ArrayList<Accio> a_accions; ///< Accions no obligatories que pot fer el jugador

    // Accions opcionals d'un jugador
    private AccOfertaCompra a_OfertaCompra;     ///< Acció de oferir una compra
    private AccOfertaVenta a_OfertaVenta;       ///< Acció de oferir una venta
    private AccDemanarPrestec a_DemanaPrestec;  ///< Acció per demanar prestec
    private AccUtilitzarTarjetaSort a_UsaTarjeta; ///< Acció per usar una tarjeta de la sort

    // Opcionals relacionades amb terrenys
    private AccConstruirCasa a_ConstrCasa;      ///< Acció de construir cases
    private AccConstruirHotel a_ConstrHotel;    ///< Acció de construir hotel
    private AccDestruirCasa a_DestrCasa;        ///< Acció de destruir cases
    private AccDestruirHotel a_DestrHotel;      ///< Acció de destruir hotel

    /**
     * @pre id=<1 && id<=6
     * @post Crea un jugador amb id, nom, estat de preso, llista de terrenys, llista de prestecs, llista de tarjetes de
     * sort, impresora per mostrar missatges, torns en els que esta en la preso i accions opcionals que pot fer.
     */
    Jugador(int id, String nom, Impresora imp){
        super();
        a_id = id;
        a_nom = nom;
        a_preso = false;
        a_terr = new ArrayList<>();
        a_prestecs = new LinkedList<>();
        a_tarjetaSort = new Vector<>();
        a_impresora = imp;
        a_tornsPreso = 0;

        a_ConstrCasa = new AccConstruirCasa(imp);
        a_ConstrHotel = new AccConstruirHotel(imp);
        a_DestrCasa = new AccDestruirCasa(imp);
        a_DestrHotel = new AccDestruirHotel(imp);
        a_UsaTarjeta = new AccUtilitzarTarjetaSort(imp);
        a_OfertaCompra = new AccOfertaCompra(imp);
        a_OfertaVenta = new AccOfertaVenta(imp);
        a_DemanaPrestec = new AccDemanarPrestec(imp);
        a_accions = new ArrayList<>();
        a_accions.add(a_ConstrCasa);
        a_accions.add(a_ConstrHotel);
        a_accions.add(a_DestrCasa);
        a_accions.add(a_DestrHotel);
        a_accions.add(a_UsaTarjeta);
        a_accions.add(a_DemanaPrestec);
        a_accions.add(a_UsaTarjeta);
        a_accions.add(a_OfertaCompra);
        a_accions.add(a_OfertaVenta);
    }

    /**
     * @pre True
     * @post Retorna l’identificador Integer del propietari (0-6).
     */
    public int id(){
        return a_id;
    }

    /**
     * @pre True
     * @post Retorna el nom del propietari actual.
     */
    public String nom(){
        return a_nom;
    }

    /**
     * @pre True
     * @post Retorna els diners actuals del jugador.
     */
    public double diners(){
        return a_diners;
    }

    /**
     * @pre True
     * @post Retorna true si el jugador esta a la preso, false en cualsevol altre cas.
     */
    public boolean preso(){
        return a_preso;
    }

    /**
     * @pre True
     * @post El jugador es enviat a la posicio de la preso i de manera efectiva entra a la preso, imediatament acabant
     * el seu torn.
     */
    public void anarPreso(){
        a_preso = true;
    }

    /**
     * @pre True
     * @post El jugador es manté en la posicio de la preso, pero ja no es considera que esta adins, si no com a visita.
     */
    public void sortirPreso(){
        a_preso = false;
    }

    /**
     * @pre True
     * @post Retorna les opcions opcionals que pot fer durant els seu torn
     */
    public ArrayList<Accio> opcions(){
        return a_accions;
    }

    /**
     * @pre True
     * @post Retorna el numero resultant de la tirada de daus.
     */
    public int[] tirarDaus(){
        int dau1 = (int) (Math.random()*(MAXDAU-MINDAU+1)+MINDAU);
        int dau2 = (int) (Math.random()*(MAXDAU-MINDAU+1)+MINDAU);
        return new int[] {dau1, dau2};
    }

    /**
     * @pre True
     * @post Avança un torn a la preso per poder sortir quan hagin passat 3
     */
    public void passarTornPreso(){
        a_tornsPreso++;
    }

    /**
     * @pre True
     * @post Retorna el numero de torns que ha estat a la preso
     */
    public int tornsPreso(){
        return a_tornsPreso;
    }

    /**
     * @pre True
     * @post Posa els torns de preso a 0 per que ha sortit de preso
     */
    public void resetPreso(){
        a_tornsPreso = 0;
    }

    /**
     * @pre True
     * @post Retorna true si toca pagar prestecs aquest torn
     */
    public boolean tocaPagarPrestec(){
        if(tePrestecs()) {
            Prestec p = primerPrestec();
            return p.tocaPagar();
        } else {
            return false;
        }
    }

    /**
     * @pre True
     * @post Avança un torn els prestecs pendents del jugador
     */
    public void passarTornPrestecs(){
        for(Prestec p: a_prestecs){
            p.pasarTorn();
        }
    }

    /**
     * @pre True
     * @post Retorna true si s'han pogut pagar el primer prestec pendent.
     */
    public boolean pagarPrestec(){
        Prestec p = primerPrestec();
        Jugador j = p.donant();
        double diners = p.dinersTornar();
        if(teDiners(diners)){
            retirarDiners(diners);
            j.ingressarDiners(diners);
            treurePrestec();
            return true;
        } else {
            return false;
        }
    }

    /**
     * @pre True
     * @post Afagueix un prestec a la pila de pretecs
     */
    public void afeguirPrestec(Prestec p){
        a_prestecs.add(p);
    }

    /**
     * @pre True
     * @post Retorna true si te prestecs pendents, false en cas contrari
     */
    public boolean tePrestecs(){
        return a_prestecs.size()>0;
    }

    /**
     * @pre True
     * @post Retorna true si objecte o i this es igual, false en cas contrari
     */
    @Override
    public abstract boolean equals(Object o);

    /**
     * @pre diners>0
     * @post Ingressa diners al jugador, retorna true si s’ha pogut fer, false en cas contrari.
     */
    public void ingressarDiners(double diners){
        a_diners += diners;
    }

    /**
     * @pre diners>0
     * @post Retira diners al jugador, retorna true si s’ha pogut fer, false en cas contrari.
     */
    public void retirarDiners(double diners){
        a_diners -= diners;
    }

    /**
     * @pre True
     * @post Treu el primer prestec de la pila de prestecs
     */
    public void treurePrestec(){
        a_prestecs.poll();
    }

    /**
     * @pre True
     * @post Retorna el primer prestec de la pila de prestecs
     */
    public Prestec primerPrestec(){
        return a_prestecs.peek();
    }

    /**
     * @pre Cert
     * @post Retorna true si el jugador actual te mes diners o igual que els demanats, false en cas contrari.
     */
    private boolean teDiners(double diners){
        return diners<=a_diners;
    }

    /**
     * @pre True
     * @post Retorna true si te tarjetes de sortir de la preso, false en cas contrari
     */
    public boolean tarjetesSortirPreso(){
        for(TarjetaSort t: a_tarjetaSort){
            if(t.paraulaClau().equals("SORTIR_PRESO")){ //Si te tarjetes de la sort
                return true;
            }
        }
        return false;
    }


    /**
     * @pre True
     * @post Retira una tarjeta de la sort de sortir de preso de la pila de tarjetes de la sort
     */
    public void treureTarjetaPreso(){
        for(TarjetaSort t: a_tarjetaSort){
            if(t.paraulaClau() == "SORTIR_PRESO"){ //Si te tarjetes de la sort
                a_tarjetaSort.remove(t);
                return;
            }
        }
    }

    /**
     * @pre !a_tarjetaSort.contains(t)
     * @post Afagueix una tarjeta t a la pila de tarjetes de la sort
     */
    public void afeguirTarjeta(TarjetaSort t){
        if(!a_tarjetaSort.contains(t)){
            a_tarjetaSort.add(t);
        }
    }

    /**
     * @pre a_tarjetaSort.contains(t)
     * @post Treu la tarjeta t de la pilla de tarjetes
     */
    public void retirarTarjeta(TarjetaSort t){
        a_tarjetaSort.remove(t);
    }

    /**
     * @pre True
     * @post Retorna la pila de tarjetes de la sort del jugador
     */
    public List<TarjetaSort> tarjetes(){
        return a_tarjetaSort;
    }

    abstract String entrarString(boolean limitat, List<String> valids);
    abstract int entrarInt(int limMinim, int limMaxim, boolean min, boolean max);
    abstract double entrarDouble(double limMinim, double limMaxim, boolean min, boolean max);
}
