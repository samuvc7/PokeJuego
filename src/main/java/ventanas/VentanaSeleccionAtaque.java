package ventanas;

import clases.Move;
import clases.Pokemon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaSeleccionAtaque extends JDialog {
    private JComboBox<String> comboBoxAtaques;
    private Pokemon pokemon;
    private static Move ataqueSeleccionado;

    public VentanaSeleccionAtaque(CombateWindow ventanaCombate, Pokemon pokemon) {

        
        super(ventanaCombate, "Seleccionar Ataque", true);

        this.pokemon = pokemon;
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setBounds(100, 100, 300, 150);
        getContentPane().setLayout(new GridLayout(0, 1, 0, 5)); // Usar un GridLayout para los botones

        // Obtener los movimientos del Pokémon
        Move[] ataques = pokemon.getMoves();

        // Crear un botón para cada ataque del Pokémon
        for (Move ataque : ataques) {
            // Reemplazar "ñ" con su representación Unicode en el nombre del ataque
            String nombreAtaque = ataque.getName().replace("ñ", "\u00F1");
            JButton btnAtaque = new JButton(nombreAtaque);
            btnAtaque.setBackground(new Color(230, 233, 240));
            btnAtaque.setFont(new Font("Consolas", Font.BOLD, 11));
            btnAtaque.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Obtener el ataque seleccionado y almacenarlo en el campo de clase
                    ataqueSeleccionado = ataque;
                    System.out.println("Ataque seleccionado: " + ataqueSeleccionado.getName());

                    // Cerrar la ventana de selección de ataque
                    dispose();
                }
            });
            getContentPane().add(btnAtaque);
        }
    }

    // Método para obtener el ataque seleccionado
    public static Move getAtaqueSeleccionado() {
        return ataqueSeleccionado;
    }

    public Object getBotonesAtaque() {
       throw new UnsupportedOperationException("Unimplemented method 'getBotonesAtaque'");
    }
}
