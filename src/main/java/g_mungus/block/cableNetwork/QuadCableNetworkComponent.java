package g_mungus.block.cableNetwork;

public interface QuadCableNetworkComponent {
    public enum Channel {
        GREEN, BLUE, RED, PURPLE;

        int getIndex() {
            return switch (this) {
                case GREEN -> 0;
                case BLUE -> 1;
                case RED -> 2;
                case PURPLE -> 3;
            };
        }
    }
}
