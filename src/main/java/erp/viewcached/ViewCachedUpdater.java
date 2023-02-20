package erp.viewcached;

import erp.process.definition.json.ProcessJsonUtil;
import erp.process.definition.json.TypedEntityUpdateJson;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zheng chengdong
 */
public abstract class ViewCachedUpdater {

    private static Map<String, ViewCachedRepository> repositories = new ConcurrentHashMap<>();

    static void registerRepository(ViewCachedRepository repository) {
        repositories.put(repository.getEntityType(), repository);
    }

    public void updateByProcessJson(String processJson) {
        List<TypedEntityUpdateJson> updateJsonList = ProcessJsonUtil.getEntityUpdateListJson(processJson);
        for (TypedEntityUpdateJson json : updateJsonList) {
            if (repositories.containsKey(json.getType())) {
                try {
                    Object entity = parseEntityFromJson(json.getUpdatedEntityJson(), Class.forName(json.getType()));
                    ViewCachedRepository repository = repositories.get(entity.getClass().getName());
                    if (repository != null) {
                        repository.invalidate(entity);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("parseEntityFromJson, get Class for type error", e);
                }
            }
        }
    }

    protected abstract Object parseEntityFromJson(String updatedEntityJson, Class<?> entityClass);

}
