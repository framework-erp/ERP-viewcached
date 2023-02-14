package erp.viewcached;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import erp.process.ProcessEntity;
import erp.repository.Store;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author zheng chengdong
 */
public class ViewCachedStore<E, ID> implements Store<E, ID> {

    private Store<E, ID> underlyingStore;
    private LoadingCache<ID, E> entities;
    private KafkaTemplate<String, String> kafkaTemplate;

    public ViewCachedStore(Store<E, ID> underlyingStore, KafkaTemplate<String, String> kafkaTemplate) {
        entities = CacheBuilder.newBuilder()
                .build(
                        new CacheLoader<ID, E>() {
                            @Override
                            public E load(ID key) throws Exception {
                                return underlyingStore.load(key);
                            }
                        });
        this.underlyingStore = underlyingStore;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ViewCachedStore(Store<E, ID> underlyingStore, KafkaTemplate<String, String> kafkaTemplate, long maximumSize) {
        entities = CacheBuilder.newBuilder()
                .maximumSize(maximumSize)
                .build(
                        new CacheLoader<ID, E>() {
                            @Override
                            public E load(ID key) throws Exception {
                                return underlyingStore.load(key);
                            }
                        });
        this.underlyingStore = underlyingStore;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public E load(ID id) {
        try {
            return entities.get(id);
        } catch (ExecutionException e) {
            throw new RuntimeException("get entity from LoadingCache error", e);
        }
    }

    @Override
    public void insert(ID id, E entity) {
        underlyingStore.insert(id, entity);
    }

    @Override
    public void saveAll(Map<Object, Object> entitiesToInsert, Map<Object, ProcessEntity> entitiesToUpdate) {
        underlyingStore.saveAll(entitiesToInsert, entitiesToUpdate);
        sendInvalid(entitiesToUpdate.keySet());
    }

    @Override
    public void removeAll(Set<Object> ids) {
        underlyingStore.removeAll(ids);
        sendInvalid(ids);
    }

    private void sendInvalid(Set<Object> idSet) {
        if (idSet == null || idSet.isEmpty()) {
            return;
        }
        String processJson = JSON.toJSONString(process, SerializerFeature.IgnoreNonFieldGetter);
        kafkaTemplate.send("viewcached-invalid-ids", processJson);
    }

}
