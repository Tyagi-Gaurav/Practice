package gt.practice.makingParallelCallsToMultipleServices.serialA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ServiceARequest {
    private String id;
    private Integer randomTimeToSleep;
}
