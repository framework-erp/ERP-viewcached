package erp.viewcached;

import erp.repository.Repository;

/**
 * @author zheng chengdong
 */
public class ViewCachedRepository<E, ID> extends Repository<E, ID> {

    private Repository<E, ID> underlyingRepository;
    private Long maximumSize;

    public ViewCachedRepository(Repository<E, ID> underlyingRepository) {
        this.underlyingRepository = underlyingRepository;
        entityType = underlyingRepository.getEntityType();
        store = new ViewCachedStore(underlyingRepository.getStore());
        mutexes = underlyingRepository.getMutexes();
    }

    @Override
    public E take(ID id) {
        return underlyingRepository.take(id);
    }

}
