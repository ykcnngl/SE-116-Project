import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FSM  {
    private Set<Character> symbols;
    private Set<String> states;
    private String initialState;
    private Set<String> finalStates;
    private List<Transition> transitions;

    public FSM() {
        symbols = new HashSet<>();
        states = new HashSet<>();
        finalStates = new HashSet<>();
        transitions = new ArrayList<>();
        initialState = "";
    }

    public boolean addSymbol(char symbol) {
        return symbols.add(Character.toUpperCase(symbol));
    }

    public Set<Character> getSymbols() {
        return symbols;
    }

    public boolean addState(String state) {
        return states.add(state.toUpperCase());
    }

    public Set<String> getStates() {
        return states;
    }

    public void setInitialState(String state) {
        this.initialState = state.toUpperCase();
    }

    public String getInitialState() {
        return initialState;
    }

    public boolean addFinalState(String state) {
        return finalStates.add(state.toUpperCase());
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void addTransition(String currentState, char symbol, String nextState) {
        currentState = currentState.toUpperCase();
        nextState = nextState.toUpperCase();
        symbol = Character.toUpperCase(symbol);
        for (Transition t : transitions) {
            if (t.matches(currentState, symbol)) {
                t.setNextState(nextState);
                return;
            }
        }
        transitions.add(new Transition(currentState, symbol, nextState));
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public String getNextState(String currentState, char symbol) {
        for (Transition t : transitions) {
            if (t.matches(currentState, symbol)) {
                return t.getNextState();
            }
        }
        return null;
    }

    public String execute(String input) {
        StringBuilder output = new StringBuilder();
        String current = initialState;
        output.append(current).append(" ");
        for (char ch : input.toCharArray()) {
            char upCh = Character.toUpperCase(ch);
            if (!symbols.contains(upCh)) {
                return "Error: invalid symbol " + ch;
            }
            String nextState = getNextState(current, upCh);
            if (nextState == null) {
                return output.toString() + "NO";
            }
            current = nextState;
            output.append(current).append(" ");
        }
        if (finalStates.contains(current))
            output.append("YES");
        else
            output.append("NO");
        return output.toString();
    }

    public void clear() {
        symbols.clear();
        states.clear();
        finalStates.clear();
        transitions.clear();
        initialState = "";
    }
}

