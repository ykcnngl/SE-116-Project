package fsm.core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class FSM implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<String> symbols = new ArrayList<>();
    private final List<String> states = new ArrayList<>();
    private final List<String> finalStates = new ArrayList<>();
    private final List<Transition> transitions = new ArrayList<>();
    private String initialState = null;

    public FSM() {}

    public void clear() {
        symbols.clear();
        states.clear();
        finalStates.clear();
        transitions.clear();
        initialState = null;
    }
}
