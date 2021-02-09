package com.osvalr.minesweeper.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.GameSize;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class GameFirestoreRepositoryImpl implements GameFirestoreRepository {

    private final Firestore firestore;

    public GameFirestoreRepositoryImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void save(Game game) {
        firestore.collection("GAMES")
                .document(game.getGameId())
                .set(game, SetOptions.merge());
    }

    @Override
    public Optional<Game> getByGameId(String gameId) {
        Game game = new Game();
        game.setGameId(gameId);
        DocumentSnapshot ref;
        try {
            ref = firestore.collection("GAMES")
                    .document(gameId)
                    .get()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
        game.setStartTime(ref.getDate("startTime"));
        game.setGameSize(ref.get("gameSize", GameSize.class));
        return Optional.of(game);
    }
}
