package ventanas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clases.Pokemon;
import clases.SaveData;
import clases.Trainer;
import javax.swing.SwingConstants;

public class MenuWindow {

	private JFrame frame;

	public MenuWindow(Trainer trainer) {
		
		frame = new JFrame();
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 450);
		
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
		
		// ========== ABRE LA VENTANA DE DATOS DE LOS POKÉMON ==========
		JButton btn_verPokemon = new JButton("Datos Pokémon");
		btn_verPokemon.setFont(new Font("Consolas", Font.BOLD, 12));
		btn_verPokemon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_verPokemon.setFocusPainted(false);
		btn_verPokemon.setBackground(new Color(255, 255, 255));
		ImageIcon iconVerPokemon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/telefono-inteligente.png"));
		btn_verPokemon.setIcon(iconVerPokemon);
		btn_verPokemon.setBounds(40, 80, 200, 40);
		btn_verPokemon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					new PokemonDataWindow(frame, trainer, trainer.getTeam()[trainer.getPartner()]);
					frame.setEnabled(false);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		contentPane.add(btn_verPokemon);

		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(110, 248, 64, 64);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/pokebola.png").getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
		
		// ========== ABRE LA VENTANA DE COMBATE ==========
		JButton btn_batalla = new JButton("Luchar");
		btn_batalla.setFont(new Font("Consolas", Font.BOLD, 12));
		btn_batalla.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_batalla.setFocusPainted(false);
		btn_batalla.setBackground(new Color(255, 255, 255));
		ImageIcon iconBatalla = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/combate.png"));
		btn_batalla.setIcon(iconBatalla);
		btn_batalla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					trainer.addPokemon(new Pokemon(13, 3), 1);
					trainer.addPokemon(new Pokemon("patrat", 4), 2);
					trainer.addPokemon(new Pokemon("fletchinder", 24), 3);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_batalla.setBounds(40, 140, 200, 40);
		contentPane.add(btn_batalla);
		
		// ========== GUARDA LA PARTIDA ==========
		JButton btn_save = new JButton("Guardar Partida");
		btn_save.setFont(new Font("Consolas", Font.BOLD, 12));
		btn_save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_save.setFocusPainted(false);
		btn_save.setBackground(new Color(255, 255, 255));
		btn_save.setBounds(40, 200, 200, 40);
		ImageIcon iconPokedex = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/pokedex.png"));
		btn_save.setIcon(iconPokedex);
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveData.saveTrainer(trainer);
	            // Muestra un mensaje de éxito al guardar la partida
	            JOptionPane.showMessageDialog(null, "Partida guardada exitosamente", "Se ha guardado la partida", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		contentPane.add(btn_save);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/bienvenido.png")));
		lblNewLabel.setBounds(86, -4, 98, 41);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/info.png")));
		lblNewLabel_1.setBounds(40, 37, 200, 32);
		contentPane.add(lblNewLabel_1);
		
		
		// ========= PANEL DE ABAJO ==========
		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 82, 129));
		panel.setBounds(0, 280, 286, 133);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btn_trainerIcon = new JButton("");
		btn_trainerIcon.setToolTipText("Ver Información");
		btn_trainerIcon.setHorizontalAlignment(SwingConstants.CENTER);
		btn_trainerIcon.setBounds(10, 59, 64, 64);
		ImageIcon iconTrainer = new ImageIcon("images/trainer1.png");
		switch (trainer.getStyle()) {
		case 1 :
			// Chico
			iconTrainer = new ImageIcon("images/trainer1.png");
			break;
		case 2 :
			// Chica
			iconTrainer = new ImageIcon("images/trainer2.png");
			break;
		}

		btn_trainerIcon.setIcon(iconTrainer);
		btn_trainerIcon.setBorderPainted(false); // Elimina el borde
		btn_trainerIcon.setContentAreaFilled(false); // Elimina el relleno del área de contenido
		btn_trainerIcon.setFocusPainted(false);
		btn_trainerIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_trainerIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TrainerDataWindow(frame, trainer);
				frame.setEnabled(false);
			}
		});
		panel.add(btn_trainerIcon);
		
		JLabel lbl_trainerRank = new JLabel("Entrenador Principiante");
		lbl_trainerRank.setFont(new Font("Consolas", Font.BOLD, 12));
		lbl_trainerRank.setForeground(Color.YELLOW);
		lbl_trainerRank.setBounds(84, 110, 180, 13);
		panel.add(lbl_trainerRank);
		
		JLabel lbl_trainerLevel = new JLabel("Nv " + trainer.getLevel());
		lbl_trainerLevel.setFont(new Font("Consolas", Font.BOLD, 12));
		lbl_trainerLevel.setForeground(Color.WHITE);
		lbl_trainerLevel.setBounds(84, 87, 120, 13);
		panel.add(lbl_trainerLevel);
		
		JLabel lbl_trainerName = new JLabel(trainer.getName());
		lbl_trainerName.setFont(new Font("Consolas", Font.BOLD, 12));
		lbl_trainerName.setForeground(Color.WHITE);
		lbl_trainerName.setBounds(84, 64, 120, 13);
		panel.add(lbl_trainerName);
		
		frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
		frame.setTitle("Menú");
		frame.setVisible(true);
		
	}
}
