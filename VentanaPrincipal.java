package munchkin;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 * Clase VentanaPrincipal implementa la interfaz Vista y contiene todo el diseño
 * y funcionalidad de la representación gráfica del juego.
 * 
 * @author Grupo
 */
public class VentanaPrincipal extends javax.swing.JFrame implements Vista{
    //Variables de control
    private boolean combatido;
    private boolean bicho;
    private boolean muerto;
    private int resultadoCombate;
    
    //Variables para almacenar, procesar y mostrar el estado del juego
    private Dialogo dialogo;
    private Munchkin juego;
    private Jugador jugadorActivo;
    private List<String> nombresJugadores=new ArrayList();
    private HashMap<String, String> avatar=new HashMap();
    private HashMap<String, Integer> muertes=new HashMap();
    private Monstruo monstruoEnJuego;
    private List<Tesoro> tesorosOcultosSeleccionados = new ArrayList();
    private List<Tesoro> tesorosVisiblesSeleccionados = new ArrayList();    
    private List<TesoroGrafico> tesorosVisiblesAlimpiar = new ArrayList();
    private List<TesoroGrafico> tesorosOcultosAlimpiar = new ArrayList();
    
    //Guardamos en vectores las coordenadas en las que se van a colocar las cartas
    //de los tesoros ocultos y de los visibles
    private static final int posXMochila[] = {2, 74, 146, 218, 290, 362, 434, 506};
    private static final int posYMochila = 30;
    private static final int posXJugador[] = {150,30,150,270,150};
    private static final int posYJugador[] = {30,160,160,160,290};
    
    //Clase Tesoro Gráfico para definir el diseño y contenido de la representación
    //gráfica de un tesoro.
    private class TesoroGrafico extends JPanel {
        protected Tesoro tesoro;
        protected javax.swing.JTextArea Nombre = new javax.swing.JTextArea();
        protected javax.swing.JLabel Bonus     = new javax.swing.JLabel();
        protected javax.swing.JLabel Oro       = new javax.swing.JLabel();
        protected javax.swing.JLabel Tipo      = new javax.swing.JLabel();                
        protected javax.swing.JLabel imgTesoro = new javax.swing.JLabel();     

        //Constructor con un tesoro como parámetro se encarga de inicializar y 
        //configurar todos los elementos de la representación gráfica
        
        TesoroGrafico(Tesoro unTesoro) {
            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
            Integer tmp;
            String txt = null;
            tesoro = unTesoro;
            this.setPreferredSize(new java.awt.Dimension(70, 130));
            this.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
            imgTesoro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/"+tesoro.obtenerNombre()+".gif"))); // NOI18N
            Nombre.setText(tesoro.obtenerNombre());
            Nombre.setLineWrap(true);
            Nombre.setWrapStyleWord(true);
            Nombre.setMargin(new java.awt.Insets(2, 2, 2, 2));
            Nombre.setOpaque(false);
            Nombre.setFont(new java.awt.Font("Tahoma", 0, 9));
            Nombre.setEditable(false);
            Nombre.setEnabled(false);
            Nombre.setDisabledTextColor(Color.black);
            
            tmp = new Integer(tesoro.obtenerBonus());
            Bonus.setText("+"+tmp.toString());
            Bonus.setFont(new java.awt.Font("Arial", 3, 12));
            Bonus.setBackground(new java.awt.Color(240, 240, 240));
            Bonus.setBorder(null);
            Bonus.setForeground(Color.blue);
            
            tmp = new Integer(tesoro.obtenerPiezasOro());
            Oro.setText(tmp.toString()+"€");
            Oro.setFont(new java.awt.Font("Arial", 3, 10));
            Oro.setBackground(new java.awt.Color(240, 240, 240));
            Oro.setBorder(null);
            Oro.setBackground(new java.awt.Color(240, 240, 240));
            Oro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);     
            
            switch(tesoro.obtenerTipo()){
                case 0: txt="casco";      break;
                case 1: txt="armadura";   break;
                case 2: txt="calzado";    break;
                case 3: txt="1 mano";     break;
                case 4: txt="2 manos";    break;
            }
            Tipo.setText(txt);
            Tipo.setFont(new java.awt.Font("Arial", 3, 11));
            Tipo.setFocusable(false);
            Tipo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            
            this.add(Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints( 0,   0, 70, 50),0);
            this.add(Bonus,  new org.netbeans.lib.awtextra.AbsoluteConstraints( 2, 100, 20, 20),1);
            this.add(Oro,    new org.netbeans.lib.awtextra.AbsoluteConstraints( 36,106, 30, 10),2);
            this.add(Tipo,   new org.netbeans.lib.awtextra.AbsoluteConstraints( 3, 117, 63, 12),3);
            this.add(imgTesoro, new org.netbeans.lib.awtextra.AbsoluteConstraints(2,15,70,117),4);   
            
            this.setBackground (new java.awt.Color(255,255,255));
            this.setOpaque(true);
        }
    }
    
    //Clase TesoroGraficoVisible para concretar la representación de los tesoros visibles
    private class TesoroGraficoVisible extends TesoroGrafico {
        public void seleccionarTesoro(){
            if(tesorosVisiblesSeleccionados.contains(TesoroGraficoVisible.this.tesoro)){
                tesorosVisiblesSeleccionados.remove(TesoroGraficoVisible.this.tesoro);
                TesoroGraficoVisible.this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, false));
            }else{
                tesorosVisiblesSeleccionados.add(TesoroGraficoVisible.this.tesoro);
                TesoroGraficoVisible.this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(244, 0, 0), 2, false));
            } 
        }
        TesoroGraficoVisible (Tesoro unTesoro) {
           super(unTesoro);
           this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
              addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt){
                    seleccionarTesoro();
                }
            });
            for(int i=0; i<this.getComponentCount(); i++){
                this.getComponent(i).addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {seleccionarTesoro(); } 
                });
            } 
        }
    }
    
    //Clase TesoroGraficoOculto para concretar la representación de los tesoros ocultos
    private class TesoroGraficoOculto extends TesoroGrafico {
        public void seleccionarTesoro(){
            if(tesorosOcultosSeleccionados.contains(TesoroGraficoOculto.this.tesoro)){
                tesorosOcultosSeleccionados.remove(TesoroGraficoOculto.this.tesoro);
                TesoroGraficoOculto.this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, false));
            }
            else{
                tesorosOcultosSeleccionados.add(TesoroGraficoOculto.this.tesoro);
                TesoroGraficoOculto.this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 244, 0), 2, false));
            }        
        }
        TesoroGraficoOculto (Tesoro unTesoro) {
           super(unTesoro);
           this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
              addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt){
                    seleccionarTesoro();
                }
            });
            for(int i=0; i<this.getComponentCount(); i++){
                this.getComponent(i).addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {seleccionarTesoro(); } 
                });
            } 
        }
    }
    
    //Constructor al que se le pasa la instancia singleton de la clase Munchkin.
    //Se encarga de llamar al método inicializador de los elementos generados
    //por el diseño gráfico de la interfaz
    public VentanaPrincipal(Munchkin unJuego) {
        juego=unJuego;
        initComponents();
    }
    
    //Método para actualizar el marcador que muestra el resumen de progreso de
    //todos los jugadores
    private void actualizarMarcador(){
        Integer tmp;
        List<Tesoro> tes;
        List<Jugador> jugadores = juego.getListaJugadores();
        JugadoresNombre1.setText(jugadores.get(0).obtenerNombre());
        JugadoresNombre2.setText(jugadores.get(1).obtenerNombre());        
        
        tmp = new Integer(jugadores.get(0).obtenerNivel());
        JugadoresBarraN1.setValue(tmp.intValue());
        JugadoresBarraN1.setString(tmp.toString());
        JugadoresBarraN1.setToolTipText("Nivel: " + tmp.toString());
        tmp = new Integer(jugadores.get(1).obtenerNivel());
        JugadoresBarraN2.setValue(tmp.intValue());
        JugadoresBarraN2.setString(tmp.toString());        
        JugadoresBarraN2.setToolTipText("Nivel: " + tmp.toString());
        
        tmp = new Integer(jugadores.get(0).nivelDeCombate());
        JugadoresBarraNC1.setValue(tmp.intValue());
        JugadoresBarraNC1.setString(tmp.toString());
        JugadoresBarraNC1.setToolTipText("Nivel de Combate: " + tmp.toString());
        tmp = new Integer(jugadores.get(1).nivelDeCombate());
        JugadoresBarraNC2.setValue(tmp.intValue());
        JugadoresBarraNC2.setString(tmp.toString());
        JugadoresBarraNC2.setToolTipText("Nivel de Combate: " + tmp.toString());
        
        tmp=muertes.get(jugadores.get(0).obtenerNombre());
        JugadoresMuertes1.setText(tmp.toString());
        tmp=muertes.get(jugadores.get(1).obtenerNombre());
        JugadoresMuertes2.setText(tmp.toString());
        
        tes=jugadores.get(0).obtenerTesorosVisibles();
        JugadoresCVisible1_1.setText("+0");
        JugadoresCVisible2_1.setText("+0");
        JugadoresCVisible3_1.setText("+0");
        JugadoresCVisible4_1.setText("+0");
        JugadoresCVisible5_1.setText("+0");
        if(tes.size()>0)
            JugadoresCVisible1_1.setText("+"+tes.get(0).obtenerBonus());
        if(tes.size()>1)
            JugadoresCVisible2_1.setText("+"+tes.get(1).obtenerBonus());
        if(tes.size()>2)
            JugadoresCVisible3_1.setText("+"+tes.get(2).obtenerBonus());
        if(tes.size()>3)
            JugadoresCVisible4_1.setText("+"+tes.get(3).obtenerBonus());
        if(tes.size()>4)
            JugadoresCVisible5_1.setText("+"+tes.get(4).obtenerBonus());
        
        tes=jugadores.get(1).obtenerTesorosVisibles();
               
        JugadoresCVisible1_2.setText("+0");
        JugadoresCVisible2_2.setText("+0");
        JugadoresCVisible3_2.setText("+0");
        JugadoresCVisible4_2.setText("+0");
        JugadoresCVisible5_2.setText("+0");
        if(tes.size()>0)
            JugadoresCVisible1_2.setText("+"+tes.get(0).obtenerBonus());
        if(tes.size()>1)
            JugadoresCVisible2_2.setText("+"+tes.get(1).obtenerBonus());
        if(tes.size()>2)
            JugadoresCVisible3_2.setText("+"+tes.get(2).obtenerBonus());
        if(tes.size()>3)
            JugadoresCVisible4_2.setText("+"+tes.get(3).obtenerBonus());
        if(tes.size()>4)
        JugadoresCVisible5_2.setText("+"+tes.get(4).obtenerBonus());
        
        if(jugadores.size()==3){
            JugadoresNombre3.setText(jugadores.get(2).obtenerNombre());
            tmp = new Integer(jugadores.get(2).obtenerNivel());
            JugadoresBarraN3.setValue(tmp.intValue());
            JugadoresBarraN3.setString(tmp.toString());
            JugadoresBarraN3.setToolTipText("Nivel: " + tmp.toString());
            tmp = new Integer(jugadores.get(2).nivelDeCombate());
            JugadoresBarraNC3.setValue(tmp.intValue());
            JugadoresBarraNC3.setString(tmp.toString());
            JugadoresBarraNC3.setToolTipText("Nivel de Combate: " + tmp.toString());
            tmp=muertes.get(jugadores.get(2).obtenerNombre());
            JugadoresMuertes3.setText(tmp.toString());
            tes=jugadores.get(2).obtenerTesorosVisibles();
                  
            JugadoresCVisible1_3.setText("+0");
            JugadoresCVisible2_3.setText("+0");
            JugadoresCVisible3_3.setText("+0");
            JugadoresCVisible4_3.setText("+0");
            JugadoresCVisible5_3.setText("+0");
            if(tes.size()>0)
                JugadoresCVisible1_3.setText("+"+tes.get(0).obtenerBonus());
            if(tes.size()>1)
                JugadoresCVisible2_3.setText("+"+tes.get(1).obtenerBonus());
            if(tes.size()>2)
                JugadoresCVisible3_3.setText("+"+tes.get(2).obtenerBonus());
            if(tes.size()>3)
                JugadoresCVisible4_3.setText("+"+tes.get(3).obtenerBonus());
            if(tes.size()>4)
                JugadoresCVisible5_3.setText("+"+tes.get(4).obtenerBonus());
        }
        else{
            JugadoresNombre3.setVisible(false);
            JugadoresBarraN3.setVisible(false);
            JugadoresBarraNC3.setVisible(false);
            JugadoresMuertes3.setVisible(false);
            JugadoresCVisible1_3.setVisible(false);
            JugadoresCVisible2_3.setVisible(false);
            JugadoresCVisible3_3.setVisible(false);
            JugadoresCVisible4_3.setVisible(false);
            JugadoresCVisible5_3.setVisible(false);
        }
    }
    
    //Método para actualizar la vista del jugador actual (Mochila y Tesoros Visibles)
    private void actualizarJugador() {
        boolean bandera=false;
        TesoroGrafico unTesoroGrafico;
        int i;
        limpiarJugador();
        Map<String,String> uno=new HashMap();
        jugadorActivo = juego.getJugadorActivo();
        jLDude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/"+avatar.get(jugadorActivo.obtenerNombre()))));        
        Integer tmp = new Integer(jugadorActivo.obtenerNivel());
        BarraNivelJugador.setValue(tmp.intValue());
        BarraNivelJugador.setString("Nivel: " + tmp.toString());
        BarraNivelJugador.setToolTipText("Nivel: " + tmp.toString());
        tmp = new Integer(jugadorActivo.nivelDeCombate());
        BarraNivelCombate.setValue(tmp.intValue());
        BarraNivelCombate.setString("N.Comb: " + tmp.toString());
        BarraNivelCombate.setToolTipText("Nivel de Combate: " + tmp.toString());
        NombreJActivo.setText(jugadorActivo.obtenerNombre());
                
        i = 0;
        for (Tesoro t : jugadorActivo.obtenerTesorosVisibles()) {
            unTesoroGrafico = new TesoroGraficoVisible(t);           
            switch(t.obtenerTipo()){
                case Tesoro.CASCO: i = 0; break;
                case Tesoro.ARMADURA: i = 2; break;
                case Tesoro.CALZADO: i = 4; break;
                case Tesoro.DOS_MANOS: i = 3; break;
                case Tesoro.UNA_MANO: 
                    if(bandera)
                        i = 1; 
                    else{
                        bandera = true;
                        i = 3;
                    }
                    break;
            }                    
            JugadorActivoPanel.add (unTesoroGrafico, new
                org.netbeans.lib.awtextra.AbsoluteConstraints
                (posXJugador[i], posYJugador[i], -1, -1),0);
                tesorosVisiblesAlimpiar.add(unTesoroGrafico);
        }
        tesorosVisiblesSeleccionados.clear();
        
        i = 0;
        for (Tesoro t : jugadorActivo.obtenerTesorosOcultos()) {
            unTesoroGrafico = new TesoroGraficoOculto(t);
            MochilaPanel.add (unTesoroGrafico, new
                org.netbeans.lib.awtextra.AbsoluteConstraints
                (posXMochila[i], posYMochila, -1, -1));
            tesorosOcultosAlimpiar.add(unTesoroGrafico);
            
            i++;
        }
        
        tesorosOcultosSeleccionados.clear();

        repaint();
        pack();
    }
    
    //Método que realiza la inicialización de los elementos de la interfaz gráfica
    //a partir de la información obtenida del diálogo inicial del juego
    @Override
    public void mostrar(String[] args){
        JD_nombresJugadores dialogoNombres;
        dialogoNombres = new JD_nombresJugadores (this,true);
        nombresJugadores = dialogoNombres.getNombres();
        avatar = dialogoNombres.getAvatar();
        for(int i=0; i<nombresJugadores.size(); i++)
            muertes.put(nombresJugadores.get(i), new Integer(0));
        try{
            juego.comenzarJuego(nombresJugadores);
            juego.siguienteTurno();
        } catch (Exception e) {
            dialogo = new Dialogo(this, true, e.getMessage());
        } 
        combatido = false;
        bicho     = false;
        muerto    = false;
        resultadoCombate=0;
        actualizarJugador();        
        actualizarMarcador();
        BarraNivelMonstruo.setForeground(Color.green);
        this.setVisible(true);
    }
    
    //Método para actualizar la vista de Monstruo en base al Monstruo en Juego
    //actual
    private void actualizarMonstruo(){
        monstruoEnJuego = juego.getMonstruoEnJuego();
        TextoNombre.setText(monstruoEnJuego.obtenerNombre());
        MR.setText(monstruoEnJuego.obtenerMalRollo());
        Integer tmp = new Integer(monstruoEnJuego.obtenerGananciaTesoros());
        TextoGTesoros.setText(tmp.toString());
        tmp = new Integer(monstruoEnJuego.obtenerGananciaNiveles());
        TextoGNiveles.setText(tmp.toString());
        tmp = new Integer(monstruoEnJuego.obtenerNivel());
        imgMonstruo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/"+monstruoEnJuego.obtenerNombre()+".gif")));
        BarraNivelMonstruo.setValue(tmp.intValue());
        BarraNivelMonstruo.setString(tmp.toString());
        BarraNivelMonstruo.setToolTipText("Nivel: " + tmp.toString());
        if(tmp.intValue()<6){
            BarraNivelMonstruo.setForeground(Color.green);
        }
        else{
            if(tmp.intValue()<12)
                BarraNivelMonstruo.setForeground(new java.awt.Color(255, 102, 0));
            else
                BarraNivelMonstruo.setForeground(Color.red);
        }
        pack();
    }
    
    //Limpia toda la información actual de la Mazmorra
    private void limpiarMazmorra(){
        TextoNombre.setText(null);
        MR.setText(null);
        TextoGTesoros.setText(null);
        TextoGNiveles.setText(null);
        BarraNivelMonstruo.setValue(0);
        BarraNivelMonstruo.setString("0");
        BarraNivelMonstruo.setForeground(Color.green);
        imgMonstruo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/mazmorra.gif")));
        pack();
    }
    
    //Limpia toda la información actual de la vista jugador
    private void limpiarJugador(){
        BarraNivelJugador.setValue(0);
        BarraNivelJugador.setString("Nivel: 0");
        BarraNivelCombate.setValue(0);
        BarraNivelCombate.setString("N.Comb: 0");
        NombreJActivo.setText("Siguiente");
        for (TesoroGrafico tg : tesorosOcultosAlimpiar)
            MochilaPanel.remove (tg);
        
        tesorosOcultosAlimpiar.clear();
        
        for (TesoroGrafico tg : tesorosVisiblesAlimpiar)
            JugadorActivoPanel.remove (tg);
        
        tesorosVisiblesAlimpiar.clear();
        
        repaint();
        pack();
    }
    
   /**
     * @param args the command line arguments
     */    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JugadoresPanel = new javax.swing.JPanel();
        Jugador1 = new javax.swing.JPanel();
        JugadoresMuertes1 = new javax.swing.JTextField();
        JugadoresNombre1 = new javax.swing.JTextField();
        JugadoresCVisible5_1 = new javax.swing.JTextField();
        JugadoresCVisible4_1 = new javax.swing.JTextField();
        JugadoresCVisible3_1 = new javax.swing.JTextField();
        JugadoresCVisible2_1 = new javax.swing.JTextField();
        JugadoresCVisible1_1 = new javax.swing.JTextField();
        JugadoresBarraN1 = new javax.swing.JProgressBar();
        JugadoresBarraNC1 = new javax.swing.JProgressBar();
        Jugador2 = new javax.swing.JPanel();
        JugadoresMuertes2 = new javax.swing.JTextField();
        JugadoresNombre2 = new javax.swing.JTextField();
        JugadoresCVisible5_2 = new javax.swing.JTextField();
        JugadoresCVisible4_2 = new javax.swing.JTextField();
        JugadoresCVisible3_2 = new javax.swing.JTextField();
        JugadoresCVisible2_2 = new javax.swing.JTextField();
        JugadoresCVisible1_2 = new javax.swing.JTextField();
        JugadoresBarraN2 = new javax.swing.JProgressBar();
        JugadoresBarraNC2 = new javax.swing.JProgressBar();
        Jugador3 = new javax.swing.JPanel();
        JugadoresMuertes3 = new javax.swing.JTextField();
        JugadoresNombre3 = new javax.swing.JTextField();
        JugadoresCVisible5_3 = new javax.swing.JTextField();
        JugadoresCVisible4_3 = new javax.swing.JTextField();
        JugadoresCVisible3_3 = new javax.swing.JTextField();
        JugadoresCVisible2_3 = new javax.swing.JTextField();
        JugadoresCVisible1_3 = new javax.swing.JTextField();
        JugadoresBarraN3 = new javax.swing.JProgressBar();
        JugadoresBarraNC3 = new javax.swing.JProgressBar();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        JugadorActivoPanel = new javax.swing.JPanel();
        LabelMazmorra2 = new javax.swing.JLabel();
        BarraNivelJugador = new javax.swing.JProgressBar();
        NombreJActivo = new javax.swing.JTextField();
        BarraNivelCombate = new javax.swing.JProgressBar();
        jLDude = new javax.swing.JLabel();
        MazmorraPanel = new javax.swing.JPanel();
        LabelMazmorra = new javax.swing.JLabel();
        MarcoMonstruo = new javax.swing.JPanel();
        imgMonstruo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TextoGNiveles = new javax.swing.JTextField();
        BarraNivelMonstruo = new javax.swing.JProgressBar();
        TextoNombre = new javax.swing.JTextField();
        TextoGTesoros = new javax.swing.JTextField();
        MalRollo = new javax.swing.JScrollPane();
        MR = new javax.swing.JTextArea();
        jBSoltarBicho = new javax.swing.JButton();
        MochilaPanel = new javax.swing.JPanel();
        LabelMazmorra1 = new javax.swing.JLabel();
        jBEquiparse = new javax.swing.JButton();
        jBRobar = new javax.swing.JButton();
        jBComprarN = new javax.swing.JButton();
        jBDescartes = new javax.swing.JButton();
        jBCombatir = new javax.swing.JButton();
        jBSiguiente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 60, 50));
        setForeground(new java.awt.Color(238, 238, 238));
        setMinimumSize(new java.awt.Dimension(1000, 700));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JugadoresPanel.setBackground(new java.awt.Color(153, 153, 255));
        JugadoresPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        JugadoresPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Jugador1.setBackground(new java.awt.Color(153, 153, 255));
        Jugador1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JugadoresMuertes1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JugadoresMuertes1.setText("0");
        Jugador1.add(JugadoresMuertes1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 20, 30));

        JugadoresNombre1.setBackground(new java.awt.Color(153, 153, 255));
        JugadoresNombre1.setText("jTextField1");
        JugadoresNombre1.setBorder(null);
        Jugador1.add(JugadoresNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 30));

        JugadoresCVisible5_1.setText("+8");
        Jugador1.add(JugadoresCVisible5_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 20, 30));

        JugadoresCVisible4_1.setText("+2");
        Jugador1.add(JugadoresCVisible4_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 20, 30));

        JugadoresCVisible3_1.setText("+2");
        Jugador1.add(JugadoresCVisible3_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 20, 30));

        JugadoresCVisible2_1.setText("+2");
        Jugador1.add(JugadoresCVisible2_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 20, 30));

        JugadoresCVisible1_1.setText("+2");
        Jugador1.add(JugadoresCVisible1_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 20, 30));

        JugadoresBarraN1.setForeground(new java.awt.Color(51, 204, 0));
        JugadoresBarraN1.setMaximum(10);
        JugadoresBarraN1.setToolTipText("Nivel:");
        JugadoresBarraN1.setValue(7);
        JugadoresBarraN1.setString(BarraNivelMonstruo.getToolTipText());
        JugadoresBarraN1.setStringPainted(true);
        Jugador1.add(JugadoresBarraN1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 40, 30));

        JugadoresBarraNC1.setForeground(new java.awt.Color(51, 0, 204));
        JugadoresBarraNC1.setMaximum(30);
        JugadoresBarraNC1.setToolTipText("Nivel de Combate:");
        JugadoresBarraNC1.setValue(23);
        JugadoresBarraNC1.setString(BarraNivelMonstruo.getToolTipText());
        JugadoresBarraNC1.setStringPainted(true);
        Jugador1.add(JugadoresBarraNC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 40, 30));

        JugadoresPanel.add(Jugador1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 340, 30));

        Jugador2.setBackground(new java.awt.Color(153, 153, 255));
        Jugador2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JugadoresMuertes2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JugadoresMuertes2.setText("0");
        Jugador2.add(JugadoresMuertes2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 20, 30));

        JugadoresNombre2.setBackground(new java.awt.Color(153, 153, 255));
        JugadoresNombre2.setText("jTextField2");
        JugadoresNombre2.setBorder(null);
        Jugador2.add(JugadoresNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 30));

        JugadoresCVisible5_2.setText("+8");
        Jugador2.add(JugadoresCVisible5_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 20, 30));

        JugadoresCVisible4_2.setText("+2");
        Jugador2.add(JugadoresCVisible4_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 20, 30));

        JugadoresCVisible3_2.setText("+2");
        Jugador2.add(JugadoresCVisible3_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 20, 30));

        JugadoresCVisible2_2.setText("+2");
        Jugador2.add(JugadoresCVisible2_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 20, 30));

        JugadoresCVisible1_2.setText("+2");
        Jugador2.add(JugadoresCVisible1_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 20, 30));

        JugadoresBarraN2.setForeground(new java.awt.Color(51, 204, 0));
        JugadoresBarraN2.setMaximum(10);
        JugadoresBarraN2.setToolTipText("Nivel:");
        JugadoresBarraN2.setValue(7);
        JugadoresBarraN2.setString(BarraNivelMonstruo.getToolTipText());
        JugadoresBarraN2.setStringPainted(true);
        Jugador2.add(JugadoresBarraN2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 40, 30));

        JugadoresBarraNC2.setForeground(new java.awt.Color(51, 0, 204));
        JugadoresBarraNC2.setMaximum(30);
        JugadoresBarraNC2.setToolTipText("Nivel de Combate:");
        JugadoresBarraNC2.setValue(23);
        JugadoresBarraNC2.setString(BarraNivelMonstruo.getToolTipText());
        JugadoresBarraNC2.setStringPainted(true);
        Jugador2.add(JugadoresBarraNC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 40, 30));

        JugadoresPanel.add(Jugador2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 340, 30));

        Jugador3.setBackground(new java.awt.Color(153, 153, 255));
        Jugador3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JugadoresMuertes3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JugadoresMuertes3.setText("0");
        Jugador3.add(JugadoresMuertes3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 20, 30));

        JugadoresNombre3.setBackground(new java.awt.Color(153, 153, 255));
        JugadoresNombre3.setText("jTextField3");
        JugadoresNombre3.setBorder(null);
        Jugador3.add(JugadoresNombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 30));

        JugadoresCVisible5_3.setText("+8");
        Jugador3.add(JugadoresCVisible5_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 20, 30));

        JugadoresCVisible4_3.setText("+2");
        Jugador3.add(JugadoresCVisible4_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 20, 30));

        JugadoresCVisible3_3.setText("+2");
        Jugador3.add(JugadoresCVisible3_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 20, 30));

        JugadoresCVisible2_3.setText("+2");
        Jugador3.add(JugadoresCVisible2_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 20, 30));

        JugadoresCVisible1_3.setText("+2");
        Jugador3.add(JugadoresCVisible1_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 20, 30));

        JugadoresBarraN3.setForeground(new java.awt.Color(51, 204, 0));
        JugadoresBarraN3.setMaximum(10);
        JugadoresBarraN3.setToolTipText("Nivel:");
        JugadoresBarraN3.setValue(7);
        JugadoresBarraN3.setString(BarraNivelMonstruo.getToolTipText());
        JugadoresBarraN3.setStringPainted(true);
        Jugador3.add(JugadoresBarraN3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 40, 30));

        JugadoresBarraNC3.setForeground(new java.awt.Color(51, 0, 204));
        JugadoresBarraNC3.setMaximum(30);
        JugadoresBarraNC3.setToolTipText("Nivel de Combate:");
        JugadoresBarraNC3.setValue(23);
        JugadoresBarraNC3.setString(BarraNivelMonstruo.getToolTipText());
        JugadoresBarraNC3.setStringPainted(true);
        Jugador3.add(JugadoresBarraNC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 40, 30));

        JugadoresPanel.add(Jugador3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 340, 30));
        Jugador3.getAccessibleContext().setAccessibleName("");
        Jugador3.getAccessibleContext().setAccessibleDescription("");

        jLabel6.setText("Cartas Visibles");
        JugadoresPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        jLabel7.setText("Nombre");
        JugadoresPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel8.setText("Nivel");
        JugadoresPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 30, -1));

        jLabel9.setText("N. Comb.");
        JugadoresPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, -1));

        jLabel10.setText("Muertes");
        JugadoresPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        getContentPane().add(JugadoresPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 11, 360, 130));

        JugadorActivoPanel.setBackground(new java.awt.Color(204, 216, 204));
        JugadorActivoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        JugadorActivoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LabelMazmorra2.setFont(new java.awt.Font("Tahoma", 0, 18));
        LabelMazmorra2.setText("Jugador Activo");
        JugadorActivoPanel.add(LabelMazmorra2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        BarraNivelJugador.setForeground(new java.awt.Color(51, 204, 0));
        BarraNivelJugador.setMaximum(10);
        BarraNivelJugador.setToolTipText("Nivel:");
        BarraNivelJugador.setValue(7);
        BarraNivelJugador.setString(BarraNivelJugador.getToolTipText());
        BarraNivelJugador.setStringPainted(true);
        JugadorActivoPanel.add(BarraNivelJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 90, -1));

        NombreJActivo.setEditable(false);
        NombreJActivo.setText("Nombre");
        JugadorActivoPanel.add(NombreJActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 110, -1));

        BarraNivelCombate.setForeground(new java.awt.Color(51, 0, 204));
        BarraNivelCombate.setMaximum(30);
        BarraNivelCombate.setToolTipText("Nivel de Combate:");
        BarraNivelCombate.setValue(23);
        BarraNivelCombate.setString(BarraNivelCombate.getToolTipText());
        BarraNivelCombate.setStringPainted(true);
        JugadorActivoPanel.add(BarraNivelCombate, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 90, -1));

        jLDude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/m6.gif"))); // NOI18N
        jLDude.setToolTipText("");
        jLDude.setFocusable(false);
        JugadorActivoPanel.add(jLDude, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 240, 380));

        getContentPane().add(JugadorActivoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, 360, 430));

        MazmorraPanel.setBackground(new java.awt.Color(153, 153, 255));
        MazmorraPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        MazmorraPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LabelMazmorra.setFont(new java.awt.Font("Tahoma", 0, 18));
        LabelMazmorra.setText("Mazmorra");
        MazmorraPanel.add(LabelMazmorra, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        MarcoMonstruo.setBackground(new java.awt.Color(153, 153, 255));
        MarcoMonstruo.setForeground(new java.awt.Color(238, 238, 238));
        MarcoMonstruo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imgMonstruo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/mazmorra.gif"))); // NOI18N
        MarcoMonstruo.add(imgMonstruo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 3, 180, -1));

        MazmorraPanel.add(MarcoMonstruo, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 50, -1, 270));

        jLabel1.setText("Mal Rollo:");
        MazmorraPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jLabel2.setText("Nivel:");
        MazmorraPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(377, 330, -1, -1));

        jLabel3.setText("Ganancia de Tesoros:");
        MazmorraPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jLabel4.setText("Ganancia de Niveles:");
        MazmorraPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel5.setText("Nombre:");
        MazmorraPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        TextoGNiveles.setEditable(false);
        TextoGNiveles.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        MazmorraPanel.add(TextoGNiveles, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 40, -1));

        BarraNivelMonstruo.setForeground(new java.awt.Color(255, 51, 0));
        BarraNivelMonstruo.setMaximum(16);
        BarraNivelMonstruo.setToolTipText("Nivel:");
        BarraNivelMonstruo.setString("0");
        BarraNivelMonstruo.setStringPainted(true);
        MazmorraPanel.add(BarraNivelMonstruo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 330, 140, -1));

        TextoNombre.setEditable(false);
        TextoNombre.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        MazmorraPanel.add(TextoNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 260, -1));

        TextoGTesoros.setEditable(false);
        TextoGTesoros.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        MazmorraPanel.add(TextoGTesoros, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 40, -1));

        MR.setBackground(new java.awt.Color(240, 240, 240));
        MR.setColumns(20);
        MR.setEditable(false);
        MR.setLineWrap(true);
        MR.setRows(5);
        MR.setWrapStyleWord(true);
        MR.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        MR.setMargin(new java.awt.Insets(4, 4, 4, 4));
        MR.setRequestFocusEnabled(false);
        MalRollo.setViewportView(MR);

        MazmorraPanel.add(MalRollo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 226, 320, 100));

        jBSoltarBicho.setText("Soltar al bicho");
        jBSoltarBicho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSoltarBichoActionPerformed(evt);
            }
        });
        MazmorraPanel.add(jBSoltarBicho, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, -1));

        getContentPane().add(MazmorraPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 580, 380));

        MochilaPanel.setBackground(new java.awt.Color(153, 153, 255));
        MochilaPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        MochilaPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LabelMazmorra1.setFont(new java.awt.Font("Tahoma", 0, 18));
        LabelMazmorra1.setText("Mochila");
        MochilaPanel.add(LabelMazmorra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

        getContentPane().add(MochilaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 580, 170));

        jBEquiparse.setText("Equiparse");
        jBEquiparse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEquiparseActionPerformed(evt);
            }
        });
        getContentPane().add(jBEquiparse, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 620, -1, -1));

        jBRobar.setText("Robar Tesoro");
        jBRobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRobarActionPerformed(evt);
            }
        });
        getContentPane().add(jBRobar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 620, -1, -1));

        jBComprarN.setText("Comprar Nivel");
        jBComprarN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBComprarNActionPerformed(evt);
            }
        });
        getContentPane().add(jBComprarN, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 620, -1, -1));

        jBDescartes.setText("Descartarse Tesoros");
        jBDescartes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDescartesActionPerformed(evt);
            }
        });
        getContentPane().add(jBDescartes, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 620, -1, -1));

        jBCombatir.setText("¡Combatir!");
        jBCombatir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCombatirActionPerformed(evt);
            }
        });
        getContentPane().add(jBCombatir, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 620, -1, -1));

        jBSiguiente.setText("Siguiente");
        jBSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSiguienteActionPerformed(evt);
            }
        });
        getContentPane().add(jBSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 620, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

//Botón para la compra de niveles con tesoros seleccionados. No deja realizar la
//con valor de tesoros menor que 1000 ni ganar la partida comprando niveles
private void jBComprarNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBComprarNActionPerformed
    int oroAcumulado=0;
    int nivelActual = jugadorActivo.obtenerNivel();
    List<Tesoro> tesorosVender = new ArrayList();
    if((!bicho || (bicho && combatido && !muerto)) && nivelActual!=9){
        tesorosVender.addAll(tesorosOcultosSeleccionados);
        tesorosVender.addAll(tesorosVisiblesSeleccionados);
        for(Tesoro t:tesorosVender){        
            if(oroAcumulado<=1000){
                oroAcumulado=oroAcumulado+t.obtenerPiezasOro();
            }
        }
        if(oroAcumulado < 1000)
            dialogo = new Dialogo(this, true, "Al menos debes seleccionar tesoros con un valor total de 1000 piezas de oro");
        else{
            juego.comprarNivelesJugador(tesorosVender);
            nivelActual = jugadorActivo.obtenerNivel();
            if(nivelActual>=10){
                jugadorActivo.modificarNivel(-(nivelActual - 9));
                dialogo = new Dialogo(this,true, "Es muy triste ganar de esta manera. Te quedas en nivel 9");
            }
            actualizarJugador();
            actualizarMarcador();
        }
    }else{
        if(nivelActual==9)
            dialogo = new Dialogo(this,true, "Esta es una manera muy ruin de ganar. Si quieres pasar a nivel 10, lucha contra un monstruo");
        else{
            if(bicho){
                if(!combatido)
                    dialogo = new Dialogo(this,true, "¿Acaso no has visto que hay un monstruo muy bonico esperando? Ve y lucha contra él");
                else{
                    if(muerto)
                        dialogo = new Dialogo(this,true, "Puede que no te hayas dado cuenta, pero... ¡Estás muerto!");
                }
            }        
        }
    }    
}//GEN-LAST:event_jBComprarNActionPerformed

//Botón para equiparse las cartas ocultas seleccionadas en caso de que sea posible
private void jBEquiparseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEquiparseActionPerformed
    if(!bicho || (bicho && combatido && !muerto)){
        List<Tesoro> devueltos=juego.equiparseJugador(tesorosOcultosSeleccionados);
        if(devueltos.isEmpty())
            for(Tesoro tes:devueltos){
                if(!jugadorActivo.obtenerTesorosOcultos().contains(tes))
                    jugadorActivo.robarTesoro(tes);
            }
        actualizarJugador();
        actualizarMarcador();
    }
    else{
        if(bicho){
            if(!combatido)
                dialogo = new Dialogo(this,true, "¿Acaso no has visto que hay un monstruo muy bonico esperando? Ve y lucha contra él");
            else{
                if(muerto)
                    dialogo = new Dialogo(this,true, "Puede que no te hayas dado cuenta, pero... ¡Estás muerto!");
            }
        }        
    }
}//GEN-LAST:event_jBEquiparseActionPerformed

//Botón para robar los tesoros ganados al ganar un combate. Controla que no se roben
//más tesoros de los que corresponden
private void jBRobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRobarActionPerformed
    if(resultadoCombate>0){
        jugadorActivo.robarTesoro(juego.primerTesoro());
        resultadoCombate--;
        actualizarJugador();
        actualizarMarcador();
    }else{
        if(muerto)
            dialogo = new Dialogo(this,true, "Puede que no te hayas dado cuenta, pero... ¡Estás muerto!");
        else
            dialogo = new Dialogo(this,true, "No seas tramposo, que no puedes robar tesoros");
    }
}//GEN-LAST:event_jBRobarActionPerformed

//Botón de combate. Comprueba si podemos combatir y muestra un mensaje según el
//resultado del combate
private void jBCombatirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCombatirActionPerformed
    if(bicho && !combatido){
        resultadoCombate = juego.desarrollarCombate();
        //bicho = false;
        combatido = true;
        switch(resultadoCombate){
            case -1:    dialogo = new Dialogo(this,true, "Intentas huir, pero no lo consigues, se te aplica el mal rollo");
                        int [] mal = monstruoEnJuego.obtenerMalRolloV();
                        if(mal[1]==-1){
                            muerto = true;
                            muertes.put(jugadorActivo.obtenerNombre(), new Integer(muertes.get(jugadorActivo.obtenerNombre())+1));
                        }
                            
                        juego.aplicarMalRollo();
                        break;
            case 0:     dialogo = new Dialogo(this, true, "El monstruo te puede, pero tú, que eres un cobarde, huyes con el rabo entre las piernas");
                        break;
            case 10:    dialogo = new Dialogo(this, true, "Felicidades, has ganado el juego");
                        jBCombatir.setEnabled(false);
                        jBDescartes.setEnabled(false);
                        jBRobar.setEnabled(false);
                        jBSiguiente.setEnabled(false);
                        jBSoltarBicho.setEnabled(false);
                        jBEquiparse.setEnabled(false);
                        jBComprarN.setEnabled(false);
                        break;
            default:    dialogo = new Dialogo(this, true, "Has derrotado al monstruo, coge tus tesoros");
        }
        actualizarJugador();
        actualizarMarcador();   
    }else{
        if(!combatido)
            dialogo = new Dialogo(this,true, "Huy qué valiente, vas ha combatir contra un bicho que está encerrado e indefenso... Suéltalo anda");
        else
            dialogo = new Dialogo(this,true, "Ya has luchado, por más que le des, no cambiará el resultado");
    }
}//GEN-LAST:event_jBCombatirActionPerformed

//Botón para descartarse las cartas seleccionadas
private void jBDescartesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDescartesActionPerformed
    if(!bicho || (bicho && combatido && !muerto)){
        if(tesorosVisiblesSeleccionados.isEmpty() && tesorosOcultosSeleccionados.isEmpty())
            dialogo = new Dialogo(this,true, "¡Muy bien! Pero... exactamente, ¿qué quieres descartar?");
        else{
            juego.descartarTesorosOcultos(tesorosOcultosSeleccionados);
            juego.descartarTesorosVisibles(tesorosVisiblesSeleccionados);
            actualizarJugador();
            actualizarMarcador();
        }       
    }else{
        if(bicho){
            if(!combatido)
                dialogo = new Dialogo(this,true, "¿Acaso no has visto que hay un monstruo muy bonico esperando? Ve y lucha contra él");
            else{
                if(muerto)
                    dialogo = new Dialogo(this,true, "Puede que no te hayas dado cuenta, pero... ¡Estás muerto!");
            }
        }        
    }
}//GEN-LAST:event_jBDescartesActionPerformed

//Botón para pasar el turno al siguiente jugador en caso de que sea posible
//Muestra un diálogo informando si se incumple algún requisito para continuar
//o bien limpia la vista del jugador para que se pueda avisar al siguiente jugador
//y no vea las cartas del jugador actual
private void jBSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSiguienteActionPerformed
    if(combatido){
        try {
            juego.siguienteTurno();
            
            combatido= false;
            bicho    = false;
            muerto   = false;
            if(resultadoCombate>0)
                 dialogo = new Dialogo(this, true, "Pringao, se te ha olvidado coger todos los tesoros");

            resultadoCombate = 0;
            limpiarMazmorra();
            limpiarJugador();
            dialogo = new Dialogo(this, true, "Ahora avisa al siguiente jugador para que continúe");
            actualizarJugador();        
            actualizarMarcador();
            limpiarMazmorra();
            this.setVisible(true);            
            
        } catch (Exception e) {
            dialogo = new Dialogo(this, true, e.getMessage());
        }
    }else{
        dialogo = new Dialogo(this, true, "Cobarde, tienes que combatir antes de terminar tu turno");
    }
}//GEN-LAST:event_jBSiguienteActionPerformed

//Botón para mostrar al monstruo contra el que hay que combatir
private void jBSoltarBichoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSoltarBichoActionPerformed
    if(bicho && !combatido)
        dialogo = new Dialogo(this, true, "El monstruo ya te está esperando, ve y lucha contra él");
    else{
        if(muerto)
            dialogo = new Dialogo(this, true, "Puede que no te hayas dado cuenta, pero... ¡Estás muerto!");
        else{            
            if(combatido)
                dialogo = new Dialogo(this, true, "¿No has tenido suficiente? No puedes combatir más este turno");
            else{
                actualizarMonstruo();
                bicho = true;
            }
        }
    }
}//GEN-LAST:event_jBSoltarBichoActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar BarraNivelCombate;
    private javax.swing.JProgressBar BarraNivelJugador;
    private javax.swing.JProgressBar BarraNivelMonstruo;
    private javax.swing.JPanel Jugador1;
    private javax.swing.JPanel Jugador2;
    private javax.swing.JPanel Jugador3;
    private javax.swing.JPanel JugadorActivoPanel;
    private javax.swing.JProgressBar JugadoresBarraN1;
    private javax.swing.JProgressBar JugadoresBarraN2;
    private javax.swing.JProgressBar JugadoresBarraN3;
    private javax.swing.JProgressBar JugadoresBarraNC1;
    private javax.swing.JProgressBar JugadoresBarraNC2;
    private javax.swing.JProgressBar JugadoresBarraNC3;
    private javax.swing.JTextField JugadoresCVisible1_1;
    private javax.swing.JTextField JugadoresCVisible1_2;
    private javax.swing.JTextField JugadoresCVisible1_3;
    private javax.swing.JTextField JugadoresCVisible2_1;
    private javax.swing.JTextField JugadoresCVisible2_2;
    private javax.swing.JTextField JugadoresCVisible2_3;
    private javax.swing.JTextField JugadoresCVisible3_1;
    private javax.swing.JTextField JugadoresCVisible3_2;
    private javax.swing.JTextField JugadoresCVisible3_3;
    private javax.swing.JTextField JugadoresCVisible4_1;
    private javax.swing.JTextField JugadoresCVisible4_2;
    private javax.swing.JTextField JugadoresCVisible4_3;
    private javax.swing.JTextField JugadoresCVisible5_1;
    private javax.swing.JTextField JugadoresCVisible5_2;
    private javax.swing.JTextField JugadoresCVisible5_3;
    private javax.swing.JTextField JugadoresMuertes1;
    private javax.swing.JTextField JugadoresMuertes2;
    private javax.swing.JTextField JugadoresMuertes3;
    private javax.swing.JTextField JugadoresNombre1;
    private javax.swing.JTextField JugadoresNombre2;
    private javax.swing.JTextField JugadoresNombre3;
    private javax.swing.JPanel JugadoresPanel;
    private javax.swing.JLabel LabelMazmorra;
    private javax.swing.JLabel LabelMazmorra1;
    private javax.swing.JLabel LabelMazmorra2;
    private javax.swing.JTextArea MR;
    private javax.swing.JScrollPane MalRollo;
    private javax.swing.JPanel MarcoMonstruo;
    private javax.swing.JPanel MazmorraPanel;
    private javax.swing.JPanel MochilaPanel;
    private javax.swing.JTextField NombreJActivo;
    private javax.swing.JTextField TextoGNiveles;
    private javax.swing.JTextField TextoGTesoros;
    private javax.swing.JTextField TextoNombre;
    private javax.swing.JLabel imgMonstruo;
    private javax.swing.JButton jBCombatir;
    private javax.swing.JButton jBComprarN;
    private javax.swing.JButton jBDescartes;
    private javax.swing.JButton jBEquiparse;
    private javax.swing.JButton jBRobar;
    private javax.swing.JButton jBSiguiente;
    private javax.swing.JButton jBSoltarBicho;
    private javax.swing.JLabel jLDude;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
