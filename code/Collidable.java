public interface Collidable {
    double getX();
    double getY();
    double getRadius();

    default boolean collidesWith(Collidable other) {
        double dx = getX() - other.getX();
        double dy = getY() - other.getY();
        double r  = getRadius() + other.getRadius();
        return dx * dx + dy * dy <= r * r;
    }
}
