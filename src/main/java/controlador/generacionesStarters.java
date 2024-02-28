package controlador;

import javax.swing.*;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class generacionesStarters {
    
    public static void mostrarPokemonGeneracion(String pokemonplanta,String pokemonfuego, String pokemonagua
                                                , JPanel panelplanta, JPanel panelfuego, JPanel panelagua) {

        pokemonplanta = pokemonplanta.toLowerCase();
        pokemonfuego = pokemonfuego.toLowerCase();
        pokemonagua = pokemonagua.toLowerCase();
        
       // if (panelagua != null || panelplanta != null || panelfuego != null) {
            panelagua.removeAll();
            panelplanta.removeAll();
            panelfuego.removeAll();
      //  }

        try {
            JSONObject plantaInfo = getPokemonInfo("https://pokeapi.co/api/v2/pokemon/" + pokemonplanta);
            JSONObject fuegoInfo = getPokemonInfo("https://pokeapi.co/api/v2/pokemon/" + pokemonfuego);
            JSONObject aguaInfo = getPokemonInfo("https://pokeapi.co/api/v2/pokemon/" + pokemonagua);

            if (plantaInfo != null) {
                String image = plantaInfo.getJSONObject("sprites").getJSONObject("other").getJSONObject("showdown")
                        .getString("front_default");

                ImageIcon imageIcon = new ImageIcon(new URL(image));
                JLabel imageLabel = new JLabel(imageIcon);
                imageLabel.setBounds(0,0,150,150);
                panelplanta.add(imageLabel);
            }

            if (fuegoInfo != null) {
                String image = fuegoInfo.getJSONObject("sprites").getJSONObject("other").getJSONObject("showdown")
                        .getString("front_default");

                ImageIcon imageIcon = new ImageIcon(new URL(image));
                JLabel imageLabel = new JLabel(imageIcon);
                imageLabel.setBounds(0,0,150,150);
                panelfuego.add(imageLabel);
            }

            if (aguaInfo != null) {
                String image = aguaInfo.getJSONObject("sprites").getJSONObject("other").getJSONObject("showdown")
                        .getString("front_default");

                ImageIcon imageIcon = new ImageIcon(new URL(image));
                JLabel imageLabel = new JLabel(imageIcon);
                imageLabel.setBounds(0,0,150,150);
                panelagua.add(imageLabel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        panelagua.revalidate();
        panelagua.repaint();
        panelplanta.revalidate();
        panelplanta.repaint();
        panelfuego.revalidate();
        panelfuego.repaint();
        */
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

    public URL obtenerGifPokemon(String nombrePokemon) {
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
}