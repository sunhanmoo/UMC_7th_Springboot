package spring.umc7th.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.umc7th.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
