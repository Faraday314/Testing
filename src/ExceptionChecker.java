/**
 * An class used to easily throw exceptions when a condition is teue.
 *
 * @author Cole Savage, Level Up
 * @since 1.0.6
 * @version 1.0.0
 *
 * Creation Date: 12/20/19
 */
@SuppressWarnings("unused")
public class ExceptionChecker {

    private ExceptionChecker() {}

    /**
     * Throws an exception if a given condition is not true.
     *
     * @param condition The condition that must be false to throw the exception.
     * @param exception The exception to throw if the condition is false.
     */
    public static void assertTrue(boolean condition, RuntimeException exception) {
        if(!condition) {
            throw exception;
        }
    }

    public static void assertFalse(boolean condition, RuntimeException exception) {
        assertTrue(!condition, exception);
    }

    public static void assertNonNull(Object object, RuntimeException exception) {
        assertFalse(object == null, exception);
    }

    public static void assertNull(Object object, RuntimeException exception) {
        assertTrue(object == null, exception);
    }

    public static void assertEqual(Object obj1, Object obj2, RuntimeException exception) {
        assertTrue(obj1.equals(obj2), exception);
    }

    public static void assertNotEqual(Object obj1, Object obj2, RuntimeException exception) {
        assertFalse(obj1.equals(obj2), exception);
    }
}