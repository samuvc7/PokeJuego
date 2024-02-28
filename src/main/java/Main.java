import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import clases.Pokemon;
import ventanas.MenuWindow;
import ventanas.PokemonDataWindow;
import ventanas.StarterSelectionWindow;

public class Main {

	public static void main(String[] args) {
		
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("pokemon.dat"))) {
            // Leer el objeto Pokemon desde el archivo
            Pokemon pk = (Pokemon) entrada.readObject();
    		//new PokemonDataWindow(pk);
            new MenuWindow(pk);
            
        } catch (FileNotFoundException e) {
        	// No hay datos guardados, elegir inicial
    		new StarterSelectionWindow();
    		
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
            
        } catch (ClassNotFoundException e) {
            System.err.println("Clase no encontrada: " + e.getMessage());
            
        }
		

	}

}
