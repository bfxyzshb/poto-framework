package frame.spi;

import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.annotation.SPI;

/**
 * Created by 278076999@qq.com 2018/8/15 14:36
 *
 * @author <a href="mailto:278076999@qq.com">simple</a>
 */
@SPI("taskOne")
public interface Task {

    String getName();

    String getName(Protocol url);
}
