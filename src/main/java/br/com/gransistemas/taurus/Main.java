package br.com.gransistemas.taurus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Starting server...");

        try {
            // System log info
            Context.logSystemInfo();

            // Initialize managers
            Context.init();

            // Start all servers
            Context.getServerManager().start();

            // Safe shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                LOGGER.info("Shutting down server...");
                Context.getServerManager().stop();
            }));
        }catch (Exception e){
            LOGGER.error("Startup error", e);
        }
    }
}
