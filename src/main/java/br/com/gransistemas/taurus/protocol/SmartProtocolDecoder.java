package br.com.gransistemas.taurus.protocol;

import br.com.gransistemas.taurus.exception.DeviceNotFoundException;
import br.com.gransistemas.taurus.model.Device;
import br.com.gransistemas.taurus.model.Position;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SmartProtocolDecoder extends BaseProtocolDecoder  {
    private double convertSpeed(double speed){
        return speed * 1.852;
    }

    @Override
    public Object decode(Channel channel, Object msg) {
        FullHttpRequest request = (FullHttpRequest) msg;

        final QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        final Map<String, List<String>> parameters = decoder.parameters();

        final Position position = new Position();
        position.setKeyIgnition(true);

        for(Map.Entry<String, List<String>> entry: parameters.entrySet()){
            String value = entry.getValue().get(0);

            switch (entry.getKey()){
                case "id": {
                    Device device = getDeviceByImei(value).orElseThrow(DeviceNotFoundException::new);
                    position.setDeviceId(device.getId());
                    break;
                }
                case "lat": {
                    position.setLatitude(Double.parseDouble(value));
                    break;
                }
                case "lon": {
                    position.setLongitude(Double.parseDouble(value));
                    break;
                }
                case "speed": {
                    double speed = Double.parseDouble(value);
                    position.setSpeed(convertSpeed(speed));
                    break;
                }
                case "bearing": {
                    position.setCourse(Double.parseDouble(value));
                    break;
                }
                case "timestamp": {
                    long timestamp = Long.parseLong(value);
                    position.setDate(new Date(timestamp * 1000));
                    break;
                }
                case "batt": {
                    position.setBatteryLevel((int) Double.parseDouble(value));
                    break;
                }
            }
        }

        if(position.getDeviceId() != 0) {
            sendResponse(channel, HttpResponseStatus.OK);
            return position;
        }

        sendResponse(channel, HttpResponseStatus.BAD_REQUEST);
        return null;
    }

    private void sendResponse(Channel channel, HttpResponseStatus status){
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH, 0);
        channel.writeAndFlush(response);
    }
}
