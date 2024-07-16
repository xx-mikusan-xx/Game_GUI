public class Player {
    private String name;
    private String gender;
    private String weapon;

    public Player(String name, String gender, String weapon) {
        this.name = name;
        this.gender = gender;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", weapon='" + weapon + '\'' +
                '}';
    }
}
