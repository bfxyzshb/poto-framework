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
        SPILoader<Task> task = SPILoader.getExtensionLoader(Task.class);

        /*//System.out.println(task.getExtension("taskOne").getName());
        System.out.println(task.getExtension("taskTwo").getName());
        //自适应实现
        System.out.println(task.getAdaptiveExtension().getName());
        */

        /*List<Task> tasks = task.getActivateExtension(protocol, "taskThree");
        System.out.println(tasks);*/
        String str = null;
        List<Task> tasks1 = task.getActivateExtension(protocol, new String[]{"taskTwo"});
        System.out.println(tasks1);
        /*List<Task> taskThreeList = task.getActivateExtension(protocol, new String[]{"taskTwo"});
        System.out.println(taskThreeList);
        Task taskThree = taskThreeList.get(0);
        System.out.println(taskThree.getName());*/
        /*System.out.println(task.getSupportedExtensions());
        System.out.println(task.getDefaultExtensionName());*/
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
