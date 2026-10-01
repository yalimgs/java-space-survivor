public class EnemyBullet extends Bullet {
    public EnemyBullet(double x, double y, double angle) {
        super(x, y, angle, 5.0, 4);
        life = 120;
    }
    @Override public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(x, y, radius);
    }
}
