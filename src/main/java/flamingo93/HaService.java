package flamingo93;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.zookeeper.metadata.ZookeeperMetadataStore;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class HaService {

    private ZookeeperMetadataStore metadataStore;

    @Autowired
    public HaService(ZookeeperMetadataStore metadataStore) {
        this.metadataStore = metadataStore;
    }

    synchronized void leaderGranted() {
        LeaderEventListener.amLeader = true;
        metadataStore.put("instance", getIp());
    }

    synchronized void leaderRevoked() {
        LeaderEventListener.amLeader = false;

    }

    private String getIp() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip == null ? "null" : ip.toString();
    }
}
