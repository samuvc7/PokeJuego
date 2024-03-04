import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import clases.Pokemon;
import clases.Trainer;
import ventanas.ElegirEntrenador;
import ventanas.MenuWindow;
import ventanas.PokemonDataWindow;
import ventanas.StarterSelectionWindow;

public class Main {

	public static void main(String[] args) {
		
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("save.dat"))) {
        	Trainer trainer = (Trainer) entrada.readObject();
            new MenuWindow(trainer);
            
        } catch (FileNotFoundException e) {
        	// No hay datos guardados, elegir inicial
    		//new StarterSelectionWindow();
    		new ElegirEntrenador();
    		
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
            
        } catch (ClassNotFoundException e) {
            System.err.println("Clase no encontrada: " + e.getMessage());
            
        }
		

	}

}
