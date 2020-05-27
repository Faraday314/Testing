import static java.lang.Math.*;

public class Vector2D extends BaseVector<Vector2D> {
    private static final Vector2D ZERO_VECTOR = new Vector2D(0,0);
    public Vector2D(Point2D start, Point2D end) {
        super(FakeNumpy.subtract(end.coordinates, start.coordinates));
    }
    public Vector2D(Point2D end) {
        this(Point2D.getOrigin(), end);
    }
    public Vector2D(double x, double y) {
        this(new Point2D(x, y));
    }

    public Vector2D(double r, double theta, AngleUnit angleUnit) {
        super(CoordinateSystem2D.POLAR.convertTo(CoordinateSystem2D.CARTESIAN).apply(new double[] {r, angleUnit.convertTo(AngleUnit.RADIANS).apply(theta)}));
    }

    private Vector2D(Vector2D v) {
        components = v.components.clone();
    }

    public double getXComponent() {
        return components[0];
    }

    public double getYComponent() {
        return components[1];
    }

    public double getAngle() {
        return atan2(getYComponent(), getXComponent());
    }

    public Vector2D rotate(double angle, AngleUnit angleUnit) {
        if(!isZeroVector()) {
            double theta = angleUnit.convertTo(AngleUnit.RADIANS).apply(angle);
            double rotX = getXComponent() * cos(theta) - getYComponent() * sin(theta);
            double rotY = getXComponent() * sin(theta) + getYComponent() * cos(theta);
            components[0] = (double) Math.round(1e9*rotX)/1e9;
            components[1] = (double) Math.round(1e9*rotY)/1e9;
        }
        return this;
    }

    public Vector2D rotate(double angle) {
        return rotate(angle, AngleUnit.RADIANS);
    }

    public Vector3D cross(Vector2D vector) {
        return new Vector3D(0, 0, this.getXComponent()*vector.getYComponent() + vector.getXComponent()*this.getYComponent());
    }

    public static Vector2D getZeroVector() {
        return ZERO_VECTOR.clone();
    }

    @Override
    public Vector2D clone() {
        return new Vector2D(this);
    }
}
