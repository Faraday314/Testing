public class Vector3D extends BaseVector<Vector3D> {
    private static final Vector3D ZERO_VECTOR = new Vector3D(0, 0, 0);

    public Vector3D(double a, double b, double c, CoordinateSystem3D coordinateSystem) {
        super(coordinateSystem.convertTo(CoordinateSystem3D.CARTESIAN).apply(new double[] {a, b, c}));
    }

    public Vector3D(double x, double y, double z) {
        this(x, y, z, CoordinateSystem3D.CARTESIAN);
    }

    public Vector3D(double r, double theta, AngleUnit angleUnit, double z) {
        this(r, angleUnit.convertTo(AngleUnit.RADIANS).apply(theta), z, CoordinateSystem3D.CYLINDRICAL);
    }

    public Vector3D(double rho, double phi, AngleUnit phiUnit, double theta, AngleUnit thetaUnit) {
        this(rho, phiUnit.convertTo(AngleUnit.RADIANS).apply(phi), thetaUnit.convertTo(AngleUnit.RADIANS).apply(theta));
    }

    private Vector3D(Vector3D v) {
        components = v.components;
    }

    public double getXComponent() {
        return components[0];
    }

    public double getYComponent() {
        return components[1];
    }

    public double getZComponent() {
        return components[2];
    }

    public Vector3D cross(Vector3D vector) {
        return new Vector3D(
                this.getYComponent()*vector.getZComponent() - this.getZComponent()*vector.getYComponent(),
                this.getZComponent()*vector.getXComponent() - this.getXComponent()*vector.getZComponent(),
                this.getXComponent()*vector.getYComponent() - this.getYComponent()*vector.getXComponent()
                );
    }

    public static Vector3D getZeroVector() {
        return ZERO_VECTOR.clone();
    }

    @Override
    public Vector3D clone() {
        return new Vector3D(this);
    }
}
