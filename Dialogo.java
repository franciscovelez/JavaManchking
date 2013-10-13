package munchkin;

/**
 * Clase Diálogo para múltiples usos. Al constructor se le pasa un string que será
 * mostrado en una nueva ventana. Para continuar con el juego hay que darle al
 * botón "ok" para devolverle el control a VentanaPrincipal.
 * 
 * @author Grupo
 */
public class Dialogo extends javax.swing.JDialog {
protected javax.swing.JTextArea  Mens = new javax.swing.JTextArea();
    public Dialogo(java.awt.Frame parent, boolean modal, String msg) {        
        super(parent, modal);        
        initComponents();     
        Mens.setText(msg);
        Mens.setLineWrap(true);
        Mens.setWrapStyleWord(true);
        Mens.setMargin(new java.awt.Insets(2, 2, 2, 2));
        Mens.setBackground(new java.awt.Color(240, 240, 240));
        Mens.setFont(new java.awt.Font("Tahoma", 0, 14));
        Mens.setEditable(false);
        Mens.setEnabled(false);
        Mens.setDisabledTextColor(java.awt.Color.black);
        this.add(Mens, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 260, 100));
        this.setVisible(true);
    }   

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(308, 200));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        OK.setText("OK");
        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKActionPerformed(evt);
            }
        });
        getContentPane().add(OK, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKActionPerformed
    this.dispose();
}//GEN-LAST:event_OKActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OK;
    // End of variables declaration//GEN-END:variables
}
