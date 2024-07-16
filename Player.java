public class Player {
    private String name;
    private String gender;
    private String weapon;
    private int experience;
    private int hp;
    private int mp;
    private int attackPower;

    public Player(String name, int experience, int hp, int mp) {
        this.name = name;
        this.experience = experience;
        this.hp = hp;
        this.mp = mp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + this.name + '\'' +
                ", experience='" + this.experience + '\'' +
                ", hp='" + this.hp + '\'' +
                ", mp='" + this.mp + '\'' +
                '}';
    }
}
