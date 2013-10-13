package munchkin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que implementa El diálogo inicial para seleccionar el número de jugadores,
 * rellenar sus nombres y seleccionar sus avatares. Al finalizar devuelve el control
 * a la clase que la ha llamado, la clase VentanaPrincipal.
 * 
 * @author Grupo
 */
public class JD_nombresJugadores extends javax.swing.JDialog {
    private List<String> nombres=new ArrayList();
    private HashMap<String, String> avatar=new HashMap();

    public JD_nombresJugadores(java.awt.Frame parent, boolean modal) {        
        super(parent, modal);        
        initComponents();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
        jLM1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/jLM1.gif")));
        jLM2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/jLM2.gif")));
        jLM3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/jLM3.gif")));
        jLM4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/jLM4.gif")));
        jLM5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/jLM5.gif")));
        jLM6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/munchkin/img/jLM6.gif")));
        jLabel3.setEnabled(false);
        Nombre3.setEnabled(false);
    }
    
    public List<String> getNombres(){
       this.setVisible(true);
       return nombres;       
    }
    public HashMap<String, String> getAvatar(){
        
       return avatar;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRBjug1 = new javax.swing.ButtonGroup();
        jRBjug2 = new javax.swing.ButtonGroup();
        jRBjug3 = new javax.swing.ButtonGroup();
        CheckTres = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Nombre1 = new javax.swing.JTextField();
        Nombre2 = new javax.swing.JTextField();
        Nombre3 = new javax.swing.JTextField();
        Comenzar = new javax.swing.JButton();
        Cancelar = new javax.swing.JButton();
        jRJ1m1 = new javax.swing.JRadioButton();
        jRJ1m2 = new javax.swing.JRadioButton();
        jRJ1m3 = new javax.swing.JRadioButton();
        jRJ1m4 = new javax.swing.JRadioButton();
        jRJ1m5 = new javax.swing.JRadioButton();
        jRJ1m6 = new javax.swing.JRadioButton();
        jRJ2m1 = new javax.swing.JRadioButton();
        jRJ2m2 = new javax.swing.JRadioButton();
        jRJ2m3 = new javax.swing.JRadioButton();
        jRJ2m4 = new javax.swing.JRadioButton();
        jRJ2m5 = new javax.swing.JRadioButton();
        jRJ2m6 = new javax.swing.JRadioButton();
        jRJ3m1 = new javax.swing.JRadioButton();
        jRJ3m2 = new javax.swing.JRadioButton();
        jRJ3m3 = new javax.swing.JRadioButton();
        jRJ3m4 = new javax.swing.JRadioButton();
        jRJ3m5 = new javax.swing.JRadioButton();
        jRJ3m6 = new javax.swing.JRadioButton();
        jLM2 = new javax.swing.JLabel();
        jLM1 = new javax.swing.JLabel();
        jLM3 = new javax.swing.JLabel();
        jLM4 = new javax.swing.JLabel();
        jLM5 = new javax.swing.JLabel();
        jLM6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(640, 320));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        CheckTres.setText("Tres jugadores");
        CheckTres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckTresActionPerformed(evt);
            }
        });
        getContentPane().add(CheckTres, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 55, -1, -1));

        jLabel1.setText("Jugador 1:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        jLabel2.setText("Jugador 2:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

        jLabel3.setText("Jugador 3:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));
        getContentPane().add(Nombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 170, -1));
        getContentPane().add(Nombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 170, -1));
        getContentPane().add(Nombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 170, -1));

        Comenzar.setText("Comenzar Juego");
        Comenzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComenzarActionPerformed(evt);
            }
        });
        getContentPane().add(Comenzar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, -1, -1));

        Cancelar.setText("Cancelar");
        Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarActionPerformed(evt);
            }
        });
        getContentPane().add(Cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, -1, -1));

        jRBjug1.add(jRJ1m1);
        jRJ1m1.setSelected(true);
        getContentPane().add(jRJ1m1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, -1, -1));

        jRBjug1.add(jRJ1m2);
        getContentPane().add(jRJ1m2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, -1, -1));

        jRBjug1.add(jRJ1m3);
        getContentPane().add(jRJ1m3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, -1, -1));

        jRBjug1.add(jRJ1m4);
        getContentPane().add(jRJ1m4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, -1));

        jRBjug1.add(jRJ1m5);
        getContentPane().add(jRJ1m5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, -1, -1));

        jRBjug1.add(jRJ1m6);
        getContentPane().add(jRJ1m6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, -1, -1));

        jRBjug2.add(jRJ2m1);
        jRJ2m1.setSelected(true);
        getContentPane().add(jRJ2m1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, -1, -1));

        jRBjug2.add(jRJ2m2);
        getContentPane().add(jRJ2m2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, -1, -1));

        jRBjug2.add(jRJ2m3);
        getContentPane().add(jRJ2m3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, -1, -1));

        jRBjug2.add(jRJ2m4);
        getContentPane().add(jRJ2m4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, -1, -1));

        jRBjug2.add(jRJ2m5);
        getContentPane().add(jRJ2m5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, -1, -1));

        jRBjug2.add(jRJ2m6);
        getContentPane().add(jRJ2m6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 130, -1, -1));

        jRBjug3.add(jRJ3m1);
        jRJ3m1.setSelected(true);
        jRJ3m1.setEnabled(false);
        getContentPane().add(jRJ3m1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, -1, -1));

        jRBjug3.add(jRJ3m2);
        jRJ3m2.setEnabled(false);
        getContentPane().add(jRJ3m2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 170, -1, -1));

        jRBjug3.add(jRJ3m3);
        jRJ3m3.setEnabled(false);
        getContentPane().add(jRJ3m3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, -1, -1));

        jRBjug3.add(jRJ3m4);
        jRJ3m4.setEnabled(false);
        getContentPane().add(jRJ3m4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, -1, -1));

        jRBjug3.add(jRJ3m5);
        jRJ3m5.setEnabled(false);
        getContentPane().add(jRJ3m5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, -1, -1));

        jRBjug3.add(jRJ3m6);
        jRJ3m6.setEnabled(false);
        getContentPane().add(jRJ3m6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 170, -1, -1));

        jLM2.setFocusable(false);
        getContentPane().add(jLM2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, 20, 30));

        jLM1.setFocusable(false);
        getContentPane().add(jLM1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 20, 30));

        jLM3.setFocusable(false);
        getContentPane().add(jLM3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 20, 30));

        jLM4.setFocusable(false);
        getContentPane().add(jLM4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, 20, 30));

        jLM5.setFocusable(false);
        getContentPane().add(jLM5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 20, 30));

        jLM6.setFocusable(false);
        getContentPane().add(jLM6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, 20, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

//Casilla tres jugadores, se encarga de habilitar y deshabilitar los elementos 
//que corresponden al tercer jugador
private void CheckTresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckTresActionPerformed
    jLabel3.setEnabled(CheckTres.isSelected());
    Nombre3.setEnabled(CheckTres.isSelected());
    jRJ3m1.setEnabled(CheckTres.isSelected());
    jRJ3m2.setEnabled(CheckTres.isSelected());
    jRJ3m3.setEnabled(CheckTres.isSelected());
    jRJ3m4.setEnabled(CheckTres.isSelected());
    jRJ3m5.setEnabled(CheckTres.isSelected());
    jRJ3m6.setEnabled(CheckTres.isSelected());
}//GEN-LAST:event_CheckTresActionPerformed

//Acción de cancelar cierra el juego
private void CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarActionPerformed
    System.exit(0);
}//GEN-LAST:event_CancelarActionPerformed

//Establece los atributos con todos los valores necesarios para que luego la clase
//VentanaPrincipal pueda obtenerlos para iniciar el juego. Al finalizar este método
//se devuelve el control a VentanaPrincipal
private void ComenzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComenzarActionPerformed
    nombres.add(Nombre1.getText());
    nombres.add(Nombre2.getText());
    if(CheckTres.isSelected()){
        nombres.add(Nombre3.getText());
        if(jRJ3m1.isSelected())
           avatar.put(nombres.get(2), "m1.gif");
        if(jRJ3m2.isSelected())
           avatar.put(nombres.get(2), "m2.gif");
        if(jRJ3m3.isSelected())
           avatar.put(nombres.get(2), "m3.gif");
        if(jRJ3m4.isSelected())
           avatar.put(nombres.get(2), "m4.gif");
        if(jRJ3m5.isSelected())
           avatar.put(nombres.get(2), "m5.gif");
        if(jRJ3m6.isSelected())
           avatar.put(nombres.get(2), "m6.gif");  
    }    
    
   if(jRJ1m1.isSelected())
       avatar.put(nombres.get(0), "m1.gif");
   if(jRJ1m2.isSelected())
       avatar.put(nombres.get(0), "m2.gif");
   if(jRJ1m3.isSelected())
       avatar.put(nombres.get(0), "m3.gif");
   if(jRJ1m4.isSelected())
       avatar.put(nombres.get(0), "m4.gif");
   if(jRJ1m5.isSelected())
       avatar.put(nombres.get(0), "m5.gif");
   if(jRJ1m6.isSelected())
       avatar.put(nombres.get(0), "m6.gif");
   if(jRJ2m1.isSelected())
       avatar.put(nombres.get(1), "m1.gif");
   if(jRJ2m2.isSelected())
       avatar.put(nombres.get(1), "m2.gif");
   if(jRJ2m3.isSelected())
       avatar.put(nombres.get(1), "m3.gif");
   if(jRJ2m4.isSelected())
       avatar.put(nombres.get(1), "m4.gif");
   if(jRJ2m5.isSelected())
       avatar.put(nombres.get(1), "m5.gif");
   if(jRJ2m6.isSelected())
       avatar.put(nombres.get(1), "m6.gif"); 
   
    this.dispose();
}//GEN-LAST:event_ComenzarActionPerformed

//Variables correspondientes a los elementos autogenerados al editar en modo
//diseño
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancelar;
    private javax.swing.JCheckBox CheckTres;
    private javax.swing.JButton Comenzar;
    private javax.swing.JTextField Nombre1;
    private javax.swing.JTextField Nombre2;
    private javax.swing.JTextField Nombre3;
    private javax.swing.JLabel jLM1;
    private javax.swing.JLabel jLM2;
    private javax.swing.JLabel jLM3;
    private javax.swing.JLabel jLM4;
    private javax.swing.JLabel jLM5;
    private javax.swing.JLabel jLM6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.ButtonGroup jRBjug1;
    private javax.swing.ButtonGroup jRBjug2;
    private javax.swing.ButtonGroup jRBjug3;
    private javax.swing.JRadioButton jRJ1m1;
    private javax.swing.JRadioButton jRJ1m2;
    private javax.swing.JRadioButton jRJ1m3;
    private javax.swing.JRadioButton jRJ1m4;
    private javax.swing.JRadioButton jRJ1m5;
    private javax.swing.JRadioButton jRJ1m6;
    private javax.swing.JRadioButton jRJ2m1;
    private javax.swing.JRadioButton jRJ2m2;
    private javax.swing.JRadioButton jRJ2m3;
    private javax.swing.JRadioButton jRJ2m4;
    private javax.swing.JRadioButton jRJ2m5;
    private javax.swing.JRadioButton jRJ2m6;
    private javax.swing.JRadioButton jRJ3m1;
    private javax.swing.JRadioButton jRJ3m2;
    private javax.swing.JRadioButton jRJ3m3;
    private javax.swing.JRadioButton jRJ3m4;
    private javax.swing.JRadioButton jRJ3m5;
    private javax.swing.JRadioButton jRJ3m6;
    // End of variables declaration//GEN-END:variables
}
