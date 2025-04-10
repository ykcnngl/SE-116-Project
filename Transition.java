public class Transition {
    private String currentState;
    private char symbol;
    private String nextState;

    public Transition(String currentState, char symbol, String nextState) {
        this.currentState = currentState.toUpperCase();
        this.symbol = Character.toUpperCase(symbol);
        this.nextState = nextState.toUpperCase();
    }

    public boolean matches(String state, char symbol) {
        return this.currentState.equals(state.toUpperCase()) &&
                this.symbol == Character.toUpperCase(symbol);
    }

    public void setNextState(String nextState) {
        this.nextState = nextState.toUpperCase();
    }

    public String getNextState() {
        return nextState;
    }

    @Override
    public String toString() {
        return "(" + currentState + ", " + symbol + ") -> " + nextState;
    }
}
