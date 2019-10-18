package flamingo93;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean;
import org.springframework.integration.zookeeper.metadata.ZookeeperMetadataStore;

@Configuration
public class ApplicationConfig {

    @Value("${zookeeper.url}")
    private String zkUrl;

    @Value("${zookeeper.path}")
    private String path;

    @Value("${zookeeper.role}")
    private String role;

    private ApplicationEventPublisher applicationEventPublisher;

    public ApplicationConfig(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Bean(destroyMethod = "close")
    public CuratorFramework curatorFramework() {
        return CuratorFrameworkFactory.builder().connectString(zkUrl).retryPolicy(new ExponentialBackoffRetry(1000, 3)).sessionTimeoutMs(5000).connectionTimeoutMs(5000).build();
    }

    @Bean(destroyMethod = "stop")
    public LeaderInitiatorFactoryBean leaderInitiator() {
        LeaderInitiatorFactoryBean bean = new LeaderInitiatorFactoryBean();
        bean.setClient(curatorFramework()).setPath(path).setRole(role).setApplicationEventPublisher(applicationEventPublisher);
        return bean;
    }

    @Bean(destroyMethod = "stop")
    public ZookeeperMetadataStore zookeeperMetadataStore() throws Exception {
        ZookeeperMetadataStore metadataStore = new ZookeeperMetadataStore(curatorFramework());
        metadataStore.setRoot(path);
        return metadataStore;
    }
}
