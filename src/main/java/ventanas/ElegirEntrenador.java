package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ElegirEntrenador extends JFrame{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ElegirEntrenador window = new ElegirEntrenador();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ElegirEntrenador() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 350, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JLabel lbl_info = new JLabel("Seleccione el género de su personaje");
		lbl_info.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_info.setFont(new Font("Consolas", Font.BOLD, 15));
		lbl_info.setBounds(10, 10, 316, 46);
		frame.getContentPane().add(lbl_info);
		
		JButton boton_chica = new JButton("");
		boton_chica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nombreEntrenador("chica");
				frame.dispose();
				
			}
		});
		boton_chica.setBounds(180, 86, 146, 175);
		boton_chica.setBorderPainted(false);
		boton_chica.setContentAreaFilled(false);
		ImageIcon iconChica = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/EntrenadoraChica.png"));
		boton_chica.setIcon(iconChica);
		frame.getContentPane().add(boton_chica);
		
		JPanel panel_azul = new JPanel();
		panel_azul.setBounds(0, 82, 170, 221);
		panel_azul.setBackground(new Color(137,207,240));
		frame.getContentPane().add(panel_azul);
		panel_azul.setLayout(null);
		
		JButton boton_chico = new JButton("");
		boton_chico.setBounds(10, 0, 146, 175);
		panel_azul.add(boton_chico);
		boton_chico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nombreEntrenador("chico");
				frame.dispose();
			}
		});
		boton_chico.setBorderPainted(false);
		boton_chico.setContentAreaFilled(false);
		ImageIcon iconChico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/EntrenadorChico2.png"));
		boton_chico.setIcon(iconChico);

		JPanel panel_rosa = new JPanel();
		panel_rosa.setBounds(169, 82, 175, 221);
		panel_rosa.setBackground(new Color(245,195,194));
		frame.getContentPane().add(panel_rosa);
		panel_rosa.setLayout(null);
		
		JLabel lbl_masculino = new JLabel("");
		lbl_masculino.setBounds(75, 51, 24, 30);
		frame.getContentPane().add(lbl_masculino);
		lbl_masculino.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon iconMasculino = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/masculino.png"));
		lbl_masculino.setIcon(iconMasculino);

		JLabel lbl_femenino = new JLabel("");
		lbl_femenino.setBounds(240, 51, 24, 30);
		frame.getContentPane().add(lbl_femenino);
		ImageIcon iconFemenino = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/femenino.png"));
		lbl_femenino.setIcon(iconFemenino);

		frame.setVisible(true);
	}
	
	private void nombreEntrenador(String genero) {
		
		final JFrame frame = new JFrame("Entrenador");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
        //frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
		
		JLabel lbl_informacion = new JLabel("¡Elige el nombre de tu entrenador!");
		lbl_informacion.setBounds(0, 10, 447, 33);
		lbl_informacion.setFont(new Font("Consolas", Font.BOLD, 18));
		lbl_informacion.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lbl_informacion);
		
		JLabel lbl_imagenEntrenador = new JLabel("");
		lbl_imagenEntrenador.setBounds(0, 53, 173, 160);
		lbl_imagenEntrenador.setHorizontalAlignment(SwingConstants.CENTER);
		if(genero.equals("chica")) {
			
			ImageIcon iconChica = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/EntrenadoraChica.png"));
			lbl_imagenEntrenador.setIcon(iconChica);
			
		}else {
			
			ImageIcon iconChico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/EntrenadorChico2.png"));
			lbl_imagenEntrenador.setIcon(iconChico);
		}
		
		panel.add(lbl_imagenEntrenador);
		
		final JTextField txtF_nombreEntrenador = new JTextField(20);
		txtF_nombreEntrenador.setBounds(183, 116, 204, 33);
		panel.add(txtF_nombreEntrenador);
		txtF_nombreEntrenador.setColumns(10);
		
		panel.add(txtF_nombreEntrenador);
		
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setBounds(0, 214, 447, 31);
		
		JButton btn_aceptar = new JButton("Aceptar");
		btn_aceptar.setBounds(74, 219, 85, 21);
		btn_aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(txtF_nombreEntrenador.getText().isEmpty()) {
					
					JOptionPane.showMessageDialog(frame, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					//conectar con la siguiente ventana
					
				}
				
			}
		});
		buttonPanel.add(btn_aceptar);
		
		JButton btn_cancelar = new JButton("Cancelar");
		btn_cancelar.setBounds(312, 219, 85, 21);
		btn_cancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				new ElegirEntrenador();
				frame.dispose();
			}
		});
		buttonPanel.add(btn_cancelar);
		
		panel.add(buttonPanel);
		
		frame.getContentPane().add(panel);
		
		
		
		frame.setSize(461, 294);
		frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		frame.setVisible(true);
	}
	
	
}
