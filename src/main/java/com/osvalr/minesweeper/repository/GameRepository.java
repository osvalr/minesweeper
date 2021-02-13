package com.osvalr.minesweeper.repository;

import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query
    Optional<Game> findById(String Id);

    @Query
    Optional<List<Game>> findByStateAndUserId(GameState state, Long userId);
}
