//usr/bin/env java --class-path ${APP_DIR:-.}/lib/'*' "$0" "$@"; exit $?

import dev.nipafx.args.Args;

import static java.lang.IO.println;

void main(String... args) {
    try {
        sayHello(Args.parse(args, ParsedArgs.class));
    } catch (Exception exception) {
        printHelp();
        throw new RuntimeException(exception);
    }
}

void sayHello(ParsedArgs parsedArgs) {
    if (parsedArgs.hasHelpFlag()) {
        printHelp();
    } else {
        println(parsedArgs.getLanguage().getGreeting());
    }
}

void printHelp() {
    println("Usage: Application [--language %s | %s] [--help]".formatted(Language.French, Language.English));
}
