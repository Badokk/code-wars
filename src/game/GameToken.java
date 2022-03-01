package game;

public enum GameToken {
    EMPTY(0),
    PLAYER_ONE(1),
    PLAYER_TWO(2),
    PADDING(3);

    public final int value;
    GameToken(int value) {
        this.value = value;
    }
}
