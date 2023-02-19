package erp.viewcached;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ViewCached {
    private static Map<String, ViewCachedRepository> repositories = new ConcurrentHashMap<>();

    static void registerRepository(ViewCachedRepository repository) {
        repositories.put(repository.getEntityType(), repository);
    }

    public static boolean isTypeViewCached(String type) {
        return repositories.containsKey(type);
    }

    public static void invalidate(Object entity) {
        ViewCachedRepository repository = repositories.get(entity.getClass().getName());
        if (repository != null) {
            repository.invalidate(entity);
        }
    }

}
