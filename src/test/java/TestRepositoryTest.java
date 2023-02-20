import erp.ERP;
import erp.repository.impl.MemRepository;
import erp.viewcached.interfaceimplementer.InterfaceViewCachedRepositoryBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestRepositoryTest {

    @Test
    public void test() {
        TestEntityRepository<TestEntityImpl, String> testEntityRepository =
                InterfaceViewCachedRepositoryBuilder.newBuilder()
                        .genericItfTypeValue(TestEntityImpl.class, String.class)
                        .build(TestEntityRepository.class, new MemRepository<>());
        TestEntityImpl testEntity0 = ERP.go("test", () -> {
            TestEntityImpl testEntity = new TestEntityImpl("0");
            testEntityRepository.put(testEntity);
            return testEntity;
        });
        TestEntityImpl testEntity1 = testEntityRepository.find(testEntity0.getId());
        assertEquals(testEntity1.getId(), testEntity0.getId());

        TestEntityImplRepository testEntityImplRepository =
                InterfaceViewCachedRepositoryBuilder.newBuilder()
                        .build(TestEntityImplRepository.class, new MemRepository<>());
        TestEntityImpl testEntityImpl0 = ERP.go("test", () -> {
            TestEntityImpl testEntityImpl = new TestEntityImpl("1");
            testEntityImplRepository.put(testEntityImpl);
            return testEntityImpl;
        });
        TestEntityImpl testEntityImpl1 = testEntityImplRepository.find(testEntityImpl0.getId());
        assertEquals(testEntityImpl1.getId(), testEntityImpl0.getId());

        TestEntityImplRepositoryExtendsCommon testEntityImplRepositoryExtendsCommon =
                InterfaceViewCachedRepositoryBuilder.newBuilder()
                        .build(TestEntityImplRepositoryExtendsCommon.class, new MemRepository<>());
        TestEntityImpl testEntityImpl2 = ERP.go("test", () -> {
            TestEntityImpl testEntityImpl = new TestEntityImpl("2");
            testEntityImplRepositoryExtendsCommon.put(testEntityImpl);
            return testEntityImpl;
        });
        TestEntityImpl testEntityImpl3 = testEntityImplRepositoryExtendsCommon.find(testEntityImpl2.getId());
        assertEquals(testEntityImpl3.getId(), testEntityImpl2.getId());

        TestEntityImplRepositoryExtendsSuper testEntityImplRepositoryExtendsSuper =
                InterfaceViewCachedRepositoryBuilder.newBuilder()
                        .build(TestEntityImplRepositoryExtendsSuper.class, new MemRepository<>());
        TestEntityImpl testEntityImpl4 = ERP.go("test", () -> {
            TestEntityImpl testEntityImpl = new TestEntityImpl("3");
            testEntityImplRepositoryExtendsSuper.put(testEntityImpl);
            return testEntityImpl;
        });
        TestEntityImpl testEntityImpl5 = testEntityImplRepositoryExtendsSuper.find(testEntityImpl4.getId());
        assertEquals(testEntityImpl5.getId(), testEntityImpl4.getId());


    }

}
