package clases;

import java.io.Serializable;

public class Trainer implements Serializable {

	private String name;
	private Pokemon[] team;
	private String lastconnected;
	private int level;
	private int partner;
	private int style;
	private int[] completed_quests;

	public Trainer(String name, int style) {
		this.name = name;
		this.team = new Pokemon[6];
		this.level = 1;
		this.setPartner(0);
		this.setStyle(style);
		completed_quests = new int[0];
		
	}
	
	public Trainer(String name, Pokemon pk) {
		this.name = name;
		this.team = new Pokemon[6];
		this.addPokemon(pk, 0);
		this.level = 1;
		this.setPartner(0);
		this.setStyle(1);
		completed_quests = new int[0];

		
	}

	public String getLastConnected() {
		return lastconnected;
	}

	public void setLastConnected(String lastconnected) {

		this.lastconnected = lastconnected;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Pokemon[] getTeam() {
		return team;
	}

	public void setTeam(Pokemon[] team) {
		this.team = team;
	}

	public void addPokemon(Pokemon pokemon, int pos) {
		this.team[pos] = pokemon;
		if (pokemon.getEo() == null) pokemon.setEo(getName());
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPartner() {
		return partner;
	}

	public void setPartner(int partner) {
		this.partner = partner;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public int[] getCompleted_quests() {
		return completed_quests;
	}

	public void setCompleted_quests(int[] completed_quests) {
		this.completed_quests = completed_quests;
	}

}
