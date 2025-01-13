package com.ravijar.cli;

import org.fusesource.jansi.Ansi;
import picocli.CommandLine.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Command(name = "fegen", version = "FeGenerator 0.2.0", mixinStandardHelpOptions = true)
public class MainCommand implements Runnable {
    private static final Logger logger = LogManager.getLogger("entryLogger");

    @Override
    public void run() {
        logger.info(Ansi.ansi().bold().fgBright(Ansi.Color.WHITE).a("Welcome to FeGenerator 0.2.0!").reset());
        logger.info(Ansi.ansi().fgCyan().a("Frontend Generator CLI Tool").reset());
        logger.info("For help and available commands, use: {}",
                Ansi.ansi().bold().a("java -jar fegen.jar --help").reset());
    }
}
