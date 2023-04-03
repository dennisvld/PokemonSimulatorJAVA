package Items;

public class ItemBuilder {

	// Using Builder Pattern
	private final String name;
	private int ItemHP;
	private int ItemAtk;
	private int specialItemAtk;
	private int ItemDef;
	private int specialItemDef;


	public ItemBuilder(String name) {
		this.name = name;
	}

	public ItemBuilder withItemHP(int ItemHP) {
		ItemHP = checkAttribute(ItemHP);
		this.ItemHP = ItemHP;
		return this;
	}

	public ItemBuilder withItemAtk(int ItemAtk) {
		ItemAtk = checkAttribute(ItemAtk);
		this.ItemAtk = ItemAtk;
		return this;
	}

	public ItemBuilder withSpecialItemAtk(int specialItemAtk) {
		specialItemAtk = checkAttribute(specialItemAtk);
		this.specialItemAtk = specialItemAtk;
		return this;
	}

	public ItemBuilder withItemDef(int ItemDef) {
		ItemDef = checkAttribute(ItemDef);
		this.ItemDef = ItemDef;
		return this;
	}

	public ItemBuilder withSpecialItemDef(int specialItemDef) {
		specialItemDef = checkAttribute(specialItemDef);
		this.specialItemDef = specialItemDef;
		return this;
	}

	// check if the item has a name in order to build it accordingly
	private boolean validateItem(ItemBuilder ib) {
		return ib.name != null;
	}


	// final build of the item
	public Item build() {
		if (validateItem(this)) {
			return new Item(this.name, this.ItemHP, this.ItemAtk,
					this.specialItemAtk, this.ItemDef,
					this.specialItemDef);
		}
		throw new IllegalArgumentException();
	}

	// checks if the attribute has a negative value (fringe case)
	// and turns it into zero
	int defVal = 0;
	private int checkAttribute(int attribute) {
		if (attribute < defVal)
			attribute = defVal;
		return attribute;
	}




}
