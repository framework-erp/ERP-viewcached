package erp.viewcached;

import erp.repository.Repository;

/**
 * @author zheng chengdong
 */
public class ViewCachedRepository<E, ID> extends Repository<E, ID> {

    private Repository<E, ID> underlyingRepository;
    private Long maximumSize;

    public ViewCachedRepository(Repository<E, ID> underlyingRepository) {
        super(new ViewCachedStore(underlyingRepository.getStore()), underlyingRepository.getMutexes(), underlyingRepository.getEntityType());
        this.underlyingRepository = underlyingRepository;
    }

    @Override
    public E take(ID id) {
        return underlyingRepository.take(id);
    }

}
