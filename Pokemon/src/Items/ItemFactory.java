package Items;

import java.util.Locale;

public class ItemFactory {
	// factory method to create an item
	public static Item createItem(String name) {
		Item item;
		name = name.toUpperCase(Locale.ROOT);

		item = switch (name) {
			case "SCUT" -> new ItemBuilder(name)
					.withItemDef(2)
					.withSpecialItemDef(2)
					.build();
			case "VESTA" -> new ItemBuilder(name)
					.withItemHP(10)
					.build();
			case "SABIUTA" -> new ItemBuilder(name)
					.withItemAtk(3)
					.build();
			case "BAGHETA MAGICA" -> new ItemBuilder(name)
					.withSpecialItemAtk(3)
					.build();
			case "VITAMINE" -> new ItemBuilder(name)
					.withItemHP(2)
					.withItemAtk(2)
					.withSpecialItemAtk(2)
					.build();
			case "BRAD DE CRACIUN" -> new ItemBuilder(name)
					.withItemAtk(3)
					.withItemDef(1)
					.build();
			case "PELERINA" -> new ItemBuilder(name)
					.withSpecialItemDef(3)
					.build();
			default -> null;
		};
		return item;
	}
}
