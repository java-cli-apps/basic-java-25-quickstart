import module java.base;

record ParsedArgs(Optional<String> language, Optional<Boolean> help) {

    ParsedArgs {
        language.ifPresent(Language::validate);
    }

    boolean hasHelpFlag() {
        return help.orElse(false);
    }

    Language getLanguage() {
        return language
                .flatMap(Language::fromString)
                .orElse(Language.French);
    }
}
