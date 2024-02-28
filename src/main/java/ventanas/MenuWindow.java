package ventanas;

import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clases.Pokemon;

public class MenuWindow {

	private JFrame frame;

	public MenuWindow(Pokemon pk) {
		
		frame = new JFrame();
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 450);
		
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
		
		
		JButton btn_verPokemon = new JButton("Datos Pokémon");
		btn_verPokemon.setFont(new Font("Consolas", Font.BOLD, 12));
		btn_verPokemon.setFocusPainted(false);
		btn_verPokemon.setBackground(new Color(255, 255, 255));
		ImageIcon iconVerPokemon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/telefono-inteligente.png"));
		btn_verPokemon.setIcon(iconVerPokemon);
		btn_verPokemon.setBounds(40, 80, 200, 40);
		btn_verPokemon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					new PokemonDataWindow(frame, pk);
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
		
		JButton btn_batalla = new JButton("Luchar");
		btn_batalla.setFont(new Font("Consolas", Font.BOLD, 12));
		btn_batalla.setFocusPainted(false);
		btn_batalla.setBackground(new Color(255, 255, 255));
		ImageIcon iconBatalla = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/combate.png"));
		btn_batalla.setIcon(iconBatalla);
		btn_batalla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//VentanaCombate ventanaCombate = new VentanaCombate();
				//ventanaCombate.setVisible(true);
			}
		});
		btn_batalla.setBounds(40, 140, 200, 40);
		contentPane.add(btn_batalla);
		
		JButton btn_pokedex = new JButton("Pokédex");
		btn_pokedex.setFont(new Font("Consolas", Font.BOLD, 12));
		btn_pokedex.setFocusPainted(false);
		btn_pokedex.setBackground(new Color(255, 255, 255));
		btn_pokedex.setBounds(40, 200, 200, 40);
		ImageIcon iconPokedex = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/pokedex.png"));
		btn_pokedex.setIcon(iconPokedex);
		btn_pokedex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Añadir ventana de pokedex
			}
		});
		contentPane.add(btn_pokedex);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/bienvenido.png")));
		lblNewLabel.setBounds(86, -4, 98, 41);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/info.png")));
		lblNewLabel_1.setBounds(40, 37, 200, 32);
		contentPane.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 82, 129));
		panel.setBounds(0, 280, 286, 133);
		contentPane.add(panel);
		
		frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
		frame.setTitle("Menú");
		frame.setVisible(true);
		
	}

}
