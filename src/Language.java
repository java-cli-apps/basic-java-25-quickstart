import module java.base;

enum Language {

    French("Bonjour", new int[]{0x1F1EB, 0x1F1F7}),
    English("Hello", new int[]{0x1F1EC, 0x1F1E7});

    private final String message;
    private final int[] emoji;

    Language(String message, int[] emoji) {
        this.message = message;
        this.emoji = emoji;
    }

    String getGreeting() {
        return "%s %s%s".formatted(message, Character.toString(emoji[0]), Character.toString(emoji[1]));
    }

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
