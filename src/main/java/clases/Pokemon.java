package clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Pokemon implements Serializable {

	private int id;
	private String name;
	private String nick;
	private int level;
	private String url_pokemon;
	private String url_species;
	private transient JSONObject pokemonInfo;
	private transient JSONObject speciesInfo;
	private String spriteFrontUrl;
	private String spriteBackUrl;
	private String iconUrl;
	private transient JSONArray types;
	private float max_hp;
	private float cur_hp;
	private float atk;
	private float def;
	private float sp_atk;
	private float sp_def;
	private float speed;
	private String gender;
	private ArrayList<String>[] move_list = new ArrayList[100];
	private Move[] moves = new Move[1];
	private String catch_date;
	private String eo; 

	// Constructor que recibe id y nivel
	public Pokemon(int id, int level) throws IOException {
		this.id = id;
		if (level > 100) {
			level = 100;
		} else if (level < 1) {
			level = 1;
		}
		this.level = level;
		this.url_pokemon = "https://pokeapi.co/api/v2/pokemon/" + this.id;
		this.url_species = "https://pokeapi.co/api/v2/pokemon-species/" + this.id;
		this.pokemonInfo = getInfo(this.url_pokemon);
		this.speciesInfo = getInfo(this.url_species);
		initializePokemon();
	}

	// Constructor que recibe nombre y nivel
	public Pokemon(String name, int level) throws IOException {
		this.name = name;
		if (level > 100) {
			level = 100;
		} else if (level < 1) {
			level = 1;
		}
		this.level = level;
		this.url_pokemon = "https://pokeapi.co/api/v2/pokemon/" + this.name;
		this.url_species = "https://pokeapi.co/api/v2/pokemon-species/" + this.name;
		this.pokemonInfo = getInfo(this.url_pokemon);
		this.speciesInfo = getInfo(this.url_species);
		initializePokemon();
	}

	// Método privado para inicializar las estadísticas
	private void initializePokemon() {
		this.id = this.pokemonInfo.getInt("id");

		this.name = this.pokemonInfo.getString("name");
		JSONArray names = this.speciesInfo.getJSONArray("names");
		for (int i = 0; i < names.length(); i++) {
			String lang = names.getJSONObject(i).getJSONObject("language").getString("name");
			if (lang.equals("es")) {
				this.name = names.getJSONObject(i).getString("name");
				break; // Importante salir del bucle una vez que se encuentra el nombre en español
			}
		}
		this.nick = this.name;

		this.spriteFrontUrl = this.pokemonInfo.getJSONObject("sprites").getJSONObject("other").getJSONObject("showdown")
				.getString("front_default");
		
		this.spriteBackUrl = this.pokemonInfo.getJSONObject("sprites").getJSONObject("other").getJSONObject("showdown")
				.getString("back_default");

		this.setIconUrl(this.pokemonInfo.getJSONObject("sprites").getJSONObject("versions").getJSONObject("generation-viii")
				.getJSONObject("icons").getString("front_default"));
		
		this.types = this.pokemonInfo.getJSONArray("types");

		JSONArray stats = this.pokemonInfo.getJSONArray("stats");
		float levelFactor = (float) this.level / 100;

		float baseHP = 0;
		float baseAttack = 0;
		float baseDefense = 0;
		float baseSpAtk = 0;
		float baseSpDef = 0;
		float baseSpeed = 0;

		for (int i = 0; i < stats.length(); i++) {
			JSONObject stat = stats.getJSONObject(i).getJSONObject("stat");
			String statName = stat.getString("name");
			float baseStatValue = (float) stats.getJSONObject(i).getInt("base_stat");

			switch (statName) {
			case "hp":
				baseHP = baseStatValue;
				break;
			case "attack":
				baseAttack = baseStatValue;
				break;
			case "defense":
				baseDefense = baseStatValue;
				break;
			case "special-attack":
				baseSpAtk = baseStatValue;
				break;
			case "special-defense":
				baseSpDef = baseStatValue;
				break;
			case "speed":
				baseSpeed = baseStatValue;
				break;
			default:
				break;
			}
		}

		// Calcular las estadísticas
		this.max_hp = 10 + (levelFactor * (baseHP * 2)) + this.level;
		this.cur_hp = max_hp;
		this.atk = 5 + (levelFactor * (baseAttack * 2));
		this.def = 5 + (levelFactor * (baseDefense * 2));
		this.sp_atk = 5 + (levelFactor * (baseSpAtk * 2));
		this.sp_def = 5 + (levelFactor * (baseSpDef * 2));
		this.speed = 5 + (levelFactor * (baseSpeed * 2));

		// Género
		int gender_rate = this.speciesInfo.getInt("gender_rate");
		this.gender = calculateGender(gender_rate);

		// Lista de movimientos
		JSONArray allmoves_list = this.pokemonInfo.getJSONArray("moves");
		for (int i = 0; i < allmoves_list.length(); i++) {
			JSONObject move = allmoves_list.getJSONObject(i);
			JSONArray versiones = move.getJSONArray("version_group_details");
			JSONObject version = versiones.getJSONObject(move.getJSONArray("version_group_details").length() - 1);
			if (version.getJSONObject("move_learn_method").getString("name").equals("level-up")) {
				int level = version.getInt("level_learned_at");
				if (move_list[level] == null) {
					move_list[level] = new ArrayList<String>();
				}
				this.move_list[level].add(move.getJSONObject("move").getString("name"));
			}
		}
		
		// Aprender Movimientos
		int x = 0;
		for (int i = 1; i <= this.level; i++) {
		    if (this.move_list[i] != null) {
		        for (String move : this.move_list[i]) {
		            try {
		                if (x >= this.moves.length) {
		                    // Si x excede el tamaño del array, crea un nuevo array con más tamaño
		                    Move[] newMoves = new Move[Math.min(this.moves.length + 1, 4)]; // Limitado a un tamaño máximo de 4
		                    System.arraycopy(this.moves, 0, newMoves, 0, this.moves.length);
		                    this.moves = newMoves;
		                }
		                if (x >= 4) {
		                	x = 0;
		                }
		                this.moves[x] = new Move(move);
		                x++;
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		}

		// Fecha de captura del Pokémon
        LocalDate fechaHoy = LocalDate.now();
        this.setFecha_captura(fechaHoy.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		
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

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUrl_pokemon() {
		return url_pokemon;
	}

	public void setUrl_pokemon(String url_pokemon) {
		this.url_pokemon = url_pokemon;
	}

	public String getUrl_species() {
		return url_species;
	}

	public void setUrl_species(String url_species) {
		this.url_species = url_species;
	}

	public JSONObject getPokemonInfo() {
		return pokemonInfo;
	}

	public void setPokemonInfo(JSONObject pokemonInfo) {
		this.pokemonInfo = pokemonInfo;
	}

	public JSONObject getSpeciesInfo() {
		return speciesInfo;
	}

	public void setSpeciesInfo(JSONObject speciesInfo) {
		this.speciesInfo = speciesInfo;
	}

	public String getSpriteFrontUrl() {
		return spriteFrontUrl;
	}

	public void setSpriteFrontUrl(String spriteFrontUrl) {
		this.spriteFrontUrl = spriteFrontUrl;
	}

	public String getSpriteBackUrl() {
		return spriteBackUrl;
	}

	public void setSpriteBackUrl(String spriteBackUrl) {
		this.spriteBackUrl = spriteBackUrl;
	}

	public JSONArray getTypes() {
		return types;
	}

	public void setTypes(JSONArray types) {
		this.types = types;
	}

	public int getMax_hp() {
		return (int) max_hp;
	}

	public void setMax_hp(float max_hp) {
		this.max_hp = max_hp;
	}

	public int getCur_hp() {
		return (int) cur_hp;
	}

	public void setCur_hp(float cur_hp) {
		this.cur_hp = cur_hp;
	}

	public int getAtk() {
		return (int) atk;
	}

	public void setAtk(float atk) {
		this.atk = atk;
	}

	public int getDef() {
		return (int) def;
	}

	public void setDef(float def) {
		this.def = def;
	}

	public int getSp_atk() {
		return (int) sp_atk;
	}

	public void setSp_atk(float sp_atk) {
		this.sp_atk = sp_atk;
	}

	public int getSp_def() {
		return (int) sp_def;
	}

	public void setSp_def(float sp_def) {
		this.sp_def = sp_def;
	}

	public int getSpeed() {
		return (int) speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public ArrayList<String>[] getMove_list() {
		return move_list;
	}

	public void setMove_list(ArrayList<String>[] move_list) {
		this.move_list = move_list;
	}

	public Move[] getMoves() {
		return moves;
	}

	public void setMoves(Move[] moves) {
		this.moves = moves;
	}

	public String getFecha_captura() {
		return catch_date;
	}

	public void setFecha_captura(String fecha_captura) {
		this.catch_date = fecha_captura;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getEo() {
		return eo;
	}

	public void setEo(String eo) {
		this.eo = eo;
	}

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

	public static String calculateGender(int gender_rate) {
		String gender;

		if (gender_rate == -1) {
			gender = "Unknown";
		} else if (gender_rate == 0) {
			gender = "Male";
		} else if (gender_rate == 8) {
			gender = "Female";
		} else {
			// Calcular la probabilidad de ser hembra o macho
			double male_chance = (double) (8 - gender_rate) / 8.0;
			double female_chance = 1.0 - male_chance;

			// Determinar el género en base a la probabilidad
			if (Math.random() < male_chance) {
				gender = "Male";
			} else {
				gender = "Female";
			}
		}

		return gender;
	}

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Serializar campos predeterminados

        // Serializar campos no predeterminados
        //out.writeObject(pokemonInfo.toString());
        //out.writeObject(speciesInfo.toString());
        out.writeObject(types.toString());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserializar campos predeterminados

		this.pokemonInfo = getInfo(this.url_pokemon);
		this.speciesInfo = getInfo(this.url_species);
    	
        // Deserializar campos no predeterminados
        try {
            types = new JSONArray((String) in.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
