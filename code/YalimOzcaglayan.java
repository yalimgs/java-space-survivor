/**
 * Javadoc description part:
 * Entry point and central game controller for Space Survivor.
 * SWE 501 - Assignment 3
 *
 * Javadoc tags part:
 * @author Yalim Ozcaglayan, Student ID: 2025719117
 * @since Date: 14.05.2026
 */

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class YalimOzcaglayan {

    public static final double WIDTH  = 800;
    public static final double HEIGHT = 600;

    private enum State { MENU, PLAYING, GAME_OVER }
    private State state = State.MENU;

    private Player player;
    private final List<Bullet>   bullets   = new ArrayList<>();
    private final List<Enemy>    enemies   = new ArrayList<>();
    private final List<PowerUp>  powerUps  = new ArrayList<>();
    private final List<Particle> particles = new ArrayList<>();

    private WaveManager waveManager;
    private HUD hud;

    private int score = 0;
    private boolean enterWasDown = false;

    public static void main(String[] args) { new YalimOzcaglayan().run(); }

    private void run() {
        StdDraw.setCanvasSize((int) WIDTH, (int) HEIGHT);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.enableDoubleBuffering();
        resetGame();

        StdDraw.clear(StdDraw.BLACK);
        StdDraw.show();

        while (true) {
            StdDraw.clear(StdDraw.BLACK);
            switch (state) {
                case MENU:      renderMenu();     break;
                case PLAYING:   updateGame(); renderGame();  break;
                case GAME_OVER: renderGame(); renderGameOver(); break;
            }
            StdDraw.show(20);
        }
    }

    private void resetGame() {
        player = new Player(WIDTH / 2, HEIGHT / 2);
        bullets.clear(); enemies.clear(); powerUps.clear(); particles.clear();
        waveManager = new WaveManager();
        hud = new HUD();
        score = 0;
    }

    private void renderMenu() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH/2, HEIGHT*0.70, "SPACE  SURVIVOR");
        StdDraw.text(WIDTH/2, HEIGHT*0.55, "by Yalim Ozcaglayan");
        StdDraw.text(WIDTH/2, HEIGHT*0.40, "A/D rotate    W thrust    SPACE shoot");
        StdDraw.text(WIDTH/2, HEIGHT*0.32, "Survive waves. Boss every 5 waves!");
        StdDraw.text(WIDTH/2, HEIGHT*0.22, "Press ENTER to start");

        boolean enterDown = StdDraw.isKeyPressed(KeyEvent.VK_ENTER);
        if (enterDown && !enterWasDown) { resetGame(); state = State.PLAYING; }
        enterWasDown = enterDown;
    }

    private void updateGame() {
        if (StdDraw.isKeyPressed(KeyEvent.VK_A)) player.rotate(+1);
        if (StdDraw.isKeyPressed(KeyEvent.VK_D)) player.rotate(-1);
        if (StdDraw.isKeyPressed(KeyEvent.VK_W)) player.thrust();
        if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && player.canShoot()) {
            player.shoot(bullets);
        }

        waveManager.update(enemies);

        player.update();
        for (Bullet b : bullets)      b.update();
        for (Enemy e : enemies)       e.update(player, bullets);
        for (PowerUp p : powerUps)    p.update();
        for (Particle pa : particles) pa.update();

        handleCollisions();

        bullets.removeIf(b -> !b.isAlive());
        enemies.removeIf(e -> !e.isAlive());
        powerUps.removeIf(p -> !p.isAlive());
        particles.removeIf(p -> !p.isAlive());

        if (player.getLives() <= 0) {
            state = State.GAME_OVER;
        }
    }

    private void renderGame() {
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        for (int i = 0; i < 40; i++) {
            double sx = (i * 73) % WIDTH;
            double sy = (i * 137) % HEIGHT;
            StdDraw.filledCircle(sx, sy, 1);
        }
        for (Particle pa : particles) pa.draw();
        for (PowerUp p  : powerUps)   p.draw();
        for (Bullet b   : bullets)    b.draw();
        for (Enemy e    : enemies)    e.draw();
        player.draw();
        hud.draw(player, score, waveManager.getWave());
    }

    private void renderGameOver() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH/2, HEIGHT*0.60, "GAME  OVER");
        StdDraw.text(WIDTH/2, HEIGHT*0.50, "Final Score: " + score);
        StdDraw.text(WIDTH/2, HEIGHT*0.40, "Wave Reached: " + waveManager.getWave());
        StdDraw.text(WIDTH/2, HEIGHT*0.30, "Press R to restart");
        if (StdDraw.isKeyPressed(KeyEvent.VK_R)) { resetGame(); state = State.PLAYING; }
    }

    private void handleCollisions() {
        for (Bullet b : bullets) {
            if (b instanceof PlayerBullet) {
                for (Enemy e : enemies) {
                    if (b.collidesWith(e)) {
                        b.kill();
                        e.takeDamage(1);
                        spawnExplosion(e.getX(), e.getY(), 6);
                        if (!e.isAlive()) {
                            score += e.getScoreValue();
                            spawnExplosion(e.getX(), e.getY(), 20);
                            if (e instanceof Asteroid)   ((Asteroid) e).split(enemies);
                            if (e instanceof BossEnemy)  dropPowerUpGuaranteed(e.getX(), e.getY());
                            else                          maybeDropPowerUp(e.getX(), e.getY());
                        }
                        break;
                    }
                }
            } else if (b instanceof EnemyBullet) {
                if (b.collidesWith(player) && !player.isInvulnerable()) {
                    b.kill();
                    player.takeDamage(1);
                    spawnExplosion(player.getX(), player.getY(), 12);
                }
            }
        }

        for (Enemy e : enemies) {
            if (e.collidesWith(player) && !player.isInvulnerable()) {
                player.takeDamage(1);
                if (!(e instanceof BossEnemy)) e.takeDamage(99);
                spawnExplosion(player.getX(), player.getY(), 15);
            }
        }

        for (PowerUp p : powerUps) {
            if (p.collidesWith(player)) {
                p.applyTo(player);
                p.kill();
            }
        }
    }

    private void maybeDropPowerUp(double x, double y) {
        double r = Math.random();
        if (r < 0.08)      powerUps.add(new ShieldPowerUp(x, y));
        else if (r < 0.18) powerUps.add(new RapidFirePowerUp(x, y));
    }

    private void dropPowerUpGuaranteed(double x, double y) {
        if (Math.random() < 0.5) powerUps.add(new ShieldPowerUp(x, y));
        else                     powerUps.add(new RapidFirePowerUp(x, y));
    }

    private void spawnExplosion(double x, double y, int n) {
        for (int i = 0; i < n; i++) particles.add(new Particle(x, y));
    }
}