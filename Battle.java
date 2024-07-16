import java.util.Random;

class Battle {
    // バトルのロジックを実装
    public void fight(Player player, int enemyStrength) {
        player.setHp(player.getHp() - enemyStrength);
        player.setExperience(player.getExperience() + 100);  // 仮の経験値
    }

    public boolean escape() {
        Random rand = new Random();
        return rand.nextBoolean();
    }
}