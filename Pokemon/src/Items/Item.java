package Items;

import java.util.ArrayList;

public class Item {

	private final String name;
	private final int ItemHP;
	private final int ItemAtk;
	private final int specialItemAtk;
	private final int ItemDef;
	private final int specialItemDef;

	public Item(String name, int ItemHP, int ItemAtk,
				int specialItemAtk, int ItemDef, int specilaItemDef) {
		this.name = name;
		this.ItemHP = ItemHP;
		this.ItemAtk = ItemAtk;
		this.specialItemAtk = specialItemAtk;
		this.ItemDef = ItemDef;
		this.specialItemDef = specilaItemDef;
	}

	public String getName() {
		return name;
	}

	public int getItemHP() {
		return ItemHP;
	}

	public int getItemAtk() {
		return ItemAtk;
	}

	public int getSpecialItemAtk() {
		return specialItemAtk;
	}

	public int getItemDef() {
		return ItemDef;
	}

	public int getSpecilaItemDef() {
		return specialItemDef;
	}


		// checks if objects are equal

	@Override
	public boolean equals(Object obj) {
		return this.name.equals(((Item) obj).getName());
	}

	// checks if the item is duplicated
	public static boolean duplicateItem(ArrayList<Item> items, Item item) {
		if (items == null || items.size() == 0)
			return false;
		return items.contains(item);
	}
}
