package Battle;

import PokeStuff.Ability;
import PokeStuff.Pokemon;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Battle implements Runnable {

	private Pokemon firstPokemon;
	private Pokemon secondPokemon;
	private final Trainer trainer1;
	private final Trainer trainer2;
	private final Logger logger;

	public Battle(Pokemon firstPokemon, Pokemon secondPokemon, Trainer trainer1, Trainer trainer2, Logger logger) {
		this.firstPokemon = firstPokemon;
		this.secondPokemon = secondPokemon;
		this.trainer1 = trainer1;
		this.trainer2 = trainer2;
		this.logger = logger;
	}


	// function which runs a battle between two pokemons
	@Override
	public void run() {
		int roundCounter = 1;
		Pokemon saveFirstPokemon = firstPokemon;
		Pokemon saveSecondPokemon = secondPokemon;

		boolean neutrelBattle = trainer2 == null;
		// If coach2 is null then the clash is against a Neutrel

		// Clash until a Pokemon is dead
		while (firstPokemon.Alive() && secondPokemon.Alive()) {
			Pokemon firstPokeCopy = null;
			Pokemon secondPokeCopy = null;

			// Cloning the current state of a pokemon to send them to clash round
			try {
				firstPokeCopy = (Pokemon) firstPokemon.clone();
				secondPokeCopy = (Pokemon) secondPokemon.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}

			// Selecting possible abilities for 2 pokemons
			assert firstPokeCopy != null;
			Ability poke1Ability = getSelectedAbility(firstPokeCopy, false);
			Ability poke2Ability;
			assert secondPokeCopy != null;
			if (neutrelBattle) {
				poke2Ability = getSelectedAbility(secondPokeCopy, true);
			} else {
				poke2Ability = getSelectedAbility(secondPokeCopy, false);
			}


			logger.log(roundCounter + ". " + firstPokemon.getName());
			if (poke1Ability != null)
				logger.log(" " + poke1Ability);
			else
				logger.log(" stunned");
			logger.log(" / " + secondPokemon.getName());
			if (poke2Ability != null)
				logger.log(" " + poke2Ability);
			else
				logger.log(" stunned");
			logger.log(" -> Rezultat:\n");


			// Pokemon attacks executed simultaneously
			ExecutorService executor = Executors.newFixedThreadPool(2);

			executor.execute(new PokemonAttack(firstPokemon, secondPokeCopy, poke1Ability));
			executor.execute(new PokemonAttack(secondPokemon, firstPokeCopy, poke2Ability));

			executor.shutdown();
			try {
				boolean finished = executor.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Post battle verifications
			int firstPokeCurrHP = firstPokemon.getHP();
			int secondPokeCurrHP = secondPokemon.getHP();
			firstPokemon = firstPokeCopy;
			secondPokemon = secondPokeCopy;

			// Logging first pokemon result
			logPokemonAttack(firstPokemon, firstPokeCurrHP, "a");

			// Logging for second pokemon
			logPokemonAttack(secondPokemon, secondPokeCurrHP, "b");

			logger.log("-===========================================-\n");

			// Implementinting the Dodge mechanic
			if (firstPokemon.isDodge()) {
				firstPokemon.setHP(firstPokeCurrHP);
				firstPokemon.setDodge(false);
				firstPokemon.setStunned(false);
			}

			if (secondPokemon.isDodge()) {
				secondPokemon.setHP(secondPokeCurrHP);
				secondPokemon.setDodge(false);
				secondPokemon.setStunned(false);
			}

			roundCounter++;
		}

		// Checking how a clash has finished
		checkResult(saveFirstPokemon, saveSecondPokemon);

	}

	// checks the final result of a battle
	private void checkResult(Pokemon saveFirstPokemon, Pokemon saveSecondPokemon) {
		// If a draw, no buffs are needed
		if (!firstPokemon.Alive() && !secondPokemon.Alive()) {
			logger.log("Draw\n");
		}
		// If second Pokemon won then it gets buffed
		if (!firstPokemon.Alive() && secondPokemon.Alive()) {
			logger.log(trainer2.getName() + "(" + secondPokemon.getName() + ") castiga" +
					" Arena invingand pe celalalt antrenor. " + secondPokemon.getName() + " are " +
					"acum + 1 la toate caracteristicile:\n");

			firstPokemon = saveFirstPokemon;
			secondPokemon = saveSecondPokemon;
			Pokemon.resetPokemon(firstPokemon);
			Pokemon.resetPokemon(secondPokemon);
			Pokemon.buffWinner(secondPokemon);

			logger.log("HP " + secondPokemon.getHP() + ", Attack " + secondPokemon.getNormalAttack() +
					", Special attack " + secondPokemon.getSpecialAttack() + ", Defense " +
					secondPokemon.getDef() + ", Special Defense " + secondPokemon.getSpecialDef() + "\n");
		}

		// If first Pokemon wins then it gets buffed
		if (firstPokemon.Alive() && !secondPokemon.Alive()) {
			logger.log(trainer1.getName() + "(" + firstPokemon.getName() + ") castiga" +
					" Arena invingand pe celalalt antrenor. " + firstPokemon.getName() + " are" +
					"acum + 1 la toate caracteristicile:\n");

			firstPokemon = saveFirstPokemon;
			secondPokemon = saveSecondPokemon;
			Pokemon.resetPokemon(firstPokemon);
			Pokemon.resetPokemon(secondPokemon);
			Pokemon.buffWinner(firstPokemon);

			logger.log("HP " + firstPokemon.getHP() + ", Attack " + firstPokemon.getNormalAttack() +
					", Special attack " + firstPokemon.getSpecialAttack() + ", Defense " +
					firstPokemon.getDef() + ", Special Defense " + firstPokemon.getSpecialDef() + "\n");
		}
	}


	// putting the attacks in the logger
	private void logPokemonAttack(Pokemon pokemon, int pokeCurrHP, String pokeNum) {
		if (pokemon.isDodge())
			logger.log("\t" + pokeNum + ". " + pokemon.getName() + " HP " + pokeCurrHP + " ");
		else
			logger.log("\t" + pokeNum + ". " + pokemon.getName() + " HP " + pokemon.getHP() + " ");
		if (pokemon.isDodge())
			logger.log("(dodge), ");
		if (pokemon.isStunned())
			logger.log("stunned, ");
		if (pokemon.getAbility1() != null && pokemon.getAbility2() != null) {
			logger.log(pokemon.getAbility1() + " " + pokemon.getAbility2());
		}
		logger.log("\n");
	}

	// randomly pick the type of attack
	private int pickAttackType(Pokemon poke) {
		Random r = new Random();
		int min = 1;
		int max = 4;

		int attType1 = -1;
		while (true) {
			attType1 = r.nextInt(max - min) + min;
			// If > 1 then it is an ability so it has to be verified
			if (attType1 > 1) {
				if (attType1 == 2) {
					if (poke.getAbility1().getCurrentCd() == 0) break;
					continue;
				} else if (poke.getAbility2().getCurrentCd() == 0) break;
				continue;
			}
			break;
		}

		return attType1;
	}

	private Ability abilityFromSimpleAttack(int dmg, boolean special) {
		return new Ability(dmg, false, false, 0, false, special);
	}

	// selects an ability for a pokemon
	private Ability getSelectedAbility(Pokemon poke, boolean isNeutrel) {
		// If Pokemon is stunned then it cannot attack
		if (poke.isStunned()) {
			poke.setStunned(false);
			return null;
		}
		// If Neutrel he can only get first type of attack
		if (isNeutrel) {
			if (poke.getSpecialAttack() != 0)
				return abilityFromSimpleAttack(poke.getSpecialAttack(), true);
			else
				return abilityFromSimpleAttack(poke.getNormalAttack(), false);
		}

		// For non-Neutrel pokemons
		int attackTypePoke1 = pickAttackType(poke);
		Ability forPoke;
		if (attackTypePoke1 == 1) {
			if (poke.getSpecialAttack() == 0) {
				forPoke = abilityFromSimpleAttack(poke.getNormalAttack(), false);
			} else forPoke = abilityFromSimpleAttack(poke.getSpecialAttack(), true);
		} else {
			if (attackTypePoke1 == 2)
				forPoke = poke.getAbility1();
			else
				forPoke = poke.getAbility2();
		}

		// if ability has dodge the set the Pokemon to dodged state
		if (forPoke.isDodge())
			poke.setDodge(true);
		return forPoke;
	}
}
