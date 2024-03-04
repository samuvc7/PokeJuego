package ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.json.JSONArray;
import org.json.JSONObject;

import clases.Move;
import clases.Pokemon;
import clases.Trainer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;
import java.awt.Cursor;

public class PokemonDataWindow {
	private JFrame frmDatos;
	private boolean front;

	public PokemonDataWindow(JFrame parent, Trainer trainer, Pokemon pokemon) throws IOException {

		front = true;
		
		frmDatos = new JFrame();
		frmDatos.setResizable(false);
		frmDatos.setTitle("Datos de " + pokemon.getNick());
		frmDatos.getContentPane().setBackground(new Color(135, 206, 235));
		frmDatos.getContentPane().setLayout(null);
		
		// ====================== Exportar Pokémon ========================
		ImageIcon image_lista = new ImageIcon(
				new ImageIcon("images/lista.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		JButton btn_export = new JButton(image_lista);
		btn_export.setToolTipText("Exportar datos del Pokémon");
		btn_export.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_export.setBounds(294, 170, 32, 32);
		btn_export.setBorderPainted(false);
		btn_export.setContentAreaFilled(false);
		btn_export.setFocusPainted(false);
		btn_export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pkdat = "";
				String genero = "";
				if (pokemon.getGender().equals("Male")) genero = "M";
				if (pokemon.getGender().equals("Female")) genero = "F";
				pkdat += pokemon.getNick() + " (" + pokemon.getName() + ") " + "(" + genero + ")";
				pkdat += "\nLevel: " + pokemon.getLevel();
				
				for (Move move : pokemon.getMoves()) {
					if (move != null) {
						String movename = move.getRaw_name().substring(0,1).toUpperCase() + move.getRaw_name().substring(1).toLowerCase();
						pkdat += "\n- " + movename;
					}
				}
				
				// Crear una selección de cadena
		        StringSelection seleccion = new StringSelection(pkdat);

		        // Obtener el sistema de portapapeles
		        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		        // Colocar la selección en el portapapeles
		        clipboard.setContents(seleccion, null);
		        
		        // Mostrar mensaje al usuario
		        JOptionPane.showMessageDialog(frmDatos, "Los datos del Pokémon se han copiado al portapapeles", "Copiado al portapapeles", JOptionPane.INFORMATION_MESSAGE);
		        
			}
		});
		frmDatos.getContentPane().add(btn_export);

		
		// ====================== Cambiar el mote del Pokémon ======================
		ImageIcon image_lapiz = new ImageIcon(
				new ImageIcon("images/lapiz.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		JButton btn_changeNick = new JButton(image_lapiz);
		btn_changeNick.setToolTipText("Cambiar mote del Pokémon");
		btn_changeNick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                // Abre el diálogo para cambiar el mote del Pokémon
                ChangeNickDialog dialog = new ChangeNickDialog(frmDatos, pokemon);
                dialog.setVisible(true);
                
				try {
					new PokemonDataWindow(parent, trainer, pokemon);
					frmDatos.dispose();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

                
			}
		});
		btn_changeNick.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_changeNick.setBounds(294, 20, 32, 32);
		frmDatos.getContentPane().add(btn_changeNick);
		btn_changeNick.setBorderPainted(false); // Elimina el borde
		btn_changeNick.setContentAreaFilled(false); // Elimina el relleno del área de contenido
		btn_changeNick.setFocusPainted(false);
		
		// ====================== Cargar el gif del Pokémon ======================
		ImageIcon imageIcon = new ImageIcon(new URL(pokemon.getSpriteFrontUrl()));
		JLabel lbl_sprite = new JLabel(imageIcon);
		lbl_sprite.setBackground(new Color(255, 255, 255));
		lbl_sprite.setBounds(322, 45, 250, 250);
		frmDatos.getContentPane().add(lbl_sprite);
		
		// ===================== Botón para girar el sprite =====================
		JButton btn_rotateSprite = new JButton();
		ImageIcon image_rotate = new ImageIcon(
				new ImageIcon("images/deshacer.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		btn_rotateSprite.setIcon(image_rotate);
		btn_rotateSprite.setToolTipText("Girar el gráfico del Pokémon");
		btn_rotateSprite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageIcon imageIcon;
				try {
					if (front) {
						imageIcon = new ImageIcon(new URL(pokemon.getSpriteBackUrl()));
						lbl_sprite.setIcon(imageIcon);
						front = false;
					} else {
						imageIcon = new ImageIcon(new URL(pokemon.getSpriteFrontUrl()));
						lbl_sprite.setIcon(imageIcon);
						front = true;

					}


				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				
			}
		});
		btn_rotateSprite.setBounds(294, 210, 32, 32);
		btn_rotateSprite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_rotateSprite.setBorderPainted(false); // Elimina el borde
		btn_rotateSprite.setContentAreaFilled(false); // Elimina el relleno del área de contenido
		btn_rotateSprite.setFocusPainted(false);
		frmDatos.getContentPane().add(btn_rotateSprite);

		// ====================== Cabecera con información básica del Pokémon ======================
		JPanel panel_header = new JPanel();
		panel_header.setBackground(new Color(0, 0, 0, 128));
		panel_header.setBounds(332, 20, 230, 26);
		frmDatos.getContentPane().add(panel_header);
		panel_header.setLayout(null);

		JLabel lbl_nickData = new JLabel(pokemon.getNick());
		lbl_nickData.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_nickData.setForeground(Color.WHITE);
		lbl_nickData.setFont(new Font("Consolas", Font.BOLD, 10));
		lbl_nickData.setBackground(Color.BLACK);
		lbl_nickData.setBounds(10, 3, 80, 20);
		panel_header.add(lbl_nickData);

		if (!pokemon.getGender().equals("Unknown")) {
			ImageIcon imageGender;
			if (pokemon.getGender().equals("Female")) {
				imageGender = new ImageIcon("images/gender_Female.png");
			} else {
				imageGender = new ImageIcon("images/gender_Male.png");
			}
			JLabel lbl_gender = new JLabel(imageGender);
			lbl_gender.setBounds(110, 3, 20, 20);
			panel_header.add(lbl_gender);
		}

		JLabel lbl_level = new JLabel("Nv. " + pokemon.getLevel());
		lbl_level.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_level.setForeground(Color.WHITE);
		lbl_level.setFont(new Font("Consolas", Font.BOLD, 10));
		lbl_level.setBackground(Color.BLACK);
		lbl_level.setBounds(170, 3, 50, 20);
		panel_header.add(lbl_level);

		// ====================== Label con los tipos del Pokémon ======================
		ArrayList<ImageIcon> imagenesTipos = obtenerImagenTipo(pokemon.getTypes());
		JLabel lbl_typeData1 = new JLabel(
				new ImageIcon(imagenesTipos.get(0).getImage().getScaledInstance(100, 22, Image.SCALE_SMOOTH)));
		lbl_typeData1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_typeData1.setForeground(Color.WHITE);
		lbl_typeData1.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_typeData1.setBackground(Color.BLACK);
		lbl_typeData1.setBounds(342, 56, 100, 22);
		frmDatos.getContentPane().add(lbl_typeData1);

		if (imagenesTipos.size() > 1) {
			JLabel lbl_typeData2 = new JLabel(
					new ImageIcon(imagenesTipos.get(1).getImage().getScaledInstance(100, 22, Image.SCALE_SMOOTH)));
			lbl_typeData2.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl_typeData2.setForeground(Color.WHITE);
			lbl_typeData2.setFont(new Font("Consolas", Font.PLAIN, 10));
			lbl_typeData2.setBackground(Color.BLACK);
			lbl_typeData2.setBounds(452, 56, 100, 22);
			frmDatos.getContentPane().add(lbl_typeData2);
		}

		// ====================== Panel con información de la especie ======================
		JPanel panel_species = new JPanel();
		// panel.setBackground(new Color(32, 178, 170));
		panel_species.setBackground(new Color(0, 0, 0, 128));
		panel_species.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_species.setBounds(84, 20, 204, 60);
		frmDatos.getContentPane().add(panel_species);
		panel_species.setLayout(null);

		JLabel lbl_dex = new JLabel("N.º Pokédex");
		lbl_dex.setForeground(new Color(255, 255, 255));
		lbl_dex.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_dex.setBackground(new Color(0, 0, 0));
		lbl_dex.setBounds(10, 10, 80, 20);
		lbl_dex.setHorizontalAlignment(SwingConstants.LEFT);
		panel_species.add(lbl_dex);

		JLabel lbl_dexData = new JLabel("" + pokemon.getId());
		lbl_dexData.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_dexData.setForeground(Color.WHITE);
		lbl_dexData.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_dexData.setBackground(Color.BLACK);
		lbl_dexData.setBounds(100, 10, 80, 20);
		panel_species.add(lbl_dexData);

		JLabel lbl_name = new JLabel("Nombre");
		lbl_name.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_name.setForeground(Color.WHITE);
		lbl_name.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_name.setBackground(Color.BLACK);
		lbl_name.setBounds(10, 30, 80, 20);
		panel_species.add(lbl_name);

		JLabel lbl_nameData = new JLabel(pokemon.getName());
		lbl_nameData.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_nameData.setForeground(Color.WHITE);
		lbl_nameData.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_nameData.setBackground(Color.BLACK);
		lbl_nameData.setBounds(100, 30, 80, 20);
		panel_species.add(lbl_nameData);

		// ====================== Panel de especificaciones ======================
		JPanel panel_data = new JPanel();
		panel_data.setLayout(null);
		panel_data.setBackground(new Color(0, 0, 0, 64));
		panel_data.setBounds(84, 296, 478, 67);
		frmDatos.getContentPane().add(panel_data);

		// Info Pokémon
		JLabel lbl_fechaCaptura = new JLabel("Capturado el " + pokemon.getFecha_captura());
		lbl_fechaCaptura.setForeground(Color.WHITE);
		lbl_fechaCaptura.setFont(new Font("Consolas", Font.BOLD, 10));
		lbl_fechaCaptura.setBounds(5, 5, 150, 20);
		panel_data.add(lbl_fechaCaptura);
		
		JLabel lbl_eo = new JLabel("EO " + pokemon.getEo());
		lbl_eo.setToolTipText("Nombre del entrenador original");
		lbl_eo.setForeground(Color.WHITE);
		lbl_eo.setFont(new Font("Consolas", Font.BOLD, 10));
		lbl_eo.setBounds(5, 30, 150, 20);
		panel_data.add(lbl_eo);
		
		// Info Ataques
		JLabel lbl_moveName = new JLabel("");
		lbl_moveName.setForeground(Color.WHITE);
		lbl_moveName.setFont(new Font("Consolas", Font.BOLD, 14));
		lbl_moveName.setBounds(5, 5, 120, 20);
		panel_data.add(lbl_moveName);
		lbl_moveName.setVisible(false);

		JLabel lbl_moveType = new JLabel();
		lbl_moveType.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_moveType.setForeground(Color.WHITE);
		lbl_moveType.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_moveType.setBackground(Color.BLACK);
		lbl_moveType.setBounds(130, 4, 100, 22);
		panel_data.add(lbl_moveType);
		lbl_moveType.setVisible(false);
		
		JLabel lbl_damage = new JLabel();
		lbl_damage.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_damage.setForeground(Color.WHITE);
		lbl_damage.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_damage.setBackground(Color.BLACK);
		lbl_damage.setBounds(235, 5, 35, 20);
		panel_data.add(lbl_damage);
		lbl_damage.setVisible(false);
		
		JLabel lbl_movePower = new JLabel("");
		lbl_movePower.setForeground(Color.WHITE);
		lbl_movePower.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_movePower.setBounds(280, 5, 80, 20);
		panel_data.add(lbl_movePower);
		lbl_movePower.setVisible(false);
		
		JLabel lbl_moveAccuracy = new JLabel("");
		lbl_moveAccuracy.setForeground(Color.WHITE);
		lbl_moveAccuracy.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_moveAccuracy.setBounds(360, 5, 80, 20);
		panel_data.add(lbl_moveAccuracy);
		lbl_moveAccuracy.setVisible(false);
		
		JLabel lbl_moveDesc = new JLabel("");
		lbl_moveDesc.setForeground(Color.WHITE);
		lbl_moveDesc.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_moveDesc.setBounds(5, 30, 467, 20);
		panel_data.add(lbl_moveDesc);
		lbl_moveDesc.setVisible(false);
		
		// ===================== Botón para mostrar la info =====================
		JButton btn_showInfo = new JButton();
		btn_showInfo.setToolTipText("Mostrar más información");
		ImageIcon image_agregar = new ImageIcon(
				new ImageIcon("images/agregar.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		btn_showInfo.setIcon(image_agregar);
		btn_showInfo.setBorderPainted(false); // Elimina el borde
		btn_showInfo.setContentAreaFilled(false); // Elimina el relleno del área de contenido
		btn_showInfo.setFocusPainted(false);
		btn_showInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_showInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lbl_moveName.setVisible(false);
				lbl_moveType.setVisible(false);
				lbl_damage.setVisible(false);
				lbl_movePower.setVisible(false);
				lbl_moveAccuracy.setVisible(false);
				lbl_moveDesc.setVisible(false);
				
				lbl_fechaCaptura.setVisible(true);
				lbl_eo.setVisible(true);

				panel_data.setVisible(false);
				panel_data.setVisible(true);

				
			}
		});
		btn_showInfo.setBounds(294, 250, 32, 32);
		frmDatos.getContentPane().add(btn_showInfo);

		// ====================== Panel con las estadisticas ======================
		JPanel panel_stats = new JPanel();
		panel_stats.setLayout(null);
		panel_stats.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		// panel_stats.setBackground(new Color(32, 178, 170));
		panel_stats.setBackground(new Color(0, 0, 0, 128));
		panel_stats.setBounds(84, 122, 204, 160);
		frmDatos.getContentPane().add(panel_stats);

		JLabel lbl_hp = new JLabel("PS");
		lbl_hp.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_hp.setForeground(Color.WHITE);
		lbl_hp.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_hp.setBackground(Color.BLACK);
		lbl_hp.setBounds(10, 10, 80, 20);
		panel_stats.add(lbl_hp);

		JLabel lbl_hpData = new JLabel("" + pokemon.getCur_hp() + "/" + pokemon.getMax_hp());
		lbl_hpData.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_hpData.setForeground(Color.WHITE);
		lbl_hpData.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_hpData.setBackground(Color.BLACK);
		lbl_hpData.setBounds(100, 10, 80, 20);
		panel_stats.add(lbl_hpData);

		JLabel lbl_atk = new JLabel("Ataque");
		lbl_atk.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_atk.setForeground(Color.WHITE);
		lbl_atk.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_atk.setBackground(Color.BLACK);
		lbl_atk.setBounds(10, 50, 80, 20);
		panel_stats.add(lbl_atk);

		JLabel lbl_atkData = new JLabel("" + pokemon.getAtk());
		lbl_atkData.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_atkData.setForeground(Color.WHITE);
		lbl_atkData.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_atkData.setBackground(Color.BLACK);
		lbl_atkData.setBounds(100, 50, 80, 20);
		panel_stats.add(lbl_atkData);

		JLabel lbl_def = new JLabel("Defensa");
		lbl_def.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_def.setForeground(Color.WHITE);
		lbl_def.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_def.setBackground(Color.BLACK);
		lbl_def.setBounds(10, 70, 80, 20);
		panel_stats.add(lbl_def);

		JLabel lbl_defData = new JLabel("" + pokemon.getDef());
		lbl_defData.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_defData.setForeground(Color.WHITE);
		lbl_defData.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_defData.setBackground(Color.BLACK);
		lbl_defData.setBounds(100, 70, 80, 20);
		panel_stats.add(lbl_defData);

		JLabel lbl_sp_atk = new JLabel("At. Esp.");
		lbl_sp_atk.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_sp_atk.setForeground(Color.WHITE);
		lbl_sp_atk.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_sp_atk.setBackground(Color.BLACK);
		lbl_sp_atk.setBounds(10, 90, 80, 20);
		panel_stats.add(lbl_sp_atk);

		JLabel lbl_sp_atkData = new JLabel("" + pokemon.getSp_atk());
		lbl_sp_atkData.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_sp_atkData.setForeground(Color.WHITE);
		lbl_sp_atkData.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_sp_atkData.setBackground(Color.BLACK);
		lbl_sp_atkData.setBounds(100, 90, 80, 20);
		panel_stats.add(lbl_sp_atkData);

		JLabel lbl_sp_def = new JLabel("Def. Esp.");
		lbl_sp_def.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_sp_def.setForeground(Color.WHITE);
		lbl_sp_def.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_sp_def.setBackground(Color.BLACK);
		lbl_sp_def.setBounds(10, 110, 80, 20);
		panel_stats.add(lbl_sp_def);

		JLabel lbl_sp_defData = new JLabel("" + pokemon.getSp_def());
		lbl_sp_defData.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_sp_defData.setForeground(Color.WHITE);
		lbl_sp_defData.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_sp_defData.setBackground(Color.BLACK);
		lbl_sp_defData.setBounds(100, 130, 80, 20);
		panel_stats.add(lbl_sp_defData);

		JLabel lbl_speed = new JLabel("Velocidad");
		lbl_speed.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_speed.setForeground(Color.WHITE);
		lbl_speed.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_speed.setBackground(Color.BLACK);
		lbl_speed.setBounds(10, 130, 80, 20);
		panel_stats.add(lbl_speed);

		JLabel lbl_speedData = new JLabel("" + pokemon.getSpeed());
		lbl_speedData.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_speedData.setForeground(Color.WHITE);
		lbl_speedData.setFont(new Font("Consolas", Font.PLAIN, 10));
		lbl_speedData.setBackground(Color.BLACK);
		lbl_speedData.setBounds(100, 110, 80, 20);
		panel_stats.add(lbl_speedData);

		JProgressBar bar_hp = new JProgressBar();
		bar_hp.setFont(new Font("Consolas", Font.PLAIN, 10));
		bar_hp.setBounds(10, 29, 140, 11);
		bar_hp.setMaximum(pokemon.getMax_hp());
		bar_hp.setValue(pokemon.getCur_hp());
		bar_hp.setForeground(Color.GREEN);
		if (bar_hp.getValue() < bar_hp.getMaximum() / 2) {
			bar_hp.setForeground(Color.YELLOW);
		}
		if (bar_hp.getValue() < bar_hp.getMaximum() / 5) {
			bar_hp.setForeground(Color.RED);
		}
		bar_hp.setBackground(Color.LIGHT_GRAY);
		panel_stats.add(bar_hp);

		// ====================== Panel con los Movimientos ======================
		JPanel panel_moves = new JPanel();
		panel_moves.setLayout(null);
		panel_moves.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_moves.setBackground(new Color(0, 0, 0, 128));
		panel_moves.setBounds(84, 122, 204, 160);
		frmDatos.getContentPane().add(panel_moves);

		JButton btn_move1 = new JButton("" + pokemon.getMoves()[0].getName());
		btn_move1.setFocusable(false);
		btn_move1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_move1.setBorder(null);
		btn_move1.setFont(new Font("Consolas", Font.PLAIN, 10));
		btn_move1.setFocusPainted(false);
		btn_move1.setBorderPainted(false);
		btn_move1.setBounds(10, 13, 184, 26);
		btn_move1.setForeground(Color.WHITE);
		btn_move1.setBackground(obtenerColorTipo(pokemon.getMoves()[0]));
		panel_moves.add(btn_move1);

		btn_move1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lbl_fechaCaptura.setVisible(false);
				lbl_eo.setVisible(false);
				
				lbl_moveName.setText(pokemon.getMoves()[0].getName());
				lbl_moveName.setVisible(true);

				lbl_moveDesc.setText(pokemon.getMoves()[0].getDesc());
				lbl_moveDesc.setVisible(true);
				lbl_moveDesc.setToolTipText("<html> " + pokemon.getMoves()[0].getDesc());
				
				try {
					ImageIcon img_type = obtenerImagenTipo(pokemon.getMoves()[0].getType());
					lbl_moveType.setIcon(new ImageIcon(img_type.getImage().getScaledInstance(100, 22, Image.SCALE_SMOOTH)));
					lbl_moveType.setVisible(true);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				ImageIcon img_type = obtenerImagenCategoria(pokemon.getMoves()[0].getDamage());
				lbl_damage.setIcon(img_type);
				lbl_damage.setVisible(true);
				
				lbl_movePower.setText("Potencia " + pokemon.getMoves()[0].getPower());
				lbl_movePower.setVisible(true);

				lbl_moveAccuracy.setText("Precisión " + pokemon.getMoves()[0].getAccuracy());
				lbl_moveAccuracy.setVisible(true);

				panel_data.setVisible(false);
				panel_data.setVisible(true);

			}

		});

		if (pokemon.getMoves().length >= 2) {
			JButton btn_move2 = new JButton("" + pokemon.getMoves()[1].getName());
			btn_move2.setFocusable(false);
			btn_move2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btn_move2.setBorder(null);
			btn_move2.setFont(new Font("Consolas", Font.PLAIN, 10));
			btn_move2.setFocusPainted(false);
			btn_move2.setBorderPainted(false);
			btn_move2.setBounds(10, 49, 184, 26);
			btn_move2.setForeground(Color.WHITE);
			btn_move2.setBackground(obtenerColorTipo(pokemon.getMoves()[1]));
			panel_moves.add(btn_move2);

			btn_move2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lbl_fechaCaptura.setVisible(false);
					lbl_eo.setVisible(false);
					
					lbl_moveName.setText(pokemon.getMoves()[1].getName());
					lbl_moveName.setVisible(true);

					lbl_moveDesc.setText(pokemon.getMoves()[1].getDesc());
					lbl_moveDesc.setVisible(true);
					lbl_moveDesc.setToolTipText("<html> " + pokemon.getMoves()[1].getDesc());
					
					try {
						ImageIcon img_type = obtenerImagenTipo(pokemon.getMoves()[1].getType());
						lbl_moveType.setIcon(new ImageIcon(img_type.getImage().getScaledInstance(100, 22, Image.SCALE_SMOOTH)));
						lbl_moveType.setVisible(true);
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					ImageIcon img_type = obtenerImagenCategoria(pokemon.getMoves()[1].getDamage());
					lbl_damage.setIcon(img_type);
					lbl_damage.setVisible(true);
					
					lbl_movePower.setText("Potencia " + pokemon.getMoves()[1].getPower());
					lbl_movePower.setVisible(true);
					
					lbl_moveAccuracy.setText("Precisión " + pokemon.getMoves()[1].getAccuracy());
					lbl_moveAccuracy.setVisible(true);
					
					panel_data.setVisible(false);
					panel_data.setVisible(true);

				}

			});

		}

		if (pokemon.getMoves().length >= 3) {
			JButton btn_move3 = new JButton("" + pokemon.getMoves()[2].getName());
			btn_move3.setFocusable(false);
			btn_move3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btn_move3.setBorder(null);
			btn_move3.setFont(new Font("Consolas", Font.PLAIN, 10));
			btn_move3.setFocusPainted(false);
			btn_move3.setBorderPainted(false);
			btn_move3.setBounds(10, 85, 184, 26);
			btn_move3.setForeground(Color.WHITE);
			btn_move3.setBackground(obtenerColorTipo(pokemon.getMoves()[2]));
			panel_moves.add(btn_move3);

			btn_move3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lbl_fechaCaptura.setVisible(false);
					lbl_eo.setVisible(false);

					lbl_moveName.setText(pokemon.getMoves()[2].getName());
					lbl_moveName.setVisible(true);

					lbl_moveDesc.setText(pokemon.getMoves()[2].getDesc());
					lbl_moveDesc.setVisible(true);
					lbl_moveDesc.setToolTipText("<html> " + pokemon.getMoves()[2].getDesc());
					
					try {
						ImageIcon img_type = obtenerImagenTipo(pokemon.getMoves()[2].getType());
						lbl_moveType.setIcon(new ImageIcon(img_type.getImage().getScaledInstance(100, 22, Image.SCALE_SMOOTH)));
						lbl_moveType.setVisible(true);
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					ImageIcon img_type = obtenerImagenCategoria(pokemon.getMoves()[2].getDamage());
					lbl_damage.setIcon(img_type);
					lbl_damage.setVisible(true);

					lbl_movePower.setText("Potencia " + pokemon.getMoves()[2].getPower());
					lbl_movePower.setVisible(true);
					
					lbl_moveAccuracy.setText("Precisión " + pokemon.getMoves()[2].getAccuracy());
					lbl_moveAccuracy.setVisible(true);
					
					panel_data.setVisible(false);
					panel_data.setVisible(true);

				}

			});

		}

		if (pokemon.getMoves().length >= 4) {
			JButton btn_move4 = new JButton("" + pokemon.getMoves()[3].getName());
			btn_move4.setFocusable(false);
			btn_move4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btn_move4.setBorder(null);
			btn_move4.setFont(new Font("Consolas", Font.PLAIN, 10));
			btn_move4.setFocusPainted(false);
			btn_move4.setBorderPainted(false);
			btn_move4.setBounds(10, 121, 184, 26);
			btn_move4.setForeground(Color.WHITE);
			btn_move4.setBackground(obtenerColorTipo(pokemon.getMoves()[3]));
			panel_moves.add(btn_move4);

			btn_move4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lbl_fechaCaptura.setVisible(false);
					lbl_eo.setVisible(false);

					lbl_moveName.setText(pokemon.getMoves()[3].getName());
					lbl_moveName.setVisible(true);

					lbl_moveDesc.setText(pokemon.getMoves()[3].getDesc());
					lbl_moveDesc.setVisible(true);
					lbl_moveDesc.setToolTipText("<html> " + pokemon.getMoves()[3].getDesc());
					
					try {
						ImageIcon img_type = obtenerImagenTipo(pokemon.getMoves()[3].getType());
						lbl_moveType.setIcon(new ImageIcon(img_type.getImage().getScaledInstance(100, 22, Image.SCALE_SMOOTH)));
						lbl_moveType.setVisible(true);
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					ImageIcon img_type = obtenerImagenCategoria(pokemon.getMoves()[3].getDamage());
					lbl_damage.setIcon(img_type);
					lbl_damage.setVisible(true);

					lbl_movePower.setText("Potencia " + pokemon.getMoves()[3].getPower());
					lbl_movePower.setVisible(true);
					
					lbl_moveAccuracy.setText("Precisión " + pokemon.getMoves()[3].getAccuracy());
					lbl_moveAccuracy.setVisible(true);
					
					panel_data.setVisible(false);
					panel_data.setVisible(true);

				}

			});

		}

		panel_moves.setVisible(false);

		// ====================== Cambiar de Panel ======================
		JButton btnCambiarPanel = new JButton("Ver Movimientos");
		btnCambiarPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCambiarPanel.setFocusPainted(false);
		btnCambiarPanel.setBorderPainted(false);
		btnCambiarPanel.setFont(new Font("Consolas", Font.BOLD, 10));
		btnCambiarPanel.setBounds(84, 90, 204, 22);
		btnCambiarPanel.setForeground(Color.WHITE);
		btnCambiarPanel.setBackground(new Color(0, 0, 0));
		frmDatos.getContentPane().add(btnCambiarPanel);

		// Agregar ActionListener al botón
		btnCambiarPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cambiar la visibilidad de los paneles
				panel_stats.setVisible(!panel_stats.isVisible());
				panel_moves.setVisible(!panel_moves.isVisible());

				// Cambiar el texto del botón
				if (btnCambiarPanel.getText().equals("Ver Estadisticas")) {
					btnCambiarPanel.setText("Ver Movimientos");
				} else {
					btnCambiarPanel.setText("Ver Estadisticas");
				}

			}
		});

		// Fondo
		ImageIcon image_background = new ImageIcon("images/databg.png");
		JLabel lbl_bg = new JLabel(image_background);
		lbl_bg.setBackground(new Color(255, 255, 255));
		lbl_bg.setBounds(74, 0, 512, 384);
		frmDatos.getContentPane().add(lbl_bg);

		// Botones para navegar entre diferentes miembros del equipo
		JButton btn_pk1 = new JButton();
		btn_pk1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_pk1.setBounds(0, 0, 74, 64);
		if (trainer.getTeam()[0] != null) {
			ImageIcon icon1 = new ImageIcon(new URL(trainer.getTeam()[0].getIconUrl()));
			btn_pk1.setIcon(icon1);
			
			btn_pk1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						new PokemonDataWindow(parent, trainer, trainer.getTeam()[0]);
						frmDatos.dispose();
						parent.setEnabled(false);


					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			
		}
		btn_pk1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_pk1.setFocusPainted(false);
		frmDatos.getContentPane().add(btn_pk1);

		JButton btn_pk2 = new JButton("");
		btn_pk2.setBounds(0, 64, 74, 64);
		if (trainer.getTeam()[1] != null) {
			ImageIcon icon1 = new ImageIcon(new URL(trainer.getTeam()[1].getIconUrl()));
			btn_pk2.setIcon(icon1);
			
			btn_pk2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						new PokemonDataWindow(parent, trainer, trainer.getTeam()[1]);
						frmDatos.dispose();
						parent.setEnabled(false);


					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			
		}
		btn_pk2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_pk2.setFocusPainted(false);
		frmDatos.getContentPane().add(btn_pk2);

		JButton btn_pk3 = new JButton("");
		btn_pk3.setBounds(0, 128, 74, 64);
		if (trainer.getTeam()[2] != null) {
			ImageIcon icon1 = new ImageIcon(new URL(trainer.getTeam()[2].getIconUrl()));
			btn_pk3.setIcon(icon1);
			
			btn_pk3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						new PokemonDataWindow(parent, trainer, trainer.getTeam()[2]);
						frmDatos.dispose();
						parent.setEnabled(false);


					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			
		}
		btn_pk3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_pk3.setFocusPainted(false);
		frmDatos.getContentPane().add(btn_pk3);

		JButton btn_pk4 = new JButton("");
		btn_pk4.setBounds(0, 192, 74, 64);
		if (trainer.getTeam()[3] != null) {
			ImageIcon icon1 = new ImageIcon(new URL(trainer.getTeam()[3].getIconUrl()));
			btn_pk4.setIcon(icon1);
			
			btn_pk4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						new PokemonDataWindow(parent, trainer, trainer.getTeam()[3]);
						frmDatos.dispose();
						parent.setEnabled(false);


					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			
		}
		btn_pk4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_pk4.setFocusPainted(false);
		frmDatos.getContentPane().add(btn_pk4);

		JButton btn_pk5 = new JButton("");
		btn_pk5.setBounds(0, 256, 74, 64);
		if (trainer.getTeam()[4] != null) {
			ImageIcon icon1 = new ImageIcon(new URL(trainer.getTeam()[4].getIconUrl()));
			btn_pk5.setIcon(icon1);
			
			btn_pk5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						new PokemonDataWindow(parent, trainer, trainer.getTeam()[4]);
						frmDatos.dispose();
						parent.setEnabled(false);


					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			
		}
		btn_pk5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_pk5.setFocusPainted(false);
		frmDatos.getContentPane().add(btn_pk5);

		JButton btn_pk6 = new JButton("");
		btn_pk6.setBounds(0, 320, 74, 64);
		if (trainer.getTeam()[5] != null) {
			ImageIcon icon1 = new ImageIcon(new URL(trainer.getTeam()[5].getIconUrl()));
			btn_pk6.setIcon(icon1);
			
			btn_pk6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						new PokemonDataWindow(parent, trainer, trainer.getTeam()[5]);
						frmDatos.dispose();
						parent.setEnabled(false);
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			
		}
		btn_pk6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_pk6.setFocusPainted(false);
		frmDatos.getContentPane().add(btn_pk6);

		frmDatos.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
		frmDatos.setSize(586, 421);
		frmDatos.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		frmDatos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frmDatos.setVisible(true);
		
        // Agregar un WindowListener para escuchar el evento de cierre de la ventana
        frmDatos.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Habilitar el parent JFrame (MainFrame) cuando SecondFrame se cierra
                parent.setEnabled(true);
            }
        });

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

	public ArrayList<ImageIcon> obtenerImagenTipo(JSONArray types) throws IOException {

		ArrayList<ImageIcon> typesImages = new ArrayList<>();

		for (int i = 0; i < types.length(); i++) {
			JSONObject type = types.getJSONObject(i).getJSONObject("type");
			String nameType = type.getString("name");

			switch (nameType) {
			case "normal":
				typesImages.add(new ImageIcon("images/normal.png"));
				break;
			case "fire":
				typesImages.add(new ImageIcon("images/fuego.png"));
				break;
			case "flying":
				typesImages.add(new ImageIcon("images/volador.png"));
				break;
			case "bug":
				typesImages.add(new ImageIcon("images/bicho.png"));
				break;
			case "electric":
				typesImages.add(new ImageIcon("images/electrico.png"));
				break;
			case "fighting":
				typesImages.add(new ImageIcon("images/lucha.png"));
				break;
			case "ghost":
				typesImages.add(new ImageIcon("images/fantasma.png"));
				break;
			case "psychic":
				typesImages.add(new ImageIcon("images/psiquico.png"));
				break;
			case "steel":
				typesImages.add(new ImageIcon("images/acero.png"));
				break;
			case "ice":
				typesImages.add(new ImageIcon("images/hielo.png"));
				break;
			case "poison":
				typesImages.add(new ImageIcon("images/veneno.png"));
				break;
			case "dragon":
				typesImages.add(new ImageIcon("images/dragon.png"));
				break;
			case "ground":
				typesImages.add(new ImageIcon("images/tierra.png"));
				break;
			case "water":
				typesImages.add(new ImageIcon("images/agua.png"));
				break;
			case "dark":
				typesImages.add(new ImageIcon("images/siniestro.png"));
				break;
			case "rock":
				typesImages.add(new ImageIcon("images/roca.png"));
				break;
			case "grass":
				typesImages.add(new ImageIcon("images/planta.png"));
				break;
			case "fairy":
				typesImages.add(new ImageIcon("images/hada.png"));
				break;
			}
		}
		return typesImages;
	}

	public ImageIcon obtenerImagenTipo(String type) throws IOException {

		ImageIcon img = new ImageIcon();

		switch (type) {
		case "normal":
			img = new ImageIcon("images/normal.png");
			break;
		case "fire":
			img = new ImageIcon("images/fuego.png");
			break;
		case "flying":
			img = new ImageIcon("images/volador.png");
			break;
		case "bug":
			img = new ImageIcon("images/bicho.png");
			break;
		case "electric":
			img = new ImageIcon("images/electrico.png");
			break;
		case "fighting":
			img = new ImageIcon("images/lucha.png");
			break;
		case "ghost":
			img = new ImageIcon("images/fantasma.png");
			break;
		case "psychic":
			img = new ImageIcon("images/psiquico.png");
			break;
		case "steel":
			img = new ImageIcon("images/acero.png");
			break;
		case "ice":
			img = new ImageIcon("images/hielo.png");
			break;
		case "poison":
			img = new ImageIcon("images/veneno.png");
			break;
		case "dragon":
			img = new ImageIcon("images/dragon.png");
			break;
		case "ground":
			img = new ImageIcon("images/tierra.png");
			break;
		case "water":
			img = new ImageIcon("images/agua.png");
			break;
		case "dark":
			img = new ImageIcon("images/siniestro.png");
			break;
		case "rock":
			img = new ImageIcon("images/roca.png");
			break;
		case "grass":
			img = new ImageIcon("images/planta.png");
			break;
		case "fairy":
			img = new ImageIcon("images/hada.png");
			break;
		}

		return img;
	}
	
	public ImageIcon obtenerImagenCategoria (String category) {
		ImageIcon img = new ImageIcon();

		switch (category) {
		case "status" :
			img = new ImageIcon("images/estado.png");
			break;
		case "special" :
			img = new ImageIcon("images/especial.png");
			break;
		case "physical" :
			img = new ImageIcon("images/fisico.png");
			break;
		}
		
		return img;
	}
}

class ChangeNickDialog extends JDialog {

	public ChangeNickDialog(JFrame parent, Pokemon pokemon) {
        super(parent, "Cambiar mote del Pokémon", true); // El true indica que el diálogo es modal
        setSize(300, 150);
		//setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Nuevo mote del Pokémon:");
        label.setFont(new Font("Consolas", Font.BOLD, 14));
		
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Consolas", Font.PLAIN, 10));
        textField.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (getLength() + str.length() <= 12) { // Cambia 15 al valor máximo deseado
					super.insertString(offs, str, a);
				}
			}
		});
        JButton btn_accept = new JButton("Aceptar");
        btn_accept.setFont(new Font("Consolas", Font.PLAIN, 10));

        btn_accept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes obtener el nuevo mote del Pokémon del JTextField
                String newNick = textField.getText();
                
    			if (!newNick.trim().isEmpty()) {
                    pokemon.setNick(newNick);

    			}
    			
                dispose();

            }
        });

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(textField);
        panel.add(btn_accept);

        getContentPane().add(panel);
    }
	
}
