import java.util.List;

public class WaveManager {
    private int wave = 0;
    private int spawnTimer = 0;
    private int waveBreak  = 0;
    private int enemiesToSpawn = 0;
    private boolean bossSpawnedThisWave = false;

    public int getWave() { return wave; }

    public void update(List<Enemy> enemies) {
        if (enemiesToSpawn == 0 && enemies.isEmpty()) {
            if (waveBreak <= 0) {
                wave++;
                bossSpawnedThisWave = false;
                if (wave % 5 == 0) {
                    enemiesToSpawn = 2;
                } else {
                    enemiesToSpawn = 4 + wave * 2;
                }
                spawnTimer = 0;
                waveBreak  = 120;
            } else {
                waveBreak--;
            }
            return;
        }

        if (wave % 5 == 0 && !bossSpawnedThisWave) {
            enemies.add(new BossEnemy(YalimOzcaglayan.WIDTH/2, YalimOzcaglayan.HEIGHT*0.8));
            bossSpawnedThisWave = true;
        }

        if (enemiesToSpawn > 0 && --spawnTimer <= 0) {
            spawnEnemy(enemies);
            enemiesToSpawn--;
            spawnTimer = Math.max(15, 60 - wave * 2);
        }
    }

    private void spawnEnemy(List<Enemy> enemies) {
        double x, y;
        if (Math.random() < 0.5) {
            x = Math.random() < 0.5 ? 0 : YalimOzcaglayan.WIDTH;
            y = Math.random() * YalimOzcaglayan.HEIGHT;
        } else {
            x = Math.random() * YalimOzcaglayan.WIDTH;
            y = Math.random() < 0.5 ? 0 : YalimOzcaglayan.HEIGHT;
        }

        double alienChance = Math.min(0.4, 0.05 + wave * 0.04);
        if (wave >= 2 && Math.random() < alienChance) {
            enemies.add(new AlienShip(x, y));
        } else {
            enemies.add(new Asteroid(x, y, Asteroid.Size.LARGE));
        }
    }
}
