//usr/bin/env java -enableassertions --class-path ${APP_DIR:-.}/lib/'*' "$0" "$@"; exit $?

private static final String SRC_APPLICATION_JAVA = "./src/Application.java";

void main() {
    TestHelper.runTests(getClass());
}

void sayHelloFrenchTest() {
    String output = TestHelper.runApplication(SRC_APPLICATION_JAVA, "--language", "French");
    assert output.contains("Bonjour 🇫🇷") : "Expected 'Bonjour 🇫🇷' but got: " + output;
}

void sayHelloEnglishTest() {
    String output = TestHelper.runApplication(SRC_APPLICATION_JAVA, "--language", "English");
    assert output.contains("Hello 🇬🇧") : "Expected 'Hello 🇬🇧' but got: " + output;
}

void helpFlagTest() {
    String output = TestHelper.runApplication(SRC_APPLICATION_JAVA, "--help");
    assert output.contains("Usage:") : "Help should start with 'Usage:'";
}
