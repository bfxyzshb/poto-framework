package frame;


import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.SPILoader;
import frame.spi.Task;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

<<<<<<< HEAD
=======
/**
 * Unit test for simple App.
 */
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
public class SPILoaderTest {
    //自定义协议
    static Protocol protocol = new Protocol() {

        @Override
        public Set<String> getParameters() {
<<<<<<< HEAD
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
=======
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
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
        SPILoader<Task> task = SPILoader.getExtensionLoader(Task.class);

        /*//System.out.println(task.getExtension("taskOne").getName());
        System.out.println(task.getExtension("taskTwo").getName());
        //自适应实现
        System.out.println(task.getAdaptiveExtension().getName());
<<<<<<< HEAD
        */

        /*List<Task> tasks = task.getActivateExtension(protocol, "taskThree");
        System.out.println(tasks);*/
        String str = null;
=======
*/

        /*List<Task> tasks = task.getActivateExtension(protocol, "taskThree");
        System.out.println(tasks);*/
        String str=null;
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
        List<Task> tasks1 = task.getActivateExtension(protocol, new String[]{"taskTwo"});
        System.out.println(tasks1);
        /*List<Task> taskThreeList = task.getActivateExtension(protocol, new String[]{"taskTwo"});
        System.out.println(taskThreeList);
        Task taskThree = taskThreeList.get(0);
        System.out.println(taskThree.getName());*/
        /*System.out.println(task.getSupportedExtensions());
        System.out.println(task.getDefaultExtensionName());*/
    }

<<<<<<< HEAD
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


=======
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
}
