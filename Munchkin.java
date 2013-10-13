package munchkin;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
/**
 * Clase Munchkin. Es el núcleo de la funcionalidad del juego, con métodos que implementan
 * características propios de éste
 * 
 * @author Grupo
 */
public class Munchkin {
    private Jugador jugadorActivo;
    private Monstruo monstruoEnJuego;
    private int indexJugador;
    private List<Jugador>   jugadores           = new ArrayList();
    private Stack<Tesoro>   mazoTesoros         = new Stack();
    private Stack<Tesoro>   descarteDeTesoros   = new Stack();
    private Stack<Monstruo> mazoMonstruos       = new Stack();
    private Stack<Monstruo> descarteDeMonstruos = new Stack();
    
    //Variables para distinguir la manera de aplicar el mal rollo
    public static final int NIVELES_Y_TESOROS = 0;
    public static final int NIVELES_SEGUN_CARTAS_MANO = 1;
    public static final int TESOROS = 2;
    public static final int TESORO_CONCRETO = 3;   
    public static final int TESORO_MENOS_CARO = 0;
    public static final int TESORO_BONUS_MAS_ALTO = 1;
    public static final int UNA_MANO = 2;
    public static final int ARMADURA = 3;
    
    //Instanciación de la clase singleton
    private static final Munchkin instance = new Munchkin();
    private Munchkin(){ }
    public static Munchkin obtenerInstancia() {
        return instance;
    }

    //Llamamos a este método para comenzar el juego haciendo todas las
    //inicializaciones necesarias
    public void comenzarJuego(List<String> nombres) throws Exception{
        if(nombres.size()>1 && nombres.size()<=3){
            inicializarJuego();
            inicializarJugadores(nombres);
            barajar(mazoMonstruos);
            barajar(mazoTesoros);
            repartirCartas();
        }
        else{
            throw new Exception("Número de jugadores incorrecto");
        }

    }

    //Instanciamos objetos para definir cada una de las cartas que componen el juego
    //tanto para los tesoros como para los monstruos pasandole a sus respectivos
    //constructores la característica de cada carta
    private void inicializarJuego(){
        mazoTesoros.add(new Tesoro("Fez Alópodo", Tesoro.CASCO, 3, 700));
        mazoTesoros.add(new Tesoro("Necrocomicón", Tesoro.UNA_MANO, 1, 100));
        mazoTesoros.add(new Tesoro("Mazo de los Antiguos", Tesoro.UNA_MANO, 3, 200));
        mazoTesoros.add(new Tesoro("La Rebeca Metálica", Tesoro.ARMADURA, 2, 400));
        mazoTesoros.add(new Tesoro("Escopeta de Tres Cañones", Tesoro.DOS_MANOS, 4, 700));
        mazoTesoros.add(new Tesoro("Necrognomicón", Tesoro.UNA_MANO, 2, 200));
        mazoTesoros.add(new Tesoro("Necroplayboycón", Tesoro.UNA_MANO, 3, 300));
        mazoTesoros.add(new Tesoro("Hacha Prehistórica", Tesoro.UNA_MANO, 2, 500));
        mazoTesoros.add(new Tesoro("Porra Preternatural", Tesoro.UNA_MANO, 2, 200));
        mazoTesoros.add(new Tesoro("Linterna a Dos Manos", Tesoro.DOS_MANOS, 3, 400));
        mazoTesoros.add(new Tesoro("Insecticida", Tesoro.UNA_MANO, 2, 300));
        mazoTesoros.add(new Tesoro("Lanzallamas", Tesoro.DOS_MANOS, 4, 800));
        mazoTesoros.add(new Tesoro("Capucha de Cthulhu", Tesoro.CASCO, 3, 500));
        mazoTesoros.add(new Tesoro("Casco Minero", Tesoro.CASCO, 2, 400));
        mazoTesoros.add(new Tesoro("Garabato Místico", Tesoro.UNA_MANO, 2, 300));
        mazoTesoros.add(new Tesoro("Tentáculo de Pega", Tesoro.CASCO, 0, 200));
        mazoTesoros.add(new Tesoro("Camiseta de la Universidad de Miskatonic", Tesoro.ARMADURA, 1, 100));
        mazoTesoros.add(new Tesoro("Necronomicón", Tesoro.DOS_MANOS, 5, 800));
        mazoTesoros.add(new Tesoro("Zapatos Para Dejar Atrás a los Amigos", Tesoro.CALZADO, 0, 500));
        mazoTesoros.add(new Tesoro("El Aparato de Protección Eléctrica del Profesor Tesla", Tesoro.ARMADURA, 4, 900));
        mazoTesoros.add(new Tesoro("Shogulador", Tesoro.DOS_MANOS, 0, 600));
        mazoTesoros.add(new Tesoro("Botas de Sutil Investigación", Tesoro.CALZADO, 3, 600));
        mazoTesoros.add(new Tesoro("Sí, Aaaaamo", Tesoro.CASCO, 4, 0));
        mazoTesoros.add(new Tesoro("Clavo de Rail Ferroviario", Tesoro.UNA_MANO, 3, 400));
        mazoTesoros.add(new Tesoro("Necrotelecom", Tesoro.CASCO, 2, 300));
        mazoTesoros.add(new Tesoro("Poncho a Prueba de Babas", Tesoro.ARMADURA, 2, 400));
        mazoTesoros.add(new Tesoro("Ametralladora Thompson", Tesoro.DOS_MANOS, 4, 600));
        mazoTesoros.add(new Tesoro("Arcana Varita de Atizamiento", Tesoro.UNA_MANO, 3, 400));
        mazoTesoros.add(new Tesoro("Gaita", Tesoro.DOS_MANOS, 4, 500));
        mazoTesoros.add(new Tesoro("El Cuchillo de Sushi Arcano", Tesoro.UNA_MANO, 2, 300));
        mazoTesoros.add(new Tesoro("Botas de Lluvia Ácida", Tesoro.CALZADO, 1, 300));

        mazoMonstruos.add(new Monstruo("Bichoggoth", 1, 1, 1,"Sientes Bichos bajo la ropa. Descarta tu armadura.", TESORO_CONCRETO, ARMADURA, -2, -2));
        mazoMonstruos.add(new Monstruo("El Mal Indecible e Impronunciable", 10, 3, 1, "Pierdes una mano.", TESORO_CONCRETO, UNA_MANO, -2, -2));
        mazoMonstruos.add(new Monstruo("El Sopor de Dunwich", 1, 1, 1, "¡El primordial bostezo contagioso! Te tragas tu objeto menos caro (los objetos sin valor no cuentan). Descártalo.", TESORO_CONCRETO, TESORO_MENOS_CARO, -2, -2));
        mazoMonstruos.add(new Monstruo("Chibithulhu", 2, 1, 1, "Te quedas embobado con el lindo primigenio y le das el objeto que te proporciona el bonus más alto.", TESORO_CONCRETO, TESORO_BONUS_MAS_ALTO, -2, -2));
        mazoMonstruos.add(new Monstruo("H.P. Munchcraft", 6, 2, 1, "Pierdes la armadura.", TESORO_CONCRETO, ARMADURA, -2, -2));
        mazoMonstruos.add(new Monstruo("El Rey de Rosa", 16, 4, 2, "Pierdes un nivel por cada carta que tengas en la mano.", NIVELES_SEGUN_CARTAS_MANO, -2, -2, -2));
        mazoMonstruos.add(new Monstruo("Pollipólipo Volante", 2, 1, 1, "Da mucho asquito... Pierdes un nivel.", NIVELES_Y_TESOROS, 1, 0, 0));
        mazoMonstruos.add(new Monstruo("Yskhthyssg-goth", 12, 3, 1, "No le hace gracia que pronuncien mal su nombre. Estás muerto.", NIVELES_Y_TESOROS, -1, -1, -1));
        mazoMonstruos.add(new Monstruo("La que Redacta en las Tinieblas", 2, 1, 1, "Toses los pulmones y pierdes un Nivel.", NIVELES_Y_TESOROS, 1, 0, 0));
        mazoMonstruos.add(new Monstruo("Roboggoth", 8, 2, 1, "La quinta directiva primaria de Roboggoth te obliga a perder dos niveles y descartarte una carta de tu mano.", NIVELES_Y_TESOROS, 2, 0, 1));
        mazoMonstruos.add(new Monstruo("Los Hondos", 8, 2, 1, "A diferencia de sus primos, Los Profundos, que siempre están hablando con gravedad de temas serios, estos monstruos resultan bastante superficiales, y te aburren mortalmente, en el sentido más literal de la palabra. Estás muerto.", NIVELES_Y_TESOROS, -1, -1, -1));
        mazoMonstruos.add(new Monstruo("El Gorrón en el Umbral", 10, 3, 1, "Pierdes tus tesoros visibles", NIVELES_Y_TESOROS, 0, -1, 0));
        mazoMonstruos.add(new Monstruo("Ángeles Descarnados de la Noche Ibicenca", 14, 4, 1, "Te atrapan para llevarte de fiesta y te dejan caer en mitad del vuelo. Después, vuelven a hacerlo, porque es muy divertido. Descarta 3 objetos.", TESOROS, 3, -2, -2));
        mazoMonstruos.add(new Monstruo("Shoggoth", 16, 4, 2, "Pierdes dos niveles.", NIVELES_Y_TESOROS, 2, 0, 0));
        mazoMonstruos.add(new Monstruo("Lolitaggoth", 1, 1, 1, "¡La!¡La!¡Pintalabios negros! Pierdes un nivel.", NIVELES_Y_TESOROS, 1, 0, 0));
        mazoMonstruos.add(new Monstruo("Semillas Frikonas de Cthulhu", 4, 2, 1, "Pierdes un nivel.", NIVELES_Y_TESOROS, 1, 0, 0));
        mazoMonstruos.add(new Monstruo("Tres Byakhees Vienen de Bonanza", 8, 2, 1, "Pierdes un tesoro visible y otro oculto.", NIVELES_Y_TESOROS, 0, 1, 1));
    }

    //Llamamos a este método pasándole una lista con los nombres de los jugadores
    //para guardarlos en el atributo jugadores
    private void inicializarJugadores(List<String> nombres){
        if(nombres!=null){
            for(String nombre: nombres){
                Jugador jugador=new Jugador(nombre);
                jugadores.add(jugador);
            }
        }
    }

    //Reparto inicial de cartas. A cada jugador le repartimos dos cartas del
    //mazo de los tesoros
    private void repartirCartas(){
        for(Jugador jugador: jugadores){
            for(int i=0; i<2; i++){
                Tesoro tesoro=primerTesoro();
                jugador.robarTesoro(tesoro);
            }
        }
    }
    
    //Llamamos a este método para asignar el siguiente turno o el primero en caso de
    //que estemos inicializando el juego.
    public void siguienteTurno() throws Exception{
        boolean fin;
        if(jugadorActivo!=null){
            fin=jugadorActivo.terminarTurno();
            if(!fin)
                throw new Exception("el jugador tiene más de 4 tesoros ocultos, no se puede pasar al siguiente turno");
            
            siguiente();
        }
        else{
            primero();            
        }
        jugadorActivo=jugadores.get(indexJugador);
        if(monstruoEnJuego!=null)
            descarteDeMonstruos.add(monstruoEnJuego);
        monstruoEnJuego=primerMonstruo();
    }
    
    //Métodos para barajar los mazos de cartas para evitar que siempre aparezcan
    //en el mismo orden
    private void barajar(Stack cartas){
        Collections.shuffle(cartas);
    }
    private void barajar(List cartas){
        Collections.shuffle(cartas);
    }
    
    //Este método sólo es usado para decidir qué jugador tiene el primer turno
    //del juego
    private void primero(){
        Random rand = new Random();
        indexJugador = rand.nextInt(jugadores.size()-1);
        jugadorActivo=jugadores.get(indexJugador);
    }
    
    //Cambiamos el índice del jugador activo para el siguiente turno
    private void siguiente(){
        indexJugador=(indexJugador+1)%jugadores.size();
    }
    
    //Obtenemos la carta superior del mazo de monstruos, asignándole el descarte
    //de monstruos barajado en caso de que no queden más cartas en mazoMonstruos
    private Monstruo primerMonstruo(){
        if(mazoMonstruos.empty()){
            barajar(descarteDeMonstruos);
            mazoMonstruos.addAll(descarteDeMonstruos);
            descarteDeMonstruos.clear();           
        }
        return mazoMonstruos.pop();            
    }

    //Obtenemos la carta superior del mazo de tesoros, asignándole el descarte
    //de tesoros barajado en caso de que no queden más cartas en mazoTesoros
    public Tesoro primerTesoro(){
        if(mazoTesoros.empty()){
            barajar(descarteDeTesoros);
            mazoTesoros.addAll(descarteDeTesoros);
            descarteDeTesoros.clear();
        }
        return mazoTesoros.pop();
    }
    
    //Devuelve el monstruo en juego
    public Monstruo getMonstruoEnJuego(){
        return monstruoEnJuego;
    }
    
    //Devuelve el jugador activo
    public Jugador getJugadorActivo(){
        return jugadorActivo;
    }
    
    //Devuelve la lista de jugadores
    public List<Jugador> getListaJugadores(){
        return jugadores;
    }
    
    //Método encargado de desarrollar el combate, en caso de ganar el jugador devuelve
    //10 en caso de victoria o bien el número de tesoros ganados
    //En caso de ser el monstuo igual o superior en nivel, se simula que se tira
    //un dado para decidir si el jugador consigue huir o no. El valor de retorno es
    //0 si lo ha conseguido o bien -1 en caso contrario
    public int desarrollarCombate(){
        int tesorosGanados, resultado, dado;
        if(monstruoEnJuego.obtenerNivel()<jugadorActivo.nivelDeCombate()){
            tesorosGanados=monstruoEnJuego.obtenerGananciaTesoros();
            jugadorActivo.modificarNivel(monstruoEnJuego.obtenerGananciaNiveles());
            if(jugadorActivo.obtenerNivel()>=10)
                resultado = 10;
            else
                resultado = tesorosGanados;
        }else{
            Random rand = new Random();
            dado = rand.nextInt(6)+1;
            if(dado<5)
                resultado = -1;
            else
                resultado = 0;
        }
        return resultado;
    }
    
    //Método para aplicarle al jugador activo el mal rollo del monstruo en juego
    //El mal rollo está codificado en monstruo y aplicado en este método según
    //el siguiente criterio:
    //  malRollo[0] contiene el 'tipo' de mal rollo que puede ser:
    //  public static final int NIVELES_Y_TESOROS = 0;  // caso más comun, se pierden niveles y/o tesoros visibles y/o tesoros ocultos
    //  public static final int NIVELES_SEGUN_CARTAS_MANO = 1; // se pierden tantos niveles como cartas se tengan en la mano
    //  public static final int TESOROS = 2;   // se pierden tesoros sin indicar si son ocultos o visibles
    //  public static final int TESORO_CONCRETO = 3; // se pierde un tesoro concreto
    //
    //Si malRollo[0] == NIVELES Y TESOROS
    //    malRollo[1] contiene el número de niveles que se pierden (-1 si se esta muerto)
    //    malRollo[2] contiene el número de tesoros visibles que se pierden (-1 si todos)
    //    malRollo[3] contiene el número de tesoros ocultos que se pierden (-1 si todos)
    //
    //Si malRollo[0] == NIVELES_SEGUN_CARTAS_MANO
    //    las posiciones 1, 2, y 3 carecen de sentido.
    //
    //Si malRollo[0] == TESOROS
    //    malRollo[1] contiene el número de tesoros que se pierden, sin distinguir si son ocultos o visibles, el jugador decide de cuales se descarta
    //    las posiciones 2 y 3 carecen de sentido
    //
    //Si malRollo[0] == TESORO_CONCRETO
    //    malRollo[1] contiene el tesoro concreto a descartarse, y puede ser
    //
    //  public static final int TESORO_MENOS_CARO = 0;
    //  public static final int TESORO_BONUS_MAS_ALTO = 1;
    //  public static final int UNA_MANO = 2;
    //  public static final int ARMADURA = 3;
    public void aplicarMalRollo(){
        int mr[] = monstruoEnJuego.obtenerMalRolloV();
        switch(mr[0]){
            case NIVELES_Y_TESOROS: 
                if(mr[1]==-1)
                    muerteJugador();
                else{
                    if(mr[1]!=0)
                        jugadorActivo.modificarNivel(-mr[1]);
                    if(mr[2]==-1){
                        List<Tesoro> aux = new ArrayList<Tesoro>(jugadorActivo.obtenerTesorosVisibles());
                        descartarTesorosVisibles(aux);
                    }else{
                        List<Tesoro> aux = new ArrayList<Tesoro>(jugadorActivo.obtenerTesorosVisibles());
                        barajar(aux);
                        while(aux.size()>mr[2]){
                            aux.remove(0);
                        }
                        descartarTesorosVisibles(aux);
                    }
                    if(mr[3]==-1)
                        descartarTesorosOcultos(jugadorActivo.obtenerTesorosOcultos());
                    else{
                        List<Tesoro> aux = new ArrayList<Tesoro>(jugadorActivo.obtenerTesorosOcultos());
                        barajar(aux);
                        while(aux.size()>mr[3]){
                            aux.remove(0);
                        }
                        descartarTesorosOcultos(aux);     
                    }
                }
                break;
            case NIVELES_SEGUN_CARTAS_MANO:
                jugadorActivo.modificarNivel(-(jugadorActivo.obtenerTesorosOcultos()).size());
                break;
            case TESOROS:                
                List<Tesoro> aux = new ArrayList<Tesoro>(jugadorActivo.obtenerTesorosVisibles());
                aux.addAll(jugadorActivo.obtenerTesorosOcultos());
                barajar(aux);
                while(aux.size()>mr[1])
                    aux.remove(0);
                descartarTesorosOcultos(aux);
                descartarTesorosVisibles(aux);               
                
                break;
            case TESORO_CONCRETO:
                List<Tesoro> tes = new ArrayList<Tesoro>(jugadorActivo.obtenerTesorosVisibles());
                Tesoro tAux = null;
                if(mr[1]==TESORO_MENOS_CARO)
                    tes.addAll(jugadorActivo.obtenerTesorosOcultos());
                
                if(!tes.isEmpty()){
                    if(mr[1]==TESORO_MENOS_CARO || mr[1]==TESORO_BONUS_MAS_ALTO)
                        tAux = tes.get(0);
                    for(Tesoro tmp : tes){
                        switch(mr[1]){
                            case TESORO_MENOS_CARO:
                                if(tAux.obtenerPiezasOro()>tmp.obtenerPiezasOro())
                                    tAux = tmp;                                    
                                break;
                            case TESORO_BONUS_MAS_ALTO:
                                if(tAux.obtenerBonus()<tmp.obtenerBonus())
                                    tAux = tmp;
                                break;
                            case UNA_MANO:                                
                                if(tmp.obtenerTipo()==Tesoro.UNA_MANO || tmp.obtenerTipo()==Tesoro.DOS_MANOS)
                                    tAux = tmp;
                                break;
                            case ARMADURA:                                
                                if(tmp.obtenerTipo()==Tesoro.ARMADURA)
                                    tAux = tmp;
                                break;
                        }
                    }
                    if(tAux!=null){
                        tes.clear();
                        tes.add(tAux);
                        descartarTesorosVisibles(tes);
                        if(mr[1]==TESORO_MENOS_CARO)
                            descartarTesorosOcultos(tes);
                    }
                }    
        }
    }
    
    //Método para llevar a cabo la acción de muerte de un jugador. Llamamos al método
    //muerte() del jugador activo y le repartimos dos cartas de tesoros.
    public void muerteJugador(){
        List<Tesoro> tesorosPerdidos = new ArrayList<Tesoro>(jugadorActivo.muerte());
        descarteDeTesoros.addAll(tesorosPerdidos);
        for(int i=0; i<2; i++){
            Tesoro tesoro=primerTesoro();
            jugadorActivo.robarTesoro(tesoro);
        }
    }
    
    //Método para comprar niveles descartándose tesoros según las reglas del juego
    //(total_piezas_oro/1000)niveles
    public void comprarNivelesJugador(List<Tesoro> tesoro){
        int total=0;
        if(tesoro!=null){
            for(Tesoro tes : tesoro)
                total = total + tes.obtenerPiezasOro();

            jugadorActivo.modificarNivel(total/1000);
            descartarTesorosOcultos(tesoro);
            descartarTesorosVisibles(tesoro);
        }
    }

    //Método para descartar tesoros ocultos del jugador activo pasados en una
    //lista como parámetro
    public void descartarTesorosOcultos(List<Tesoro> listaTesoros){
        if(listaTesoros!=null){
            for(Tesoro tmp: listaTesoros){
                boolean eliminado=jugadorActivo.eliminarTesoroOculto(tmp);
                if(eliminado)
                    descarteDeTesoros.add(tmp);
            }
        }        
    }
    
    //Método para descartar tesoros visibles del jugador activo pasados en una
    //lista como parámetro
    public void descartarTesorosVisibles(List<Tesoro> listaTesoros){
        if(listaTesoros!=null){
            for(Tesoro tmp: listaTesoros){
                boolean eliminado=jugadorActivo.eliminarTesoroVisible(tmp);
                if(eliminado)
                    descarteDeTesoros.add(tmp);
            }
        }
    }
    
    //Acción de equiparse del jugador moviendo tesoros de la lista de tesoros ocultos
    //a la lista de tesoros visibles. Esta acción se realiza llamando al método
    //equiparse() de la clase jugador que se encarga, a su vez, de comprobar que
    //es posible equiparse los tesoros. Este método devuelve una lista con los tesoros
    //que no ha sido posible equiparse
    public List<Tesoro> equiparseJugador(List<Tesoro> listaTesoros){
        List<Tesoro> tesorosDevueltos = new ArrayList<Tesoro>(jugadorActivo.equiparse(listaTesoros));
        return tesorosDevueltos;
    }
}