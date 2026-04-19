//usr/bin/env java -enableassertions --class-path ${APP_DIR:-.}/lib/'*' "$0" "$@"; exit $?

void main() {
    TestHelper.runTests(getClass());
}

void fromStringTest() {
    Optional<Language> optLanguage = Language.fromString("French");
    assert optLanguage.isPresent() : "No language returned";
    assert optLanguage.get().equals(Language.French) : "Incorrect language returned";
}

void failingTest() {
    assert false : "Test was expected to fail.";
}

void frenchGreetTest() {
    String greet = Language.French.greet();
    assert greet.equals("Bonjour 🇫🇷") : "Incorrect greet returned";
}

void englishGreetTest() {
    String greet = Language.English.greet();
    assert greet.equals("Hello 🇬🇧") : "Incorrect greet returned";
}