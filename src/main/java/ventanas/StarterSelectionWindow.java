package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import clases.Pokemon;
import clases.SaveData;
import clases.Trainer;
import controlador.generacionesStarters;

public class StarterSelectionWindow {
	
	private JFrame frmPokochi;
	private JLabel lbl_tipoPlanta;
	private JLabel lbl_tipoFuego;
	private JButton btn_grassType;
	private JButton btn_fireType;
	private JButton btn_waterType;
	private JPanel panel_planta;
	private JComboBox<String> cb_generaciones;

	private ArrayList<String> generaciones; // ArrayList para almacenar las generaciones
	private JLabel lbl_tipoAgua;
	private JPanel panel_agua;
	private JPanel panel_fuego;

	public StarterSelectionWindow(Trainer trainer) {
		
		frmPokochi = new JFrame();
		frmPokochi.setResizable(false);
		frmPokochi.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
		frmPokochi.getContentPane().setForeground(Color.BLACK);
		frmPokochi.setFont(new Font("Consolas", Font.PLAIN, 12));
		frmPokochi.setTitle("Pokochi");
		frmPokochi.setForeground(Color.BLACK);
		frmPokochi.setBackground(Color.BLACK);
		frmPokochi.getContentPane().setBackground(new Color(255, 255, 255));
		frmPokochi.setSize(600, 400);
		frmPokochi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPokochi.getContentPane().setLayout(null);

		// Etiquetas para mostrar los tipos de Pokémon
		lbl_tipoAgua = new JLabel("Agua");
		lbl_tipoAgua.setBounds(405, 260, 120, 15);
		lbl_tipoAgua.setForeground(new Color(0, 128, 255));
		lbl_tipoAgua.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_tipoAgua.setFont(new Font("Consolas", Font.BOLD, 12));
		frmPokochi.getContentPane().add(lbl_tipoAgua);

		lbl_tipoPlanta = new JLabel("Planta");
		lbl_tipoPlanta.setBounds(65, 260, 120, 15);
		lbl_tipoPlanta.setForeground(new Color(0, 128, 0));
		lbl_tipoPlanta.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_tipoPlanta.setFont(new Font("Consolas", Font.BOLD, 12));
		frmPokochi.getContentPane().add(lbl_tipoPlanta);

		lbl_tipoFuego = new JLabel("Fuego");
		lbl_tipoFuego.setBounds(235, 260, 120, 15);
		lbl_tipoFuego.setForeground(new Color(255, 0, 0));
		lbl_tipoFuego.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_tipoFuego.setFont(new Font("Consolas", Font.BOLD, 12));
		frmPokochi.getContentPane().add(lbl_tipoFuego);
		
		// ComboBox para seleccionar la generación de Pokémon
		cb_generaciones = new JComboBox<String>();
		cb_generaciones.setBounds(240, 33, 170, 21);
		cb_generaciones.setForeground(new Color(0, 0, 0));
		cb_generaciones.setFont(new Font("Consolas", Font.PLAIN, 11));
		cb_generaciones.setBackground(new Color(243, 243, 243));
		cb_generaciones.setToolTipText("Seleccione la generación...");
		frmPokochi.getContentPane().add(cb_generaciones);
		
		// Inicialización del ArrayList de generaciones y configuración del ComboBox
		generaciones = new ArrayList<String>(); // Inicializa el ArrayList
		generaciones.add("Generación 1");
		generaciones.add("Generación 2");
		generaciones.add("Generación 3");
		generaciones.add("Generación 4");
		generaciones.add("Generación 5");
		generaciones.add("Generación 6");
		generaciones.add("Generación 7");
		generaciones.add("Generación 8");
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(generaciones.toArray(new String[0]));
		cb_generaciones.setModel(comboBoxModel);
		
		frmPokochi.getContentPane().add(cb_generaciones);
		
		cb_generaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGeneration();
			}
		});

		panel_planta = new JPanel();
		panel_planta.setBounds(50, 100, 150, 150);
		panel_planta.setBackground(new Color(228, 240, 189));
		panel_planta.setForeground(new Color(255, 255, 255));
		panel_planta.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		frmPokochi.getContentPane().add(panel_planta);
		panel_planta.setLayout(null);

		btn_grassType = new JButton("Elegir");
		btn_grassType.setBounds(65, 290, 120, 30);
		btn_grassType.setBackground(new Color(237, 247, 204));
		btn_grassType.setFocusPainted(false);
		btn_grassType.setFont(new Font("Consolas", Font.PLAIN, 11));
		btn_grassType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPokemon(trainer, lbl_tipoPlanta.getText());
				frmPokochi.dispose();
			}
		});
		btn_grassType.setForeground(Color.BLACK);
		frmPokochi.getContentPane().add(btn_grassType);

		btn_fireType = new JButton("Elegir");
		btn_fireType.setBounds(235, 290, 120, 30);
		btn_fireType.setBackground(new Color(253, 206, 207));
		btn_fireType.setFocusPainted(false);
		btn_fireType.setFont(new Font("Consolas", Font.PLAIN, 11));
		btn_fireType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPokemon(trainer, lbl_tipoFuego.getText());
				frmPokochi.dispose();
			}
		});
		btn_fireType.setForeground(Color.BLACK);
		frmPokochi.getContentPane().add(btn_fireType);

		btn_waterType = new JButton("Elegir");
		btn_waterType.setBounds(405, 290, 120, 30);
		btn_waterType.setBackground(new Color(223, 236, 255));
		btn_waterType.setFocusPainted(false);
		btn_waterType.setFont(new Font("Consolas", Font.PLAIN, 11));
		btn_waterType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPokemon(trainer, lbl_tipoAgua.getText());
				frmPokochi.dispose();
			}
		});
		btn_waterType.setForeground(Color.BLACK);
		frmPokochi.getContentPane().add(btn_waterType);

		JLabel lblNewLabel = new JLabel("Generación:");
		lblNewLabel.setBackground(new Color(51, 51, 51));
		lblNewLabel.setBounds(160, 30, 123, 32);
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 12));
		lblNewLabel.setLabelFor(cb_generaciones);
		frmPokochi.getContentPane().add(lblNewLabel);

		panel_fuego = new JPanel();
		panel_fuego.setBounds(220, 100, 150, 150);
		panel_fuego.setForeground(new Color(255, 255, 255));
		panel_fuego.setBackground(new Color(253, 206, 207));
		panel_fuego.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		frmPokochi.getContentPane().add(panel_fuego);
		panel_fuego.setLayout(null);

		panel_agua = new JPanel();
		panel_agua.setBounds(390, 100, 150, 150);
		panel_agua.setForeground(Color.WHITE);
		panel_agua.setBackground(new Color(223, 236, 255));
		panel_agua.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		frmPokochi.getContentPane().add(panel_agua);
		panel_agua.setLayout(null);

		cb_generaciones.setSelectedIndex(0);
		updateGeneration();
		
		// Mostrar ventana
		frmPokochi.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		frmPokochi.setVisible(true);
		
	}
	
	public void updateGeneration () {
		String selectedGeneration = (String) cb_generaciones.getSelectedItem();
		String nombreAgua = "";
        String nombrePlanta = "";
        String nombreFuego = "";

        switch (selectedGeneration) {
            case "Generación 1":
                nombreAgua = "Squirtle";
                nombrePlanta = "Bulbasaur";
                nombreFuego = "Charmander";
                break;
            case "Generación 2":
                nombreAgua = "Totodile";
                nombrePlanta = "Chikorita";
                nombreFuego = "Cyndaquil";
                break;
            case "Generación 3":
                nombreAgua = "Mudkip";
                nombrePlanta = "Treecko";
                nombreFuego = "Torchic";
                break;
            case "Generación 4":
                nombreAgua = "Piplup";
                nombrePlanta = "Turtwig";
                nombreFuego = "Chimchar";
                break;
            case "Generación 5":
                nombreAgua = "Oshawott";
                nombrePlanta = "Snivy";
                nombreFuego = "Tepig";
                break;
            case "Generación 6":
                nombreAgua = "Froakie";
                nombrePlanta = "Chespin";
                nombreFuego = "Fennekin";
                break;
            case "Generación 7":
                nombreAgua = "Popplio";
                nombrePlanta = "Rowlet";
                nombreFuego = "Litten";
                break;
            case "Generación 8":
                nombreAgua = "Sobble";
                nombrePlanta = "Grookey";
                nombreFuego = "Scorbunny";
                break;
            // Puedes agregar casos para otras generaciones aquí si es necesario
            default:
                // Manejo para generaciones no implementadas o no válidas
                break;
        }

        lbl_tipoAgua.setText(nombreAgua);
        lbl_tipoPlanta.setText(nombrePlanta);
        lbl_tipoFuego.setText(nombreFuego);

        generacionesStarters.mostrarPokemonGeneracion(nombrePlanta, nombreFuego, nombreAgua, panel_planta, panel_fuego, panel_agua);
	
	}
	
	private void mostrarPokemon(Trainer trainer, String nombrePokemon) {
		// Crear una nueva ventana emergente (JOptionPane)
		final JFrame frame = new JFrame("¡Bienvenido!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		frame.setResizable(false);
        
		// Crear un JPanel para organizar los componentes
		JPanel panel = new JPanel(new BorderLayout());
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));

		// Crear un JLabel para mostrar el nombre del Pokémon
		JLabel lblNombrePokemon = new JLabel("¡Has elegido a " + nombrePokemon + "!");
		lblNombrePokemon.setFont(new Font("Consolas", Font.BOLD, 18));
		lblNombrePokemon.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNombrePokemon, BorderLayout.NORTH);

		// Obtener la URL del GIF del Pokémon elegido
		generacionesStarters starters = new generacionesStarters();
		URL gifURL = starters.obtenerGifPokemon(nombrePokemon);
		if (gifURL != null) {
			// Crear un JLabel para mostrar el GIF del Pokémon
			ImageIcon gifIcon = new ImageIcon(gifURL);
			JLabel lblGifPokemon = new JLabel(gifIcon);
			panel.add(lblGifPokemon, BorderLayout.CENTER);
		} else {
			// Si no se puede obtener el GIF, mostrar un mensaje de error
			JLabel lblError = new JLabel("No se pudo cargar el GIF del Pokémon.");
			lblError.setFont(new Font("Consolas", Font.PLAIN, 12));
			lblError.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(lblError, BorderLayout.CENTER);
		}

		// Crear un JPanel para contener los botones
		JPanel buttonPanel = new JPanel(new FlowLayout());

		// Botón para volver a elegir el Pokémon
		JButton btnElegirPokemon = new JButton("Volver a elegir");
		btnElegirPokemon.setBackground(new Color(204, 211, 222));
		btnElegirPokemon.setFocusPainted(false);
		btnElegirPokemon.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnElegirPokemon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Cerrar la ventana emergente
				new StarterSelectionWindow(trainer);
				frame.dispose();
				
			}
		});
		buttonPanel.add(btnElegirPokemon);

		// Botón para jugar
		JButton btnJugar = new JButton("Comenzar");
		btnJugar.setBackground(new Color(204, 211, 222));
		btnJugar.setFocusPainted(false);
		btnJugar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnJugar.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				Pokemon pokemon;
				String pkname = nombrePokemon.toLowerCase();
				try {
					pokemon = new Pokemon(pkname, 5);
					trainer.addPokemon(pokemon, 0);
					SaveData.saveTrainer(trainer);
					
					new MenuWindow(trainer);
	                frame.dispose(); // asumiendo que "frmPokochi" es la instancia de tu ventana actual
	                
				} catch (IOException e1) {
					e1.printStackTrace();
				}

            }
        });
		buttonPanel.add(btnJugar);

		// Agregar el panel de botones al panel principal
		panel.add(buttonPanel, BorderLayout.SOUTH);

		// Agregar el panel principal a la ventana
		frame.getContentPane().add(panel);

		// Ajustar el tamaño de la ventana y hacerla visible
		frame.setSize(350, 250);
		frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		frame.setVisible(true);
	}

}
