package ventanas;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import clases.Trainer;
import clases.Pokemon;
import javax.swing.SwingConstants;

public class VentanaGachapon extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btn_generar;
	private JLabel lbl_id;
	private JLabel lbl_nivel;
	private JLabel lbl_nom;
	private JLabel lbl_tipo;
	private JLabel lbl_pokemonGenerado;
	private JPanel panel_1;
	private JLabel lbl_label_Saldo;
	private JLabel lbl_imagenGacha;
	private JLabel lbl_saldo;
	private JButton btn_addToTeam;
	private JTextPane textPane;

	private Trainer trainer;
	private Pokemon pokemon;

	public VentanaGachapon(Trainer trainer) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 656, 432);

		this.trainer = trainer;
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 632, 404);
		panel_1.setLayout(null);

		lbl_id = new JLabel("");
		lbl_id.setBounds(306, 71, 62, 13);
		panel_1.add(lbl_id);

		lbl_nivel = new JLabel("");
		lbl_nivel.setBounds(306, 94, 45, 13);
		panel_1.add(lbl_nivel);

		lbl_nom = new JLabel("");
		lbl_nom.setBounds(306, 48, 74, 13);
		panel_1.add(lbl_nom);

		lbl_tipo = new JLabel("");
		lbl_tipo.setBounds(291, 117, 112, 41);
		panel_1.add(lbl_tipo);

		btn_generar = new JButton("Tirar (100)");
		btn_generar.setBounds(240, 209, 179, 30);
		btn_generar.setFont(new Font("Consolas", Font.BOLD, 12));
		btn_generar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (trainer.getMoney()>=100){
					trainer.setMoney(trainer.getMoney() -100); 
				
				

				// Crear un hilo para la generación del Pokémon
				Thread hiloA = new MiHilo_GenerarPokemon("hilo1");
				Thread hiloB = new MiHilo_GenerarPokemon("hilo2");

				// Se arrancan los dos hilos, para que comiencen su ejecución
				hiloA.start();
				hiloB.start();

				System.out.println(Thread.currentThread());
				while (true) {
					if (!hiloA.isAlive()) {
						hiloB.stop();
						break;

					} else if (!hiloB.isAlive()) {
						hiloA.stop();
						break;
					}
				}
				System.out.println("Todos los hilos han terminado");
				}else{
					JOptionPane.showMessageDialog(panel_1, "No tienes saldo suficiente :C");
				}
			}
		 
		});
		
		panel_1.add(btn_generar);

		JSeparator separator = new JSeparator();
		separator.setBounds(45, 249, 480, 2);
		panel_1.add(separator);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(78, 269, 447, 95);
		panel_1.add(textPane);

		btn_addToTeam = new JButton("Meter en el equipo");
		btn_addToTeam.setEnabled(false);
		btn_addToTeam.setBounds(240, 168, 179, 31);
		btn_addToTeam.setFont(new Font("Consolas", Font.BOLD, 12));
		btn_addToTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("Añadido al equipo ");
				showTeam();
				//JOptionPane.showMessageDialog(panel_1, "Se ha añadido el pokemon a tu equipo");
			}
		});
		panel_1.add(btn_addToTeam);

		JLabel lbl_label_nom = new JLabel("Nombre: ");
		lbl_label_nom.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_label_nom.setBounds(240, 48, 62, 13);
		lbl_label_nom.setFont(new Font("Consolas", Font.BOLD, 12));
		panel_1.add(lbl_label_nom);

		JLabel lbl_label_tipo = new JLabel("Tipo:");
		lbl_label_tipo.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_label_tipo.setBounds(240, 119, 62, 13);
		lbl_label_tipo.setFont(new Font("Consolas", Font.BOLD, 12));
		panel_1.add(lbl_label_tipo);

		JLabel lbl_label_id = new JLabel("Id:");
		lbl_label_id.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_label_id.setBounds(240, 71, 59, 13);
		lbl_label_id.setFont(new Font("Consolas", Font.BOLD, 12));
		panel_1.add(lbl_label_id);

		JLabel lbl_label_nivel = new JLabel("Nivel: ");
		lbl_label_nivel.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_label_nivel.setBounds(243, 94, 59, 13);
		lbl_label_nivel.setFont(new Font("Consolas", Font.BOLD, 12));
		panel_1.add(lbl_label_nivel);

		lbl_pokemonGenerado = new JLabel();
		lbl_pokemonGenerado.setBounds(413, 28, 158, 130);
		panel_1.add(lbl_pokemonGenerado);
				
		
		lbl_label_Saldo = new JLabel("SALDO");
		lbl_label_Saldo.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_label_Saldo.setFont(new Font("Consolas", Font.BOLD, 20));
		lbl_label_Saldo.setBounds(450, 169, 91, 24);
		panel_1.add(lbl_label_Saldo);
		
		lbl_saldo = new JLabel("" + trainer.getMoney());
		lbl_saldo.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_label_Saldo.setFont(new Font("Consolas", Font.BOLD, 16));
		lbl_saldo.setBounds(450, 210, 91, 24);
		panel_1.add(lbl_saldo);
		

		ImageIcon icongacha = new ImageIcon(getClass().getClassLoader().getResource("pokemon_gachapon.png"));
		lbl_imagenGacha = new JLabel(new ImageIcon(icongacha.getImage()
				.getScaledInstance(180, 211, Image.SCALE_SMOOTH)));
		lbl_imagenGacha.setBounds(48, 28, 179, 211);
		panel_1.add(lbl_imagenGacha);
	
       
       

		
		setContentPane(panel_1);
		setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("pokebola.png")).getImage());
		setTitle("Gachapon");
		setVisible(true);
	}

	private void definirPokemonGenerado(Pokemon p1) {

		String name = String.valueOf(p1.getName());
		System.out.println(name);
		lbl_nom.setFont(new Font("Consolas", Font.BOLD, 12));

		lbl_nom.setText(name);

		String id = String.valueOf(p1.getId());
		System.out.println(id);
		lbl_id.setFont(new Font("Consolas", Font.BOLD, 12));

		lbl_id.setText(id);

		String nivel = String.valueOf(p1.getLevel());
		System.out.println(p1.getLevel());
		lbl_nivel.setFont(new Font("Consolas", Font.BOLD, 12));

		lbl_nivel.setText(nivel);

		String tipo = "";
		JSONArray types = p1.getTypes();
		for (int i = 0; i < types.length(); i++) {
			JSONObject type = types.getJSONObject(i).getJSONObject("type");
			String nameType = type.getString("name");
			tipo += "\n" + nameType;
		}

		System.out.println(tipo);
		lbl_tipo.setFont(new Font("Consolas", Font.BOLD, 12));

		lbl_tipo.setText(tipo);

		try {
			ImageIcon icon = new ImageIcon(new URL(p1.getSpriteFrontUrl()));
			lbl_pokemonGenerado.setIcon(icon);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.pokemon = p1;
		panel_1.add(lbl_pokemonGenerado);
	}

	class MiHilo_GenerarPokemon extends Thread {

		public MiHilo_GenerarPokemon(String nombreHilo) {
			super(nombreHilo);
		}

		public void run() {
			// Presenta en pantalla información sobre este hilo en particular
			System.out.println(Thread.currentThread().getName());
			Random random = new Random();
			Pokemon p1 = new Pokemon();
			float sumastat = 0;

			while (sumastat >= 0 && !Thread.interrupted()) {
				int randomNumber = random.nextInt(890);
				int nivelRamdom = random.nextInt(10);

				System.out.println(nivelRamdom);
				try {
					p1 = new Pokemon(randomNumber, nivelRamdom);

					JSONArray stats = p1.getPokemonInfo().getJSONArray("stats");
					sumastat = 0;
					float baseStatValue = 0;

					for (int i = 0; i < stats.length(); i++) {
						//JSONObject stat = stats.getJSONObject(i).getJSONObject("stat");
						baseStatValue = (float) stats.getJSONObject(i).getInt("base_stat");
						sumastat += baseStatValue;
					}
					System.out.println(sumastat + Thread.currentThread().getName());

					if (sumastat <= 350) {
						definirPokemonGenerado(p1);
						btn_addToTeam.setEnabled(true);
						break;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e2){

				}
			}

			System.out.println(Thread.currentThread().getName() + " ha terminado");
		}

	} // fin de la clase mi hilo

	private void showTeam() {
		// Dialog Equipo
        JDialog dialog = new JDialog((JFrame) null, "Seleccionar Pokémon", true);
        dialog.setSize(50, panel_1.getHeight() + 100);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    	dialog.setLocationRelativeTo(null);
		dialog.setContentPane(createTeamPanel(dialog));
    	dialog.setVisible(true);
   
	} // fin metodo show

	// metodo que crea el panel del dialogo 
	private JPanel createTeamPanel(JDialog dialog) {
		JPanel panel = new JPanel();
	
        panel.setLayout(new GridLayout(6, 1));
        for (int i = 0; i < 6; i++) {
			JButton btn;
            if (trainer.getTeam()[i] != null) {

                Pokemon pk = trainer.getTeam()[i];

                // Obtener la URL del icono del Pokémon
                URL iconUrl = null;
                try {
                    iconUrl = new URL(pk.getIconUrl());
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }

                // Crear un ImageIcon a partir de la URL
                ImageIcon pokemonIcon = new ImageIcon(iconUrl);

                // Crear un botón con la imagen y el nombre del Pokémon
                btn = new JButton(pk.getNick(), pokemonIcon);
				btn.setPreferredSize(new Dimension(150, 150));
                btn.setFont(new Font("Consolas", Font.BOLD, 11));
                btn.setBackground(new Color(235, 237, 240));
                btn.setForeground(Color.BLACK);
                btn.setHorizontalTextPosition(SwingConstants.CENTER);
                btn.setVerticalTextPosition(SwingConstants.BOTTOM);

				final int slotIndex2 = i;
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();

						int res = JOptionPane.showConfirmDialog(panel,
						"¿Seguro que quieres sobreescribir este Slot?\nEsto liberará al pokemon que se encuentra en él\n\tNombre: "
								+ pk.getNick(),
						"Confirmar Liberación", JOptionPane.YES_NO_OPTION);
							if (res == JOptionPane.YES_OPTION) {
								addPokemonToSlot(slotIndex2);
							} else {
								JOptionPane.showMessageDialog(panel, "Acción cancelada");
							}
						
						
					}
				});// fin btn JDialog
                
                panel.add(btn);
            }else {
				// Create an empty button for empty slots
				btn = new JButton("Slot" + i);
				btn.setEnabled(true);
				
				btn.setPreferredSize(new Dimension(150, 150));
				final int slotIndex = i;
				
				// Boton para cambiar de Pokémon
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
						addPokemonToSlot(slotIndex);
						//System.out.printf("Button Clicked for Pokemon at slot " , i);
					}
				});// fin btn JDialog
			}
			// Add the button to the panel
			panel.add(btn);
        }// fin for
	
		return panel;
	}

	private void addPokemonToSlot(int slotIndex) {
		if (pokemon != null) {
			trainer.getTeam()[slotIndex] = pokemon;
		
			System.out.println("Pokemon added to slot " + (slotIndex + 1));
		}
	}
}
