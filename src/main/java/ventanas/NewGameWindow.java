package ventanas;


import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import clases.Trainer;

import java.awt.Font;


public class NewGameWindow {

	private JFrame frame;
	private JButton btnCargarPartida;
	private JButton btnNuevaPartida;
	private JLabel lblBackground;

	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewGameWindow window = new NewGameWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public NewGameWindow() {
		// this.setContentPane(fondo);
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Pokochi\r\n");
		frame.getContentPane().setLayout(null);
		frame.setSize(800, 525);
		frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
	

		
		ImageIcon imageIcon = new ImageIcon("images/PokochiFondoInicio900x500.png");
		Image image = imageIcon.getImage();
		// Image scaledImage = image.getScaledInstance(lblBackground.getWidth(),
		// lblBackground.getHeight(), Image.SCALE_DEFAULT);
		Image scaledImage = image.getScaledInstance(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
				Image.SCALE_DEFAULT);

		btnNuevaPartida = new JButton("Nueva Partida");
		btnNuevaPartida.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnNuevaPartida.setBounds(77, 400, 158, 30);
		frame.getContentPane().add(btnNuevaPartida);

		btnCargarPartida = new JButton("Cargar Partida");
		btnCargarPartida.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnCargarPartida.setBounds(77, 440, 158, 30);
		frame.getContentPane().add(btnCargarPartida);

		//----------- meter la imagen de fondo ----------------
		lblBackground = new JLabel(new ImageIcon(scaledImage));
		lblBackground.setBounds(0, 0, 785, 500);
		frame.getContentPane().add(lblBackground);

		btnCargarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("save.dat"))) {
                    Trainer trainer = (Trainer) entrada.readObject();
                    PantallaCargarPartidas pcp = new PantallaCargarPartidas(trainer);
                } catch (FileNotFoundException e3) {
                    // No hay datos guardados, elegir inicial

					JOptionPane.showMessageDialog(frame, "No se encuentran datos de ninguna partida anterior, creando nueva partida...");
    		        new ElegirEntrenador();
					frame.dispose();
                } catch (IOException e1) {
                    System.err.println("Error de entrada/salida: " + e1.getMessage());
                    
                } catch (ClassNotFoundException e2) {
                    System.err.println("Clase no encontrada: " + e2.getMessage());
                    
                }
			}
		});

		btnNuevaPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("save.dat"))) {
                    Trainer trainer = (Trainer) entrada.readObject();

				// si no hay ninguna partida guardada anteriormente deberia crearla directamente 
					int res = JOptionPane.showConfirmDialog(frame,
					"¿Seguro que quieres crear una nueva partida?\nEsto borrará todos los datos de la anterior\n\tNick: " + trainer.getName(),
					"Confirmar Borrado", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
					// tecnicamente no se borra solo se sobreescribe 
					JOptionPane.showMessageDialog(frame, "Partida antigua borrada, creando nueva partida...");
					new ElegirEntrenador();
					frame.dispose();
					} else {
					JOptionPane.showMessageDialog(frame, "Acción cancelada");
					}

                    
                } catch (FileNotFoundException e3) {
                    // No hay datos guardados, elegir inicial
					JOptionPane.showMessageDialog(frame, "Creando nueva partida...");
    		        new ElegirEntrenador();
					frame.dispose();
                } catch (IOException e1) {
                    System.err.println("Error de entrada/salida: " + e1.getMessage());
                    
                } catch (ClassNotFoundException e2) {
                    System.err.println("Clase no encontrada: " + e2.getMessage());
                    
                }	
			}
		});

		frame.setVisible(true);
	

	}
	
}
