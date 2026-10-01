public abstract class Entity implements Drawable, Updatable, Collidable {
    protected double x, y;
    protected Vector2 velocity = new Vector2(0, 0);
    protected double radius;
    protected boolean alive = true;

    protected Entity(double x, double y, double radius) {
        this.x = x; this.y = y; this.radius = radius;
    }

    @Override public double getX() { return x; }
    @Override public double getY() { return y; }
    @Override public double getRadius() { return radius; }

    public boolean isAlive() { return alive; }
    public void kill() { alive = false; }

    protected void wrap() {
        if (x < 0) x += YalimOzcaglayan.WIDTH;
        if (x > YalimOzcaglayan.WIDTH) x -= YalimOzcaglayan.WIDTH;
        if (y < 0) y += YalimOzcaglayan.HEIGHT;
        if (y > YalimOzcaglayan.HEIGHT) y -= YalimOzcaglayan.HEIGHT;
    }
}
