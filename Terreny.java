/**
* @class Terreny
* @brief Terreny és una parcela de tauler, que es pot comprar per ser Terreny privada de un jugador, per defecte totes les Terrenys son de la banca, també definirem varies característiques de la casella Terreny, en el constructor, com: preu de venta, si pertany a una agrupació, etc.
* @author Marc Torres Vilagut (u1959683)
*/

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Terreny implements Casella{
    private String a_nom;                       ///< Nom de la propietat
    private double a_pVenda;                    ///< Preu de venda de la propietat (preu pel qual el jugador compra el terreny)
    private double a_pHipoteca;                 ///< Preu per hipotecar el terreny
    protected String a_agrupacio;               ///< Nom de l'agrupació a la que perteny el terreny
    protected double a_pAgrupacio;              ///< Preu del lloguer si es té tota l'agrupació
    protected List<Double> a_pLloger;           ///< Conté el preu de lloguer amb agrupació
    protected Propietari a_propietari;          ///< Identificador del jugador propietari

    /* Accions d'una casella de Terreny */
    protected AccComprarTerreny a_comprar;      ///< Acció de comprar el terreny
    protected AccPagarLloguer a_accLloguer;   ///< Acció de pagar el lloguer del terreny





    /** @pre: --
    @post: Crea un terreny amb només un nom. */
    public Terreny(String nom){
        a_nom = nom;
    }

    /** @brief Crea un terreny amb unes dades inicials
    @pre: preuVenta>0 && preuAgrup>0 && lloguerCases[i]>0
    @post: Crea un terreny complet amb tots els paràmetres. */
    public Terreny(String nom, double pVenda, double pHipoteca, String agrupacio, List<Double> pLloger, double pLlogerAgrupacio, Propietari prop, Impresora imp){
        this(nom);
        a_pVenda = pVenda;
        a_pHipoteca = pHipoteca;
        a_agrupacio = agrupacio;
        a_pLloger = pLloger;
        a_pAgrupacio = pLlogerAgrupacio;
        a_propietari = prop; // Banca

        // Accions:
        a_comprar = new AccComprarTerreny(imp);
        a_accLloguer = new AccPagarLloguer(imp);


    }

    /** @pre ---
    @post Retorna el nom del terreny */
    public String obtNom(){
        return a_nom;
    }

    /** @pre ---
    @post Retorna el preu de venda */
    public double obtPreuVenda(){
        return a_pVenda;
    }

    /** @pre ---
    @post Retorna el preu d'hipoteca */
    public double obtPreuHipoteca(){
        return a_pHipoteca;
    }
    
    /** @pre ---
    @post Retorna el preu de lloguer del terreny */
    public double obtPreuLloguer(boolean propietariTeAgrupacio){
        // Comprovar si el jugador té tots els terrenys de l'agrupació
        // Si té tots aleshotes retorna el valor del lloguer amb tota l'agrupació:
        if(propietariTeAgrupacio)
            return a_pAgrupacio;
        // Altrament retornar el valor del lloguer per defecte:
        return a_pLloger.get(0);

    }

    /** @pre ---
    @post Retorna el nom de l'agrupació a la que pertany el terreny. */
    public String obtAgrupacio(){
        return a_agrupacio;
    }

    /** @pre ---
    @post Modifica el propietari del terreny */
    public void canviarPropietari(Propietari p){
        a_propietari = p;
    }

    /** @pre ---
    @post Retorna el identificador del jugador propietari del terreny */
    public Propietari obtPropietari(){
        return a_propietari;
    }

    /** @pre ---
    @post Retorna el preu de construcció d'una casa */
    public double obtPreuCasa(){
        return 0.0;
    }

    /** @pre ---
    @post Retorna el preu de construcció d'un hotel */
    public double obtPreuHotel(){
        return 0.0;
    }

    /** @pre --
     @post Retorna el numero d'hotels que es poden construir.*/
    public int obtMaxHotels() {return 0;}

    /** @pre --
    @post Retorna el numero de cases que es poden construir.*/
    public int obtMaxCases() {return 0;}

    /** @pre --
    @post Retorna el numero de cases construides.*/
    public int obtNCases(){return 0;}

    /** @pre --
    @post Retorna el numero d'hotels construits.*/
    public int obtNHotel(){return 0;}

    /** @brief Retorna cert si s'han pogut construir les cases al terreny, fals altrament
    @pre quant>0 && quant<=maxCasesTerreny
    @post Retorna cert si es poden construir la quantitat de cases al terreny sense execir el màxim, fals altrament */
    public boolean construirCasa(int quantitat, boolean propietariTeAgrupacio){
        return false;
    }

    /** @brief Es destrueix/ven una casa del terreny i retorna el preu pagat per construir-les
    @pre quant>0 && quant<=maxCasesTerreny
    @post Es destrueixen la quantitat de cases al terreny i retorna el seu valor */
    public double treureCasa(int quantitat){
        return 0.0;
    }

    /** @brief Retorna cert si s'ha pogut construir un hotel al terreny, fals altrament
    @pre ---
    @post Retorna cert si es construiex l'hotel al terreny, fals altrament. */
    public boolean construirHotel(){
        return false;
    }

    /** @brief Es destrueix/ven l'hotel del terreny
    @pre ---
    @post Es destrueixen l'hotel del terreny */
    public double treureHotel(){
        return 0.0;
    }

    /** @pre ---
    @post Retorna les opcions d'accions que un jugador té amb un terreny.  */
    @Override
    public Accio opcions() {
        if (a_propietari instanceof Banca) {
            return a_comprar;
        } else {
            return a_accLloguer;
        }
    }

    /** @pre ---
     @post Retorna l'identificador de la casella */
    @Override
    public String id(){ return "T"; }

    /** @brief Retorna el Terreny en format String
    @pre ---
    @post Retrona un string amb les dades de Terreny */
    @Override
    public String toString(){
        String s = "Terreny: "+ a_nom +" [Propietari: ";

        if(a_propietari instanceof Banca){
            s += "Banca]:\n";
            s += "    Pots comprar aquesta casella per " + a_pVenda + ".";
        } else {
            s += ((Jugador) a_propietari).nom() +"]:\n";
            s += "    Pots fer una oferta de compra aquesta casella si no es teva.";
        }

        return s;
    }

    /** @pre ---
     @post Retrona cert són la matiexa propietat, fals altrament */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        /*if (o == null || getClass() != o.getClass())
            return false;*/
        Terreny terreny = (Terreny) o;
        return a_nom.equals(terreny.a_nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a_nom);
    }
	
}
