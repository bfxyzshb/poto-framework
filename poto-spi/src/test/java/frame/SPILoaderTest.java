package frame;


import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.SPILoader;
import frame.spi.Task;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class SPILoaderTest {
    //自定义协议
    static Protocol protocol = new Protocol() {

        @Override
        public Set<String> getParameters() {
            Set set=new HashSet();
            set.add("taskThree");

            return set;
        }
    };

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        SPILoader<Task> task = SPILoader.getExtensionLoader(Task.class);

        /*//System.out.println(task.getExtension("taskOne").getName());
        System.out.println(task.getExtension("taskTwo").getName());
        //自适应实现
        System.out.println(task.getAdaptiveExtension().getName());
*/

        /*List<Task> tasks = task.getActivateExtension(protocol, "taskThree");
        System.out.println(tasks);*/
        String str=null;
        List<Task> tasks1 = task.getActivateExtension(protocol, new String[]{"taskTwo"});
        System.out.println(tasks1);
        /*List<Task> taskThreeList = task.getActivateExtension(protocol, new String[]{"taskTwo"});
        System.out.println(taskThreeList);
        Task taskThree = taskThreeList.get(0);
        System.out.println(taskThree.getName());*/
        /*System.out.println(task.getSupportedExtensions());
        System.out.println(task.getDefaultExtensionName());*/
    }

}
