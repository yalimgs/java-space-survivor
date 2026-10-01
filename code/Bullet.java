public abstract class Bullet extends Entity {
    protected int life = 80;
    protected double speed;
    protected double angle;

    protected Bullet(double x, double y, double angle, double speed, double r) {
        super(x, y, r);
        this.angle = angle;
        this.speed = speed;
        velocity.x = Math.cos(angle) * speed;
        velocity.y = Math.sin(angle) * speed;
    }

    @Override
    public void update() {
        x += velocity.x;
        y += velocity.y;
        wrap();
        if (--life <= 0) kill();
    }
}
