package coding.task.shoppoints.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @description: AOP Point cuts
 * @author: Ryan Mei
 * @date: 4/5/24
 */
public class PointCuts {

    // controller point cut
    @Pointcut("within(coding.task.shoppoints.trigger.http.*)")
    public void inControllerLayer(){}
}
