/** @file main.java
    @brief Classe Main
 */

/**
 * @class Main
 * @author Albert Corominas Mariscot (u1960480)
 * @brief Aquesta es la classe inicial a on es fan operacions de lectura de entrada i mostrar per consola.
 */

public class main {
    /**
     * @pre True
     * @post Inicia programa i crea l'ojbecte principal de monopolyText
     */
    public static void main(String[] args){
        MonopolyText mt = new MonopolyText();
        imprimirTitol();
        mt.crearPartida();
    }

    /**
     * @pre True
     * @post Imprimeix titol :)
     */
    public static void imprimirTitol(){
        System.out.println("                                                                                           \n" +
                "▀████▄     ▄███▀ ▄▄█▀▀██▄ ▀███▄   ▀███▀ ▄▄█▀▀██▄ ▀███▀▀▀██▄  ▄▄█▀▀██▄ ▀████▀   ▀███▀   ▀██▀\n" +
                "  ████    ████ ▄██▀    ▀██▄ ███▄    █ ▄██▀    ▀██▄ ██   ▀██▄██▀    ▀██▄ ██       ███   ▄█  \n" +
                "  █ ██   ▄█ ██ ██▀      ▀██ █ ███   █ ██▀      ▀██ ██   ▄████▀      ▀██ ██        ███ ▄█   \n" +
                "  █  ██  █▀ ██ ██        ██ █  ▀██▄ █ ██        ██ ███████ ██        ██ ██         ████    \n" +
                "  █  ██▄█▀  ██ ██▄      ▄██ █   ▀██▄█ ██▄      ▄██ ██      ██▄      ▄██ ██     ▄    ██     \n" +
                "  █  ▀██▀   ██ ▀██▄    ▄██▀ █     ███ ▀██▄    ▄██▀ ██      ▀██▄    ▄██▀ ██    ▄█    ██     \n" +
                "▄███▄ ▀▀  ▄████▄ ▀▀████▀▀ ▄███▄    ██   ▀▀████▀▀ ▄████▄      ▀▀████▀▀ ██████████  ▄████▄   \n" +
                "                                                                                           \n" +
                "                                                         per Albert Corominas i Marc Torres\n");
    }
}
