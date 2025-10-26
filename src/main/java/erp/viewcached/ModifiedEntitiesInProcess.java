package erp.viewcached;

import erp.process.definition.TypedEntity;
import erp.process.definition.TypedEntityUpdate;

import java.util.List;

public class ModifiedEntitiesInProcess {
    private List<TypedEntity> deletedEntityList;
    private List<TypedEntityUpdate> entityUpdateList;

    public List<TypedEntity> getDeletedEntityList() {
        return deletedEntityList;
    }

    public void setDeletedEntityList(List<TypedEntity> deletedEntityList) {
        this.deletedEntityList = deletedEntityList;
    }

    public List<TypedEntityUpdate> getEntityUpdateList() {
        return entityUpdateList;
    }

    public void setEntityUpdateList(List<TypedEntityUpdate> entityUpdateList) {
        this.entityUpdateList = entityUpdateList;
    }
}
