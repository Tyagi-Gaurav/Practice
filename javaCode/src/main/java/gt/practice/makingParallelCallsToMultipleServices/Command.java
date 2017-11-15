package gt.practice.makingParallelCallsToMultipleServices;

public interface Command {
    CommandResult execute();

    CommandResult rollback();
}
