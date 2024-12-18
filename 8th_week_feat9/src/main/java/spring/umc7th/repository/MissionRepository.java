package spring.umc7th.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.umc7th.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
