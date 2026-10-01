import java.awt.Color;

public class Particle extends Entity {
    private int life;
    private final int maxLife;

    public Particle(double x, double y) {
        super(x, y, 2);
        double a = Math.random() * Math.PI * 2;
        double s = 1 + Math.random() * 3;
        velocity.x = Math.cos(a) * s;
        velocity.y = Math.sin(a) * s;
        maxLife = 25 + (int)(Math.random() * 20);
        life = maxLife;
    }

    @Override
    public void update() {
        x += velocity.x;
        y += velocity.y;
        velocity.scale(0.92);
        if (--life <= 0) kill();
    }

    @Override
    public void draw() {
        float t = (float) life / maxLife;
        StdDraw.setPenColor(new Color(1f, t, 0f, 1f));
        StdDraw.filledCircle(x, y, radius);
    }
}
