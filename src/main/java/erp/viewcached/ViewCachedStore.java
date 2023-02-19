package erp.viewcached;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import erp.process.ProcessEntity;
import erp.repository.Store;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author zheng chengdong
 */
public class ViewCachedStore<E, ID> implements Store<E, ID> {

    private Store<E, ID> underlyingStore;
    private LoadingCache<ID, E> entities;

    public ViewCachedStore(Store<E, ID> underlyingStore) {
        entities = CacheBuilder.newBuilder()
                .build(
                        new CacheLoader<ID, E>() {
                            @Override
                            public E load(ID key) throws Exception {
                                return underlyingStore.load(key);
                            }
                        });
        this.underlyingStore = underlyingStore;
    }

    public ViewCachedStore(Store<E, ID> underlyingStore, long maximumSize) {
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
    }

    @Override
    public void removeAll(Set<Object> ids) {
        underlyingStore.removeAll(ids);
    }

    public void invalidate(ID id) {
        entities.invalidate(id);
    }

}
