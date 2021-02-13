package com.osvalr.minesweeper.domain;

import com.osvalr.minesweeper.exception.GameExplodedException;
import com.osvalr.minesweeper.exception.GameFinishedException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
public class Game extends BaseEntity {

    private Date startTime;
    private Date endTime;
    private int size;
    private double mines;

    @Column(columnDefinition = "LONGVARCHAR")
    private String fieldStr;

    @Transient
    private MineField mineField;

    @Enumerated(EnumType.ORDINAL)
    private GameState state;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    public Game() {
    }

    public Game(int size, double mines) {
        this.size = size;
        this.mines = mines;
        this.mineField = MineField.fromSize(size, mines);
        this.fieldStr = JsonHelper.toJson(mineField.getField());
        this.startTime = new Date();
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public GameState getState() {
        return state;
    }

    public String getFieldStr() {
        return fieldStr;
    }

    public void setFieldStr(String fieldStr) {
        this.fieldStr = fieldStr;
    }

    @PostLoad
    private void setField() {
        List<List<Map<String, Object>>> res = JsonHelper.fromJson(List.class, fieldStr);
        if (res == null) {
            mineField = null;
        }
        mineField = new MineField(new Position[size][size], size);
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(i).size(); j++) {
                Map<String, Object> vals = res.get(i).get(j);
                mineField.set(i, j, new Position(
                        Boolean.parseBoolean(vals.get("mined").toString()),
                        Boolean.parseBoolean(vals.get("open").toString()),
                        Boolean.parseBoolean(vals.get("flag").toString()),
                        Integer.parseInt(vals.get("hint").toString())));
            }
        }
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void toggleFlag(Integer row, Integer col) {
        mineField.toggleFlag(row-1, col-1);
        fieldStr = JsonHelper.toJson(mineField.getField());
    }

    public void openPosition(Integer row, Integer col) throws GameExplodedException, GameFinishedException {
        mineField.open(row-1, col-1);
        fieldStr = JsonHelper.toJson(mineField.getField());
        if (!mineField.hasAvailablePositions()) {
            throw new GameFinishedException();
        }
    }
}
