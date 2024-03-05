package ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;

import clases.Trainer;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class TrainerDataWindow {

	private JDialog window;
	private JPanel panel_quest;
	private Map<Integer, String> questMap = new HashMap<>();


	public TrainerDataWindow(JFrame parent, Trainer trainer) {
		// ======= MISIONES ========
	    questMap.put(1, "Gana un combate peleando con tu compañero");
	    //questMap.put(2, "Dos");
	    //questMap.put(3, "Tres");
		
		// =========================
		
		window = new JDialog();
		window.setResizable(false);
		window.setTitle("Datos de " + trainer.getName());
		window.getContentPane().setBackground(new Color(135, 206, 235));
		window.getContentPane().setLayout(null);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("pokebola.png")).getImage());
		window.setSize(450, 225);
		window.setLocationRelativeTo(parent); // Centrar la ventana en la pantalla
		
		// Nombre
		JLabel lbl_name = new JLabel(trainer.getName());
		lbl_name.setFont(new Font("Consolas", Font.BOLD, 14));
		lbl_name.setForeground(Color.WHITE);
		lbl_name.setBounds(10, 10, 120, 20);
		window.getContentPane().add(lbl_name);
		
		// Titulo
		JLabel lbl_trainerRank = new JLabel("Entrenador Principiante");
		lbl_trainerRank.setFont(new Font("Consolas", Font.BOLD, 14));
		lbl_trainerRank.setForeground(Color.YELLOW);
		lbl_trainerRank.setBounds(180, 38, 200, 20);
		window.getContentPane().add(lbl_trainerRank);
		
		// Nivel
		JLabel lbl_level = new JLabel("Nivel " + trainer.getLevel());
		lbl_level.setFont(new Font("Consolas", Font.BOLD, 14));
		lbl_level.setForeground(Color.WHITE);
		lbl_level.setBounds(90, 38, 80, 20);
		window.getContentPane().add(lbl_level);
		
		// Imagen Entrenador
		JLabel lbl_trainerImage = new JLabel();
		lbl_trainerImage.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_trainerImage.setBounds(10, 48, 64, 124);
		ImageIcon trainerSprite = new ImageIcon(getClass().getClassLoader().getResource("Entrenador1.png"));
		if(trainer.getStyle() == 2) {
			 trainerSprite = new ImageIcon(getClass().getClassLoader().getResource("Entrenador2.png"));
		}
		lbl_trainerImage.setIcon(trainerSprite);
		window.getContentPane().add(lbl_trainerImage);
		
		// Panel con las misiones
		panel_quest = new JPanel();
		panel_quest.setBorder(new TitledBorder(null, "Lista de Misiones", TitledBorder.LEADING, TitledBorder.TOP, new Font("Consolas", Font.BOLD, 12), null));
		panel_quest.setBounds(90, 68, 325, 100);
		window.getContentPane().add(panel_quest);
		panel_quest.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
		
		switch (trainer.getLevel()) {
		case 1 :
			mostrarMisiones(1);
			break;
		case 2 :
			mostrarMisiones(2);
			mostrarMisiones(3);

		}
		
		// Fondo
		ImageIcon image_background = new ImageIcon(getClass().getClassLoader().getResource("databg.png"));
		JLabel lbl_bg = new JLabel(image_background);
		lbl_bg.setBackground(new Color(255, 255, 255));
		lbl_bg.setBounds(0, 0, 436, 188);
		window.getContentPane().add(lbl_bg);
		
        // Agregar un WindowListener para escuchar el evento de cierre de la ventana
		window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Habilitar el parent JFrame (MainFrame) cuando SecondFrame se cierra
                parent.setEnabled(true);
            }
        });
		
		window.setVisible(true);
	}

	private void mostrarMisiones(int i) {
        String quest_name = questMap.get(i);
		JLabel lbl_quest = new JLabel("<html> - " + quest_name);
		lbl_quest.setFont(new Font("Consolas", Font.BOLD, 10));
		lbl_quest.setSize(250, 20);
		panel_quest.add(lbl_quest);
		
	}
}
