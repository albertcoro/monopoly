/**
 * @class Preso
 * @brief Preso es una casella del tauler que no es pot comprar. Si un jugador cau en aquesta casella no passa absolutament res. Si un jugador ha accedit a aquesta casella a través de una casella amb la comanda d’AnarPreso, perdrà 3 torns i no es contarà com si hagués passat per la casella de sortida.
 * @author Marc Torres Vilagut (u1959683)
 */

import java.util.ArrayList;

public class Preso implements Casella{
	String a_nom;	///< Nom de la Preso

	/** @pre: --
    @post: Crea una casella de preso */
	public Preso(String nom) {
		a_nom = nom;
	}

	public String nom(){
		return a_nom;
	}

    /** @brief No retorna res. Es visita la presó
    @pre ---
    @post No retorna cap opcio (caure en presó no implica cap acció obligatoria o opcional). */
    @Override
	public Accio opcions() {
		return null;
	}

	/** @pre ---
	 @post Retorna l'identificador de la casella */
	@Override
	public String id(){ return "P"; }

	/** @brief Retorna la casella de Preso en format String
    @pre ---
    @post Retrona un string amb les dades de la casella de Preso */
	@Override
	public String toString() {
		return  "Preso " + a_nom + ":\n"+
				"   Només els jugadors que caiguin en aquesta casella per una infracció o comanda diracte quedaran empresonats.";
	}

	/** @pre ---
	 @post Retrona cert si els dos objectes son iguals */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Preso preso = (Preso) o;
		return a_nom.equals(preso.a_nom);
	}

}
