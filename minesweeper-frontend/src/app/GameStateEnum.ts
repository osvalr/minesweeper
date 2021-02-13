import { IdValue } from "./IdValue";

export const GameStateEnum = {
    IN_PROGRESS: 'in_progress',
    WIN: 'win',
    EXPLODED: 'exploded'
};

export const GameStateArray: IdValue[] = [];

for (let key in GameStateEnum) {
    GameStateArray.push({ id: key, value: GameStateEnum[key] });
}
