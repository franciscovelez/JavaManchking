package munchkin;

/**
 * Clase tesoro con todos los atributos necesarios para describir uno con:
 * el nombre, tipo, bonus y su valor.
 * 
 * @author Grupo
 */
public class Tesoro {
    private String nombre;
    private int tipo;
    private int bonus;
    private int piezasOro;

    public static final int CASCO = 0;
    public static final int ARMADURA = 1;
    public static final int CALZADO = 2;
    public static final int UNA_MANO = 3;
    public static final int DOS_MANOS = 4;

    public Tesoro(String unNombre, int unTipo, int unosBonus, int unasPiezas){
        nombre=unNombre;
        tipo=unTipo;
        bonus=unosBonus;
        piezasOro=unasPiezas;
    }
    
    public String obtenerNombre(){
        return nombre;
    }

    public int obtenerBonus(){
        return bonus;
    }

    public int obtenerTipo(){
        return tipo;
    }

    public int obtenerPiezasOro(){
        return piezasOro;
    }
}