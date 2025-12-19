import module java.base;

import static java.util.stream.Collectors.joining;

class TestRunner {

    static void runTests(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            runTests(getInstance(clazz));
        }
    }

    static String runApplication(String name, String... args) {
        try {
            var command = new ArrayList<String>();
            command.add(name);
            command.addAll(List.of(args));
            var process = new ProcessBuilder(command).start();
            try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String output = reader.lines().collect(joining("\n"));
                process.waitFor();
                return output;
            }
        } catch (Exception exception) {
            throw new RuntimeException("Application has failed to start", exception);
        }
    }

    private static void runTests(Object instance) {
        Arrays.stream(instance.getClass().getDeclaredMethods())
                .forEach(method -> runTest(instance, method));
    }

    private static void runTest(Object instance, Method method) {
        var testName = method.getName();
        if (!testName.contains("Test")) {
            return;
        }
        try {
            method.invoke(instance);
            IO.println("✅ Test %s is successful".formatted(testName));
        } catch (InvocationTargetException targetException) {
            IO.println("❌ Test %s has failed".formatted(testName));
            targetException.getCause().printStackTrace();
        } catch (IllegalAccessException accessException) {
            throw new RuntimeException("Failed to run test: %s".formatted(testName), accessException);
        }
    }

    private static Object getInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor(new Class[]{}).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException exception) {
            throw new RuntimeException("Failed to instantiate Test class: %s".formatted(clazz.getName()), exception);
        }
    }
}
