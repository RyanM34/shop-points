package coding.task.shoppoints.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @description: Logging Aspect
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("coding.task.shoppoints.aop.PointCuts.inControllerLayer()")
    public ResponseEntity logStartAndEndTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        // before
        log.info("From LoggingAspect.logStartAndEndTime: " + proceedingJoinPoint.getSignature());
        log.info("Start time: " + System.currentTimeMillis());

        //Invoke the actual object
        ResponseEntity httpResponse = (ResponseEntity) proceedingJoinPoint.proceed();

        // after
        log.info("End time: " + System.currentTimeMillis());
        return httpResponse;
    }
}
