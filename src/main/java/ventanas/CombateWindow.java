package ventanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.json.JSONObject;

import clases.Move;
import clases.Pokemon;
import clases.Trainer;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CombateWindow extends JDialog {

    private JPanel contentPane;
    private String nombrePokemon;
    private JTextField txt_mipokemon;

    public CombateWindow(JFrame parent, Pokemon pokemon, Trainer trainer, String[] nombresStarters) {
        super(parent, "Ventana de Combate", true);

        this.nombrePokemon = pokemon.getName();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 439, 346);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblPokemon = new JLabel("");
        lblPokemon.setBounds(90, 115, 100, 100);
        contentPane.add(lblPokemon);
        contentPane.add(crearPanelVida(pokemon));

        JLabel lblPokemonEnemigo = new JLabel("");
        lblPokemonEnemigo.setBounds(252, 46, 100, 100);
        contentPane.add(lblPokemonEnemigo);
        contentPane.add(crearPanelVida(pokemon));

        mostrarPokemonAleatorio(nombresStarters, lblPokemonEnemigo);

        try {
            ImageIcon icon = new ImageIcon(new URL(pokemon.getSpriteBackUrl()));
            Image image = icon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
            lblPokemon.setIcon(new ImageIcon(image));
            mostrarPokemonAleatorio(nombresStarters, lblPokemonEnemigo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton btnLuchar = new JButton("LUCHAR");
        btnLuchar.setForeground(new Color(255, 0, 0));
        btnLuchar.setBackground(new Color(240, 202, 202));
        btnLuchar.setFont(new Font("Consolas", Font.BOLD, 11));
        btnLuchar.setBounds(225, 243, 188, 24);
        contentPane.add(btnLuchar);
        btnLuchar.addActionListener(e -> {
            // Aquí pasas el Pokémon actual a la ventana de selección de ataques
            VentanaSeleccionAtaque ventanaAtaques = new VentanaSeleccionAtaque(this, pokemon);
            ventanaAtaques.setLocationRelativeTo(null);
            ventanaAtaques.setVisible(true);

            Move ataqueSeleccionado = VentanaSeleccionAtaque.getAtaqueSeleccionado();
            if (ataqueSeleccionado != null) {
                mostrarDetallesMovimiento(ataqueSeleccionado);
            }
        });

        JButton btnHuir = new JButton("HUIR");
        btnHuir.setForeground(new Color(60, 60, 255));
        btnHuir.setBackground(new Color(162, 199, 240));
        btnHuir.setFont(new Font("Consolas", Font.BOLD, 11));
        btnHuir.setBounds(324, 278, 89, 23);
        contentPane.add(btnHuir);
        btnHuir.addActionListener(e -> {
            new MenuWindow(pokemon, trainer);
            parent.setEnabled(true);
            dispose();
        });

        JButton btnPokemon = new JButton("POKÉMON");
        btnPokemon.setForeground(new Color(0, 128, 0));
        btnPokemon.setBackground(new Color(189, 255, 149));
        btnPokemon.setFont(new Font("Consolas", Font.BOLD, 11));
        btnPokemon.setBounds(225, 278, 89, 23);
        contentPane.add(btnPokemon);
        btnPokemon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog((JFrame) null, "Seleccionar Pokémon", true);
                dialog.setSize(100, 300);
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(6, 1));
                for (int i = 0; i < 6; i++) {
                    if (trainer.getTeam()[i] != null) {

                        Pokemon pokemon = trainer.getTeam()[i];

                        // Obtener la URL del icono del Pokémon
                        URL iconUrl = null;
                        try {
                            iconUrl = new URL(pokemon.getIconUrl());
                        } catch (MalformedURLException ex) {
                            ex.printStackTrace();
                        }

                        // Crear un ImageIcon a partir de la URL
                        ImageIcon pokemonIcon = new ImageIcon(iconUrl);

                        // Crear un botón con la imagen y el nombre del Pokémon
                        JButton btn = new JButton(pokemon.getName(), pokemonIcon);
                        btn.setFont(new Font("Consolas", Font.BOLD, 11));
                        btn.setBackground(new Color(235, 237, 240));
                        btn.setForeground(Color.BLACK);
                        btn.setHorizontalTextPosition(SwingConstants.CENTER);
                        btn.setVerticalTextPosition(SwingConstants.BOTTOM);

                        // Boton para mmostrar un JDialog con el pokemon al que vas a cambiar
                        btn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.BOLD, 11));
                                UIManager.put("OptionPane.buttonFont", new Font("Consolas", Font.BOLD, 11));
                                UIManager.put("OptionPane.messageForeground", Color.BLACK);

                                JOptionPane.showMessageDialog(null, "Has seleccionado a " + pokemon.getName(),
                                        "Selección", JOptionPane.INFORMATION_MESSAGE);

                                dialog.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
                                dialog.dispose();

                                // Cambiar el nombre del Pokémon en el JTextField
                                txt_mipokemon.setText("¿QUÉ DESEA HACER " + pokemon.getName().toUpperCase() + " ?");

                                // Cambiar la imagen del Pokémon controlado por el jugador
                                try {
                                    ImageIcon icon = new ImageIcon(new URL(pokemon.getSpriteBackUrl()));
                                    Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                                    lblPokemon.setIcon(new ImageIcon(image));
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });// fin btn JDialog
                        panel.add(btn);
                    }
                }
                JScrollPane scrollPane = new JScrollPane(panel);
                dialog.add(scrollPane);
                dialog.setLocationRelativeTo(null);
                dialog.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));
                dialog.setVisible(true);
            }
        });

        txt_mipokemon = new JTextField();
        txt_mipokemon.setForeground(new Color(128, 128, 0));
        txt_mipokemon.setBackground(new Color(255, 255, 255));
        txt_mipokemon.setFont(new Font("Consolas", Font.BOLD, 11));
        txt_mipokemon.setText("¿QUÉ DESEA HACER?");
        txt_mipokemon.setBounds(0, 239, 196, 62);
        txt_mipokemon.setEditable(false);
        contentPane.add(txt_mipokemon);
        txt_mipokemon.setColumns(10);

        JLabel lblFondo = new JLabel("");
        lblFondo.setBounds(0, -12, 500, 261);
        lblFondo.setIcon(new ImageIcon("images/fondoCombate.png"));
        contentPane.add(lblFondo);
    }

    // Dialogo para mostrar los detalles del movimiento elegido.
    private void mostrarDetallesMovimiento(Move move) {
        JDialog dialog = new JDialog(this, "Detalles del Movimiento", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setLayout(new BorderLayout());

        JPanel panelDetalles = new JPanel(new GridLayout(5, 1));
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Configurar la fuente para todos los componentes de texto
        Font consolas = new Font("Consolas", Font.BOLD, 11);

        JLabel lblNombre = new JLabel("Nombre: " + move.getName());
        lblNombre.setFont(consolas);
        JLabel lblTipo = new JLabel("Tipo: " + move.getType());
        lblTipo.setFont(consolas);
        JLabel lblPotencia = new JLabel("Potencia: " + move.getPower());
        lblPotencia.setFont(consolas);
        JLabel lblPrecision = new JLabel("Precisión: " + move.getAccuracy());
        lblPrecision.setFont(consolas);
        JLabel lblDescripcion = new JLabel("Descripción: " + move.getDesc());
        lblDescripcion.setFont(consolas);

        panelDetalles.add(lblNombre);
        panelDetalles.add(lblTipo);
        panelDetalles.add(lblPotencia);
        panelDetalles.add(lblPrecision);
        panelDetalles.add(lblDescripcion);

        dialog.getContentPane().add(panelDetalles, BorderLayout.CENTER);
        dialog.setIconImage(Toolkit.getDefaultToolkit().getImage("images/pokebola.png"));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(230, 233, 240));
        btnCerrar.setFont(new Font("Consolas", Font.BOLD, 11));
        btnCerrar.addActionListener(e -> dialog.dispose());
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnCerrar);
        dialog.getContentPane().add(panelBoton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }// fin

    private JPanel crearPanelVida(Pokemon pokemon) {
        JPanel panelVida = new JPanel();
        panelVida.setLayout(null);
        panelVida.setBounds(10, 90, 140, 11);

        JProgressBar barHp = new JProgressBar();
        barHp.setFont(new Font("Consolas", Font.PLAIN, 10));
        barHp.setBounds(0, 0, 140, 11);
        barHp.setMaximum(pokemon.getMax_hp());
        barHp.setValue(pokemon.getCur_hp());
        barHp.setForeground(Color.GREEN);
        if (barHp.getValue() < barHp.getMaximum() / 2) {
            barHp.setForeground(Color.YELLOW);
        }
        if (barHp.getValue() < barHp.getMaximum() / 5) {
            barHp.setForeground(Color.RED);
        }
        barHp.setBackground(Color.LIGHT_GRAY);

        panelVida.add(barHp);

        return panelVida;
    }

    private void mostrarPokemonAleatorio(String[] nombresStarters, JLabel lblPokemonEnemigo) {
        Random random = new Random();
        int randomIndex = random.nextInt(nombresStarters.length);
        String pokemonAleatorio = nombresStarters[randomIndex];
        URL gifUrl = obtenerGifPokemon(pokemonAleatorio);
        if (gifUrl != null) {
            try {
                ImageIcon enemigoIcon = new ImageIcon(gifUrl);
                Image enemigoImage = enemigoIcon.getImage().getScaledInstance(50, 60, Image.SCALE_DEFAULT);
                lblPokemonEnemigo.setIcon(new ImageIcon(enemigoImage));
                System.out.println("URL del gif del Pokemon enemigo: " + gifUrl);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No se pudo obtener la URL del gif del Pokemon enemigo");
        }
    }

    protected static URL obtenerGifPokemon(String nombrePokemon) {
        try {
            JSONObject pokemon = getPokemonInfo("https://pokeapi.co/api/v2/pokemon/" + nombrePokemon.toLowerCase());
            if (pokemon != null) {
                String gifUrl = pokemon.getJSONObject("sprites").getJSONObject("other").getJSONObject("showdown")
                        .getString("front_default");
                return new URL(gifUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static JSONObject getPokemonInfo(String url) throws IOException {
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
}
