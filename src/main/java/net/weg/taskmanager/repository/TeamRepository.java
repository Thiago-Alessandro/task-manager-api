package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.Team;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findTeamByProjectsContaining(Project project);
    Collection<Team> findTeamsByParticipantsContaining(User user);

}
