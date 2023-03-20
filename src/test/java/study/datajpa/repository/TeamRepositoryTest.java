package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void testTeam() throws Exception{
        //given
        Team team = new Team("tina");

        //when
        Team savedTeam = teamRepository.save(team);

        //then
        Team findTteam = teamRepository.findById(savedTeam.getId()).get();

        assertThat(findTteam).isNotNull();
        assertThat(findTteam.getId()).isEqualTo(savedTeam.getId());
        assertThat(findTteam.getName()).isEqualTo(savedTeam.getName());
        assertThat(findTteam).isEqualTo(savedTeam);
    }

    @Test
    public void basicCRUD() throws Exception{
        //given
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");

        teamRepository.save(team1);
        teamRepository.save(team2);

        //when
        Team foundTeam1 = teamRepository.findById(team1.getId()).get();
        Team foundTeam2 = teamRepository.findById(team2.getId()).get();

        //then
        assertThat(foundTeam1).isEqualTo(team1);
        assertThat(foundTeam2).isEqualTo(team2);

        //리스트 조회
        List<Team> foundTeams = teamRepository.findAll();
        assertThat(foundTeams.size()).isEqualTo(2);

        //count 검증
        long count = teamRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        teamRepository.delete(team1);
        teamRepository.delete(team2);
        assertThat(teamRepository.count()).isEqualTo(0);

    }
}
