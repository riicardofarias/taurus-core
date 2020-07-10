package br.com.gransistemas.taurus;

import br.com.gransistemas.taurus.protocol.SmartProtocol;
import br.com.gransistemas.taurus.protocol.SviasProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerManager.class);
    private List<ServerTracker> servers = new ArrayList<>();

    public ServerManager(){
        servers.add(new ServerTracker(new SviasProtocol()));
        servers.add(new ServerTracker(new SmartProtocol()));
    }

    public void start() throws Exception {
        for(ServerTracker server: servers){
            try{
                server.start();

                LOGGER.info("Server {} started at port {}", server.getName(), server.getPort());
            }catch (BindException e){
                LOGGER.warn("Failed to start protocol {} at port {}", server.getName(), server.getPort());
            }
        }
    }

    public void stop(){
        for(ServerTracker server: servers){
            server.stop();
        }
    }
}
