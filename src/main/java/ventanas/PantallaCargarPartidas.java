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
import java.awt.Font;

import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.awt.event.ActionEvent;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import clases.Pokemon;
import clases.Trainer;
import java.awt.SystemColor;

public class PantallaCargarPartidas {

	private JFrame frame;
	private JButton btn_atras;
	private JButton btn_continuar;


	public PantallaCargarPartidas(Trainer trainer) {

		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Consolas", Font.PLAIN, 11));
		frame.setBounds(100, 100, 598, 431);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		ImageIcon imageIcon = new ImageIcon("images/fondoPokochi.jpg");
		Image image = imageIcon.getImage();
		Image scaledImage = image.getScaledInstance(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
				Image.SCALE_DEFAULT);
		JLabel lbl_background = new JLabel("");


	
		ImageIcon trainerSprite = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Entrenador1.png"));
		if (trainer.getStyle() == 2) {
			trainerSprite = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Entrenador2.png"));
		}

		btn_continuar = new JButton("Continuar");
		btn_continuar.setBackground(new Color(65, 105, 225));
		btn_continuar.setFont(new Font("Consolas", Font.BOLD, 11));
		btn_continuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("save.dat"))) {
					Trainer trainer = (Trainer) entrada.readObject();
					Pokemon pokemon = trainer.getTeam()[0];

					new MenuWindow(pokemon, trainer);
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
		frame.getContentPane().add(btn_continuar);
		
		btn_atras = new JButton("Atras");
		btn_atras.setBackground(new Color(255, 20, 147));
		btn_atras.setFont(new Font("Consolas", Font.BOLD, 11));
		btn_atras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame.dispose();
			}
		});

		JLayeredPane panel = new JLayeredPane();
		panel.setBorder(new LineBorder(new Color(255, 20, 147), 4, true));
		panel.setBackground(SystemColor.window);
		panel.setBounds(73, 29, 436, 285);
		frame.getContentPane().add(panel);
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
		panel_2.setBounds(37, 210, 352, 53);
		panel.add(panel_2);

		JLabel lbl_pokemon1 = new JLabel();
		if (trainer.getTeam()[0] != null) {
			lbl_pokemon1 = new JLabel(trainer.getTeam()[0].getName());
		}
		lbl_pokemon1.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon1.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_pokemon2 = new JLabel();
		if (trainer.getTeam()[1] != null) {
			lbl_pokemon2 = new JLabel(trainer.getTeam()[1].getName());
		}
		lbl_pokemon2.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon2.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_pokemon3 = new JLabel();
		if (trainer.getTeam()[2] != null) {
			lbl_pokemon3 = new JLabel(trainer.getTeam()[2].getName());
		}
		lbl_pokemon3.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon3.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_pokemon4 = new JLabel();
		if (trainer.getTeam()[3] != null) {
			lbl_pokemon4 = new JLabel(trainer.getTeam()[3].getName());
		}
		lbl_pokemon4.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon4.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_pokemon5 = new JLabel();
		if (trainer.getTeam()[4] != null) {
			lbl_pokemon5 = new JLabel(trainer.getTeam()[4].getName());
		}
		lbl_pokemon5.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_pokemon5.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbl_pokemon6 = new JLabel();
		if (trainer.getTeam()[5] != null) {
			lbl_pokemon6 = new JLabel(trainer.getTeam()[5].getName());
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

		JLabel lbl_jugador = new JLabel("nick: " + trainer.getName());
		lbl_jugador.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_jugador.setBounds(228, 87, 119, 23);
		panel.add(lbl_jugador);

		JLabel lbl_nivel = new JLabel("nivel: " + trainer.getLevel());
		lbl_nivel.setFont(new Font("Consolas", Font.PLAIN, 11));
		lbl_nivel.setBounds(228, 116, 106, 13);
		panel.add(lbl_nivel);

		JLabel lbl_imagenjugador = new JLabel();
		lbl_imagenjugador.setBounds(88, 68, 87, 131);
		lbl_imagenjugador.setIcon(trainerSprite);

		panel.add(lbl_imagenjugador);
		btn_atras.setBounds(83, 324, 180, 45);

		frame.getContentPane().add(btn_atras);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(73, 29, 436, 285);
		panel_3.setBackground(SystemColor.menu);
		frame.getContentPane().add(panel_3);

		lbl_background = new JLabel(new ImageIcon(scaledImage));
		lbl_background.setBounds(0, 0, 584, 394);
		frame.getContentPane().add(lbl_background);

	
		frame.setVisible(true);

	}
}
