
package fsm.core;
import java.io.Serializable;

public class Transition implements Serializable {
    private static final long serialVersionUID = 1L;


    private final String symbol;
    private final String currentState;
    private final String nextState;


    public Transition(String symbol, String currentState, String nextState) {
        this.symbol = symbol.toUpperCase(); // case-insensitive olsun diye
        this.currentState = currentState.toUpperCase();
        this.nextState = nextState.toUpperCase();
    }


    public String getSymbol() {
        return symbol;
    }

    public String getCurrentState() {
        return currentState;
    }

    public String getNextState() {
        return nextState;
    }

    @Override
    public String toString() {
        return symbol + " " + currentState + " -> " + nextState;
    }
}
