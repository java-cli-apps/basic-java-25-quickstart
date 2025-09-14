//usr/bin/env java -enableassertions --class-path ${APP_DIR:-.}/lib/'*' "$0" "$@"; exit $?

void main() {
    TestRunner.runTests(getClass());
}

void fromStringTest() {
    Optional<Language> optLanguage = Language.fromString("French");
    assert optLanguage.isPresent() : "No language returned";
    assert optLanguage.get().equals(Language.French) : "Incorrect language returned";
}

void failingTest() {
    assert false : "Test was expected to fail.";
}

void getGreetingTest() {
    String greeting = Language.French.getGreeting();
    assert greeting.equals("Bonjour 🇫🇷") : "Incorrect greeting returned";
}
