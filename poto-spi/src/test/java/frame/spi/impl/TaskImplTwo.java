package frame.spi.impl;

import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.annotation.Activate;
import frame.spi.Task;

/**
 * Created by 278076999@qq.com 2018/8/15 14:36
 *
 * @author <a href="mailto:278076999@qq.com">simple</a>
 */
//@Adaptive({"taskTwo"})
@Activate(value = {"taskTwo"}, group = {"alicdn"})
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
