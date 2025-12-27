import java.util.Stack;

/**
 * Created by alexi on 21.12.2025
 */

public class StringBuilderWithUndo {
    private StringBuilder stringBuilder;
    private Stack<String> history;

    public StringBuilderWithUndo() {
        this.stringBuilder = new StringBuilder();
        this.history = new Stack<>();
        this.history.push("");
    }

    public StringBuilderWithUndo(String str) {
        this.stringBuilder = new StringBuilder(str);
        this.history = new Stack<>();
        this.history.push(str);
    }

    public StringBuilderWithUndo append(String str) {
        stringBuilder.append(str);
        saveState();
        return this;
    }

    public StringBuilderWithUndo undo() {
        if (history.size() > 1) {
            history.pop();

            String previous = history.peek();
            stringBuilder = new StringBuilder(previous);
        }
        return this;
    }

    private void saveState() {
        history.push(stringBuilder.toString());
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        StringBuilderWithUndo builder = new StringBuilderWithUndo("Hello");
        System.out.println(builder);
        builder.append(" World");
        System.out.println(builder);
        builder.undo();
        System.out.println(builder);
    }
}