package com.haige.util;


import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Archie
 * @date 2019/10/19 12:49
 */
public class TimerUtils {

    private static final Timer timer = new Timer();
    // 5分钟之后异步执行
    public static void schedule(TimerTask timerTask){
        timer.schedule(timerTask, 5* 60 * 1000);
    }
}
