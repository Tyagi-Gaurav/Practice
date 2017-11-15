package gt.practice.makingParallelCallsToMultipleServices;

import gt.practice.makingParallelCallsToMultipleServices.serialA.ServiceARequest;

public class ServiceACommand implements Command {
    private ServiceARequest serviceARequest;

    public ServiceACommand(ServiceARequest serviceARequest) {
        this.serviceARequest = serviceARequest;
    }

    @Override
    public CommandResult execute() {
        try {
            Thread.sleep(serviceARequest.getRandomTimeToSleep());
        } catch (InterruptedException e) {
            return new CommandResult(-1);
        }

        return new CommandResult(0);
    }

    @Override
    public CommandResult rollback() {
        return null;
    }
}
