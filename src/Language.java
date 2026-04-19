import module java.base;

enum Language implements Greeter {

    French() {
        @Override
        public String greet() {
            return "Bonjour 🇫🇷";
        }
    },
    English() {
        @Override
        public String greet() {
            return "Hello 🇬🇧";
        }
    };

    static Optional<Language> fromString(String language) {
        return Arrays.stream(values())
                .filter(current_language -> current_language.name().equalsIgnoreCase(language))
                .findFirst();
    }

    static void validate(String language) {
        fromString(language)
                .orElseThrow(() -> new IllegalArgumentException("Unknown language provided: %s".formatted(language)));
    }
}
