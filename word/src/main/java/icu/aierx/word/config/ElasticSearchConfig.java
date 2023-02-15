package icu.aierx.word.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "spring.elasticsearch.rest")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
    private String uris = "124.221.118.223:9200";
    private String username = "elastic";
    private String password = "02580258@lL";
    @Override
    @Bean(destroyMethod = "close")
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(uris)
//                .withConnectTimeout(30000)
                .withSocketTimeout(30000)
                .withBasicAuth(username, password)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
