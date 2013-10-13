package munchkin;

import java.util.List;
import java.util.ArrayList;

/**
 * Clase Jugador. Representa la situación de cada jugador con los atributos necesarios
 * para definirlo y los tesoros que posee. Además, implementa la funcionalidad necesaria
 * para desarrollar el juego.
 * 
 * @author Grupo
 */
public class Jugador {
    private String nombre;
    private int nivel;
    private List<Tesoro> tesorosVisibles = new ArrayList();
    private List<Tesoro> tesorosOcultos = new ArrayList();

    public Jugador(String unNombre){
        nombre=unNombre;
        nivel=1;
    }

    //Modifica el nivel sumándole el valor pasado como parámetro. Si deseamos
    //decrementar el nivel pasamos un parámetro con valor negativo
    public void modificarNivel(int incDec){
        nivel=nivel+incDec;
        if(nivel<1)
            nivel=1;
    }

    //Devuelve el nivel de un jugador
    public int obtenerNivel(){
        return nivel;
    }

    //Devuelve el nombre de un jugador
    public String obtenerNombre(){
        return nombre;
    }
    
    //Para representar la acción de robar tesoro agregamos a la lista de tesoros
    //ocultos un tesoro pasado a este método como parámetro
    public void robarTesoro(Tesoro tesoro){
        tesorosOcultos.add(tesoro);
    }
    
    //Terminamos el turno de un jugador comprobando que sea posible según las
    //reglas del juego
    public boolean terminarTurno(){
        boolean fin=true;
        if(tesorosOcultos.size()>4)
            fin=false;
        
        return fin;
    }

    //Devuelve el nivel de combate de un jugador
    public int nivelDeCombate(){
        int bonus=0;
        if(!tesorosVisibles.isEmpty()){  
            for(Tesoro tmp: tesorosVisibles){
                bonus=bonus+tmp.obtenerBonus();
            }
        }
        return nivel+bonus;
    }
    
    //Determina si un tesoro puede equiparse devolviéndolo en un booleano
    public boolean puedoEquipar(Tesoro tesoro){
        int manos=0;
        boolean puedo=true;
       
        
        if(tesoro.obtenerTipo()==3 || tesoro.obtenerTipo()==4){
            for(Tesoro tmp: tesorosVisibles){
                if(tmp.obtenerTipo()==3)
                   manos++;
                if(tmp.obtenerTipo()==4)
                    puedo=false;
            }
            if((manos==1 && tesoro.obtenerTipo()==4) || (manos==2))
                puedo=false;
        }
        else
            for(Tesoro tmp: tesorosVisibles){
                if(tmp.obtenerTipo()==tesoro.obtenerTipo())
                   puedo=false;
            }        
        return puedo;        
    }
    
    //Agrega una lista de tesoros a los tesoros visibles de un jugador comprobando
    //si es posible y devolviendo aquellos que no ha sido posible agregar
    public List<Tesoro> equiparse(List<Tesoro> listaTesoros){
        List<Tesoro> tesorosDevueltos = new ArrayList();
        for(Tesoro tmp: listaTesoros){
            if(this.puedoEquipar(tmp)){
                tesorosOcultos.remove(tmp);
                tesorosVisibles.add(tmp);
            }
            else
                tesorosDevueltos.add(tmp);
        }
        return tesorosDevueltos;
    }
    
    //Elimina un tesoro pasado como parámetro de la lista de tesoros visibles
    public boolean eliminarTesoroVisible(Tesoro tesoro){
        if(tesoro!=null)
            return tesorosVisibles.remove(tesoro);
        else
            return false;
    }
    
    //Elimina un tesoro pasado como parámetro de la lista de tesoros ocultos
    public boolean eliminarTesoroOculto(Tesoro tesoro){
        if(tesoro!=null)
            return tesorosOcultos.remove(tesoro);
        else
            return false;
    }
    
    //Devuelve los tesoros visibles
    public List<Tesoro> obtenerTesorosVisibles(){
        return tesorosVisibles;
    }
    
    //Devuelve los tesoros ocultos
    public List<Tesoro> obtenerTesorosOcultos(){
        return tesorosOcultos;
    }
    
    //Acción de muerte de un jugador que pone el nivel del jugador a 1 y devuelve
    //una lista con todos los tesoros que poseía el jugador
    public List<Tesoro> muerte(){
        List<Tesoro> tmp = new ArrayList<Tesoro>(tesorosVisibles);
        tmp.addAll(tesorosOcultos);
        tesorosVisibles.clear();
        tesorosOcultos.clear();
        nivel = 1;
        return tmp;
    }
}