import java.util.function.Function;

public enum AngleUnit {
    DEGREES, RADIANS;

    public Function<Double, Double> convertTo(AngleUnit angleUnit) {
        if(this.equals(angleUnit)) {
            return Double::doubleValue;
        }
        else if(angleUnit.equals(RADIANS)) {
            return Math::toRadians;
        }
        else {
            return Math::toDegrees;
        }
    }
}
