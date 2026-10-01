public class PlayerBullet extends Bullet {
    public PlayerBullet(double x, double y, double angle) {
        super(x, y, angle, 9.0, 3);
    }
    @Override public void draw() {
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.filledCircle(x, y, radius);
    }
}
