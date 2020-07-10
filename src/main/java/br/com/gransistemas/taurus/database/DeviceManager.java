package br.com.gransistemas.taurus.database;

import br.com.gransistemas.taurus.model.Device;
import br.com.gransistemas.taurus.model.Position;
import br.com.gransistemas.taurus.model.Status;
import br.com.gransistemas.taurus.repository.DeviceRepository;
import org.redisson.api.RLiveObjectService;

import java.util.Objects;
import java.util.Optional;

public class DeviceManager extends BaseObjectManager<Device> {
    public Optional<Device> getDeviceByImei(String imei) throws Exception {
        return getDeviceRepository().findByImei(imei);
    }

    public synchronized void saveDeviceSession(Device device){
        getLiveObjectService().merge(device);
    }

    public Optional<Device> getDeviceSession(long id){
        return Optional.ofNullable(
            getLiveObjectService().get(Device.class, id)
        );
    }

    public synchronized void updateDeviceStatus(long deviceId, Status status) throws Exception {
        Device device = getDeviceSession(deviceId).orElse(null);
        if(Objects.isNull(device)){
            return;
        }

        getDeviceRepository().updateDeviceStatus(
            deviceId, status
        );

        device.setStatus(status);
        saveDeviceSession(device);
    }

    public synchronized void saveLastPosition(Position position) throws Exception {
        getDeviceRepository().updateDevicePosition(position);

        RLiveObjectService liveObjectService = getLiveObjectService();
        Device device = getDeviceSession(position.getDeviceId()).orElse(null);

        if(Objects.nonNull(device)){
            getDevicePosition(device.getLastPositionId()).ifPresent(liveObjectService::delete);
        }

        liveObjectService.merge(position);
    }

    public Optional<Position> getDevicePosition(long positionId){
        return Optional.ofNullable(
            getLiveObjectService().get(Position.class, positionId)
        );
    }

    protected DeviceRepository getDeviceRepository() {
        return getDatabaseManager().getDeviceRepository();
    }
}
