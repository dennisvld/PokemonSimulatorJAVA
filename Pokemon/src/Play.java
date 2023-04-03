import Battle.Arena;

public class Play {


	public static void main(String[] args) {
		String logType = null;

		if (args.length > 0) {
			logType = args[0];
		}

		Arena.start("Tests/ErrDuplicateItems.txt", logType);
	}
}
