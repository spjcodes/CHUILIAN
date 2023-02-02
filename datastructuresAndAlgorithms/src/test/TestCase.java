import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/1 下午9:57
 */
public class TestCase {


    @Test
    public void t() {
        System.out.println(new AtomicInteger(0).decrementAndGet());

    }
}
