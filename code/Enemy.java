import java.util.List;

public abstract class Enemy extends Entity {
    protected int hp;
    protected int scoreValue;

    protected Enemy(double x, double y, double radius, int hp, int scoreValue) {
        super(x, y, radius);
        this.hp = hp;
        this.scoreValue = scoreValue;
    }

    public void takeDamage(int d) {
        hp -= d;
        if (hp <= 0) kill();
    }
    public int getScoreValue() { return scoreValue; }

    public abstract void update(Player p, List<Bullet> bullets);

    @Override public void update() { /* not used */ }
}
