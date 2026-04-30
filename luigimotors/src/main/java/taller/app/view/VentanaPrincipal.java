package taller.app.view;

import javax.swing.JFrame;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("Luigi Motors");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Menu menu = new Menu();
        setContentPane(menu);
    }
}