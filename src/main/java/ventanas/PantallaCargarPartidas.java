package ventanas;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JLayeredPane;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import clases.Pokemon;
import clases.Trainer;
import java.awt.SystemColor;

public class PantallaCargarPartidas {

	private JFrame frmCargarPartida;
	private JButton btn_atras;
	private JButton btn_continuar;


	public PantallaCargarPartidas(JFrame parent, Trainer trainer) {

		frmCargarPartida = new JFrame();
		frmCargarPartida.setTitle("Cargar Partida");
		frmCargarPartida.getContentPane().setFont(new Font("Consolas", Font.PLAIN, 11));
		frmCargarPartida.setBounds(100, 100, 598, 431);
		frmCargarPartida.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCargarPartida.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		frmCargarPartida.getContentPane().setLayout(null);
		frmCargarPartida.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("pokebola.png")).getImage());


		ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("fondoPokochi.jpg"));
		Image image = imageIcon.getImage();
		Image scaledImage = image.getScaledInstance(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
				Image.SCALE_DEFAULT);
		JLabel lbl_background = new JLabel("");


	
		ImageIcon trainerSprite = new ImageIcon(getClass().getClassLoader().getResource("Entrenador1.png"));
		if (trainer.getStyle() == 2) {
			trainerSprite = new ImageIcon(getClass().getClassLoader().getResource("Entrenador2.png"));
		}

		btn_continuar = new JButton("Continuar");
		btn_continuar.setBackground(new Color(65, 105, 225));
		btn_continuar.setFont(new Font("Consolas", Font.BOLD, 11));
		btn_continuar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_continuar.setFocusPainted(false);
		btn_continuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("save.dat"))) {
					Trainer trainer = (Trainer) entrada.readObject();
					new MenuWindow(trainer);
					parent.dispose();
					frmCargarPartida.dispose();
					
				} catch (FileNotFoundException e3) {
					// No hay datos guardados, elegir inicial
					// new StarterSelectionWindow();
					new ElegirEntrenador();
				} catch (IOException e1) {
					System.err.println("Error de entrada/salida: " + e1.getMessage());

				} catch (ClassNotFoundException e2) {
					System.err.println("Clase no encontrada: " + e2.getMessage());

				}

			}
		});

		btn_continuar.setBounds(314, 324, 180, 45);
		frmCargarPartida.getContentPane().add(btn_continuar);
		
		btn_atras = new JButton("Atras");
		btn_atras.setBackground(new Color(255, 20, 147));
		btn_atras.setFont(new Font("Consolas", Font.BOLD, 11));
		btn_atras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_atras.setFocusPainted(false);
		btn_atras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setEnabled(true);
				frmCargarPartida.dispose();
			}
		});

		JLayeredPane panel = new JLayeredPane();
		panel.setBorder(new LineBorder(new Color(255, 20, 147), 4, true));
		panel.setBackground(SystemColor.window);
		panel.setBounds(73, 29, 436, 285);
		frmCargarPartida.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(65, 105, 225), 3, true));
		panel_1.setBounds(37, 17, 352, 41);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lbl_fechahora = new JLabel(trainer.getLastConnected());
		lbl_fechahora.setFont(new Font("Consolas", Font.PLAIN, 11));

		// JLabel lbl_fechahora = new JLabel("01/01/01(00:00)");
		lbl_fechahora.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lbl_fechahora, BorderLayout.EAST);

		JLabel lbl_lastconnectionlabel = new JLabel("    Última conexión: ");
		lbl_lastconnectionlabel.setFont(new Font("Consolas", Font.PLAIN, 11));
		panel_1.add(lbl_lastconnectionlabel, BorderLayout.WEST);
		lbl_lastconnectionlabel.setHorizontalAlignment(SwingConstants.TRAILING);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(65, 105, 225), 3, true));
		panel_2.setBounds(23, 200, 384, 64);
		panel.add(panel_2);

		JLabel lbl_pokemon1 = new JLabel();
		if (trainer.getTeam()[0] != null) {
			try {
				ImageIcon icon1 = new ImageIcon(new URL(trainer.getTeam()[0].getIconUrl()));
				lbl_pokemon1 = new JLabel(icon1);
				lbl_pokemon1.setToolTipText(trainer.getTeam()[0].getNick());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
		lbl_pokemon1.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon1.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_pokemon2 = new JLabel();
		if (trainer.getTeam()[1] != null) {
			try {
				ImageIcon icon2 = new ImageIcon(new URL(trainer.getTeam()[1].getIconUrl()));
				lbl_pokemon2 = new JLabel(icon2);
				lbl_pokemon2.setToolTipText(trainer.getTeam()[1].getNick());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}

		}
		lbl_pokemon2.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon2.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_pokemon3 = new JLabel();
		if (trainer.getTeam()[2] != null) {
			try {
				ImageIcon icon3 = new ImageIcon(new URL(trainer.getTeam()[2].getIconUrl()));
				lbl_pokemon3 = new JLabel(icon3);
				lbl_pokemon3.setToolTipText(trainer.getTeam()[2].getNick());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
		lbl_pokemon3.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon3.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_pokemon4 = new JLabel();
		if (trainer.getTeam()[3] != null) {
			try {
				ImageIcon icon4 = new ImageIcon(new URL(trainer.getTeam()[3].getIconUrl()));
				lbl_pokemon4 = new JLabel(icon4);
				lbl_pokemon4.setToolTipText(trainer.getTeam()[3].getNick());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
		lbl_pokemon4.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon4.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_pokemon5 = new JLabel();
		if (trainer.getTeam()[4] != null) {
			try {
				ImageIcon icon5 = new ImageIcon(new URL(trainer.getTeam()[4].getIconUrl()));
				lbl_pokemon5 = new JLabel(icon5);
				lbl_pokemon5.setToolTipText(trainer.getTeam()[4].getNick());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
		lbl_pokemon5.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon5.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_pokemon6 = new JLabel();
		if (trainer.getTeam()[5] != null) {
			try {
				ImageIcon icon6 = new ImageIcon(new URL(trainer.getTeam()[5].getIconUrl()));
				lbl_pokemon6 = new JLabel(icon6);
				lbl_pokemon6.setToolTipText(trainer.getTeam()[5].getNick());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
		lbl_pokemon6.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon6.setHorizontalAlignment(SwingConstants.CENTER);

		panel_2.setLayout(new GridLayout(0, 6, 0, 0));
		panel_2.add(lbl_pokemon1);
		panel_2.add(lbl_pokemon2);
		panel_2.add(lbl_pokemon3);
		panel_2.add(lbl_pokemon4);
		panel_2.add(lbl_pokemon5);
		panel_2.add(lbl_pokemon6);

		JLabel lbl_jugador = new JLabel("Nick: " + trainer.getName());
		lbl_jugador.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_jugador.setBounds(228, 87, 119, 23);
		panel.add(lbl_jugador);

		JLabel lbl_nivel = new JLabel("Nivel: " + trainer.getLevel());
		lbl_nivel.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_nivel.setBounds(228, 116, 106, 13);
		panel.add(lbl_nivel);

		JLabel lbl_imagenjugador = new JLabel();
		lbl_imagenjugador.setBounds(88, 68, 87, 131);
		lbl_imagenjugador.setIcon(trainerSprite);

		panel.add(lbl_imagenjugador);
		btn_atras.setBounds(83, 324, 180, 45);

		frmCargarPartida.getContentPane().add(btn_atras);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(73, 29, 436, 285);
		panel_3.setBackground(SystemColor.menu);
		frmCargarPartida.getContentPane().add(panel_3);

		lbl_background = new JLabel(new ImageIcon(scaledImage));
		lbl_background.setBounds(0, 0, 584, 394);
		frmCargarPartida.getContentPane().add(lbl_background);

	
		frmCargarPartida.setVisible(true);
		
		// Agregar un WindowListener para escuchar el evento de cierre de la ventana
		frmCargarPartida.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Habilitar el parent JFrame (MainFrame) cuando SecondFrame se cierra
				parent.setEnabled(true);
			}
		});

	}
}
