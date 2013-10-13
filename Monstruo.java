package munchkin;

/**
 * Clase Monstruo. Representa a un monstruo con sus pérdidas y ganancias de niveles y tesoros así como
 * el mal rollo con su texto descriptivo y la manera de aplicarlo.
 * 
 * @author Grupo
 */
public class Monstruo {
    private String nombre;
    private int nivel;
    private int gananciaTesoros;
    private int gananciaNiveles;
    private String malRollo;
    private int nivelesPerdidos;
    private int malRollov[]=new int[4];
    
    public Monstruo(String nom, int niv, int tg, int ng, String mr, int mr0, int mr1, int mr2, int mr3){
        nombre=nom;
        nivel=niv;
        gananciaTesoros=tg;
        gananciaNiveles=ng;
        malRollo=mr;
        malRollov[0]=mr0;
        malRollov[1]=mr1;
        malRollov[2]=mr2;
        malRollov[3]=mr3;
    }

    public int obtenerNivel(){
        return nivel;
    }

    public int obtenerGananciaTesoros(){
        return gananciaTesoros;
    }

    public int obtenerGananciaNiveles(){
        return gananciaNiveles;
    }

    public String obtenerMalRollo(){
        return malRollo;
    }
    
    public int[] obtenerMalRolloV(){
        return malRollov;
    }

    public String obtenerNombre(){
        return nombre;
    }

    public int obtenerNivelesPerdidos(){
        return nivelesPerdidos;
    }
}