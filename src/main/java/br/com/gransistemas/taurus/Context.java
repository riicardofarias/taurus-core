package br.com.gransistemas.taurus;

import br.com.gransistemas.taurus.database.DeviceManager;
import br.com.gransistemas.taurus.helpers.AppConfig;
import br.com.gransistemas.taurus.helpers.GetIt;
import br.com.gransistemas.taurus.repository.DeviceRepository;
import br.com.gransistemas.taurus.repository.EventRepository;
import br.com.gransistemas.taurus.repository.PositionRepository;
import br.com.gransistemas.taurus.repository.ZoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.Charset;

public class Context {
    private static Logger log = LoggerFactory.getLogger(Context.class);

    private Context(){
        // Private constructor
    }

    static void logSystemInfo() {
        try {
            OperatingSystemMXBean operatingSystemBean = ManagementFactory.getOperatingSystemMXBean();
            log.info("Operating system"
                + " name: " + operatingSystemBean.getName()
                + " version: " + operatingSystemBean.getVersion()
                + " architecture: " + operatingSystemBean.getArch()
            );

            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
            log.info("Java runtime"
                + " name: " + runtimeBean.getVmName()
                + " vendor: " + runtimeBean.getVmVendor()
                + " version: " + runtimeBean.getVmVersion()
            );

            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            log.info("Memory limit"
                + " heap: " + memoryBean.getHeapMemoryUsage().getMax() / (1024 * 1024) + "mb"
                + " non-heap: " + memoryBean.getNonHeapMemoryUsage().getMax() / (1024 * 1024) + "mb"
            );

            log.info("Character encoding: "
                + System.getProperty("file.encoding") + " charset: " + Charset.defaultCharset()
            );
        } catch (Exception error) {
            log.warn("Failed to get system info");
        }
    }

    /**
     * Initialize managers
     */
    static void init(){
        { // Config properties
            GetIt.register(AppConfig.class, new AppConfig());
            log.info("Configuration properties initialized");
        }

        { // Database
            GetIt.register(DeviceRepository.class, new DeviceRepository());
            GetIt.register(EventRepository.class, new EventRepository());
            GetIt.register(ZoneRepository.class, new ZoneRepository());
            GetIt.register(PositionRepository.class, new PositionRepository());
            log.info("Database manager initialized");
        }

        { // Device manager
            GetIt.register(DeviceManager.class, new DeviceManager());
            log.info("Device manager initialized");
        }

        { // Server manager
            GetIt.register(ServerManager.class, new ServerManager());
            log.info("Server manager initialized");
        }
    }

    public static ServerManager getServerManager() {
        return GetIt.get(ServerManager.class);
    }
}
