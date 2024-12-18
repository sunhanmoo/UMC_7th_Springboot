package spring.umc7th.service.storeService;

import java.util.List;
import java.util.Optional;
import spring.umc7th.domain.Store;

public interface StoreQueryService {

    Optional<Store> findStore(Long id);

    List<Store> findStoresByNameAnsScore(String name, Float score);
}
