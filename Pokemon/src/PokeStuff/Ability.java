package PokeStuff;

public class Ability {
	private final int dmg;
	private final boolean stun;
	private final boolean dodge;
	private final int cd;
	private int currentCd;
	private final boolean realAbility;
	private final boolean specialAttack;

	public Ability(int dmg, boolean stun, boolean dodge, int cd, boolean realAbility, boolean specialAttack) {
		this.dmg = dmg;
		this.stun = stun;
		this.dodge = dodge;
		this.cd = cd;
		this.realAbility = realAbility;
		this.specialAttack = specialAttack;
		this.currentCd = 0;
	}

	public int getDmg() {
		return dmg;
	}

	public boolean isStun() {
		return stun;
	}

	public boolean isDodge() {
		return dodge;
	}

	public int getCd() {
		return cd;
	}

	public int getCurrentCd() {
		return currentCd;
	}

	public boolean isRealAbility() {
		return realAbility;
	}

	public boolean isSpecialAttack() {
		return specialAttack;
	}

	public void setCurrentCd(int currentCd) {
		this.currentCd = currentCd;
	}

	@Override
	public String toString() {
		return "Ability{" +
				"dmg=" + dmg +
				", stun=" + stun +
				", dodge=" + dodge +
				", currentCd=" + currentCd +
				", cd=" + cd +
				'}';
	}
}
