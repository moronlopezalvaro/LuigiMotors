package taller.app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import taller.app.controller.ConexionBBDD;
import taller.app.model.Cita;
import taller.app.utils.UIUtils;
import java.util.List;

public class VentanaInicial extends JPanel {

    private String nombreCliente;

    public VentanaInicial(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        setLayout(new BorderLayout());
        setOpaque(false);

        // Cabecera con menú unificada
        add(UIUtils.crearCabeceraConMenu("Luigi Motors", "Bienvenido", nombreCliente, this), BorderLayout.NORTH);

        // panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        panelBotones.setOpaque(false);

        // colores de la paleta del proyecto
        Color colorNaranja = Color.decode("#FF6B00");
        Color colorAzulOscuro = Color.decode("#1E3A5F");
        Color colorGrisOscuro = Color.decode("#2C2C2C");

        // boton 1: pedir cita (color naranja, destacado)
        JButton btnPedirCita = UIUtils.createRoundedButton("Pedir cita", colorNaranja, Color.WHITE);
        panelBotones.add(btnPedirCita);

        // boton 2: Calcular gastos (color azul)
        JButton btnCalcularGastos = UIUtils.createRoundedButton("Calcular gastos", colorAzulOscuro, Color.WHITE);
        panelBotones.add(btnCalcularGastos);

        // boton 3: Salir y cerrar sesión (color gris)
        JButton btnSalir = UIUtils.createRoundedButton("Salir y cerrar sesión", colorGrisOscuro, Color.WHITE);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // boton 1: abre la pantalla para pedir cita
        btnPedirCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaInicial.this);
                if (frame != null) {
                    frame.setContentPane(new VentanaPedirCita(nombreCliente));
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        // boton 2: calcular gastos
        btnCalcularGastos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexionBBDD bd = new ConexionBBDD();
                java.util.List<taller.app.model.Cita> citas = bd.obtenerCitasCliente(nombreCliente);

                if (citas.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(VentanaInicial.this,
                            "Actualmente no tienes citas registradas.\nFuncionalidad próximamente para nuevas reparaciones.",
                            "Calcular gastos",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaInicial.this);
                    if (frame != null) {
                        frame.setContentPane(new VentanaGastos(nombreCliente));
                        frame.revalidate();
                        frame.repaint();
                    }
                }
            }
        });

        // boton 3: cerrar sesion
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaInicial.this);
                if (frame != null) {
                    frame.setContentPane(new VentanaLogin());
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        UIUtils.pintarFondoDegradado(g, getWidth(), getHeight());
    }

}
