package ru.gvsmirnov.sample.generator;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;

public class LargeClassGenerator {

    private final int numberOfClasses;
    private final int numberOfMethods;
    private final String targetPackage;
    private final String rootFolder;
    private final boolean simulation;

    public LargeClassGenerator(Options options) {
        this.numberOfClasses = options.numberOfClasses;
        this.numberOfMethods = options.numberOfMethods;
        this.targetPackage = options.targetPackage;
        this.rootFolder = options.rootFolder;
        this.simulation = options.simulation;
    }

    private void generateStuff() {
        range(0, numberOfClasses)
                .mapToObj(id -> "GeneratedClass" + id)
                .forEach(className ->
                        generateSourceClass(className,
                                new Persister(generateSourceFile(className), simulation)
                        )
                );

        range(0, numberOfClasses)
                .mapToObj(id -> "GeneratedClass" + id)
                .forEach(className ->
                                generateTestClass(className,
                                        new Persister(generateTestFile(className), simulation)
                                )
                );
    }

    private File generateSourceFile(String className) {
        return generateFile(className, false);
    }

    private File generateTestFile(String className) {
        return generateFile(className, true);
    }

    private File generateFile(String className, boolean isTest) {

        final String prefix = isTest ? "src.test.java" : "src.main.java";

        File root = new File(rootFolder);
        String path = (prefix + "." + targetPackage).replace('.', File.separatorChar);

        return new File(new File(root, path), className + (isTest ? "Test" : "") +  ".java");
    }

    private void generateSourceClass(String className, Consumer<String> consumer) {
        generateClass(className, consumer, false);
    }

    private void generateTestClass(String className, Consumer<String> consumer) {
        generateClass(className, consumer, true);
    }

    private void generateClass(String className, Consumer<String> consumer, boolean isTest) {
        consumer.accept(format("package %s;", targetPackage));
        consumer.accept("");
        consumer.accept(format("public class %s {", isTest ? className + "Test" : className));
        consumer.accept("");

        Consumer<String> indentedConsumer = indent(consumer);

        range(0, numberOfMethods)
                .mapToObj(id -> "method" + id)
                .forEach(methodName -> {
                            if(isTest) {
                                generateTestMethod(methodName, className, indentedConsumer);
                            } else {
                                generateSourceMethod(methodName, indentedConsumer);
                            }
                        }
                );

        consumer.accept("}");
        consumer.accept(null);
    }

    private void generateSourceMethod(String methodName, Consumer<String> consumer) {
        consumer.accept(String.format("public static int %s(int a, int b) {", methodName));

        Arrays.asList(METHOD_BODY.split("\n")).forEach(indent(consumer));

        consumer.accept("}");
        consumer.accept("");
    }

    private void generateTestMethod(String methodName, String className, Consumer<String> consumer) {
        consumer.accept("@org.junit.Test");
        consumer.accept(String.format("public void %s() {", methodName));

        Arrays.asList(String.format(TEST_METHOD_BODY, className, methodName).split("\n")).forEach(indent(consumer));

        consumer.accept("}");
        consumer.accept("");
    }

    private static Consumer<String> indent(Consumer<String> consumer) {
        return line -> consumer.accept("    " + line);
    }

    public static void main(String[] args){
        Options options = new Options();
        CmdLineParser parser = new CmdLineParser(options);

        try {
            parser.parseArgument(args);
            new LargeClassGenerator(options).generateStuff();
        } catch( CmdLineException e ) {
            parser.printUsage(System.err);
        }
    }

    public static class Options {

        @Option(name = "-c", usage = "Number of classes to generate")
        private int numberOfClasses = 1;

        @Option(name = "-m", usage = "Number of methods to generate per class")
        private int numberOfMethods = 1;

        @Option(name = "-p", usage = "Package name")
        private String targetPackage = "ru.gvsmirnov.sample.generated";

        @Option(name = "-r", usage = "Root folder")
        private String rootFolder = ".";

        @Option(name = "-s", usage = "Simulation")
        private boolean simulation = false;

        @Override
        public String toString() {
            return "Options{" +
                    "numberOfClasses=" + numberOfClasses +
                    ", numberOfMethods=" + numberOfMethods +
                    ", targetPackage='" + targetPackage + '\'' +
                    ", rootFolder='" + rootFolder + '\'' +
                    ", simulation=" + simulation +
                    '}';
        }
    }

    private static class Persister implements Consumer<String> {

        private final PrintWriter writer;
        private final boolean simulation;

        public Persister(File file, boolean simulation) {
            this.simulation = simulation;

            if(simulation) {
                writer = new PrintWriter(System.out);
            } else {
                file.getParentFile().mkdirs();
                try {
                    writer = new PrintWriter(file);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Writing to file: " + file.getAbsolutePath());
        }

        @Override
        public void accept(String s) {
            if(s != null) {
                writer.println(s);
            } else {
                writer.flush();
                if(!simulation) {
                    writer.close();
                }
            }
        }
    }

    public static final String METHOD_BODY = "int c;\n" +
            "\n" +
            "if (a > b) {\n" +
            "    c = -1;\n" +
            "} else if(a < b) {\n" +
            "    c = 1;\n" +
            "} else {\n" +
            "    c = 100;\n" +
            "}\n" +
            "\n" +
            "int d = a + b + c;\n" +
            "int e = a + b;\n" +
            "\n" +
            "int x;\n" +
            "\n" +
            "if (a > b) {\n" +
            "    x = 1;\n" +
            "} else if(a < b) {\n" +
            "    x = -1;\n" +
            "} else {\n" +
            "    x = -100;\n" +
            "}\n" +
            "\n" +
            "return d + x - e;";

    public static final String TEST_METHOD_BODY = "final int expected = 0;\n" +
            "\n" +
            "for(int a = -5; a <= 5; a ++) {\n" +
            "    for(int b = -5; b <= 5; b ++) {\n" +
            "        org.junit.Assert.assertEquals(expected, %s.%s(a, b));\n" +
            "    }\n" +
            "}";
}
