package br.com.gransistemas.taurus.database;

import br.com.gransistemas.taurus.helpers.GetIt;
import br.com.gransistemas.taurus.model.Device;
import br.com.gransistemas.taurus.model.Position;
import br.com.gransistemas.taurus.model.Status;
import br.com.gransistemas.taurus.repository.DeviceRepository;
import org.redisson.api.RLiveObjectService;

import java.util.Objects;
import java.util.Optional;

public class DeviceManager extends BaseObjectManager {

    public Optional<Device> getByImei(String imei) throws Exception {
        return getDeviceRepository().findByImei(imei);
    }

    public Optional<Device> getById(long id){
        return Optional.ofNullable(
            getLiveObjectService().get(Device.class, id)
        );
    }

    public synchronized void save(Device device){
        getLiveObjectService().merge(device);
    }

    public synchronized void updateStatus(long deviceId, Status status) throws Exception {
        Device device = getById(deviceId).orElse(null);
        if(Objects.isNull(device)){
            return;
        }

        getDeviceRepository().updateStatus(
            deviceId, status
        );

        device.setStatus(status);

        save(device);
    }

    public synchronized void updatePosition(Position position) throws Exception {
        getDeviceRepository().updateDevicePosition(position);

        RLiveObjectService liveObjectService = getLiveObjectService();
        Device device = getById(position.getDeviceId()).orElse(null);

        if(Objects.nonNull(device)){
            getPosition(device.getLastPositionId()).ifPresent(liveObjectService::delete);
        }

        liveObjectService.merge(position);
    }

    public Optional<Position> getPosition(long positionId){
        return Optional.ofNullable(
            getLiveObjectService().get(Position.class, positionId)
        );
    }

    private DeviceRepository getDeviceRepository() {
        return GetIt.get(DeviceRepository.class);
    }
}
