package munchkin;

/**
 * Instancia un objeto de la clase Munchkin, un objeto de la clase VentanaPrincipal
 * y se encarga de iniciar el juego.
 * 
 * @author Grupo
 */
public class Main {
    public static void main(String[] args){
        Munchkin juego = Munchkin.obtenerInstancia();
        Vista v = new VentanaPrincipal(juego);
        v.mostrar(args);
    }    
}
