package clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Move implements Serializable {

	private int id;
    private String raw_name;
    private String name;
    private String url_move;
    private transient JSONObject moveInfo;
    private String type;
    private String damage;
    private int power;
    private int accuracy;
    private String desc;


	public Move(String name) throws IOException {
		this.raw_name = name;
        this.url_move = "https://pokeapi.co/api/v2/move/" + this.raw_name;
        this.moveInfo = getInfo(this.url_move);
        
        this.id = this.moveInfo.getInt("id");
        
        this.name = this.raw_name;
        JSONArray names = this.moveInfo.getJSONArray("names");
        for (int i = 0; i < names.length(); i++) {
            String lang = names.getJSONObject(i).getJSONObject("language").getString("name");
            if (lang.equals("es")) {
                this.name = names.getJSONObject(i).getString("name");
                break; // Importante salir del bucle una vez que se encuentra el nombre en español
            }
        }
        
        this.type = this.moveInfo.getJSONObject("type").getString("name");
        
        this.damage = this.moveInfo.getJSONObject("damage_class").getString("name");
        
        Object pw = this.moveInfo.get("power");
        try {
            this.power = (int) pw;
            
        } catch (Exception ex) {
        	if (this.damage.equals("status")) {
            	this.power = 0;

            } else {
            	this.power = 100;
            }
        }

        Object ac = this.moveInfo.get("accuracy");
        try {
            this.accuracy = (int) ac;
            
        } catch (Exception ex) {
        	this.accuracy = 100;
        }

        JSONArray entries = this.moveInfo.getJSONArray("flavor_text_entries");
        for (int i = 0; i < entries.length(); i++) {
            String lang = entries.getJSONObject(i).getJSONObject("language").getString("name");
            if (lang.equals("es")) {
                this.setDesc(entries.getJSONObject(i).getString("flavor_text"));
                break; // Importante salir del bucle una vez que se encuentra el nombre en español
            }
        }
        
        
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRaw_name() {
		return raw_name;
	}

	public void setRaw_name(String raw_name) {
		this.raw_name = raw_name;
	}

	// Acceder a la API
	private static JSONObject getInfo(String url) throws IOException {
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
	
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Serializar campos predeterminados

        // Serializar campos no predeterminados
        //out.writeObject(moveInfo.toString());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserializar campos predeterminados

        this.moveInfo = getInfo(this.url_move);

    }
    
}

