package gt.practice.makingParallelCallsToMultipleServices;

import gt.practice.makingParallelCallsToMultipleServices.serialA.ServiceARequest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    public static void main(String[] args) {
        successScenario();
//        rollbackScenario();
    }

    private static void successScenario() {
        // Create Commands To Execute


        ServiceARequest serviceARequest1 = new ServiceARequest("1", 3000);
        ServiceACommand command1 = new ServiceACommand(serviceARequest1);

        ServiceARequest serviceARequest2 = new ServiceARequest("2", 5000);
        ServiceACommand command2 = new ServiceACommand(serviceARequest2);

        List<Command> commands = Arrays.asList(command1, command2);

        List<CommandResult> collect = commands.stream()
                .map(x -> CompletableFuture.supplyAsync(x::execute))
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        List<CommandResult> collect1 = collect.stream()
                .filter(CommandResult::isSuccess)
                .collect(Collectors.toList());

        if (collect1.size() != commands.size()) {
            //Rollback
        } else {
            System.out.println("All good.");
        }


        /*
        Get Result For Each Task
         */

        /*
        If Any result is failure, then rollback.
         */
    }
}
