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

public class TrainerDataWindow {

	private JDialog window;
	private JPanel panel_quest;

	public TrainerDataWindow(JFrame parent, Trainer trainer) {
		window = new JDialog();
		window.setResizable(false);
		window.setTitle("Datos de " + trainer.getName());
		window.getContentPane().setBackground(new Color(135, 206, 235));
		window.getContentPane().setLayout(null);
		
		// Nombre
		JLabel lbl_name = new JLabel(trainer.getName());
		lbl_name.setFont(new Font("Consolas", Font.BOLD, 14));
		lbl_name.setForeground(Color.WHITE);
		lbl_name.setBounds(100, 20, 120, 20);
		window.getContentPane().add(lbl_name);
		
		// Titulo
		JLabel lbl_trainerRank = new JLabel("Entrenador Principiante");
		lbl_trainerRank.setFont(new Font("Consolas", Font.BOLD, 14));
		lbl_trainerRank.setForeground(Color.YELLOW);
		lbl_trainerRank.setBounds(100, 40, 200, 20);
		window.getContentPane().add(lbl_trainerRank);
		
		// Nivel
		JLabel lbl_level = new JLabel("Nivel " + trainer.getLevel());
		lbl_level.setFont(new Font("Consolas", Font.BOLD, 14));
		lbl_level.setForeground(Color.WHITE);
		lbl_level.setBounds(100, 60, 120, 20);
		window.getContentPane().add(lbl_level);
		
		// Panel con las misiones
		panel_quest = new JPanel();
		panel_quest.setBorder(new TitledBorder(null, "Lista de Misiones", TitledBorder.LEADING, TitledBorder.TOP, new Font("Consolas", Font.BOLD, 12), null));
		panel_quest.setBounds(100, 80, 300, 150);
		window.getContentPane().add(panel_quest);
		panel_quest.setLayout(null);
		
		switch (trainer.getLevel()) {
		case 1 :
			mostrarMisiones(1);
			break;
		}
		

		
		
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
		window.setSize(450, 300);
		window.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		
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
		Map<Integer, String> questMap = new HashMap<>();
        questMap.put(1, "Gana un combate peleando con tu compañero");
        //questMap.put(2, "Dos");
        //questMap.put(3, "Tres");
        
        String quest_name = questMap.get(i);
		JLabel lbl_quest = new JLabel("<html> - " + quest_name);
		lbl_quest.setFont(new Font("Consolas", Font.BOLD, 10));
		lbl_quest.setBounds(20, 20, 250, 20);
		panel_quest.add(lbl_quest);
		
	}
}
