package clases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveData {

	public static void saveTrainer(Trainer trainer) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("save.dat"))) {
            salida.writeObject(trainer);

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
