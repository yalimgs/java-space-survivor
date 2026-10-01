import java.util.List;

public class Asteroid extends Enemy {
    public enum Size { LARGE, MEDIUM, SMALL }
    private final Size size;
    private double rotation = 0;
    private final double rotSpeed;

    public Asteroid(double x, double y, Size size) {
        super(x, y, radiusFor(size), hpFor(size), scoreFor(size));
        this.size = size;
        double a = Math.random() * Math.PI * 2;
        double s = (size == Size.SMALL ? 2.5 : size == Size.MEDIUM ? 1.7 : 1.1);
        velocity.x = Math.cos(a) * s;
        velocity.y = Math.sin(a) * s;
        rotSpeed = (Math.random() - 0.5) * 0.05;
    }

    private static double radiusFor(Size s) {
        return s == Size.LARGE ? 36 : s == Size.MEDIUM ? 22 : 12;
    }
    private static int hpFor(Size s) {
        return s == Size.LARGE ? 3 : s == Size.MEDIUM ? 2 : 1;
    }
    private static int scoreFor(Size s) {
        return s == Size.LARGE ? 20 : s == Size.MEDIUM ? 50 : 100;
    }

    @Override
    public void update(Player p, List<Bullet> bullets) {
        x += velocity.x;
        y += velocity.y;
        rotation += rotSpeed;
        wrap();
    }

    public void split(List<Enemy> enemies) {
        if (size == Size.SMALL) return;
        Size next = (size == Size.LARGE) ? Size.MEDIUM : Size.SMALL;
        enemies.add(new Asteroid(x, y, next));
        enemies.add(new Asteroid(x, y, next));
    }

    @Override
    public void draw() {
        StdDraw.setPenColor(java.awt.Color.GRAY);
        int sides = 10;
        double[] xs = new double[sides];
        double[] ys = new double[sides];
        for (int i = 0; i < sides; i++) {
            double a = rotation + i * 2 * Math.PI / sides;
            double r = radius * (0.8 + 0.2 * Math.sin(i * 3.1));
            xs[i] = x + Math.cos(a) * r;
            ys[i] = y + Math.sin(a) * r;
        }
        StdDraw.filledPolygon(xs, ys);
        StdDraw.setPenColor(java.awt.Color.DARK_GRAY);
        StdDraw.polygon(xs, ys);
    }
}
