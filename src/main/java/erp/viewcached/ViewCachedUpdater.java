package erp.viewcached;

import erp.process.definition.Process;
import erp.process.definition.TypedEntity;
import erp.process.definition.TypedEntityUpdate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zheng chengdong
 */
public abstract class ViewCachedUpdater {

    private static Map<String, ViewCachedRepository> repositories = new ConcurrentHashMap<>();

    static void registerRepository(ViewCachedRepository repository) {
        repositories.put(repository.getName(), repository);
    }

    public void updateByProcessJson(String processJson) {
        Process process = parseProcessFromJson(processJson);
        List<TypedEntityUpdate> entityUpdateList = process.getEntityUpdateList();
        for (TypedEntityUpdate typedEntityUpdate : entityUpdateList) {
            if (repositories.containsKey(typedEntityUpdate.getRepositoryName())) {
                Object entity = typedEntityUpdate.getUpdatedEntity();
                ViewCachedRepository repository = repositories.get(typedEntityUpdate.getRepositoryName());
                if (repository != null) {
                    repository.invalidate(entity);
                }
            }
        }
        List<TypedEntity> deletedEntityList = process.getDeletedEntityList();
        for (TypedEntity typedEntity : deletedEntityList) {
            if (repositories.containsKey(typedEntity.getRepositoryName())) {
                Object entity = typedEntity.getEntity();
                ViewCachedRepository repository = repositories.get(typedEntity.getRepositoryName());
                if (repository != null) {
                    repository.invalidate(entity);
                }
            }
        }
    }

    protected abstract Process parseProcessFromJson(String processJson);

}
