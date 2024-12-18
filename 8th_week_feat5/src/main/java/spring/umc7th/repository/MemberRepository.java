package spring.umc7th.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.umc7th.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
