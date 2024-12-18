package spring.umc7th.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.umc7th.domain.Member;
import spring.umc7th.domain.Mission;
import spring.umc7th.domain.enums.MissionStatus;
import spring.umc7th.domain.mapping.MemberMission;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    Optional<MemberMission> findByMemberAndMission(Member member, Mission mission);

    Page<MemberMission> findAllByMemberAndStatus(Member member, MissionStatus missionStatus, PageRequest pageRequest);
}
