/**
* @class TerrenyCases
* @brief TerrenyCases refina els conceptes de la classe Terreny afegint la característica de poder edificar cases.
* @author Marc Torres Vilagut (u1959683)
*/

import java.util.List;

public class TerrenyCases extends Terreny {
    
    protected int a_nCases;             ///< Nombre de cases contruides al terreny
    protected int a_maxCases;           ///< Nombre màxim de cases edificables
    private double a_pCasa;             ///< Preu per construir un casa
    private String a_construible;       ///< Valor que indica si es pot construir en la propietat, i si es pot construir, si es necessita tenir totes les propietats de la mateixa agrupació
    private List<Accio> a_opcions;      ///< Accions que el jugador pot fer sobre la casella

    /** @brief Crea un terreny edificable de cases amb unes dades inicials
    @pre: preuVenta>0 && preuAgrup>0 && lloguerCases[i]>0 && maxCases>0 && preuCasa>0
    @post: Crea un terreny edificable de cases complet amb tots els paràmetres. */
    public TerrenyCases(String nom, double pVenda, double pHipoteca, String agrupacio, List<Double> pLloger, double pLlogerAgrupacio, Propietari prop, Impresora imp, String construible, int maxCases, double pCasa, boolean hotelConstruible) {
        super(nom, pVenda, pHipoteca, agrupacio, pLloger, pLlogerAgrupacio, prop, imp);
        
        a_construible = construible;
        a_nCases = 0;
        a_maxCases = maxCases;
        a_pCasa = pCasa;
    }

    /** @pre ---
    @post Retorna el preu de construcció d'una casa */
    public double obtPreuCasa(){
        return a_pCasa;
    }

    /** @pre ---
    @post Retorna el preu de construcció d'un hotel*/
    public double obtPreuHotel(){
        return 0.0;
    }

    /** @pre ---
    @post Retorna el preu de lloguer del terreny */
    public double obtPreuLloguer(boolean propietariTeAgrupacio){
        // Comprovar si el jugador té tots els terrenys de l'agrupació
        // Si té tots aleshotes retorna el valor del lloguer amb tota l'agrupació
        if (propietariTeAgrupacio)
            return Math.max(a_pAgrupacio,a_pLloger.get(a_nCases));
        // Altrament retornar el valor del lloguer per defecte:
        return a_pLloger.get(a_nCases);
    }

    /** @pre --
    @post Retorna el numero de cases que es poden construir.*/
    public int obtMaxCases() {return a_maxCases;}

    /**  @pre --
    @post Retorna el numero de cases construides.*/
    public int obtNCases(){return a_nCases;}

    /** @pre --
    @post Retorna el numero d'hotels construits.*/
    public int obtNHotel(){return 0;}

    /** @brief Retorna cert si s'han pogut construir les cases al terreny, fals altrament
    @pre quant>0 && quant<=maxCasesTerreny
    @post Retorna cert si es poden construir la quantitat de cases al terreny sense execir el màxim, fals altrament */
    public boolean construirCasa(int quantitat, boolean propietariTeAgrupacio){
        // Comprovar si el jugador té tots els terrenys de l'agrupació

        if(a_construible.equals("agrupacio") && !propietariTeAgrupacio ){
            return false;
        }
        else {
            if (a_nCases < a_maxCases){
                a_nCases = Math.min(a_nCases+quantitat, a_maxCases); // Si la quantitat s'ha excedit agafem el nombre màxim de cases
                return true;
            }
        }
        return false;
    }

    /** @brief Es destrueix/ven una casa del terreny i retorna el preu pagat per construir-les
    @pre quant>0 && quant<=maxCasesTerreny
    @post Es destrueixen la quantitat de cases al terreny i retorna el seu valor */
    public double treureCasa(int quantitat){
        double res = 0.0;
        int n = Math.min(a_nCases,quantitat); // Si la quantitat s'ha excedit agafem el nombre de cases restants
        res = (n*a_pCasa);
        a_nCases -= n;
        return res;
    }

    /** @brief Retorna cert si s'ha pogut construir un hotel al terreny, fals altrament
    @pre ---
    @post Retorna cert si es construiex l'hotel al terreny, altrament fals. */
    public boolean construirHotel(){
        return false;
    }

    /** @brief Es destrueix/ven l'hotel del terreny
    @pre ---
    @post Es destrueixen l'hotel del terreny */
    public double treureHotel(){
        return 0.0;
    }

}
