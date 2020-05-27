public class Foo {
    private static String classField;
    private String instanceField;

    private String ignoreMe;

    public Foo() {
        System.out.println(classField);
        System.out.println(instanceField);
        System.out.println(ignoreMe);
    }
}