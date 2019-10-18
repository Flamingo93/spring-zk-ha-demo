package flamingo93;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.integration.leader.event.AbstractLeaderEvent;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.stereotype.Component;

@Component
public class LeaderEventListener {
    private static final Logger logger = LoggerFactory.getLogger(LeaderEventListener.class);

    private HaService haService;

    volatile static boolean amLeader = false;

    @Autowired
    public LeaderEventListener(HaService haService) {
        this.haService = haService;
    }

    @EventListener
    public void processEvent(AbstractLeaderEvent event) {
        if (event instanceof OnGrantedEvent) {
            logger.info("on grant:" + event);
            haService.leaderGranted();
            logger.info(" leader grant process succeed");
        } else if (event instanceof OnRevokedEvent) {
            logger.info("on revoke:" + event);
            haService.leaderRevoked();
            logger.info(" leader revoke process succeed");
        }
    }
}
