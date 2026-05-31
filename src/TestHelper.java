import module java.base;

import static java.util.stream.Collectors.joining;

class TestHelper {

    static {
        if (!TestHelper.class.desiredAssertionStatus()) {
            throw new AssertionError("Please enable assertions: java -enableassertions ...");
        }
    }

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
            return getProcessOutput(process);
        } catch (Exception exception) {
            throw new RuntimeException("Application has failed to start", exception);
        }
    }

    private static void runTests(Object instance) {
        var returnCodes = Arrays.stream(instance.getClass().getDeclaredMethods())
                .filter(TestHelper::isTestMethod)
                .map(method -> runTest(instance, method))
                .distinct()
                .toList();
        if (returnCodes.stream().anyMatch(code -> code != 0)) {
            throw new RuntimeException("One or more tests have failed");
        }
    }

    private static int runTest(Object instance, Method method) {
        var testName = method.getName();
        try {
            method.invoke(instance);
            IO.println("✅ Test %s is successful".formatted(testName));
            return 0;
        } catch (InvocationTargetException targetException) {
            IO.println("❌ Test %s has failed".formatted(testName));
            targetException.getCause().printStackTrace();
            return 1;
        } catch (IllegalAccessException accessException) {
            throw new RuntimeException("🚨 Failed to run test: %s".formatted(testName),
                    accessException);
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

    private static boolean isTestMethod(Method method) {
        return method.getName().toLowerCase().contains("test");
    }

    private static String getProcessOutput(Process process) throws IOException, InterruptedException {
        try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String output = reader.lines().collect(joining("\n"));
            process.waitFor();
            return output;
        }
    }
}
