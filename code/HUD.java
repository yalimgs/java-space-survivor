import java.awt.Color;
import java.awt.Font;

public class HUD {
    public void draw(Player p, int score, int wave) {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 14));

        StdDraw.textLeft(10, YalimOzcaglayan.HEIGHT - 15, "Lives: " + p.getLives());
        StdDraw.textLeft(10, YalimOzcaglayan.HEIGHT - 35, "Score: " + score);
        StdDraw.text(YalimOzcaglayan.WIDTH / 2, YalimOzcaglayan.HEIGHT - 15, "Wave " + wave);

        if (p.getShieldTimer() > 0)
            StdDraw.textRight(YalimOzcaglayan.WIDTH - 10, YalimOzcaglayan.HEIGHT - 15,
                    "SHIELD " + (p.getShieldTimer() / 60 + 1) + "s");
        if (p.getRapidTimer() > 0)
            StdDraw.textRight(YalimOzcaglayan.WIDTH - 10, YalimOzcaglayan.HEIGHT - 35,
                    "RAPID " + (p.getRapidTimer() / 60 + 1) + "s");
    }
}
