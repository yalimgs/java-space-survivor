import java.awt.Color;
import java.util.List;

public class Player extends Entity {

    private double angle = Math.PI / 2;
    private int lives = 3;
    private int shootCooldown = 0;
    private final int baseCooldown  = 10;
    private int invulnTimer   = 0;
    private int rapidFireTimer = 0;
    private int shieldTimer    = 0;

    public Player(double x, double y) { super(x, y, 14); }

    public void rotate(int dir) { angle += dir * 0.13; }

    public void thrust() {
        velocity.x += Math.cos(angle) * 0.30;
        velocity.y += Math.sin(angle) * 0.30;
        velocity.clampMagnitude(7.0);
    }

    public boolean canShoot() { return shootCooldown <= 0; }

    public void shoot(List<Bullet> bullets) {
        int cd = (rapidFireTimer > 0) ? baseCooldown / 3 : baseCooldown;
        shootCooldown = cd;
        double bx = x + Math.cos(angle) * radius;
        double by = y + Math.sin(angle) * radius;
        bullets.add(new PlayerBullet(bx, by, angle));
        if (rapidFireTimer > 0) {
            bullets.add(new PlayerBullet(bx, by, angle + 0.15));
            bullets.add(new PlayerBullet(bx, by, angle - 0.15));
        }
    }

    @Override
    public void update() {
        x += velocity.x;
        y += velocity.y;
        velocity.scale(0.97);
        wrap();
        if (shootCooldown  > 0) shootCooldown--;
        if (invulnTimer    > 0) invulnTimer--;
        if (rapidFireTimer > 0) rapidFireTimer--;
        if (shieldTimer    > 0) shieldTimer--;
    }

    public void takeDamage(int dmg) {
        if (shieldTimer > 0) { shieldTimer = 0; return; }
        if (invulnTimer > 0) return;
        lives -= dmg;
        invulnTimer = 90;
    }

    public boolean isInvulnerable() { return invulnTimer > 0 || shieldTimer > 0; }
    public int  getLives()   { return lives; }
    public void addLife()    { lives++; }
    public void giveRapidFire(int t) { rapidFireTimer = t; }
    public void giveShield(int t)    { shieldTimer    = t; }
    public int  getShieldTimer()     { return shieldTimer; }
    public int  getRapidTimer()      { return rapidFireTimer; }

    @Override
    public void draw() {
        if (invulnTimer > 0 && (invulnTimer / 5) % 2 == 0) return;

        double[] xs = new double[3];
        double[] ys = new double[3];
        xs[0] = x + Math.cos(angle) * 18;
        ys[0] = y + Math.sin(angle) * 18;
        xs[1] = x + Math.cos(angle + 2.5) * 12;
        ys[1] = y + Math.sin(angle + 2.5) * 12;
        xs[2] = x + Math.cos(angle - 2.5) * 12;
        ys[2] = y + Math.sin(angle - 2.5) * 12;

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledPolygon(xs, ys);

        if (shieldTimer > 0) {
            StdDraw.setPenColor(new Color(80, 180, 255));
            StdDraw.circle(x, y, radius + 6);
        }
    }
}
