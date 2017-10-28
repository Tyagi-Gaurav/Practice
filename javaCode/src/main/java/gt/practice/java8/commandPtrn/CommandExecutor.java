package gt.practice.java8.commandPtrn;

import java.util.List;

public class CommandExecutor {
    public static final void execute(List<Action> actions) {
        actions.stream().forEach(Action::perform);
    }
}
