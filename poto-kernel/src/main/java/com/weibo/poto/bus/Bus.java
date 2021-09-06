package com.weibo.poto.bus;


<<<<<<< HEAD
import com.weibo.poto.bus.command.Command;

public interface Bus<T,R> {

    R dispatch(T object);
=======
public interface Bus<R> {

    R dispatch(Object object);
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
}
