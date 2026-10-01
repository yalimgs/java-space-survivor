import java.awt.Color;
import java.util.List;

public class AlienShip extends Enemy {
    private int shootTimer;
    private final double speed = 1.6;

    public AlienShip(double x, double y) {
        super(x, y, 16, 3, 150);
        shootTimer = 60 + (int)(Math.random() * 60);
    }

    @Override
    public void update(Player p, List<Bullet> bullets) {
        double dx = p.getX() - x;
        double dy = p.getY() - y;
        double len = Math.sqrt(dx*dx + dy*dy);
        if (len > 0.001) {
            velocity.x = (dx / len) * speed;
            velocity.y = (dy / len) * speed;
        }
        x += velocity.x;
        y += velocity.y;
        wrap();

        if (--shootTimer <= 0) {
            double angle = Math.atan2(p.getY() - y, p.getX() - x);
            bullets.add(new EnemyBullet(x, y, angle));
            shootTimer = 90;
        }
    }

    @Override
    public void draw() {
        StdDraw.setPenColor(new Color(180, 60, 200));
        StdDraw.filledEllipse(x, y, radius, radius * 0.5);
        StdDraw.setPenColor(new Color(120, 220, 255));
        StdDraw.filledEllipse(x, y + 4, radius * 0.5, radius * 0.4);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.circle(x, y + 4, radius * 0.5);
    }
}
