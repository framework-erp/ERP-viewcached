package erp.viewcached.interfaceimplementer;

import erp.repository.Repository;
import erp.viewcached.ViewCachedRepository;

public class GenericTemplateEntityRepositoryImpl extends ViewCachedRepository<TemplateEntityImpl, Object> implements GenericTemplateEntityRepository<TemplateEntityImpl, Object> {

    public GenericTemplateEntityRepositoryImpl(Repository<TemplateEntityImpl, Object> underlyingRepository) {
        super(underlyingRepository);
    }

    public GenericTemplateEntityRepositoryImpl(Repository<TemplateEntityImpl, Object> underlyingRepository, long maximumSize) {
        super(underlyingRepository, maximumSize);
    }
    
}
