package spring.umc7th.repository.storeRepository;

import java.util.List;
import spring.umc7th.domain.Store;

public interface StoreRepositoryCustom {
    List<Store> dynamicQueryWithBooleanBuilder(String name, Float score);
}
