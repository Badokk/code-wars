package game;

public enum GameToken {
    // This enum is never cast to int, no need to define int values
    EMPTY(0),
    PLAYER_ONE(1),
    PLAYER_TWO(2),
    PADDING(3);

    // Not used, remove it
    public final int value;

    GameToken(int value) {
        this.value = value;
    }
}
