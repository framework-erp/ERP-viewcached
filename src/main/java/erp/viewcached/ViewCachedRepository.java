package erp.viewcached;

import erp.AppContext;
import erp.repository.Repository;

/**
 * @author zheng chengdong
 */
public class ViewCachedRepository<E, ID> extends Repository<E, ID> {

    protected Repository<E, ID> underlyingRepository;

    public ViewCachedRepository(Repository<E, ID> underlyingRepository) {
        super(underlyingRepository.getEntityType(), underlyingRepository.getName());
        replaceRepositoryRegistry(underlyingRepository);
        this.store = new ViewCachedStore(underlyingRepository.getStore());
        this.mutexes = underlyingRepository.getMutexes();
        this.underlyingRepository = underlyingRepository;
        ViewCachedUpdater.registerRepository(this);
    }

    public ViewCachedRepository(Repository<E, ID> underlyingRepository, long maximumSize) {
        super(underlyingRepository.getEntityType(), underlyingRepository.getName());
        replaceRepositoryRegistry(underlyingRepository);
        this.store = new ViewCachedStore(underlyingRepository.getStore(), maximumSize);
        this.mutexes = underlyingRepository.getMutexes();
        this.underlyingRepository = underlyingRepository;
        ViewCachedUpdater.registerRepository(this);
    }

    private void replaceRepositoryRegistry(Repository<E, ID> underlyingRepository) {
        AppContext.unregisterRepository(underlyingRepository);
        AppContext.registerRepository(this);
    }

    @Override
    public E take(ID id) {
        return underlyingRepository.take(id);
    }

    public void invalidate(E entity) {
        ((ViewCachedStore) store).invalidate(getId(entity));
    }
}
