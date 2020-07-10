package br.com.gransistemas.taurus;

import br.com.gransistemas.taurus.database.DatabaseManager;
import br.com.gransistemas.taurus.database.DeviceManager;
import br.com.gransistemas.taurus.notification.NotificationManager;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.Charset;

public class Context {
    private static Logger LOGGER = LoggerFactory.getLogger(Context.class);
    private static ServerManager serverManager;
    private static DatabaseManager databaseManager;
    private static NotificationManager notificationManager;
    private static DeviceSession deviceSession;
    private static DeviceManager deviceManager;

    public static final AttributeKey<Long> deviceIdKey = AttributeKey.valueOf("device_id");

    private Context(){
        // Private constructor
    }

    static void logSystemInfo() {
        try {
            OperatingSystemMXBean operatingSystemBean = ManagementFactory.getOperatingSystemMXBean();
            LOGGER.info("Operating system"
                + " name: " + operatingSystemBean.getName()
                + " version: " + operatingSystemBean.getVersion()
                + " architecture: " + operatingSystemBean.getArch()
            );

            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
            LOGGER.info("Java runtime"
                + " name: " + runtimeBean.getVmName()
                + " vendor: " + runtimeBean.getVmVendor()
                + " version: " + runtimeBean.getVmVersion()
            );

            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            LOGGER.info("Memory limit"
                + " heap: " + memoryBean.getHeapMemoryUsage().getMax() / (1024 * 1024) + "mb"
                + " non-heap: " + memoryBean.getNonHeapMemoryUsage().getMax() / (1024 * 1024) + "mb"
            );

            LOGGER.info("Character encoding: "
                + System.getProperty("file.encoding") + " charset: " + Charset.defaultCharset()
            );
        } catch (Exception error) {
            LOGGER.warn("Failed to get system info");
        }
    }

    /**
     * Initialize managers
     */
    static void init(){
        serverManager = new ServerManager();
        LOGGER.info("Server manager initialized...");

        databaseManager = new DatabaseManager();
        LOGGER.info("Database manager initialized...");

        deviceSession = new DeviceSession();
        LOGGER.info("Device session manager initialized...");

        notificationManager = new NotificationManager();
        LOGGER.info("Notification manager initialized...");

        deviceManager = new DeviceManager();
        LOGGER.info("Device manager initialized...");
    }

    /**
     * Server manager
     */
    static ServerManager getServerManager(){
        return serverManager;
    }

    /**
     * Database manager
     */
    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /**
     * Notification manager
     */
    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }

    /**
     * Device session manager
     */
    public static DeviceSession getDeviceSession() {
        return deviceSession;
    }

    /**
     * Device manager
     */
    public static DeviceManager getDeviceManager() {
        return deviceManager;
    }
}
