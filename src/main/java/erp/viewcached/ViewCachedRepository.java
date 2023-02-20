package erp.viewcached;

import erp.repository.Repository;

/**
 * @author zheng chengdong
 */
public class ViewCachedRepository<E, ID> extends Repository<E, ID> {

    private Repository<E, ID> underlyingRepository;

    public ViewCachedRepository(Repository<E, ID> underlyingRepository) {
        super(new ViewCachedStore(underlyingRepository.getStore()), underlyingRepository.getMutexes(), underlyingRepository.getEntityType());
        this.underlyingRepository = underlyingRepository;
        ViewCachedUpdater.registerRepository(this);
    }

    public ViewCachedRepository(Repository<E, ID> underlyingRepository, long maximumSize) {
        super(new ViewCachedStore(underlyingRepository.getStore(), maximumSize), underlyingRepository.getMutexes(), underlyingRepository.getEntityType());
        this.underlyingRepository = underlyingRepository;
        ViewCachedUpdater.registerRepository(this);
    }

    @Override
    public E take(ID id) {
        return underlyingRepository.take(id);
    }

    public void invalidate(E entity) {
        ((ViewCachedStore) store).invalidate(getId(entity));
    }
}
