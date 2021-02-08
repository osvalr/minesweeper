package com.osvalr.minesweeper.repository;

import com.osvalr.minesweeper.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
