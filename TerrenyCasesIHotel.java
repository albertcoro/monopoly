/**
* @class TerrenyCasesIHotel
* @brief TerrenyCasesIHotel refina els conceptes de la classe TerrenyCases afegint la característica de poder edificar un hotel.
* @author Marc Torres Vilagut (u1959683)
*/

import java.util.List;

public class TerrenyCasesIHotel extends TerrenyCases {
    private int a_nHotel;                ///< Nombre d'hotels construits a la propietat (0/1)
    private double a_pHotel;             ///< Preu per construir un hotel
    private double a_pLloguerHotel;      ///< Preu de lloguer del terreny amb un hotel
    
    /** @brief Crea un terreny edificable de cases i un hotel amb unes dades inicials
    @pre: preuVenta>0 && preuAgrup>0 && lloguerCases[i]>0 && maxCases>0 && preuCasa>0 && preuHotel>0
    @post: Crea un terreny edificable de cases i un hotel complet amb tots els paràmetres. */
    public TerrenyCasesIHotel(String nom, double pVenda, double pHipoteca, String agrupacio, List<Double> pLloger, double pLlogerAgrupacio, Propietari prop, Impresora imp, String construible, int maxCases, double pCasa, boolean hotelConstruible, double pHotel, double pLlogerHotel) {
        super(nom, pVenda, pHipoteca, agrupacio, pLloger, pLlogerAgrupacio, prop, imp, construible, maxCases, pCasa, hotelConstruible);

        a_nHotel = 0;
        a_pHotel = pHotel;
        a_pLloguerHotel = pLlogerHotel;
    }
    
    /** @pre ---
    @post Retorna el preu de construcció d'un hotel*/
    public double obtPreuHotel(){
        return a_pHotel;
    }

    /** @pre ---
    @post Retorna el preu de lloguer del terreny */
    public double obtPreuLloguer(boolean propietariTeAgrupacio){
        double res = a_pLloger.get(a_nCases);

        // Comprovar si el jugador té tots els terrenys de l'agrupació
        if (propietariTeAgrupacio)
            res = Math.max(a_pAgrupacio,res);

        // Altrament retornar el valor del lloguer per defecte:
        if (a_nHotel==1)
            res = Math.max(a_pLloguerHotel,res);

        return res;
    }

    /** @pre --
     @post Retorna el numero d'hotels que es poden construir.*/
    public int obtMaxHotels() {return 1;}

    /** @pre --
    @post Retorna el numero d'hotels construits.*/
    public int obtNHotel(){return a_nHotel;}

    /* @brief Retorna cert si s'ha pogut construir un hotel al terreny, fals altrament
    @pre ---
    @post Retorna cert si es construiex l'hotel al terreny, fals altrament. */
    public boolean construirHotel() {
        // Comprovem si té el nombre màxim de cases construides
        if(a_nCases == a_maxCases){
            // Comprovem si no té un hotel construit
            if (a_nHotel==0) {
                a_nHotel=1;
                return true;
            }
        }
        return false;
    }

    /* @brief Es destrueix/ven l'hotel del terreny
    @pre ---
    @post Es destrueixen l'hotel del terreny */
    public double treureHotel(){
        if (a_nHotel==1) {
            a_nHotel = 0;
            return a_pHotel;
        }
        return 0.0;
    }
}
