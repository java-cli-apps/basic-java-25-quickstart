//usr/bin/env java -enableassertions --class-path ${APP_DIR:-.}/lib/'*' "$0" "$@"; exit $?

void main() {
    TestRunner.runTests(getClass());
}

void sayHelloFrenchTest() {
    String output = runApplication("--language", "French");
    assert output.contains("Bonjour 🇫🇷") : "Expected 'Bonjour 🇫🇷' but got: " + output;
}

void sayHelloEnglishTest() {
    String output = runApplication("--language", "English");
    assert output.contains("Hello 🇬🇧") : "Expected 'Hello 🇬🇧' but got: " + output;
}

void helpFlagTest() {
    String output = runApplication("--help");
    assert output.contains("Usage:") : "Help should start with 'Usage:'";
}

String runApplication(String... args) {
    return TestRunner.runApplication("./src/Application.java", args);
}