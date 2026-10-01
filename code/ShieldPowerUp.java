import java.awt.Color;

public class ShieldPowerUp extends PowerUp {
    public ShieldPowerUp(double x, double y) { super(x, y); }
    @Override public void applyTo(Player p) { p.giveShield(360); }
    @Override public void draw() {
        StdDraw.setPenColor(new Color(80, 180, 255));
        StdDraw.filledCircle(x, y, radius);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(x, y, "S");
    }
}
