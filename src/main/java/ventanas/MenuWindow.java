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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
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
		//frame.setSize(300, 450);
		frame.setSize(375, 450);
		
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
		ImageIcon iconVerPokemon = new ImageIcon(getClass().getClassLoader().getResource("telefono-inteligente.png"));
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
		lblNewLabel_2.setIcon(new ImageIcon (new ImageIcon(getClass().getClassLoader().getResource("pokebola.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
		
		// ========== ABRE LA VENTANA DE COMBATE ==========
		JButton btn_batalla = new JButton("Luchar");
		btn_batalla.setFont(new Font("Consolas", Font.BOLD, 12));
		btn_batalla.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_batalla.setFocusPainted(false);
		btn_batalla.setBackground(new Color(255, 255, 255));
		ImageIcon iconBatalla = new ImageIcon(getClass().getClassLoader().getResource("combate.png"));
		btn_batalla.setIcon(iconBatalla);
		btn_batalla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        // Lista de nombres de Pokémon para la ruta 1
		        ArrayList<String> pknames = new ArrayList<>();
		        pknames.add("rattata");
		        pknames.add("pidgey");
		        pknames.add("spearow");
		        pknames.add("sentret");
		        pknames.add("hoothoot");
		        pknames.add("zigzagoon");
		        pknames.add("taillow");
		        pknames.add("bidoof");
		        pknames.add("starly");
		        pknames.add("lillipup");
		        pknames.add("patrat");
		        pknames.add("pidove");
		        pknames.add("bunnelby");
		        pknames.add("fletchling");
		        pknames.add("yungoos");
		        pknames.add("pikipek");
		        pknames.add("wooloo");
		        pknames.add("rookidee");
		        
		        // Acceder aleatoriamente a un nombre de Pokémon
		        Random random = new Random();
		        int indiceAleatorio = random.nextInt(pknames.size());
		        String enemy_name = pknames.get(indiceAleatorio);
		        
		        // Seleccionar nivel del pokemon
		        Random random2 = new Random();
		        int enemy_level = random2.nextInt(4) +1;
		        
		        Pokemon enemy;
				try {
					enemy = new Pokemon(enemy_name, enemy_level);
			        
					// Crear una instancia de CombateWindow y pasar el objeto Trainer
					BattleWindow battleWindow = new BattleWindow(frame, trainer, trainer.getTeam()[trainer.getPartner()], enemy);
					frame.setVisible(false);
			        
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
		ImageIcon iconPokedex = new ImageIcon(getClass().getClassLoader().getResource("pokedex.png"));
		btn_save.setIcon(iconPokedex);
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/// --- Guardamos el datetime de la ultima conexion en el trainer 
				LocalDateTime ahora= LocalDateTime.now();//("01/01/01(00:00)")
				trainer.setLastConnected(ahora.format(DateTimeFormatter.ofPattern("uuuu/MM/dd (HH:mm)")));

				SaveData.saveTrainer(trainer);
	            // Muestra un mensaje de éxito al guardar la partida
	            JOptionPane.showMessageDialog(null, "Partida guardada exitosamente", "Se ha guardado la partida", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		contentPane.add(btn_save);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("bienvenido.png")));
		lblNewLabel.setBounds(86, -4, 98, 41);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("info.png")));
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
		ImageIcon iconTrainer = new ImageIcon(getClass().getClassLoader().getResource("trainer1.png"));
		switch (trainer.getStyle()) {
		case 1 :
			// Chico
			break;
		case 2 :
			// Chica
			iconTrainer = new ImageIcon(getClass().getClassLoader().getResource("trainer2.png"));
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
		
		

		// parte de silvia 

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(284, -4, 2, 415);
		contentPane.add(separator);
		
		JLabel lbl_label_Saldo = new JLabel("Saldo");
		lbl_label_Saldo.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_label_Saldo.setBounds(284, 10, 66, 15);
		lbl_label_Saldo.setFont(new Font("Consolas", Font.BOLD, 12));
		contentPane.add(lbl_label_Saldo);
		
		JLabel lbl_saldo = new JLabel("" + trainer.getMoney());
		lbl_saldo.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_saldo.setBounds(286, 25, 64, 32);
		lbl_saldo.setFont(new Font("Consolas", Font.BOLD, 12));
		contentPane.add(lbl_saldo);
		
		JButton btn_tienda = new JButton();
		btn_tienda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JFrame mapa = new JFrame(new TiendaPokemon());
				new VentanaGachapon(trainer);
				
				//mapa.setLocationRelativeTo(frame);
			}
		});
		btn_tienda.setBackground(Color.WHITE);
		btn_tienda.setBounds(296, 80, 51, 54);
		btn_tienda.setFocusable(false);
		btn_tienda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_tienda.setBorder(null);
		btn_tienda.setFont(new Font("Consolas", Font.PLAIN, 10));
		btn_tienda.setFocusPainted(false);
		btn_tienda.setBorderPainted(false);
		btn_tienda.setForeground(Color.WHITE);
		ImageIcon iconTienda = new ImageIcon(getClass().getClassLoader().getResource("tienda.png"));
		btn_tienda.setIcon(new ImageIcon(iconTienda.getImage()
				.getScaledInstance(34, 34, Image.SCALE_SMOOTH)));
		contentPane.add(btn_tienda);
		
		// BOTON DE MAPA QUE GENERA UN JFRAME QUE POR EL MOMENTO SOLO CONTIENE UNA IMAGEN 
		JButton btn_mapa = new JButton();
		btn_mapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame mapa = new JFrame();
				mapa.setLocationRelativeTo(frame);
				mapa.setSize(500, 500);
				mapa.setVisible(true);
				mapa.setTitle("Mapa");
				
				
				// CAMBIAR POR EL MAPA DE SAMU 
				//ImageIcon iconMapa= new ImageIcon(getClass().getClassLoader().getResource("mapa.png"));

				ImageIcon iconMapa= new ImageIcon(getClass().getClassLoader().getResource("mapaSamuel.jpg"));
				JLabel lblBackground = new JLabel();
				//ImageIcon imageIcon = new ImageIcon("images/200px-Sinnoh_mapa_juegos.png");
			
				lblBackground = new JLabel(new ImageIcon(iconMapa.getImage()
					.getScaledInstance(480, 320, Image.SCALE_SMOOTH)));
				
				//JLabel lblBackground = new JLabel(new ImageIcon(iconMapa.getImage()
				//	.getScaledInstance(34, 34, Image.SCALE_SMOOTH)));
				lblBackground.setBounds(0, 0, 480, 320);
				mapa.getContentPane().add(lblBackground);
				mapa.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("pokebola.png")).getImage());
				
			}
		});
		btn_mapa.setBackground(Color.WHITE);
		btn_mapa.setBounds(296, 126, 51, 54);
		btn_mapa.setFocusable(false);
		btn_mapa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_mapa.setBorder(null);
		btn_mapa.setFont(new Font("Consolas", Font.PLAIN, 10));
		// añadir imagenes desde la raiz reescalables (getScaledInstance())
		ImageIcon iconMap = new ImageIcon(getClass().getClassLoader().getResource("mapa.png"));
		btn_mapa.setIcon(new ImageIcon(iconMap.getImage()
				.getScaledInstance(34, 34, Image.SCALE_SMOOTH)));

		contentPane.add(btn_mapa);

		// mostrar la frame 
		frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("pokebola.png")).getImage());
		frame.setTitle("Menú");
		frame.setVisible(true);
		
	}
}
