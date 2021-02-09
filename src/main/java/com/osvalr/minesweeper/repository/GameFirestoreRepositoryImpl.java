package com.osvalr.minesweeper.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.common.base.Strings;
import com.osvalr.minesweeper.domain.Field;
import com.osvalr.minesweeper.domain.FieldConverter;
import com.osvalr.minesweeper.domain.FirestoreGame;
import com.osvalr.minesweeper.domain.Game;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
public class GameFirestoreRepositoryImpl implements GameFirestoreRepository {

    private static final String COLLECTION = "GAMES";
    private final Firestore firestore;

    public GameFirestoreRepositoryImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void save(Game game) {
        FirestoreGame fGame = new FirestoreGame(game.getGameId(), game.getStartTime(),
                FieldConverter.toJson(game.getField()));
        firestore.collection(COLLECTION)
                .document(game.getGameId())
                .set(fGame, SetOptions.merge());
    }

    @Override
    public Optional<Game> getByGameId(String gameId) {
        Game game = new Game();
        game.setGameId(gameId);
        DocumentSnapshot ref;
        try {
            ref = firestore.collection(COLLECTION)
                    .document(gameId)
                    .get()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
        game.setStartTime(ref.getDate("startTime"));
        String fieldValue = ref.getString("field");
        if (Strings.isNullOrEmpty(fieldValue)) {
            return Optional.empty();
        }
        game.setField(FieldConverter.fromJson(Field.class, fieldValue));
        return Optional.of(game);
    }
}
