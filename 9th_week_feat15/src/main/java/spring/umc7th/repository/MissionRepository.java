package spring.umc7th.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.umc7th.domain.Mission;
import spring.umc7th.domain.Store;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Page<Mission> findAllByStore(Store store, PageRequest pageRequest);
}
