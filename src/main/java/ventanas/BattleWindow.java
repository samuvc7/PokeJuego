package ventanas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.net.HttpURLConnection;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import clases.Move;
import clases.Pokemon;
import clases.Trainer;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;

public class BattleWindow {

	private JFrame frame;

	private JPanel ctrl_panel;
	private JTextArea txtArea_ctrl;
	private JButton btnLuchar;
	private JButton btnPokemon;
	private JButton btnHuir;

	private JPanel panel_vida;
	private JLabel lbl_pokeNick;
	private JLabel lbl_pokeLevel;
	private JProgressBar bar_pokeHp;
	private JLabel lbl_pokeHpData;

	private JPanel panel_vida_1;
	private JLabel lbl_enemyNick;
	private JLabel lbl_enemyLevel;
	private JProgressBar bar_enemyHp;

	private JLabel lbl_sprite;
	private JLabel lbl_enemySprite;

	private Trainer trainer;
	private Pokemon pokemon;
	private Pokemon enemy;

	private Move selectedMove;

	public BattleWindow(JFrame parent, Trainer trainer, Pokemon pokemon, Pokemon enemy) {

		this.trainer = trainer;
		this.pokemon = pokemon;
		this.enemy = enemy;

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(440, 370);
		frame.setResizable(false);
		frame.setTitle("Combate");
		frame.setLocationRelativeTo(parent);
		frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("pokebola.png")).getImage());
		frame.getContentPane().setLayout(null);

		// Control Panel
		ctrl_panel = new JPanel();
		ctrl_panel.setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
		ctrl_panel.setBackground(new Color(255, 255, 255));
		ctrl_panel.setBounds(0, 240, 426, 93);
		ctrl_panel.setLayout(null);
		frame.getContentPane().add(ctrl_panel);

		// Panel Vida
		panel_vida = new JPanel();
		panel_vida.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_vida.setBackground(new Color(255, 255, 255, 128));
		panel_vida.setBounds(236, 166, 180, 64);
		panel_vida.setLayout(null);
		frame.getContentPane().add(panel_vida);

		lbl_pokeNick = new JLabel();
		lbl_pokeNick.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_pokeNick.setFont(new Font("Consolas", Font.BOLD, 12));
		lbl_pokeNick.setBounds(10, 0, 120, 20);
		panel_vida.add(lbl_pokeNick);

		lbl_pokeLevel = new JLabel();
		lbl_pokeLevel.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_pokeLevel.setFont(new Font("Consolas", Font.BOLD, 12));
		lbl_pokeLevel.setBounds(120, 0, 50, 20);
		panel_vida.add(lbl_pokeLevel);

		bar_pokeHp = new JProgressBar();
		bar_pokeHp.setBounds(10, 25, 140, 11);
		bar_pokeHp.setBackground(Color.LIGHT_GRAY);
		panel_vida.add(bar_pokeHp);

		lbl_pokeHpData = new JLabel();
		lbl_pokeHpData.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_pokeHpData.setFont(new Font("Consolas", Font.BOLD, 12));
		lbl_pokeHpData.setBounds(86, 40, 64, 20);
		panel_vida.add(lbl_pokeHpData);

		// Panel Vida Enemigo
		panel_vida_1 = new JPanel();
		panel_vida_1.setLayout(null);
		panel_vida_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_vida_1.setBackground(new Color(255, 255, 255, 128));
		panel_vida_1.setBounds(10, 10, 180, 40);
		frame.getContentPane().add(panel_vida_1);

		lbl_enemyNick = new JLabel();
		lbl_enemyNick.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_enemyNick.setFont(new Font("Consolas", Font.BOLD, 12));
		lbl_enemyNick.setBounds(10, 0, 120, 20);
		panel_vida_1.add(lbl_enemyNick);

		lbl_enemyLevel = new JLabel();
		lbl_enemyLevel.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_enemyLevel.setFont(new Font("Consolas", Font.BOLD, 12));
		lbl_enemyLevel.setBounds(120, 0, 50, 20);
		panel_vida_1.add(lbl_enemyLevel);

		bar_enemyHp = new JProgressBar();
		bar_enemyHp.setBackground(Color.LIGHT_GRAY);
		bar_enemyHp.setBounds(10, 20, 140, 11);
		panel_vida_1.add(bar_enemyHp);

		// Sprites
		lbl_sprite = new JLabel();
		lbl_sprite.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_sprite.setBounds(0, 35, 250, 250);
		frame.getContentPane().add(lbl_sprite);

		lbl_enemySprite = new JLabel();
		lbl_enemySprite.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_enemySprite.setBounds(176, -35, 250, 250);
		frame.getContentPane().add(lbl_enemySprite);

		// ================= CARGAR LOS POKÉMON ==============
		// 0 - Jugador / 1 - Enemigo
		updatePokemon(pokemon, 0);
		updatePokemon(enemy, 1);
		// ===================================================

		// ================= Panel de Control ================

		// Area de Texto
		txtArea_ctrl = new JTextArea();
		txtArea_ctrl.setEditable(false);
		txtArea_ctrl.setWrapStyleWord(true);
		txtArea_ctrl.setLineWrap(true);
		txtArea_ctrl.setForeground(new Color(128, 128, 0));
		txtArea_ctrl.setBackground(new Color(255, 255, 255));
		txtArea_ctrl.setFont(new Font("Consolas", Font.BOLD, 16));
		txtArea_ctrl.setText("¿QUÉ DESEA HACER?");
		txtArea_ctrl.setBounds(10, 10, 196, 70);
		ctrl_panel.add(txtArea_ctrl);
		txtArea_ctrl.setColumns(10);

		// Botón Luchar
		btnLuchar = new JButton("LUCHAR");
		btnLuchar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setEnabled(false);
				showMoves();

			}
		});
		btnLuchar.setForeground(new Color(255, 0, 0));
		btnLuchar.setBackground(new Color(240, 202, 202));
		btnLuchar.setFont(new Font("Consolas", Font.BOLD, 14));
		btnLuchar.setBounds(225, 10, 188, 30);
		ctrl_panel.add(btnLuchar);

		// Botón Pokémon
		btnPokemon = new JButton("POKÉMON");
		btnPokemon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setEnabled(false);
				showTeam();

			}
		});
		btnPokemon.setForeground(new Color(0, 128, 0));
		btnPokemon.setBackground(new Color(189, 255, 149));
		btnPokemon.setFont(new Font("Consolas", Font.BOLD, 14));
		btnPokemon.setBounds(225, 50, 92, 30);
		ctrl_panel.add(btnPokemon);

		// Botón Huir
		btnHuir = new JButton("HUIR");
		btnHuir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();

				parent.setLocationRelativeTo(parent);
				parent.setVisible(true);

			}

		});
		btnHuir.setForeground(new Color(60, 60, 255));
		btnHuir.setBackground(new Color(162, 199, 240));
		btnHuir.setFont(new Font("Consolas", Font.BOLD, 14));
		btnHuir.setBounds(321, 50, 92, 30);
		ctrl_panel.add(btnHuir);

		// ===================================================

		// ================= Background Image ================
		ImageIcon image_background = new ImageIcon(getClass().getClassLoader().getResource("fondoCombate.png"));
		JLabel lbl_bg = new JLabel(image_background);
		lbl_bg.setBounds(0, 0, 430, 240);
		frame.getContentPane().add(lbl_bg);

		// ===================================================

		frame.setVisible(true);

	}

	private void updatePokemon(Pokemon pokemon, int i) {
		if (i == 0) {
			this.pokemon = pokemon;
		} else {
			this.enemy = pokemon;
		}
		// ================= Paneles de Vida ===============
		cargarVida(pokemon, i);

		// =================================================

		// ================= Cargar gif ====================
		try {
			cargarGif(pokemon, i);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// =================================================

		panel_vida.setVisible(false);
		panel_vida_1.setVisible(false);
		panel_vida.setVisible(true);
		panel_vida_1.setVisible(true);
	}

	private void cargarVida(Pokemon pokemon, int i) {
		if (i == 0) {
			// Pokémon
			lbl_pokeNick.setText(pokemon.getNick());

			if (!pokemon.getGender().equals("Unknown")) {
				ImageIcon imageGender;
				if (pokemon.getGender().equals("Female")) {
					imageGender = new ImageIcon(getClass().getClassLoader().getResource("gender_female.png"));
				} else {
					imageGender = new ImageIcon(getClass().getClassLoader().getResource("gender_male.png"));
				}
				lbl_pokeNick.setIcon(imageGender);
			}

			lbl_pokeLevel.setText("Nv" + pokemon.getLevel());

			bar_pokeHp.setMaximum(pokemon.getMax_hp());
			bar_pokeHp.setValue(pokemon.getCur_hp());
			bar_pokeHp.setForeground(Color.GREEN);
			if (bar_pokeHp.getValue() < bar_pokeHp.getMaximum() / 2) {
				bar_pokeHp.setForeground(Color.YELLOW);
			}
			if (bar_pokeHp.getValue() < bar_pokeHp.getMaximum() / 5) {
				bar_pokeHp.setForeground(Color.RED);
			}

			lbl_pokeHpData.setText("" + pokemon.getCur_hp() + "/" + pokemon.getMax_hp());

		} else {
			// Enemigo
			lbl_enemyNick.setText(pokemon.getNick());

			if (!pokemon.getGender().equals("Unknown")) {
				ImageIcon imageGender;
				if (pokemon.getGender().equals("Female")) {
					imageGender = new ImageIcon(getClass().getClassLoader().getResource("gender_female.png"));
				} else {
					imageGender = new ImageIcon(getClass().getClassLoader().getResource("gender_male.png"));
				}
				lbl_enemyNick.setIcon(imageGender);
			}

			lbl_enemyLevel.setText("Nv" + pokemon.getLevel());

			bar_enemyHp.setMaximum(pokemon.getMax_hp());
			bar_enemyHp.setValue(pokemon.getCur_hp());
			bar_enemyHp.setForeground(Color.GREEN);
			if (bar_enemyHp.getValue() < bar_enemyHp.getMaximum() / 2) {
				bar_enemyHp.setForeground(Color.YELLOW);
			}
			if (bar_enemyHp.getValue() < bar_enemyHp.getMaximum() / 5) {
				bar_enemyHp.setForeground(Color.RED);
			}

		}

	}

	private void cargarGif(Pokemon pokemon, int i) throws MalformedURLException {
		ImageIcon imageIcon;
		if (i == 0) {
			// Sprite del Pokémon del jugador
			imageIcon = new ImageIcon(new URL(pokemon.getSpriteBackUrl()));
			lbl_sprite.setIcon(imageIcon);

		} else {
			// Sprite del Pokémon enemigo
			imageIcon = new ImageIcon(new URL(pokemon.getSpriteFrontUrl()));
			lbl_enemySprite.setIcon(imageIcon);

		}

	}

	private void showTeam() {
		// Dialog Equipo
		JDialog dialog = new JDialog((JFrame) null, "Seleccionar Pokémon", true);
		dialog.setSize(frame.getWidth() / 2, frame.getHeight());
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 1));
		for (int i = 0; i < 6; i++) {
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
				JButton btn = new JButton(pk.getNick(), pokemonIcon);
				btn.setFont(new Font("Consolas", Font.BOLD, 11));
				btn.setBackground(new Color(235, 237, 240));
				btn.setForeground(Color.BLACK);
				btn.setHorizontalTextPosition(SwingConstants.CENTER);
				btn.setVerticalTextPosition(SwingConstants.BOTTOM);

				// Boton para cambiar de Pokémon
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
						changePoke(pk);

					}
				});// fin btn JDialog
				panel.add(btn);
			}
		}

		JScrollPane scrollPane = new JScrollPane(panel);
		dialog.getContentPane().add(scrollPane);
		dialog.setLocation(frame.getLocation().x + frame.getWidth() + 5, frame.getLocation().y);
		dialog.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("pokebola.png")).getImage());
		dialog.setVisible(true);

		// Agregar un WindowListener para escuchar el evento de cierre de la ventana
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				frame.setVisible(false);
				frame.setVisible(true);
				frame.setEnabled(true);
			}
		});

	}

	private void changePoke(Pokemon pk) {
		frame.setEnabled(false);

		updatePokemon(pk, 0);
		frame.setVisible(false);
		frame.setVisible(true);

		// Acceder aleatoriamente a un movimiento del Pokémon
		Random random = new Random();
		int indiceAleatorio = random.nextInt(enemy.getMoves().length);
		Move move = enemy.getMoves()[indiceAleatorio];
		// Ataque del enemigo
		ataque(enemy, move, pokemon);

	}

	private void ataque(Pokemon from, Move move, Pokemon to) {
		btnLuchar.setEnabled(false);
		btnPokemon.setEnabled(false);
		btnHuir.setEnabled(false);

		String msg = from.getNick() + " ha usado " + move.getName();
		txtArea_ctrl.setText(""); // Limpiar el texto existente en el JTextArea
		escribirPocoAPoco(msg, 30); // Llamar al método para escribir poco a poco

		// Lógica para escribir el segundo mensaje después del primero
		Timer timer = new Timer(30 * msg.length() + 100, null); // Ajusta el tiempo según el primer mensaje
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLuchar.setEnabled(false);
				btnPokemon.setEnabled(false);
				btnHuir.setEnabled(false);

				String msg;
				if (move.getPower() > 0) {
					// Lógica del Ataque
					Random random = new Random();
					int resultado = random.nextInt(100); // Generar un número aleatorio del 0 al 99

					if (resultado < move.getAccuracy()) {
						// El ataque tiene éxito

						// Bonus por Stab
						double B = 1;
						JSONArray types = from.getTypes();
						for (int i = 0; i < types.length(); i++) {
							JSONObject type = types.getJSONObject(i).getJSONObject("type");
							String nameType = type.getString("name");

							if (nameType.equals(move.getName()))
								B = 1.5;

						}

						// Efectividad
						double E = 1;
						try {
							E = efective(move.getType(), to.getTypes());

						} catch (IOException e1) {
							e1.printStackTrace();
						}

						// Variable random
						random = new Random();
						resultado = random.nextInt(16) + 85; // Generar un número aleatorio del 85 al 100
						int V = resultado;

						int N = from.getLevel();

						// Stat At
						int A = from.getAtk();
						if (move.getDamage().equals("special")) {
							A = from.getSp_atk();
						}

						// Poder
						int P = move.getPower();

						// Stat Def
						int D = from.getDef();
						if (move.getDamage().equals("special")) {
							D = from.getSp_def();
						}

						double damage = 0.01 * B * E * V * ((((0.2 * N + 1) * A * P) / (25 * D)) + 2);

						updateBar(to, damage);

						msg = "¡El ataque de " + from.getNick() + " fue exitoso!";

					} else {
						// El ataque falla debido a la baja precisión
						msg = "El ataque de " + from.getNick() + " ha fallado.";
					}
				} else {
					msg = "El ataque de " + from.getNick() + " ha fallado.";
				}
				txtArea_ctrl.setText(""); // Limpiar el texto existente en el JTextArea
				escribirPocoAPoco(msg, 30); // Escribir el segundo mensaje

				timer.stop(); // Detener el timer después de escribir el segundo mensaje
			}

		});
		timer.start(); // Iniciar el timer para el segundo mensaje

	}

	private void escribirPocoAPoco(String texto, int retrasoEntreLetras) {

		Timer timer = new Timer(retrasoEntreLetras, null);
		timer.addActionListener(new ActionListener() {
			int index = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (index < texto.length()) {
					txtArea_ctrl.append(String.valueOf(texto.charAt(index)));
					index++;
					btnLuchar.setEnabled(false);
    btnPokemon.setEnabled(false);
    btnHuir.setEnabled(false);
				} else {
					timer.stop(); // Detener el timer cuando se haya escrito todo el texto
					btnLuchar.setEnabled(true);
					btnPokemon.setEnabled(true);
					btnHuir.setEnabled(true);
				}
			}
		});
		timer.start(); // Iniciar el timer
	}

	public double efective(String from, JSONArray types) throws IOException {
		double efectividad = 1;
		String url = "https://pokeapi.co/api/v2/type/" + from;
		JSONObject info = getInfo(url);
		for (int i = 0; i < types.length(); i++) {
			JSONObject type = types.getJSONObject(i).getJSONObject("type");
			String to = type.getString("name");

			JSONArray doble = info.getJSONObject("damage_relations").getJSONArray("double_damage_to");
			for (int j = 0; j < doble.length(); j++) {
				JSONObject obj = doble.getJSONObject(j);
				if (obj.getString("name").equals(to)) {
					efectividad *= 2;
					break;
				}
			}

			JSONArray mitad = info.getJSONObject("damage_relations").getJSONArray("half_damage_to");
			for (int j = 0; j < mitad.length(); j++) {
				JSONObject obj = mitad.getJSONObject(j);
				if (obj.getString("name").equals(to)) {
					efectividad *= 0.5;
					break;
				}
			}

			JSONArray nada = info.getJSONObject("damage_relations").getJSONArray("no_damage_to");
			for (int j = 0; j < nada.length(); j++) {
				JSONObject obj = nada.getJSONObject(j);
				if (obj.getString("name").equals(to)) {
					efectividad *= 0;
					break;
				}
			}

		}

		return efectividad;
	}

	private static JSONObject getInfo(String url) throws IOException {
		HttpURLConnection connection = null;
		try {
			URL apiUrl = new URL(url);
			connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("GET");

			StringBuilder response = new StringBuilder();
			try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				String inputLine;
				while ((inputLine = inputReader.readLine()) != null) {
					response.append(inputLine);
				}
			}
			return new JSONObject(response.toString());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private void updateBar(Pokemon to, double damage) {
		int danio = (int) Math.round(damage);

		to.setCur_hp(to.getCur_hp() - danio);
		updatePokemon(this.pokemon, 0);
		updatePokemon(this.enemy, 1);

	}

	private void showMoves() {
		// Dialog Equipo
		JDialog dialog = new JDialog((JFrame) null, "Seleccionar Ataque", true);
		dialog.setSize(frame.getWidth() / 2, frame.getHeight());
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));
		for (int i = 0; i < pokemon.getMoves().length; i++) {
			Move move = pokemon.getMoves()[i];

			// Crear un botón con cada movimiento
			JButton btn = new JButton(move.getName());
			btn.setFont(new Font("Consolas", Font.BOLD, 11));
			btn.setForeground(Color.BLACK);
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			btn.setVerticalTextPosition(SwingConstants.BOTTOM);
			btn.setFocusable(false);
			btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btn.setFocusPainted(false);
			btn.setForeground(Color.WHITE);
			btn.setBackground(obtenerColorTipo(pokemon.getMoves()[i]));

			// Boton para usar un movimiento
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();

					// Ejecutar el ataque del Pokémon del jugador
					ataquePokemonJugador(move);

					// Luego de que el jugador ataque, ejecutar el ataque del Pokémon enemigo
					Timer timer = new Timer(2500, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							// Acceder aleatoriamente a un movimiento del Pokémon enemigo
							Random random = new Random();
							int indiceAleatorio = random.nextInt(enemy.getMoves().length);
							Move move2 = enemy.getMoves()[indiceAleatorio];
							// Ataque del enemigo
							ataquePokemonEnemigo(move2);
						}
					});
					timer.setRepeats(false); // Para que el timer se ejecute solo una vez
					timer.start();
				}
			});// fin btn JDialog
			panel.add(btn);
		}

		JScrollPane scrollPane = new JScrollPane(panel);
		dialog.getContentPane().add(scrollPane);
		dialog.setLocation(frame.getLocation().x + frame.getWidth() + 5, frame.getLocation().y);
		dialog.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("pokebola.png")).getImage());
		dialog.setVisible(true);

		// Agregar un WindowListener para escuchar el evento de cierre de la ventana
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				frame.setVisible(false);
				frame.setVisible(true);
				frame.setEnabled(true);
			}
		});
	}

	private void ataquePokemonJugador(Move move) {
		btnLuchar.setEnabled(false);
		btnPokemon.setEnabled(false);
		btnHuir.setEnabled(false);

		String msg = pokemon.getNick() + " ha usado " + move.getName();
		txtArea_ctrl.setText(""); // Limpiar el texto existente en el JTextArea
		escribirPocoAPoco(msg, 30); // Llamar al método para escribir poco a poco

		// Lógica para escribir el segundo mensaje después del primero
		Timer timer = new Timer(30 * msg.length() + 300, null); // Ajusta el tiempo según el primer mensaje
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				btnLuchar.setEnabled(false);
				btnPokemon.setEnabled(false);
				btnHuir.setEnabled(false);

				String msg;
				if (move.getPower() > 0) {
					// Lógica del Ataque
					Random random = new Random();
					int resultado = random.nextInt(100); // Generar un número aleatorio del 0 al 99

					if (resultado < move.getAccuracy()) {
						// El ataque tiene éxito

						// Bonus por Stab
						double B = 1;
						JSONArray types = pokemon.getTypes();
						for (int i = 0; i < types.length(); i++) {
							JSONObject type = types.getJSONObject(i).getJSONObject("type");
							String nameType = type.getString("name");

							if (nameType.equals(move.getName()))
								B = 1.5;

						}

						// Efectividad
						double E = 1;
						try {
							E = efective(move.getType(), enemy.getTypes());

						} catch (IOException e1) {
							e1.printStackTrace();
						}

						// Variable random
						random = new Random();
						resultado = random.nextInt(16) + 85; // Generar un número aleatorio del 85 al 100
						int V = resultado;

						int N = pokemon.getLevel();

						// Stat At
						int A = pokemon.getAtk();
						if (move.getDamage().equals("special")) {
							A = pokemon.getSp_atk();
						}

						// Poder
						int P = move.getPower();

						// Stat Def
						int D = enemy.getDef();
						if (move.getDamage().equals("special")) {
							D = enemy.getSp_def();
						}

						double damage = 0.01 * B * E * V * ((((0.2 * N + 1) * A * P) / (25 * D)) + 2);

						updateBar(enemy, damage);

						msg = "¡El ataque de " + pokemon.getNick() + " fue exitoso!";

					} else {
						// El ataque falla debido a la baja precisión
						msg = "El ataque de " + pokemon.getNick() + " ha fallado.";
					}
				} else {
					msg = "El ataque de " + pokemon.getNick() + " ha fallado.";
				}
				txtArea_ctrl.setText(""); // Limpiar el texto existente en el JTextArea
				escribirPocoAPoco(msg, 30); // Escribir el segundo mensaje

				timer.stop(); // Detener el timer después de escribir el segundo mensaje
			}

		});
		timer.start(); // Iniciar el timer para el segundo mensaje
	}

	private void ataquePokemonEnemigo(Move move) {

		btnLuchar.setEnabled(false);
		btnPokemon.setEnabled(false);
		btnHuir.setEnabled(false);

		String msg = enemy.getNick() + " ha usado " + move.getName();
		txtArea_ctrl.setText(""); // Limpiar el texto existente en el JTextArea
		escribirPocoAPoco(msg, 30); // Llamar al método para escribir poco a poco

		// Lógica para escribir el segundo mensaje después del primero
		Timer timer = new Timer(30 * msg.length() + 300, null); // Ajusta el tiempo según el primer mensaje
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg;
				if (move.getPower() > 0) {
					// Lógica del Ataque
					Random random = new Random();
					int resultado = random.nextInt(100); // Generar un número aleatorio del 0 al 99

					if (resultado < move.getAccuracy()) {
						// El ataque tiene éxito

						// Bonus por Stab
						double B = 1;
						JSONArray types = enemy.getTypes();
						for (int i = 0; i < types.length(); i++) {
							JSONObject type = types.getJSONObject(i).getJSONObject("type");
							String nameType = type.getString("name");

							if (nameType.equals(move.getName()))
								B = 1.5;

						}

						// Efectividad
						double E = 1;
						try {
							E = efective(move.getType(), pokemon.getTypes());

						} catch (IOException e1) {
							e1.printStackTrace();
						}

						// Variable random
						random = new Random();
						resultado = random.nextInt(16) + 85; // Generar un número aleatorio del 85 al 100
						int V = resultado;

						int N = enemy.getLevel();

						// Stat At
						int A = enemy.getAtk();
						if (move.getDamage().equals("special")) {
							A = enemy.getSp_atk();
						}

						// Poder
						int P = move.getPower();

						// Stat Def
						int D = pokemon.getDef();
						if (move.getDamage().equals("special")) {
							D = pokemon.getSp_def();
						}

						double damage = 0.01 * B * E * V * ((((0.2 * N + 1) * A * P) / (25 * D)) + 2);

						updateBar(pokemon, damage);

						msg = "¡El ataque de " + enemy.getNick() + " fue exitoso!";

					} else {
						// El ataque falla debido a la baja precisión
						msg = "El ataque de " + enemy.getNick() + " ha fallado.";
					}
				} else {
					msg = "El ataque de " + enemy.getNick() + " ha fallado.";
				}
				txtArea_ctrl.setText(""); // Limpiar el texto existente en el JTextArea
				escribirPocoAPoco(msg, 30); // Escribir el segundo mensaje

				timer.stop(); // Detener el timer después de escribir el segundo mensaje

				// Habilitar los botones después del ataque del enemigo
				btnLuchar.setEnabled(true);
				btnPokemon.setEnabled(true);
				btnHuir.setEnabled(true);
			}
		});
		timer.start(); // Iniciar el timer para el segundo mensaje
	}

	public Color obtenerColorTipo(Move move) {

		Color color = new Color(0, 0, 0);
		String nameType = move.getType();

		switch (nameType) {
			case "normal":
				color = new Color(160, 162, 160);
				break;
			case "fire":
				color = new Color(231, 35, 36);
				break;
			case "flying":
				color = new Color(130, 186, 240);
				break;
			case "bug":
				color = new Color(146, 162, 18);
				break;
			case "electric":
				color = new Color(250, 193, 0);
				break;
			case "fighting":
				color = new Color(255, 129, 0);
				break;
			case "ghost":
				color = new Color(113, 63, 113);
				break;
			case "psychic":
				color = new Color(239, 63, 122);
				break;
			case "steel":
				color = new Color(96, 162, 185);
				break;
			case "ice":
				color = new Color(61, 217, 255);
				break;
			case "poison":
				color = new Color(146, 63, 204);
				break;
			case "dragon":
				color = new Color(79, 96, 226);
				break;
			case "ground":
				color = new Color(146, 80, 27);
				break;
			case "water":
				color = new Color(36, 129, 240);
				break;
			case "dark":
				color = new Color(79, 63, 61);
				break;
			case "rock":
				color = new Color(176, 171, 130);
				break;
			case "grass":
				color = new Color(61, 162, 36);
				break;
			case "fairy":
				color = new Color(239, 113, 240);
				break;
		}

		return color;
	}

}
