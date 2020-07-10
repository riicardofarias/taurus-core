package br.com.gransistemas.taurus.protocol;

import br.com.gransistemas.taurus.exception.DeviceNotFoundException;
import br.com.gransistemas.taurus.helpers.BitUtil;
import br.com.gransistemas.taurus.helpers.Parser;
import br.com.gransistemas.taurus.helpers.PatternBuilder;
import br.com.gransistemas.taurus.model.Device;
import br.com.gransistemas.taurus.model.Position;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public class SViasProtocolDecoder extends BaseProtocolDecoder {
    private Pattern pattern = new PatternBuilder()
        .any()
        .text("[")
        .number("d{4},")                // hardware
        .number("d{4},")                // software
        .number("d+,")                  // index
        .number("(d+),")                // imei
        .number("d+,")                  // hour meter
        .number("(d+)(dd)(dd),")        // date
        .number("(d+)(dd)(dd),")        // hour
        .number("(-?)(d+)(dd)(d{5}),")  // latitude
        .number("(-?)(d+)(dd)(d{5}),")  // longitude
        .number("(d+),")                // speed
        .number("(d+),")                // course
        .number("(d+),")                // odometer
        .number("(d+),")                // input
        .number("(d+),")                // status
        .number("d+,")
        .number("d+,")
        .number("(d+),")                // power
        .number("(d+),")                // battery level
        .number("(d+)")                 // signal power
        .any()
    .compile();

    public Object decode(Channel channel, Object msg) {
        if(Objects.nonNull(channel)){
            channel.writeAndFlush("@");
        }

        Parser parser = new Parser(pattern, (String) msg);
        if(!parser.matches()){
            return null;
        }

        // Imei
        Device device = getDeviceByImei(parser.next()).orElseThrow(
            DeviceNotFoundException::new
        );

        Position position = new Position();
        position.setDeviceId(device.getId());

        // Data do registro
        Date date = parser.nextDateTime(Parser.DateTimeFormat.DMY_HMS);
        position.setDate(date);

        // Latitude
        double latitude = parser.nextCoordinate(Parser.CoordinateFormat.DEG_MIN_MIN_HEM);
        position.setLatitude(latitude);

        // Longitude
        double longitude = parser.nextCoordinate(Parser.CoordinateFormat.DEG_MIN_MIN_HEM);
        position.setLongitude(longitude);

        // Velocidade
        double speed = parser.nextDouble() * 0.01;
        position.setSpeed(speed);

        // Curso do GPS
        double course = parser.nextDouble() * 0.01;
        position.setCourse(course);

        // Hodômetro
        int odometer = parser.nextInt();
        position.setOdometer(odometer);

        // Alarme
        int input = parser.nextInt();
        position.setAlarm(BitUtil.check(input, 0) ? Position.ALARM_SOS : null);
        position.setKeyIgnition(BitUtil.check(input, 4));

        // Status
        int output = parser.nextInt();

        // Tensão de alimentação
        double power = parser.nextInt() * 0.001;
        position.setPower(power);

        // Nível da bateria do GPS
        int batteryLevel = parser.nextInt();
        position.setBatteryLevel(batteryLevel);

        // Potência do sinal
        int signalPower = parser.nextInt();
        position.setSignalLevel(signalPower);

        return position;
    }
}
