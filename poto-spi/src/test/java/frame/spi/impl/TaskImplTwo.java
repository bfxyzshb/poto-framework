package frame.spi.impl;

import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.annotation.Activate;
import com.weibo.poto.spi.annotation.Adaptive;
import frame.spi.Task;


//@Adaptive({"taskTwo"})
@Activate(value = {"taskTwo"})
public class TaskImplTwo implements Task {
    @Override
    public String getName() {
        return "TaskImplTwo";
    }

    @Override
    public String getName(Protocol url) {
        return "two";
    }
}
