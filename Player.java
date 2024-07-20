// Player.java
public class Player {
    private String name;
    private int hp;
    private int mp;
    private int experience;
    private int attackPower;
    private String weapon;
    private String gender;

    public Player(String name, int hp, int mp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.experience = 0;
        this.attackPower = attackPower;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public String getGender(String gender){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void getWeapon(String weapon) {
        this.weapon = weapon;
    }
    public String setWeapon(String weapon) {
        return weapon;
    }

}
