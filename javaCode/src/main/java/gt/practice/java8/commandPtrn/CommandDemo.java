package gt.practice.java8.commandPtrn;

import java.util.Arrays;
import java.util.List;

public class CommandDemo {
    public static void main(String[] args) {
        CommandExecutor executor = new CommandExecutor();

        List<Action> actionList = Arrays.asList(
                Doer::doSomething,
                Doer::doSomethingAgain,
                Doer::doSomethingRepeatedly
        );

        executor.execute(actionList);
    }
}
