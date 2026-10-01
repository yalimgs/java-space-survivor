public abstract class PowerUp extends Entity {
    protected int life = 600;

    protected PowerUp(double x, double y) { super(x, y, 10); }

    @Override
    public void update() {
        if (--life <= 0) kill();
    }

    public abstract void applyTo(Player p);
}
