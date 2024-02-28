package clases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveData {

    public static void guardarPokemon(Pokemon pokemon) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("pokemon.dat"))) {
            salida.writeObject(pokemon);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
