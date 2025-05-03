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

    public void addSymbol(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            System.out.println("Error: Symbol cannot be null or empty.");
            return;
        }
        symbol = symbol.toUpperCase();
        if (!symbol.matches("[A-Z0-9]")) {
            System.out.println("Error: Symbol '" + symbol + "' is not alphanumeric and ignored.");
            return;
        }
        if (symbols.contains(symbol)) {
            System.out.println("Warning: Symbol '" + symbol + "' was already declared.");
        } else {
            symbols.add(symbol);
        }
    }

    public void addState(String state) {
        if (state == null || state.isEmpty()) {
            System.out.println("Error: State cannot be null or empty.");
            return;
        }
        state = state.toUpperCase();
        if (!state.matches("[A-Z0-9]+")) {
            System.out.println("Error: State '" + state + "' is not alphanumeric and ignored.");
            return;
        }
        if (states.contains(state)) {
            System.out.println("Warning: State '" + state + "' was already declared.");
        } else {
            states.add(state);
            if (initialState == null) {
                initialState = state;
            }
        }
    }

    public void setInitialState(String state) {
        if (state == null || state.isEmpty()) {
            System.out.println("Error: No initial state specified.");
            return;
        }
        state = state.toUpperCase();
        if (!states.contains(state)) {
            System.out.println("Warning: State '" + state + "' was not previously declared. Adding it now.");
            states.add(state);
        }
        initialState = state;
    }

    public void addFinalState(String state) {
        if (state == null || state.isEmpty()) {
            System.out.println("Error: No final state specified.");
            return;
        }
        state = state.toUpperCase();
        if (!states.contains(state)) {
            System.out.println("Warning: State '" + state + "' was not previously declared. Adding it now.");
            states.add(state);
        }
        if (finalStates.contains(state)) {
            System.out.println("Warning: State '" + state + "' was already declared as a final state.");
        } else {
            finalStates.add(state);
        }
    }

    public void addTransition(String symbol, String currentState, String nextState) {
        if (symbol == null || currentState == null || nextState == null) {
            System.out.println("Error: Symbol and states must not be null.");
            return;
        }
        symbol = symbol.toUpperCase();
        currentState = currentState.toUpperCase();
        nextState = nextState.toUpperCase();

        if (!symbols.contains(symbol)) {
            System.out.println("Error: Invalid symbol '" + symbol + "'. It was not previously declared.");
            return;
        }
        if (!states.contains(currentState)) {
            System.out.println("Error: Invalid current state '" + currentState + "'. It was not previously declared.");
            return;
        }
        if (!states.contains(nextState)) {
            System.out.println("Error: Invalid next state '" + nextState + "'. It was not previously declared.");
            return;
        }

        // Eğer aynı symbol ve currentState varsa öncekiyi kaldır
        String finalSymbol = symbol;
        String finalCurrentState = currentState;
        for (int i = 0; i < transitions.size(); i++) {
            Transition t = transitions.get(i);
            if (t.getSymbol().equals(finalSymbol) && t.getCurrentState().equals(finalCurrentState)) {
                transitions.remove(i);
                i--; // dikkat: eleman silindiği için indeksi geri alıyoruz!
            }
        }

        transitions.add(new Transition(symbol, currentState, nextState));
    }
	
	public String execute(String input) {
        if (initialState == null) {
            System.out.println("Error: Initial state is not set.");
            return "NO";
        }
        if (input == null) {
            System.out.println("Error: Input cannot be null.");
            return "NO";
        }

        String currentState = initialState;
        System.out.print(currentState + " ");

        for (char ch : input.toCharArray()) {
            String symbol = String.valueOf(ch).toUpperCase();

            if (!symbols.contains(symbol)) {
                System.out.println("\nError: Invalid symbol '" + symbol + "' encountered.");
                return "NO";
            }

            boolean found = false;
            for (Transition t : transitions) {
                if (t.getSymbol().equals(symbol) && t.getCurrentState().equals(currentState)) {
                    currentState = t.getNextState();
                    System.out.print(currentState + " ");
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("\nNo valid transition from state '" + currentState + "' using symbol '" + symbol + "'.");
                return "NO";
            }
        }

        if (finalStates.contains(currentState)) {
            System.out.println("\nYES");
            return "YES";
        } else {
            System.out.println("\nNO");
            return "NO";
        }
    }
	
	public void print() {
        System.out.println("SYMBOLS " + symbols);

        System.out.print("STATES {");
        boolean first = true;
        for (String state : states) {
            if (!first) System.out.print(", ");
            System.out.print(state);
            if (state.equals(initialState)) System.out.print(" (initial)");
            if (finalStates.contains(state)) System.out.print(" (final)");
            first = false;
        }
        System.out.println("}");

        System.out.println("TRANSITIONS:");
        for (Transition t : transitions) {
            System.out.println(t);
        }
    }

    public void printToFile(String filename) {
        if (filename == null || filename.isEmpty()) {
            System.out.println("Error: Invalid filename.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("SYMBOLS ");
            for (String symbol : symbols) {
                writer.write(symbol + " ");
            }
            writer.write(";\n");

            writer.write("STATES ");
            for (String state : states) {
                writer.write(state + " ");
            }
            writer.write(";\n");

            if (initialState != null) {
                writer.write("INITIAL-STATE " + initialState + ";\n");
            }

            if (!finalStates.isEmpty()) {
                writer.write("FINAL-STATES ");
                for (String finalState : finalStates) {
                    writer.write(finalState + " ");
                }
                writer.write(";\n");
            }

            writer.write("TRANSITIONS ");
            boolean first = true;
            for (Transition t : transitions) {
                if (!first) writer.write(", ");
                writer.write(t.getSymbol() + " " + t.getCurrentState() + " " + t.getNextState());
                first = false;
            }
            writer.write(";\n");

            System.out.println("FSM printed to file: '" + filename + "'");
        } catch (IOException e) {
            System.out.println("Error: Could not write FSM to file. " + e.getMessage());
        }
    }
	}
