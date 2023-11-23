package net.weg.taskmanager.repository;

import net.weg.taskmanager.model.property.TarefaProjetoPropriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaProjetoPropriedadeRepository extends JpaRepository<TarefaProjetoPropriedade, Integer> {
}
