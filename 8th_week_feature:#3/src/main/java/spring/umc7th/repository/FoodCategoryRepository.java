package spring.umc7th.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.umc7th.domain.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}
