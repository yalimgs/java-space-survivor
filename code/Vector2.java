public class Vector2 {
    public double x, y;
    public Vector2(double x, double y) { this.x = x; this.y = y; }
    public void add(Vector2 v)    { x += v.x; y += v.y; }
    public void scale(double s)   { x *= s;   y *= s;   }
    public double length()        { return Math.sqrt(x*x + y*y); }
    public void clampMagnitude(double max) {
        double l = length();
        if (l > max) { x = x / l * max; y = y / l * max; }
    }
}
