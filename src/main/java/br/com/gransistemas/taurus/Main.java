package br.com.gransistemas.taurus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting server...");

        try {
            // System log info
            Context.logSystemInfo();

            // Initialize managers
            Context.init();

            // Start all servers
            Context.getServerManager().start();

            // Safe shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Shutting down server...");
                Context.getServerManager().stop();
            }));

            logger.info("Server started successfully");
        }catch (Exception e){
            logger.error("Startup error", e);
        }
    }
}
