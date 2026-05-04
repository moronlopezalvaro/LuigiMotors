package taller.app.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import taller.app.view.*;

public class UIUtils {

    public static JPanel crearCabeceraConMenu(String titulo, String subtitulo, String nombreUsuario, JPanel currentPanel) {
        Color colorAzulOscuro = Color.decode("#1E3A5F");
        Color colorGrisOscuro = Color.decode("#2C2C2C");

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(true);
        panelTitulo.setBackground(colorAzulOscuro);
        panelTitulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(15, 20, 10, 20),
                        BorderFactory.createLineBorder(colorGrisOscuro, 2)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        // Título principal
        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        // Panel inferior (Bienvenida + Menú)
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel lblUser = new JLabel(subtitulo + " " + nombreUsuario);
        lblUser.setFont(new Font("Arial", Font.ITALIC, 13));
        lblUser.setForeground(Color.decode("#F5F5F5"));
        panelInferior.add(lblUser, BorderLayout.WEST);

        // Botón Menú
        JButton btnMenu = createHamburgerButton(nombreUsuario, currentPanel);
        panelInferior.add(btnMenu, BorderLayout.EAST);

        panelTitulo.add(panelInferior, BorderLayout.SOUTH);

        return panelTitulo;
    }

    private static JButton createHamburgerButton(String nombreUsuario, JPanel currentPanel) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                int w = getWidth();
                int h = getHeight();
                int thickness = 3; // Un poco más grueso
                int width = 22;
                int x = (w - width) / 2;
                g2.fillRect(x, h/4, width, thickness);
                g2.fillRect(x, h/2 - thickness/2, width, thickness);
                g2.fillRect(x, 3*h/4 - thickness, width, thickness);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(40, 40)); // Un poco más grande
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("Menú de opciones");

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem itemDatos = new JMenuItem("Mi perfil");
        JMenuItem itemCitas = new JMenuItem("Mis citas");

        Font menuFont = new Font("Arial", Font.PLAIN, 14);
        itemDatos.setFont(menuFont);
        itemCitas.setFont(menuFont);

        popupMenu.add(itemDatos);
        popupMenu.add(itemCitas);

        itemDatos.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(btn);
            if (window instanceof JFrame) {
                JFrame frame = (JFrame) window;
                frame.setContentPane(new taller.app.view.VentanaPerfil(nombreUsuario));
                frame.revalidate();
                frame.repaint();
            }
        });

        itemCitas.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(btn);
            if (window instanceof JFrame) {
                JFrame frame = (JFrame) window;
                frame.setContentPane(new taller.app.view.VentanaCitas(nombreUsuario));
                frame.revalidate();
                frame.repaint();
            }
        });

        btn.addActionListener(e -> {
            popupMenu.show(btn, btn.getWidth() - popupMenu.getPreferredSize().width, btn.getHeight());
        });

        return btn;
    }

    public static JButton createRoundedButton(String text, Color bgColor, Color fgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 50));
        return btn;
    }
    
    public static void pintarFondoDegradado(Graphics g, int w, int h) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Color color1 = Color.decode("#F5F5F5");
        Color color2 = Color.decode("#D3DEED");
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
    }
}
