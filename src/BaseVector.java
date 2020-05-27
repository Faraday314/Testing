import java.util.Arrays;

import static java.lang.Math.*;

/**
 * A class for doing mathematical operations on 2 dimensional vectors.
 *
 * @author Cole Savage, Level Up
 * @since 0.0.0
 * @version 1.0.0
 *
 * Creation Date: 7/11/17
 */
public abstract class BaseVector<V extends BaseVector<V>> implements Vector<V> {
    protected double[] components;

    public BaseVector(double... components) {
        this.components = components;
    }

    @Override
    public double[] getComponents() {
        return components;
    }

    @Override
    public boolean isZeroVector() {
        boolean isZero = true;
        for(double component : components) {
            isZero &= component == 0;
        }
        return isZero;
    }

    @Override
    public boolean isNormalTo(V vector) {
        return this.dot(vector) == 0;
    }

    @Override
    public boolean isUnitVector() {
        return this.norm() == 1;
    }

    @Override
    public double norm() {
        return sqrt(this.dot((V) this));
    }

    public double magnitude() {
        return this.norm();
    }

    @Override
    public double angleTo(V vector, AngleUnit angleUnit) {
        return AngleUnit.RADIANS.convertTo(angleUnit).apply(acos(this.dot(vector)/(this.norm()*vector.norm())));
    }

    public double angleTo(V vector) {
        return angleTo(vector, AngleUnit.RADIANS);
    }

    @Override
    public V normalize() {
        double norm = this.norm();
        for (int i = 0; i < components.length ; i++) {
            components[i] /= norm;
        }
        return (V) this;
    }

    @Override
    public V add(V vector) {
        double[] otherComponents = vector.getComponents();
        for (int i = 0; i < components.length; i++) {
            components[i] += otherComponents[i];
        }
        return (V) this;
    }

    @Override
    public V subtract(V vector) {
        return add(vector.negate());
    }

    @Override
    public V multiply(double scalar) {
        for (int i = 0; i < components.length ; i++) {
            components[i] *= scalar;
        }
        return (V) this;
    }

    @Override
    public V divide(double scalar) {
        return multiply(1/scalar);
    }

    @Override
    public V negate() {
        return this.multiply(-1);
    }

    @Override
    public V scaleTo(double scale) {
        return this.normalize().multiply(scale);
    }

    @Override
    public double dot(V vector) {
        double[] otherComponents = vector.getComponents();
        double total = 0;
        for (int i = 0; i < components.length; i++) {
            total += components[i] * otherComponents[i];
        }
        return total;
    }

    @Override
    public V project(V ontoVector) {
        return ontoVector.multiply(this.dot(ontoVector)/ontoVector.dot(ontoVector));
    }

    @Override
    public String toString() {
        String componentArrayString = Arrays.toString(components);
        return "<" + componentArrayString.substring(1,componentArrayString.length()-1) + '>';
    }

    public abstract V clone();
}
