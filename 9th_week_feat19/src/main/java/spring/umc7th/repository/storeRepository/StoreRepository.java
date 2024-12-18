package spring.umc7th.repository.storeRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.umc7th.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
}
