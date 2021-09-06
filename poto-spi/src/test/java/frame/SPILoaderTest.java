package frame;


import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.SPILoader;
import frame.spi.Task;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SPILoaderTest {
    //自定义协议
    static Protocol protocol = new Protocol() {

        @Override
        public Set<String> getParameters() {
            Set set = new HashSet();
            //set.add("taskFour");
            return set;
        }

        @Override
        public String getExtensionName() {
            return null;
        }
    };


    @Test
    public void test() {
        Set set = new HashSet();
        set.add("taskThree");
    }


    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        SPILoader<Task> task = SPILoader.getExtensionLoader(Task.class);
    }

    @Test
    public void test2() {
        SPILoader<Task> taskExtension = SPILoader.getExtensionLoader(Task.class);
        Task task = taskExtension.getDefaultExtension();
        System.out.println(task);

    }

    @Test
    public void test3() {
        SPILoader<Task> taskExtension = SPILoader.getExtensionLoader(Task.class);
        List<Task> tasks = taskExtension.getActivateExtension(protocol, null);
        System.out.println(tasks);

    }
}