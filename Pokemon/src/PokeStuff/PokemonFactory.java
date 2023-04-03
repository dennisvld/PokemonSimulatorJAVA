package PokeStuff;

import Items.Item;

import java.util.ArrayList;
import java.util.Locale;

public class PokemonFactory {
	// create pokemons based on specific types
	// adding their stats and abilities (except for neutrels)
	public Pokemon createPokemon(String pokeName, ArrayList<Item> items) {
		pokeName = pokeName.toUpperCase(Locale.ROOT);
		Pokemon pokemon;

		switch (pokeName) {
			case "NEUTREL1":
				return new Pokemon("Neutrel1", 10, 3, 0, 1, 1, items);
			case "NEUTREL2":
				return new Pokemon("Neutrel2", 20, 4, 0, 1, 1, items);
			case "PIKACHU":
				pokemon = new Pokemon("Pikachu", 35, 0, 4, 2, 3, items);
				pokemon.setAbility1(new Ability(6, false, false, 4, true, false));
				pokemon.setAbility2(new Ability(4, true, true, 5, true, false));
				return pokemon;

			case "BULBASAUR":
				pokemon = new Pokemon("Bulbasaur", 42, 0, 5, 3, 1, items);
				pokemon.setAbility1(new Ability(6, false, false, 4, true, false));
				pokemon.setAbility2(new Ability(5, false, false, 3, true, false));
				return pokemon;

			case "CHARMANDER":
				pokemon = new Pokemon("Charmander", 50, 4, 0, 3, 2, items);
				pokemon.setAbility1(new Ability(4, true, false, 4, true, false));
				pokemon.setAbility2(new Ability(7, false, false, 6, true, false));
				return pokemon;

			case "EEVEE":
				pokemon = new Pokemon("Eevee", 39, 0, 4, 3, 3, items);
				pokemon.setAbility1(new Ability(5, false, false, 3, true, false));
				pokemon.setAbility2(new Ability(3, true, false, 3, true, false));
				return pokemon;

			case "JIGGLYPUFF":
				pokemon = new Pokemon("Jigglypuff", 34, 4, 0, 2, 3, items);
				pokemon.setAbility1(new Ability(4, true, false, 4, true, false));
				pokemon.setAbility2(new Ability(3, true, false, 4, true, false));
				return pokemon;

			case "MEOWTH":
				pokemon = new Pokemon("Meowth", 41, 3, 0, 4, 2, items);
				pokemon.setAbility1(new Ability(5, false, true, 4, true, false));
				pokemon.setAbility2(new Ability(1, false, true, 3, true, false));
				return pokemon;

			case "PSYDUCK":
				pokemon = new Pokemon("Psyduck", 43, 3, 0, 3, 3, items);
				pokemon.setAbility1(new Ability(2, false, false, 4, true, false));
				pokemon.setAbility2(new Ability(2, true, false, 5, true, false));
				return pokemon;

			case "SNORLAX":
				pokemon = new Pokemon("Snorlax", 62, 3, 0, 6, 4, items);
				pokemon.setAbility1(new Ability(4, true, false, 5, true, false));
				pokemon.setAbility2(new Ability(0, false, true, 5, true, false));
				return pokemon;

			case "VULPIX":
				pokemon = new Pokemon("Vulpix", 36, 5, 0, 2, 4, items);
				pokemon.setAbility1(new Ability(8, true, false, 6, true, false));
				pokemon.setAbility2(new Ability(2, false, true, 7, true, false));
				return pokemon;

			case "SQUIRTLE":
				pokemon = new Pokemon("Squirtle", 60, 0, 3, 5, 5, items);
				pokemon.setAbility1(new Ability(4, false, false, 3, true, false));
				pokemon.setAbility2(new Ability(2, true, false, 2, true, false));
				return pokemon;

			default:
				return null;
		}
	}
}
