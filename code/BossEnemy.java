import java.awt.Color;
import java.util.List;

public class BossEnemy extends Enemy {

    private int attackTimer = 120;
    private int currentAttack = 0;
    private double angle = 0;
    private boolean charging = false;
    private int chargeTimer = 0;
    private final int maxHp;

    public BossEnemy(double x, double y) {
        super(x, y, 40, 25, 500);
        this.maxHp = 25;
    }

    @Override
    public void update(Player p, List<Bullet> bullets) {
        angle += 0.02;

        if (charging) {
            x += velocity.x;
            y += velocity.y;
            velocity.scale(0.97);
            if (--chargeTimer <= 0) { charging = false; velocity.x = 0; velocity.y = 0; }
        } else {
            double dx = (YalimOzcaglayan.WIDTH/2 - x) * 0.0008;
            double dy = (YalimOzcaglayan.HEIGHT*0.7 - y) * 0.0008;
            x += dx + Math.cos(angle) * 0.6;
            y += dy + Math.sin(angle) * 0.6;
        }
        wrap();

        if (--attackTimer <= 0) {
            currentAttack = (int)(Math.random() * 3);
            performAttack(p, bullets);
            attackTimer = 90 + (int)(Math.random() * 60);
        }
    }

    private void performAttack(Player p, List<Bullet> bullets) {
        switch (currentAttack) {
            case 0:
                for (int i = 0; i < 8; i++) {
                    double a = i * Math.PI / 4;
                    bullets.add(new EnemyBullet(x, y, a));
                }
                break;
            case 1:
                double aim = Math.atan2(p.getY() - y, p.getX() - x);
                bullets.add(new EnemyBullet(x, y, aim));
                bullets.add(new EnemyBullet(x, y, aim + 0.25));
                bullets.add(new EnemyBullet(x, y, aim - 0.25));
                break;
            case 2:
                double ca = Math.atan2(p.getY() - y, p.getX() - x);
                velocity.x = Math.cos(ca) * 7;
                velocity.y = Math.sin(ca) * 7;
                charging = true;
                chargeTimer = 35;
                break;
        }
    }

    @Override
    public void draw() {
        StdDraw.setPenColor(new Color(150, 30, 30));
        StdDraw.filledCircle(x, y, radius);
        StdDraw.setPenColor(new Color(220, 80, 80));
        StdDraw.filledCircle(x, y, radius * 0.65);
        StdDraw.setPenColor(charging ? Color.YELLOW : Color.WHITE);
        StdDraw.filledCircle(x, y, radius * 0.3);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(x, y, radius * 0.15);

        StdDraw.setPenColor(new Color(120, 20, 20));
        for (int i = 0; i < 8; i++) {
            double a = angle + i * Math.PI / 4;
            double sx = x + Math.cos(a) * radius;
            double sy = y + Math.sin(a) * radius;
            double tx = x + Math.cos(a) * (radius + 12);
            double ty = y + Math.sin(a) * (radius + 12);
            StdDraw.setPenRadius(0.005);
            StdDraw.line(sx, sy, tx, ty);
        }
        StdDraw.setPenRadius();

        double w = 80, h = 6;
        double bx = x - w/2;
        double by = y + radius + 18;
        StdDraw.setPenColor(Color.DARK_GRAY);
        StdDraw.filledRectangle(x, by, w/2, h/2);
        double pct = (double) hp / maxHp;
        StdDraw.setPenColor(Color.RED);
        StdDraw.filledRectangle(bx + (w * pct)/2, by, (w * pct)/2, h/2);
    }
}
