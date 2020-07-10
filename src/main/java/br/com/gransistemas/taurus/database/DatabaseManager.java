package br.com.gransistemas.taurus.database;

import br.com.gransistemas.taurus.repository.DeviceRepository;
import br.com.gransistemas.taurus.repository.EventRepository;
import br.com.gransistemas.taurus.repository.ZoneRepository;

public class DatabaseManager {
    private static DeviceRepository deviceRepository;
    private static EventRepository eventRepository;
    private static ZoneRepository zoneRepository;

    public DatabaseManager() {
        deviceRepository = new DeviceRepository();
        eventRepository = new EventRepository();
        zoneRepository = new ZoneRepository();
    }

    public static DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }

    public static EventRepository getEventRepository(){
        return eventRepository;
    }

    public static ZoneRepository geoFenceRepository(){
        return zoneRepository;
    }
}
