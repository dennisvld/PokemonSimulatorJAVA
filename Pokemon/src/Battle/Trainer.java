package Battle;

import Items.Item;
import Items.ItemFactory;
import PokeStuff.Pokemon;
import PokeStuff.PokemonFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Trainer {

	private final String name;
	private final int age;
	private final ArrayList<Pokemon> pokemons;

	public Trainer(String name, int age, ArrayList<Pokemon> pokemons) {
		this.name = name;
		this.age = age;
		this.pokemons = pokemons;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Pokemon> getPokemons() {
		return pokemons;
	}


	 // Method to read from file and create the two coacher for duel
	 // Also checks for duplicates and returns appropiate error

	public static ArrayList<Trainer> createCoaches(String path) {
		ArrayList<Trainer> trainers = new ArrayList<>();
		PokemonFactory pf = new PokemonFactory();

		File f = new File(path);
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			// read 2 coaches
			for (int i = 0; i < 2; i++) {
				String name = br.readLine();
				int age = Integer.parseInt(br.readLine());

				// read pokemons
				ArrayList<Pokemon> pokemonList = new ArrayList<>();
				String[] pokemons = br.readLine().split(",");
				// place pokemon duplicate test here
				if (Pokemon.duplicatePokemon(pokemonList, pokemons)) {
					throw new IllegalArgumentException("Duplicate pokemon detected");
				}

				// read items
				String[] items = br.readLine().split(":");

				// match each pokemon with their items
				for (int k = 0; k < 3; k++) {
					ArrayList<Item> pokemonItemList = new ArrayList<>();
					String[] pokeItems = items[k].split(";");
					for (var itemName : pokeItems) {
						if (itemName.equals("0")) {
							continue;
						}
						Item item = ItemFactory.createItem(itemName);
						if (!Item.duplicateItem(pokemonItemList, item)) {
							pokemonItemList.add(item);
						} else {
							throw new IllegalArgumentException("Duplicate item detected");
						}
					}
					pokemonList.add(pf.createPokemon(pokemons[k], pokemonItemList));

					// place duplicate pokemon test here
				}
				trainers.add(new Trainer(name, age, pokemonList));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Trainer trainer : trainers) {
			trainer.applyAllItemBuffs();
		}

		return trainers;
	}

	public void applyAllItemBuffs() {
		for (Pokemon p : this.pokemons) {
			ArrayList<Item> items = p.getItems();
			if (items != null && items.size() > 0) {
				for (Item i : items) {
					p.applyItemBuffs(i);
				}
			}
		}
	}
}
