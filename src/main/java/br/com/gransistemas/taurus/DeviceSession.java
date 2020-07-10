package br.com.gransistemas.taurus;

import br.com.gransistemas.taurus.model.ActiveDevice;
import br.com.gransistemas.taurus.model.Device;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceSession {
    private static final Map<Long, ActiveDevice> devices = new ConcurrentHashMap<>();

    public void addActiveDevice(Device device, Channel channel){
        devices.put(device.getId(), new ActiveDevice(device, channel));
    }

    public Optional<ActiveDevice> getActiveDevice(Long id){
        ActiveDevice device = devices.get(id);

        if(Objects.nonNull(device)){
            return Optional.of(device);
        }

        return Optional.empty();
    }

    public void removeActiveDevice(Channel channel){
        for(ActiveDevice device: devices.values()){
            if(channel == device.getChannel()){
                devices.remove(device.getDevice().getId());
            }
        }
    }

    public int getDeviceCount(){
        return devices.size();
    }
}
