package gt.practice.makingParallelCallsToMultipleServices;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandResult {
    private int returnCode;

    public boolean isSuccess() { return returnCode == 0;}
}
