import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import org.opencv.core.Core;

import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;


@Log
public class MainClass {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    public static void main(String[] args) {
        Matrix testMatrix = new Matrix(new double[][]{
                {1,2,2},
                {2,1,2},
                {-2,2,1}
        });
        Matrix testMatrix2 = new Matrix(new double[][]{
                {1,2,2},
                {2,1,2},
                {2,3,1}
        });

        System.out.println(testMatrix.invert(Matrix.InverseMethod.LU));


        //testMatrix2.isSymmetric();
        //System.out.println(testMatrix2.isSymmetric());
        //System.out.println(testMatrix.invert(Matrix.InverseMethod.LU));
        //System.out.println(Double.valueOf(42.5).hashCode());
        //System.out.println(Double.valueOf(42.5).hashCode());

        /*
        Matrix testMatrix = new Matrix(new double[][]{
                {1,2,3},
                {4,5,6},
                {5,7,9}
        });

        Point2D.getOrigin().setX(10);
        System.out.println(Point2D.getOrigin().getX());

        /*ClassPool pool = ClassPool.getDefault();

        try {
            CtClass cc = pool.get("TestClass");
            cc.defrost();
            CtConstructor[] constructors = cc.getConstructors();
            for(CtConstructor c : constructors) {
                if(c.hasAnnotation(ConfigConstructor.class)) {
                    c.insertAfter("config = true;");
                }
            }
            cc.writeFile(".");
            cc.toClass();
        }
        catch (NotFoundException e) {
            e.printStackTrace();
        }
        catch (CannotCompileException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            TestClass test = new TestClass();
            System.out.println(test.isConfigurable());
        }*/
    }

    public static void interceptConstructorFailsWithThis() {
        new ByteBuddy()
                .rebase(TypePool.Default.ofClassPath().describe("Foo").resolve(), ClassFileLocator.ForClassLoader.ofClassPath())
                .constructor(ElementMatchers.any())
                .intercept(MethodDelegation.to(
                        new Object() {
                            //Can't use @This here...
                            public void m(@This Object o) {
                                System.out.println(o);
                            }
                        }
                ).andThen(SuperMethodCall.INSTANCE))
                .make()
                .load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION);
        //fails with "Type uninitializedThis (current frame,stack[1]) is not assignable to 'java/lang/Object'"
        new Foo();
    }
}
