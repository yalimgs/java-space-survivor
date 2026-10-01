import java.awt.Color;

public class RapidFirePowerUp extends PowerUp {
    public RapidFirePowerUp(double x, double y) { super(x, y); }
    @Override public void applyTo(Player p) { p.giveRapidFire(360); }
    @Override public void draw() {
        StdDraw.setPenColor(new Color(255, 180, 60));
        StdDraw.filledCircle(x, y, radius);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(x, y, "R");
    }
}
