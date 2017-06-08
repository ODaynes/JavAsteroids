package JavAsteroids.Utilities;

/**
 * Created by ODaynes on 27/01/2017.
 */
public final class Vector2D {
    public double x, y;

    public Vector2D(double x, double y) {
        this.x = x; this.y = y;
    }

    public Vector2D(Vector2D v) {
        this.x = v.x; this.y = v.y;
    }

    public Vector2D set(double x, double y) {
        this.x = x; this.y = y;
        return this;
    }

    public Vector2D set(Vector2D v) {
        this.x = v.x; this.y = v.y;
        return this;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Vector2D)) return false;

        Vector2D other = (Vector2D) o;

        if(this.x == other.x && this.y == other.y) {
            return true;
        }

        return false;
    }

    public String toString() {
        return "[" + String.valueOf(this.x) + ", " + String.valueOf(this.y) + "]";
    }

    public double mag() {
        return Math.sqrt((x*x) + (y*y));
    }

    public double angle() {
        return Math.atan2(y, x);
    }

    public double angle(Vector2D other) {

        double res = other.angle() - this.angle();

        if(res < -Math.PI) {
            res += 2*Math.PI;
        }

        if(res > Math.PI) {
            res -= 2*Math.PI;
        }

        return res;
    }

    public Vector2D add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;

        return this;
    }

    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public Vector2D addScaled(Vector2D v, double fac) {
        this.x += v.x*fac;
        this.y += v.y*fac;
        return this;
    }

    public Vector2D subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2D mult(double fac) {
        this.x *= fac;
        this.y *= fac;
        return this;
    }

    public Vector2D rotate(double angle) {
        double tempX = ((x*Math.cos(angle)) - (y*Math.sin(angle)));
        double tempY = ((x*Math.sin(angle)) + (y*Math.cos(angle)));
        this.x = tempX;
        this.y = tempY;
        return this;
    }

    public double dot(Vector2D v) {
        return this.x*v.x + this.y*v.y;
    }

    public double dist(Vector2D v) {
        return Math.hypot(v.x - this.x, v.y - this.y);
    }

    public Vector2D normalise() {
        double mag = this.mag();
        this.x /= mag;
        this.y /= mag;
        return this;
    }

    public Vector2D wrap(double w, double h) {
        if(this.x > w) this.x %= w;
        if(this.x < 0) this.x = (this.x+w)%w;
        if(this.y > h) this.y %= h;
        if(this.y < 0) this.y = (this.y+h)%h;
        return this;
    }

    public static Vector2D polar(double angle, double mag) { // theta, r
        double x = mag * Math.cos(angle);
        double y = mag * Math.sin(angle);
        return new Vector2D(x, y);
    }
}
