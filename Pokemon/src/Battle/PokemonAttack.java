package Battle;


import PokeStuff.Ability;
import PokeStuff.Pokemon;

public class PokemonAttack implements Runnable {

	private final Pokemon you;
	private final Pokemon oponnent;
	private final Ability attack;

	public PokemonAttack(Pokemon you, Pokemon opponent, Ability attack) {
		this.you = you;
		this.oponnent = opponent;
		this.attack = attack;
	}


	@Override
	public void run() {
		you.hitOpponent(oponnent, attack);
	}
}
