package concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class ObjectWithValue {
    private AtomicInteger value = new AtomicInteger(0);

    public void incrementValue() {
        value.incrementAndGet();
    }
}
