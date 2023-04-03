package Battle;

import PokeStuff.Pokemon;
import PokeStuff.PokemonFactory;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Arena {

	private static Arena theArena;

	private int numOfAdventures;
	private Trainer trainer1;
	private Trainer trainer2;

	public static Arena Instance(ArrayList<Trainer> trainers) {
		if (theArena == null) {
			theArena = new Arena();
		}
		theArena.setTrainer1(trainers.get(0));
		theArena.setTrainer2(trainers.get(1));
		theArena.numOfAdventures = 0;
		return theArena;
	}


	private Arena() {

	}

	public void setTrainer1(Trainer trainer1) {
		this.trainer1 = trainer1;
	}

	public void setTrainer2(Trainer trainer2) {
		this.trainer2 = trainer2;
	}

	// the main function which picks the adventure types and loads the adventure
	public void adventure(String logType) {
		Random r = new Random();
		int min = 1;
		int max = 4; // exclusive
		Pokemon forTrainer1;
		Pokemon forTrainer2;

		// Until all 4 adventures are played
		while (numOfAdventures < 4) {
			int fightType = r.nextInt(max - min) + min;

			// For first 3 adventures a pokemon is chosen by his position
			if (numOfAdventures < 3) {
				forTrainer1 = trainer1.getPokemons().get(numOfAdventures);
				forTrainer2 = trainer2.getPokemons().get(numOfAdventures);
			} else { // For last adventure the pokemons are chosen based on their stats
				forTrainer1 = calculateStrongestPokemon(trainer1.getPokemons());
				forTrainer2 = calculateStrongestPokemon(trainer2.getPokemons());
			}

			logBattleType(logType, fightType);

			// Determining the type of fight and initiating the clashes
			// 1 -> vs. Neutrel1 / 2 -> vs. Neutrel2 / 3 -> vs. other Trainer
			switch (fightType) {
				case 1 -> pokemonClashInitiator(forTrainer1, forTrainer2, trainer1, trainer2, 1, logType);
				case 2 -> pokemonClashInitiator(forTrainer1, forTrainer2, trainer1, trainer2, 2, logType);
				case 3 -> pokemonClashInitiator(forTrainer1, forTrainer2, trainer1, trainer2, 3, logType);
			}

			// After batlle vs other trainer, go to next adventure
			if (fightType == 3) {
				numOfAdventures++;
			}
		}
		numOfAdventures = 0;
	}

	private void logBattleType(String logType, int fightType) {
		Logger log = new Logger(logType);
		log.log("Aventura " + (numOfAdventures + 1) + "\n");
		log.log("Lupta tip: " + fightType + "\n");
		log.log("----------------------------------------\n");
	}


	// function which calculates the strongest pokemon from a trainer's list
	private Pokemon calculateStrongestPokemon(ArrayList<Pokemon> pokes) {
		if (pokes == null || pokes.size() == 0)
			return null;

		Pokemon strongest = pokes.get(0);
		int power = strongest.getHP() + strongest.getNormalAttack() + strongest.getSpecialAttack() +
				strongest.getDef() + strongest.getSpecialDef();

		for (int i = 1; i < 3; i++) {
			Pokemon p = pokes.get(i);
			int newPower = p.getHP() + p.getNormalAttack() + p.getSpecialAttack() + p.getDef() + p.getSpecialDef();
			if (newPower > power) {
				strongest = p;
				power = newPower;
			} else if (newPower == power) {
				int compare = p.getName().compareTo(strongest.getName());
				if (compare < 0)
					strongest = p;
			}
		}

		return strongest;
	}

	// Method to initiate a clash for two Traineres
	 
	 // forTrainer1 = trainer1's Pokemon
	 // forTrainer2 = trainer2's Pokemon
	 // trainer1 = The first trainer
	 // param trainer2 = The second trainer
	 // param fightType = The type of the fight
	 // param logType = The type of logging
	 
	private void pokemonClashInitiator(Pokemon forTrainer1, Pokemon forTrainer2, Trainer trainer1, Trainer trainer2, int fightType, String logType) {
		Logger loggerTrainer1 = new Logger(logType);
		Logger loggerTrainer2 = new Logger(logType);
		Pokemon adversary1 = forTrainer2;
		Pokemon adversary2 = forTrainer1;

		PokemonFactory pf = new PokemonFactory();
		// If fightType1 -> adversary will be Neutrel1 for both Traineres
		if (fightType == 1) {
			adversary1 = pf.createPokemon("Neutrel1", null);
			adversary2 = pf.createPokemon("Neutrel1", null);
		} else if (fightType == 2) { // If 2 then adversary Neutrel2
			adversary1 = pf.createPokemon("Neutrel2", null);
			adversary2 = pf.createPokemon("Neutrel2", null);
		}
		// Clash between 2 Traineres
		if (fightType == 3) {
			Battle cl = new Battle(forTrainer1, forTrainer2, trainer1, trainer2, loggerTrainer1);
			cl.run();
			loggerTrainer1.log("\n");
		} else { // Two threads for simultaneous Battles between trainer and neutrel
			ExecutorService executor = Executors.newFixedThreadPool(2);
			executor.execute(new Battle(forTrainer1, adversary1,
					trainer1, null, loggerTrainer1));
			executor.execute(new Battle(forTrainer2, adversary2,
					trainer2, null, loggerTrainer2));

			executor.shutdown();
			try {
				boolean finished = executor.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Logging the results
			loggerTrainer1.log("\n");
			loggerTrainer2.log("\n");
		}

	}

	public static void start(String path, String logType) {
		ArrayList<Trainer> trainers = Trainer.createCoaches(path);
		Arena fightingArena = Arena.Instance(trainers);
		fightingArena.adventure(logType);
	}

}
