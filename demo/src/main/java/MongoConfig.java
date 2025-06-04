import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        System.out.println("MongoClient bean created");
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient) {
        System.out.println("GridFSBucket bean created");
        return GridFSBuckets.create(mongoClient.getDatabase("BVresource"));
    }
}
