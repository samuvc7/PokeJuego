package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import clases.Trainer;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class ElegirEntrenador extends JFrame{

	private JFrame frmPersonalizacin;

	public ElegirEntrenador() {
		frmPersonalizacin = new JFrame();
		frmPersonalizacin.setTitle("Personalización");
		frmPersonalizacin.setBounds(100, 100, 350, 300);
		frmPersonalizacin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPersonalizacin.setLocationRelativeTo(null);
		frmPersonalizacin.setResizable(false);
		frmPersonalizacin.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
		frmPersonalizacin.getContentPane().setLayout(null);
		
		JLabel lbl_info = new JLabel("Seleccione el género de su personaje");
		lbl_info.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_info.setFont(new Font("Consolas", Font.BOLD, 15));
		lbl_info.setBounds(10, 10, 316, 46);
		frmPersonalizacin.getContentPane().add(lbl_info);
		
		// ========= CHICO ===========
		JPanel panel_azul = new JPanel();
		panel_azul.setBounds(0, 82, 170, 181);
		panel_azul.setBackground(new Color(137,207,240));
		frmPersonalizacin.getContentPane().add(panel_azul);
		panel_azul.setLayout(null);
		
		// Botón
		JButton boton_chico = new JButton("");
		boton_chico.setBounds(0, 0, 170, 181);
		boton_chico.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		boton_chico.setBorderPainted(false);
		boton_chico.setContentAreaFilled(false);
		boton_chico.setFocusPainted(false);
		ImageIcon iconChico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Entrenador1.png"));
		boton_chico.setIcon(iconChico);

		panel_azul.add(boton_chico);
		boton_chico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nombreEntrenador("chico");
				frmPersonalizacin.dispose();
			}
		});
		
		// ======== CHICA =========
		JPanel panel_rosa = new JPanel();
		panel_rosa.setBounds(169, 82, 170, 181);
		panel_rosa.setBackground(new Color(245,195,194));
		frmPersonalizacin.getContentPane().add(panel_rosa);
		panel_rosa.setLayout(null);
		
		// Botón
		JButton boton_chica = new JButton("");
		boton_chica.setContentAreaFilled(false);
		boton_chica.setBorderPainted(false);
		boton_chica.setFocusPainted(false);
		boton_chica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		boton_chica.setBounds(0, 0, 170, 181);
		ImageIcon iconChica = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Entrenador2.png"));
		boton_chica.setIcon(iconChica);
		panel_rosa.add(boton_chica);
		
		boton_chica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nombreEntrenador("chica");
				frmPersonalizacin.dispose();
			}
		});
		
		/*
		// ========== ICONOS ============
		JLabel lbl_masculino = new JLabel("");
		lbl_masculino.setBounds(64, 51, 24, 30);
		frame.getContentPane().add(lbl_masculino);
		lbl_masculino.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon iconMasculino = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/trainer1.png"));
		lbl_masculino.setIcon(iconMasculino);

		JLabel lbl_femenino = new JLabel("");
		lbl_femenino.setBounds(240, 51, 24, 30);
		frame.getContentPane().add(lbl_femenino);
		ImageIcon iconFemenino = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/trainer2.png"));
		lbl_femenino.setIcon(iconFemenino);
		 */

		frmPersonalizacin.setVisible(true);
	}
	
	private void nombreEntrenador(String genero) {
		
		final JFrame frame = new JFrame("Entrenador");
		frame.setTitle("Personalización");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
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
		lbl_imagenEntrenador.setBounds(64, 50, 64, 128);
		lbl_imagenEntrenador.setHorizontalAlignment(SwingConstants.CENTER);
		if(genero.equals("chica")) {
			
			ImageIcon iconChica = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Entrenador2.png"));
			lbl_imagenEntrenador.setIcon(iconChica);
			
		}else {
			
			ImageIcon iconChico = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/Entrenador1.png"));
			lbl_imagenEntrenador.setIcon(iconChico);
		}
		
		panel.add(lbl_imagenEntrenador);
		
		final JTextField txtF_nombreEntrenador = new JTextField(20);
		txtF_nombreEntrenador.setBounds(183, 116, 204, 33);
		txtF_nombreEntrenador.setFont(new Font("Consolas", Font.PLAIN, 18));
		panel.add(txtF_nombreEntrenador);
		txtF_nombreEntrenador.setColumns(10);
		txtF_nombreEntrenador.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (getLength() + str.length() <= 12) { // Cambia 15 al valor máximo deseado
					super.insertString(offs, str, a);
				}
			}
		});
		
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
					int style = 1;
					if (genero.equals("chica")) {
						style = 2;
					}
					
					Trainer trainer = new Trainer(txtF_nombreEntrenador.getText(), style);
					new StarterSelectionWindow(trainer);
					frame.dispose();
					
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
