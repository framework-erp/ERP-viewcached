package erp.viewcached.interfaceimplementer;

import erp.repository.Repository;
import erp.viewcached.ViewCachedRepository;

public class TemplateEntityRepositoryImpl extends ViewCachedRepository<TemplateEntityImpl, Object> implements TemplateEntityRepository {

    public TemplateEntityRepositoryImpl(Repository<TemplateEntityImpl, Object> underlyingRepository) {
        super(underlyingRepository);
    }

    public TemplateEntityRepositoryImpl(Repository<TemplateEntityImpl, Object> underlyingRepository, long maximumSize) {
        super(underlyingRepository, maximumSize);
    }
}
