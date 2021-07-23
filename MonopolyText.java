/** @file MonopolyText.java
 @brief Classe MonopolyText
 */

import java.io.*;
import java.util.*;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static java.lang.String.valueOf;

/**
 * @class MonopolyText
 * @author Albert Corominas Mariscot (u1960480)
 * @brief Modul funcional que permet l'us de entrades i sortides en mode text. Aquest modul es basicament de
 * conveniencia per poder tenir una classe on poder fer System.outs i next().
 */

public class MonopolyText {
    List<Jugador> a_jug; ///< Llista Jugadors ordenats amb la tirada de ordre
    GUI a_gui; ///< GUI que mostra graficament la partida
    Tauler a_tauler; ///< Tauler de la partida amb caselles determinades
    int a_jugadorActual; ///< Numero de Objecte a la llista de jugadors que es el seu torn actual
    Banca a_banca; ///< Banca que te totes les propietats no vengudes a jugadors
    Scanner a_entrada; ///< Objecte Scanner per entrades per teclat
    String a_modalitat; ///< Modalitat/Variació de joc que jugarem
    List<String> a_accionsNoAplicables; ///< Accions que podem fer durant la partida o accions valides
    double a_dinersInicials; ///< Diners inicials donats a tots els jugadors
    double a_recompensaSortidaM; ///< Recompensa Monetaria si no es pot tenir recompensa monetaria, sera 0
    boolean a_recompensaSortidaT; ///< Recompensa Terreny true si es pot false si no es pot
    boolean a_configCorrecte; ///< Llegueix totes les configuracions i determina si es pot jugar la partida o no
    Impresora a_impresora; ///< Classe per mostrar per pantalla globalment a traves de totes les classes

    /**
     * @pre True
     * @post Crea un objecte MonopolyText inicialitzant jugadoractual, entrada per teclat, accionsNoAplicables,
     * configuracio correcte per comprovar arxius de configuracio i objecte impresora per treure per pantalla.
     */
    MonopolyText(){
        a_jugadorActual = 0;
        a_entrada = new Scanner(System.in);
        a_accionsNoAplicables = new Vector<String>();
        a_configCorrecte = true;
        a_impresora = new Impresora();
        a_banca = new Banca();
    }

    /**
     * @pre True
     * @post Crea els jugadors, llegueix fitxers de normes de partida i normes de tauler, comprova que la configuracio
     * es valida/correcte.
     */
    public void crearPartida(){
        a_jug = entrarJugadors();
        a_gui = null;
        lleguirFitxerNormesPartida();
        lleguirFitxerNormesTauler();


        ingressarDinersInicials();
        if(a_configCorrecte){
            iniciarPartida();
        } else {
            a_impresora.imprimir("ERROR: Configuracio de la partida incorrecte");
            a_impresora.imprimir("ERROR: Revisa els fitxers per possibles discrepancies!");
        }
    }

    /**
     * @pre True
     * @post Ingressa a tots els jugadors els diners inicials amb els quals començar la partida.
     */
    public void ingressarDinersInicials(){
        for (Jugador j: a_jug) {
            j.ingressarDiners(a_dinersInicials);
        }
    }

    /**
     * @pre True
     * @post Inicia la partida amb la primera ronda amb el primer torn del primer jugador, segueix jugant torns fins
     * final de la partida (nomes quedi un jugador).
     */
    public void iniciarPartida(){
        Jugador jugAct;
        while(!finalPartida()){
            jugAct = jugActual();
            pagarPrestecs(jugAct);
            a_impresora.imprimir("Comença el torn de " + jugAct.nom() + " tens " + jugAct.diners());
            if(jugAct.preso()){ //Si esta a preso, donar oportunitat de sortir de preso
                if(jugAct.tornsPreso()==3){
                    jugAct.resetPreso();
                    jugAct.sortirPreso();
                } else {
                    preTornPreso(jugAct);
                }
            }
            if(!jugAct.preso()) { //Si ha pogut sortir de preso
                iniciarTorn(jugAct);
            } else {
                jugAct.passarTornPreso();
            }
            jugAct.passarTornPrestecs();
            a_impresora.imprimir("Acaba el torn de " + jugAct.nom());
            finalitzarTorn();
        }
        a_impresora.imprimir("El guanyador de la partida es: " + a_jug.get(0).nom());
    }

    /**
     * @pre True
     * @post Finalitza el torn del jugador actual, actualitzant a_jugadorActual
     */
    public void finalitzarTorn(){
        if(a_jugadorActual==a_jug.size()-1){
            a_jugadorActual = 0;
        } else {
            a_jugadorActual++;
        }
    }

    /**
     * @pre True
     * @post S'intenta pagar tots els prestecs del jugador j que caduquen aquest torn, si no es poden pagar tots els que
     * caduquen aquest torn es desqualifica el jugador.
     */
    public void pagarPrestecs(Jugador j){
        Prestec p;
        boolean pagat;
        if(j.tocaPagarPrestec()){
            a_impresora.imprimir("Toca pagar prestecs:");
            while(j.tocaPagarPrestec()){
                a_impresora.imprimir("Has de pagar el seguent prestec");
                p = j.primerPrestec();
                a_impresora.imprimir(p.toString(0));
                pagat = j.pagarPrestec();
                if(pagat){
                    a_impresora.imprimir("S'ha pagat pogut pagar el prestec.");
                } else {
                    a_impresora.imprimir("No s'ha pogut pagar el prestec.");
                    desqualificarJugador(j);
                }

            }
        }
    }

    /**
     * @pre True
     * @post Retorna cert si la partida ha finalitzat, false en cualsevol altre cas.
     */
    public boolean finalPartida(){
        return a_jug.size()==1;
    }

    /**
     * @pre True
     * @post Pregunta el jugador si te una tarjeta de sortir de preso i si la vol fer servir per sortir de la preso, en
     * cas de que digui que si: surt de preso i en cas de que digui que no: es manté a la preso.
     */
    public void preTornPreso(Jugador j){
        List<String> s = new Vector<String>();
        String resposta;
        a_impresora.imprimir("Actualment et trobes a la preso");
        if(j.tarjetesSortirPreso()){
            a_impresora.imprimir("Sembla que tens una/unes tarjetes per Sortir de la Preso, la vols utilitzar? (SI/NO)");
            s.add("SI");
            s.add("NO");
            resposta = j.entrarString(true,s);
            if(resposta.equals("SI")){
                a_impresora.imprimir("Has Sortit de la preso!");
                j.treureTarjetaPreso();
                iniciarTorn(j);
            }
        }
    }

    /**
     * @pre True
     * @post Inicia el torn del jugador j amb una tirada de daus, calculant a quina casella cau i quines accions pot
     * fer, tinguent en compte a la casella que es troba i els seus recursos.
     */
    public void iniciarTorn(Jugador j){
        int[] tirada;
        int totalTirada;
        int numTirades = 0;
        int cas;
        Casella c;
        boolean tirarMes = true;
        while(tirarMes){
            a_impresora.imprimir(j.nom() + " et toca tirar daus:");
            tirada = j.tirarDaus();
            totalTirada = tirada[0]+tirada[1];
            a_impresora.imprimir("La teva tirada es la seguent: " + tirada[0] + " + " + tirada[1] + " = " + totalTirada);
            cas = a_tauler.moure(j,totalTirada);
            c = a_tauler.casella(cas);
            a_impresora.imprimir("Has caigut a la casella " + cas + " es una " + c.toString());
            opcionsObligatoria(j,c);
            opcionsOpcionals(j,c);
            if(tirada[0]==tirada[1] && numTirades<3){
                a_impresora.imprimir("Has tret dobles, torna tirar!");
                numTirades++;
            } else {
                tirarMes = false;
                if(numTirades == 3){
                    a_impresora.imprimir("Has tret dobles tres cops seguits, vas a preso!");
                    AccPreso a = new AccPreso(a_impresora);
                    a.executa(j,c,a_jug,a_banca,a_tauler);
                }
            }
        }
    }

    /**
     * @pre True
     * @post Mostra per pantalla les accions que pot fer a traves de la casella en que es troba i pregunta quina vol fer.
     */
    void opcionsObligatoria(Jugador j, Casella c){
        Accio accio = c.opcions();
        a_impresora.imprimir("Accio obligatoria de casella:");
        if(accio != null){
            accio.executa(j,c,a_jug,a_banca,a_tauler);
        }
    }

    /**
     * @pre True
     * @post Mostra per pantalla les accions que pot fer el jugador de manera opcional i pregunta quina vol fer.
     */
    void opcionsOpcionals(Jugador j, Casella c){
        ArrayList<Accio> accions = j.opcions();
        a_impresora.imprimir("Accions opcionals de jugador:");
        int i = 0, opcio;
        if(accions != null){
            for(Accio a: accions){
                a_impresora.imprimir(i + ": " + a.paraulaClau());
                i++;
            }
            a_impresora.imprimir(i + ": Acabar torn");
            opcio = j.entrarInt(0,i,true,true);
            while(opcio != i && a_jug.contains(j)){
                Accio a = accions.get(opcio);
                a.executa(j,c,a_jug,a_banca,a_tauler);
                a_impresora.imprimir("Quina acció vols fer? (0-" + accions.size() + ")");
                opcio = j.entrarInt(0,i,true,true);
            }
        }

    }

    /**
     * @pre True
     * @post Desqualifica un jugador i el treu de la cua de jugadors. Mostra per pantalla que s'ha desqualificat.
     */
    public void desqualificarJugador(Jugador j){
        a_impresora.imprimir("S'ha desqualificat el jugador (" + j.nom() + ")");
        for (Terreny terreny : j.prop()) {
            a_banca.afegirTerreny(terreny);
            terreny.canviarPropietari(a_banca);
        }
        a_jug.remove(j);
    }

    /**
     * @pre True
     * @post Retorna el jugador actual d'aquest torn.
     */
    public Jugador jugActual(){
        return a_jug.get(a_jugadorActual);
    }

    /**
     * @pre True
     * @post Crea jugadors amb nom i defineix quin tipus son (HUMA o BOT), els afagueix a la partida.
     */
    public List<Jugador> entrarJugadors(){
        String nom;
        List<Jugador> j = new Vector<>();
        List<String> s = new Vector<>();
        String tipusJug;
        int jugTotal;
        s.add("BOT");
        s.add("HUMA");
        a_impresora.imprimir("Quants jugadors hi haura a la partida? (2-6)");
        jugTotal = entrarInt(2,6, true, true);
        for(int i = 1; i<jugTotal+1; i++){
            a_impresora.imprimir("Quin sera el nom del Jugador " + i + "?");
            nom = entrarString(false,null);
            a_impresora.imprimir("Sera jugador huma o un bot? (HUMA/BOT)");
            tipusJug = entrarString(true,s);
            if(tipusJug.equals("HUMA")){
                Jugador jug = new Huma(i-1,nom,a_impresora);
                j.add(jug);
                a_impresora.imprimir("El Jugador " + nom + " (HUMA) s'ha afegit a la partida");
            } else {
                Jugador jug = new Bot(i-1,nom,a_impresora);
                j.add(jug);
                a_impresora.imprimir("El Jugador " + nom + " (BOT) s'ha afegit a la partida");
            }
        }
        j = ordenarJugadors(j);
        return j;
    }

    /**
     * @pre True
     * @post Crea i configura les normes de tauler i el propi tauler donats pel fitxer de configuracio
     */
    public void lleguirFitxerNormesTauler(){
        String nomFitxer;
        Map<Jugador,Integer> mapJugadors = new LinkedHashMap<>();
        boolean casMalDefinides = false;
        List<Double> lloguer = new Vector<>();
        List<Integer> presons = new Vector<>();
        List<TarjetaSort> tarj = new Vector<>();
        List<Terreny> aux = new Vector<>();
        Map<Integer,Casella> cas = new LinkedHashMap<>();
        JSONParser jsonParser = new JSONParser();
        a_impresora.imprimir("Indica el nom del Arxiu de Normes de tauler: (Cal que indiquis la extensio, Exemple: prova.json)");
        a_impresora.imprimir("Recorda que el fitxer ha d'estar dipositat en la carpeta ./test/");
        nomFitxer = entrarString(false,null);
        nomFitxer = "./test/"+nomFitxer;
        File fitxer = new File(nomFitxer);
        while(!fitxer.exists()) {
            a_impresora.imprimir("ERROR: Sembla que el fitxer entrat: " + nomFitxer + " no existeix, torna a provarho.");
            nomFitxer = entrarString(false,null);
            nomFitxer = "./test/"+nomFitxer;
            fitxer = new File(nomFitxer);
        }
        try {
            int maxCasa;
            int countCaselles = 0;
            Reader r = new FileReader(nomFitxer);
            Object jsonObj = jsonParser.parse(r);
            JSONObject jObj = (JSONObject) jsonObj;
            Long caselles = (Long) jObj.get("nombreCaselles");
            if(a_recompensaSortidaM>0){
                cas.put(1,new Sortida(a_recompensaSortidaT,true,a_recompensaSortidaM,a_impresora));
            } else {
                cas.put(1,new Sortida(a_recompensaSortidaT,false,a_recompensaSortidaM,a_impresora));
            }
            JSONArray terreny = (JSONArray) jObj.get("casellesTerreny");
            JSONArray preso = (JSONArray) jObj.get("casellesPreso");
            JSONArray comanda = (JSONArray) jObj.get("casellesComandaDirecta");
            JSONArray aposta = (JSONArray) jObj.get("casellesAposta");
            JSONArray sort = (JSONArray) jObj.get("casellesSort");
            JSONArray tarjSort = (JSONArray) jObj.get("targetesSort");
            if(caselles == null){
                a_impresora.imprimir("Error: Numero de caselles no definit");
                a_configCorrecte = false;
            }
            if(terreny != null) {
                Iterator<Object> it = terreny.iterator();
                while(it.hasNext()) {
                    JSONObject terr = (JSONObject) jsonParser.parse(it.next().toString());
                    Long numCasella = (Long) terr.get("numCasella"); //No pot ser Null
                    String nom = (String) terr.get("nom"); //No pot ser Null
                    Long preu = (Long) terr.get("preu"); //No pot ser Null
                    //Long preuAgrupacio = (Long) jObj.get("preuHipoteca"); No fem servir grup de 2 persones
                    String agrupacio = (String) terr.get("agrupacio"); //Pot ser Null
                    if(agrupacio == null)
                        agrupacio = "no";
                    Long preuLloguerBasic = (Long) terr.get("preuLloguerBasic"); //Pot ser Null
                    Long preuLloguerAgrupacio = (Long) terr.get("preuLloguerAgrupacio"); //Pot ser Null
                    String construible = (String) terr.get("construible"); //Pot ser no
                    Long maxCases = (Long) terr.get("maxCases"); //Pot ser 0
                    Long preuCasa = (Long) terr.get("preuCasa"); //Pot ser null
                    Boolean hotel = (Boolean) terr.get("hotel"); //Pot ser false o true
                    Long preuHotel = (Long) terr.get("preuHotel"); //Pot ser null
                    JSONArray lloguerCases = (JSONArray) terr.get("lloguerAmbCases");
                    Long lloguerHotel = (Long) terr.get("lloguerAmbHotel");
                    lloguer.add(preuLloguerBasic.doubleValue());
                    maxCasa = maxCases.intValue();
                    if (lloguerCases != null) {
                        Iterator<Long> it2 = lloguerCases.iterator();
                        while (it2.hasNext()) {
                            lloguer.add(it2.next().doubleValue());
                        }
                    }
                    if (numCasella == null || nom == null || preu == null || preuLloguerBasic == null ||
                            maxCases == null || construible == null || hotel == null) {
                        casMalDefinides = true;
                    } else {
                        Casella c;
                        if (preuCasa != null) {
                            if (!hotel) {
                                c = new TerrenyCases(nom, preu, 0, agrupacio, lloguer, preuLloguerAgrupacio,
                                        a_banca, a_impresora, construible, maxCasa, preuCasa, hotel);
                            } else {
                                c = new TerrenyCasesIHotel(nom, preu, 0, agrupacio, lloguer,
                                        preuLloguerAgrupacio, a_banca, a_impresora, construible, maxCasa, preuCasa,
                                        hotel, preuHotel, lloguerHotel);
                            }
                        } else {
                            if(preuLloguerAgrupacio == null){
                                c = new Terreny(nom, preu, 0, agrupacio, lloguer, 0, a_banca,
                                        a_impresora);
                            } else {
                                c = new Terreny(nom, preu, 0, agrupacio, lloguer, preuLloguerAgrupacio, a_banca,
                                        a_impresora);
                            }
                        }
                        countCaselles++;
                        cas.put(numCasella.intValue(),c);
                        aux.add((Terreny) c);
                    }
                }
            } else {
                a_impresora.imprimir("ERROR: Caselles no definides");
                a_configCorrecte = false;
            }
            if(preso != null){
                Iterator<Object> it = preso.iterator();
                while(it.hasNext()) {
                    JSONObject terr = (JSONObject) jsonParser.parse(it.next().toString());
                    Long numCasella = (Long) terr.get("numCasella"); //No pot ser Null
                    String nom = (String) terr.get("nom"); //No pot ser Null
                    if(numCasella == null || nom == null){
                        casMalDefinides = true;
                    } else {
                        Casella c = new Preso(nom);
                        cas.put(numCasella.intValue(),c);
                        presons.add(numCasella.intValue());
                        countCaselles++;
                    }
                }
            } else {
                a_impresora.imprimir("ERROR: Presons no defindes");
                a_configCorrecte = false;
            }
            if(comanda != null){
                Iterator<Object> it = comanda.iterator();
                while(it.hasNext()) {
                    JSONObject terr = (JSONObject) jsonParser.parse(it.next().toString());
                    Long numCasella = (Long) terr.get("numCasella"); //No pot ser Null
                    String accio = (String) terr.get("accio"); //No pot ser Null
                    Long quantitat;
                    if(numCasella == null || accio == null){
                        casMalDefinides = true;
                    } else {
                        Accio a = null;
                        Casella c;
                        if(accio.equals("DONAR") || accio.equals("REBRE") || accio.equals("MULTA") ||
                                accio.equals("COBRAR") || accio.equals("PAGAR") || accio.equals("PRESO") ||
                                accio.equals("HIPOTECAR") || accio.equals("DISPENSAR")){
                            if(a_accionsNoAplicables.contains(accio)){
                                a = new AccDeshabilitada(a_impresora);
                            } else {
                                switch (accio) {
                                    case ("DONAR"):
                                        a = new AccDonar(a_impresora);
                                        break;
                                    case ("REBRE"):
                                        a = new AccRebre(a_impresora);
                                        break;
                                    case ("MULTA"):
                                        quantitat = (Long) terr.get("quantitat"); //Pot ser null
                                        a = new AccMulta(a_impresora, quantitat.doubleValue());
                                        break;
                                    case ("COBRAR"):
                                        quantitat = (Long) terr.get("quantitat"); //Pot ser null
                                        a = new AccCobrarPremi(a_impresora, quantitat.doubleValue(), false, true);
                                        break;
                                    case ("PAGAR"):
                                        quantitat = (Long) terr.get("quantitat"); //Pot ser null
                                        a = new AccPagar(a_impresora, quantitat.doubleValue());
                                        break;
                                    case ("PRESO"):
                                        a = new AccPreso(a_impresora);
                                        break;
                                    case ("HIPOTECAR"):
                                        a = new AccHipoteca(a_impresora);
                                        break;
                                    case ("DISPENSAR"):
                                        a = new AccDispensar(a_impresora);
                                        break;
                                }
                            }
                            c = new ComandaDirecta(a);
                            cas.put(numCasella.intValue(),c);
                            countCaselles++;
                        }
                    }
                }
            } else {
                a_impresora.imprimir("ERROR: Caselles de Comanda directe no definides");
                a_configCorrecte = false;
            }
            if(aposta != null){
                Iterator<Object> it = aposta.iterator();
                while(it.hasNext()) {
                    Long numCasella = (Long) it.next(); //No pot ser Null
                    Casella c = new Aposta(a_impresora);
                    cas.put(numCasella.intValue(),c);
                    countCaselles++;
                }
            } else {
                a_impresora.imprimir("ERROR: Caselles de Aposta no definides");
                a_configCorrecte = false;
            }
            if(sort != null){
                Iterator<Object> it = sort.iterator();
                while(it.hasNext()) {
                    Long numCasella = (Long) it.next(); //No pot ser Null
                    Casella c = new Sort(a_impresora);
                    cas.put(numCasella.intValue(),c);

                    countCaselles++;
                }
            } else {
                a_impresora.imprimir("ERROR: Caselles de Sort no definides");
                a_configCorrecte = false;
            }
            if(tarjSort != null){
                Iterator<Object> it = tarjSort.iterator();
                while(it.hasNext()) {
                    JSONObject terr = (JSONObject) jsonParser.parse(it.next().toString());
                    String accio = (String) terr.get("accio"); //No pot ser Null
                    Boolean posposable = (Boolean) terr.get("posposable"); //No pot ser Null
                    Long quantitat;
                    if(posposable == null || accio == null){
                        casMalDefinides = true;
                    } else {
                        Accio a = null;
                        TarjetaSort t;
                        if(accio.equals("DONAR") || accio.equals("ANAR") || accio.equals("REBRE") || accio.equals("MULTA") ||
                                accio.equals("COBRAR") || accio.equals("PAGAR") || accio.equals("PRESO") || accio.equals("SORTIR_PRESO") ||
                                accio.equals("HIPOTECAR") || accio.equals("DISPENSAR")){
                            switch(accio){
                                case("DONAR"):
                                    a = new AccDonar(a_impresora);
                                    break;
                                case("REBRE"):
                                    a = new AccRebre(a_impresora);
                                    break;
                                case("MULTA"):
                                    quantitat = (Long) terr.get("quantitat"); //Pot ser null
                                    a = new AccMulta(a_impresora, quantitat.doubleValue());
                                    break;
                                case("COBRAR"):
                                    quantitat = (Long) terr.get("quantitat"); //Pot ser null
                                    a = new AccCobrarPremi(a_impresora, quantitat.doubleValue(), false, true);
                                    break;
                                case("PAGAR"):
                                    quantitat = (Long) terr.get("quantitat"); //Pot ser null
                                    a = new AccPagar(a_impresora,quantitat.doubleValue());
                                    break;
                                case("PRESO"):
                                    a = new AccPreso(a_impresora);
                                    break;
                                case("ANAR"):
                                    quantitat = (Long) terr.get("numCasella"); //Pot ser null
                                    a = new AccAnarA(a_impresora,quantitat.intValue());
                                    break;
                                case("SORTIR_PRESO"):
                                    a = new AccSortirPreso(a_impresora);
                                    break;
                                case("HIPOTECAR"):
                                    a = new AccHipoteca(a_impresora);
                                    break;
                                case("DISPENSAR"):
                                    a = new AccDispensar(a_impresora);
                                    break;
                            }
                            t = new TarjetaSort(a,posposable);
                            tarj.add(t);
                        }
                    }
                }
            } else {
                a_impresora.imprimir("ERROR: Tarjetes de Sort no definides");
                a_configCorrecte = false;
            }
            r.close();
            for(Jugador j: a_jug){
                mapJugadors.put(j,1);
            }
            a_tauler = new Tauler(cas,presons,mapJugadors,tarj);
            a_banca.terrenys(aux);
            if(countCaselles>cas.size()+1){
                a_impresora.imprimir("ERROR: No s'han emplenat totes les caselles, revisa el fitxer");
                a_configCorrecte = false;
            }
            if(casMalDefinides){
                a_impresora.imprimir("ERROR: No s'han definit be totes els terrenys, revisa que tinguin tots els camps");
                a_configCorrecte = false;
            }
            if(!casMalDefinides && countCaselles<=cas.size()+1){
                a_impresora.imprimir("Lectura de fitxer: " + nomFitxer + " s'ha completat correctament!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @pre True
     * @post Crea i configura les normes de partida donats pel fitxer de configuracio
     */
    public void lleguirFitxerNormesPartida() {
        String nomFitxer;

        JSONParser jsonParser = new JSONParser();
        a_impresora.imprimir("Indica el nom del Arxiu de Normes de Partida: (Cal que indiquis la extensio, Exemple: prova.json)");
        a_impresora.imprimir("Recorda que el fitxer ha d'estar dipositat en la carpeta ./test/");
        nomFitxer = entrarString(false,null);
        nomFitxer = "./test/"+nomFitxer;
        File fitxer = new File(nomFitxer);
        a_accionsNoAplicables.add("HIPOTECAR"); //No implementada, grup de 2 persones
        a_accionsNoAplicables.add("DESHIPOTECAR"); //No implementada, grup de 2 persones
        a_accionsNoAplicables.add("INTERCANVIAR"); //No implementada, grup de 2 persones
        a_accionsNoAplicables.add("DISPENSAR"); //No implementada, grup de 2 persones
        a_accionsNoAplicables.add("RESTABLIR"); //No implementada, grup de 2 persones
        a_recompensaSortidaT = false; //Valors per defecte
        a_recompensaSortidaM = 0; //Valors per defecte
        while(!fitxer.exists()) {
            a_impresora.imprimir("Sembla que el fitxer entrat: " + nomFitxer + " no existeix, torna a provarho.");
            nomFitxer = entrarString(false,null);
            nomFitxer = "./test/"+nomFitxer;
            fitxer = new File(nomFitxer);
        }
        try {
            Reader r = new FileReader(nomFitxer);
            Object jsonObj = jsonParser.parse(r);
            JSONObject jObj = (JSONObject) jsonObj;
            String moda = (String) jObj.get("modalitat");
            JSONArray accio = (JSONArray) jObj.get("accionsNoAplicables");
            Long diners = (Long) jObj.get("dinersInicials");
            JSONArray recomp = (JSONArray) jObj.get("recompensesCasellaSortida");
            if(moda != null){
                if(!moda.equals("classica")){
                    a_impresora.imprimir("Modalitat: " + moda + " no implementada, implementant modalitat classica");
                    a_configCorrecte = false;
                } else {
                    a_modalitat = moda;
                }
            } else {
                a_impresora.imprimir("ERROR: Modalitat no definida");
                a_configCorrecte = false;
            }
            if(accio != null) {
                Iterator<String> it = accio.iterator();
                while (it.hasNext()) {
                    String opcio = it.next();
                    if(opcio.equals("COMPRAR") || opcio.equals("VENDRE") || opcio.equals("PRESTEC")) {
                        a_accionsNoAplicables.add(opcio);
                    }
                }

            } else {
                a_impresora.imprimir("ERROR: Accions no aplicables no definides");
                a_configCorrecte = false;
            }
            if(diners != null){
                a_dinersInicials = diners.floatValue();
            } else {
                a_impresora.imprimir("ERROR: Diners inicials no definits");
                a_configCorrecte = false;
            }
            if(recomp != null){
                Iterator it = recomp.iterator();
                while (it.hasNext()) {
                    Object t = it.next();
                    if(t instanceof String){
                        a_recompensaSortidaT = true;
                    } else if (t instanceof Long){
                        Long aux = (Long) t;
                        a_recompensaSortidaM = aux.floatValue();
                    }
                }
            } else {
                a_impresora.imprimir("ERROR: Recompenses no definides");
                a_configCorrecte = false;
            }
            r.close();
            if(a_configCorrecte){
                a_impresora.imprimir("Lectura de fitxer: " + nomFitxer + " s'ha completat correctament!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @pre jug.size()>=2
     * @post Ordena els jugadors per tirada inicial de dos daus de 6.
     */
    private List<Jugador> ordenarJugadors(List<Jugador> jug) {
        List<Integer> tirades = new Vector<Integer>();
        int[] tirada;
        int total;
        a_impresora.imprimir("Tirant daus per ordenar jugadors...");
        for (int i = 0; i < jug.size(); i++) {
            tirada = jug.get(i).tirarDaus();
            total = tirada[0] + tirada[1];
            tirades.add(total);
            a_impresora.imprimir(jug.get(i).nom() + " ha tret " + total);
        }

        Jugador temp;
        int temp2;
        int n = jug.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (tirades.get(j) < tirades.get(j + 1)) {
                    // swap arr[j+1] and arr[j]
                    temp2 = tirades.get(j);
                    tirades.set(j,tirades.get(j+1));
                    tirades.set(j+1,temp2);
                    temp = jug.get(j);
                    jug.set(j,jug.get(j+1));
                    jug.set(j+1,temp);
                }
            }
        }

        a_impresora.imprimir("Jugadors Ordenats per tirada:");
        for(int i = 0; i<jug.size(); i++){
            a_impresora.imprimir(i + ":" + jug.get(i).nom());
        }
        return jug;
    }

    /**
     * @pre True
     * @post Demana strings, fins que es trobi un string que conincideixi amb un string de dintre valids.
     */
    private String entrarString(boolean limitat, List<String> valids){
        String aux;
        aux = a_entrada.next();
        boolean acceptable = false;
        while(!acceptable){
            if(!limitat){ // Rangs desactivats cualsevol int es acceptable
                acceptable = true;
            } else {
                for(String s: valids){
                    if(aux.equals(s)){
                        acceptable = true;
                    }
                }
            }
            if(!acceptable) {
                a_impresora.imprimir("ERROR: String no valid! \n Opcions valides:");
                for(String s: valids){
                    a_impresora.imprimir(" - "+s);
                }
                System.out.print("STRING: ");
                aux = a_entrada.next();
            }
        }
        a_impresora.imprimirFitxerOnly(aux);
        return aux;
    }

    /**
     * @pre limMinim <= limMaxim
     * @post Demana ints fins que es entrat un que compleix amb el limit superior i inferior (si aquest son actius).
     */
    private int entrarInt(int limMinim, int limMaxim, boolean min, boolean max){
        int aux;
        String s;
        System.out.print("INT: ");
        aux = a_entrada.nextInt();
        boolean acceptable = false;
        while(!acceptable){
            if(!min && !max){ // Rangs desactivats cualsevol int es acceptable
                acceptable = true;
            } else {
                if (min && max) { // Ha de estar entre limMinim i limMaxim
                    acceptable = limMinim<=aux && limMaxim>=aux;
                } else if(!min && max){ // Nomes ens importa que sigui mes petit que limMaxim
                    acceptable = limMaxim>=aux;
                } else if(!max && min){ // Nomes ens importa que sigui mes gran que limMinim
                    acceptable = limMinim<=aux;
                }
            }
            if(!acceptable) {
                a_impresora.imprimir("ERROR: Integer no valid! \n Limits:");
                if(min){
                    a_impresora.imprimir("El limit inferior es: " + limMinim);
                } else {
                    a_impresora.imprimir("No hi ha limit inferior.");
                }
                if(max){
                    a_impresora.imprimir("El limit superior es: " + limMaxim);
                } else {
                    a_impresora.imprimir("No hi ha limit superior.");
                }
                System.out.print("INT: ");
                aux = a_entrada.nextInt();
            }
        }
        s = valueOf(aux);
        a_impresora.imprimirFitxerOnly(s);
        return aux;
    }
}
