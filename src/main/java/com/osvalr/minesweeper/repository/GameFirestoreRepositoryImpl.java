package com.osvalr.minesweeper.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.common.base.Strings;
import com.osvalr.minesweeper.domain.FieldConverter;
import com.osvalr.minesweeper.domain.FirestoreGame;
import com.osvalr.minesweeper.domain.Game;
import com.osvalr.minesweeper.domain.Position;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
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
                FieldConverter.toJson(game.getField()),
                game.getSize(), game.getState());
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
        game.setSize(ref.get("size", Integer.class));
        String fieldValue = ref.getString("field");
        if (Strings.isNullOrEmpty(fieldValue)) {
            return Optional.empty();
        }
        List<List<Map<String, Boolean>>> res = FieldConverter.fromJson(List.class, fieldValue);
        if (res == null) {
            return Optional.empty();
        }
        Position[][] field = new Position[game.getSize()][game.getSize()];
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(i).size(); j++) {
                Map<String, Boolean> vals = res.get(i).get(j);
                field[i][j] = new Position(vals.get("mined"), vals.get("open"), vals.get("flag"));;
            }
        }
        game.setField(field);
        return Optional.of(game);
    }
}
