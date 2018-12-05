package gt.practice.concurrency;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.catchThrowable;

public class MyBlockingQueueTest {

    private BlockingQueue<Integer> blockingQueue = new MyBlockingQueue<>(2);

    @Test
    void shouldThrowNullPointerWhenTryToAddNull() {
        //when
        Throwable throwable = catchThrowable(() -> blockingQueue.add(null));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowIllegalStateWhenTryToAddAtMaximumCapacity() {
        //given
        blockingQueue.add(2);
        blockingQueue.add(3);

        //when
        Throwable throwable = catchThrowable(() -> blockingQueue.add(4));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldAddElementsToQueue() {
        //when
        boolean add = blockingQueue.add(2);

        //then
        Object[] items = (Object[]) Whitebox.getInternalState(blockingQueue, "items");
        Object addIndex = Whitebox.getInternalState(blockingQueue, "addIndex");

        assertThat(add).isEqualTo(true);
        assertThat(items).isNotNull();
        assertThat(addIndex).isEqualTo(1);
        assertThat(items[0]).isEqualTo(2);
    }

    @Test
    void offerShouldAddElementsToQueueWhenThereIsCapacity() {
        //when
        boolean offer = blockingQueue.offer(2);

        //then
        Object[] items = (Object[]) Whitebox.getInternalState(blockingQueue, "items");
        Object addIndex = Whitebox.getInternalState(blockingQueue, "addIndex");

        assertThat(offer).isEqualTo(true);
        assertThat(items).isNotNull();
        assertThat(addIndex).isEqualTo(1);
        assertThat(items[0]).isEqualTo(2);
    }

    @Test
    void offerShouldReturnFalseWhenAddElementsToQueueWithCapacityFull() {
        //given
        blockingQueue.add(1);
        blockingQueue.add(3);

        //when
        boolean offer = blockingQueue.offer(2);

        //then
        assertThat(offer).isEqualTo(false);
    }

    @Test
    void offerShould() {

    }
}