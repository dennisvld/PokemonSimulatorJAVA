package PokeStuff;

import Items.Item;

import java.util.ArrayList;

public class Pokemon implements Cloneable{

    private final String name;
    private int HP, baseHP, normalAttack, specialAttack, def, specialDef;
    private Ability ability1, ability2;
    private final ArrayList<Item> items;
    private boolean stunned, dodge;

    public Pokemon(String name, int HP, int normalAttack,
                   int specialAttack, int def, int specialDef,
                   ArrayList<Item> items) {
        this.name = name;
        this.HP = HP;
        this.normalAttack = normalAttack;
        this.specialAttack = specialAttack;
        this.def = def;
        this.specialDef = specialDef;
        this.items = items;
        this.baseHP = HP;
        this.stunned = false;
        this.dodge = false;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
    public void setNormalAttack(int normalAttack) {
        this.normalAttack = normalAttack;
    }
    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }
    public void setDef(int def) {
        this.def = def;
    }
    public void setSpecialDef(int specialDef) {
        this.specialDef = specialDef;
    }
    public void setAbility1(Ability ability1) {
        this.ability1 = ability1;
    }
    public void setAbility2(Ability ability2) {
        this.ability2 = ability2;
    }
    public void setBaseHP(int baseHP) {
        this.baseHP = baseHP;
    }
    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }
    public void setDodge(boolean dodge) {
        this.dodge = dodge;
    }
    public String getName() {
        return name;
    }
    public int getHP() {
        return HP;
    }
    public int getNormalAttack() {
        return normalAttack;
    }
    public int getSpecialAttack() {
        return specialAttack;
    }
    public int getDef() {
        return def;
    }
    public int getSpecialDef() {
        return specialDef;
    }
    public Ability getAbility1() {
        return ability1;
    }
    public Ability getAbility2() {
        return ability2;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    public int getBaseHP() {
        return baseHP;
    }
    public boolean isStunned() {
        return stunned;
    }
    public boolean isDodge() {
        return dodge;
    }
    public boolean Alive() {
        return this.HP > 0;
    }

    // function which equips an item to a pokemon
    public void applyItemBuffs(Item item) {
        this.HP += item.getItemHP();
        this.baseHP += item.getItemHP();
        if (this.normalAttack != 0)
            this.normalAttack += item.getItemAtk();
        if (this.specialAttack != 0)
            this.specialAttack += item.getSpecialItemAtk();
        if (this.def != 0)
            this.def += item.getItemDef();
        if (this.specialDef != 0)
            this.specialDef += item.getSpecilaItemDef();
    }

    // function which levels up the pokemon's stats after winning
    public static void buffWinner(Pokemon winnerPokemon) {
        winnerPokemon.setBaseHP(winnerPokemon.getBaseHP() + 1);
        winnerPokemon.setHP(winnerPokemon.getBaseHP());
        if (winnerPokemon.getNormalAttack() != 0)
            winnerPokemon.setNormalAttack(winnerPokemon.getNormalAttack() + 1);
        if (winnerPokemon.getSpecialAttack() != 0)
            winnerPokemon.setSpecialAttack(winnerPokemon.getSpecialAttack() + 1);
        if (winnerPokemon.getDef() != 0)
            winnerPokemon.setDef(winnerPokemon.getDef() + 1);
        if (winnerPokemon.getSpecialDef() != 0)
            winnerPokemon.setSpecialDef(winnerPokemon.getSpecialDef() + 1);
        winnerPokemon.setDodge(false);
        winnerPokemon.setStunned(false);
    }

    // function whichi simulates an attack or ability being used by a pokemon
    public void hitOpponent(Pokemon opponent, Ability attack) {
        // Reduce the cooldown of the abilities if any
        if (this.ability1 != null && this.ability1.getCurrentCd() > 0)
            this.ability1.setCurrentCd(this.ability1.getCurrentCd() - 1);
        if (this.ability2 != null && this.ability2.getCurrentCd() > 0)
            this.ability2.setCurrentCd(this.ability2.getCurrentCd() - 1);
        // in case pokemon is stunned he does not attack
        if (attack == null) {
            return;
        }

        // checking if attack is an ability or a simple attack
        if (attack.isRealAbility()) {
            opponent.setHP(opponent.getHP() - attack.getDmg());
            if (attack.isStun())
                opponent.setStunned(true);
            attack.setCurrentCd(attack.getCd());
        } else {
            int damageDealt;
            if (attack.isSpecialAttack()) {
                damageDealt = attack.getDmg() - opponent.getSpecialDef();
            }
            else {
                damageDealt = attack.getDmg() - opponent.getDef();
            }
            if (damageDealt < 0)
                damageDealt = 0;
            opponent.setHP(opponent.getHP() - damageDealt);

        }

    }

    // stat reset
    public static void resetPokemon(Pokemon pokemon) {
        pokemon.setHP(pokemon.getBaseHP());
        if (pokemon.getAbility1() != null)
            pokemon.getAbility1().setCurrentCd(0);
        if (pokemon.getAbility2() != null)
            pokemon.getAbility2().setCurrentCd(0);
        pokemon.setStunned(false);
        pokemon.setDodge(false);
    }

    // function which detects duplicate pokemon
    public static boolean duplicatePokemon(ArrayList<Pokemon> pokes, String[] pokemonName) {
        if (pokes == null || pokes.size() == 0)
            return false;
        for (Pokemon p : pokes) {
            for(String s : pokemonName) {
                if (p.getName().equals(s))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
