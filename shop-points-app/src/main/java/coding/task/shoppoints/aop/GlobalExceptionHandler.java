package coding.task.shoppoints.aop;

import coding.task.shoppoints.common.exceptions.NegativeTransactionAmountException;
import coding.task.shoppoints.common.exceptions.RewardPointsCalculationOverflowException;
import coding.task.shoppoints.common.exceptions.TransactionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @description: Global Exception Handler
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // handle general exception
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleException(Exception e){
        log.error("An exception occurred: {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred");
    }

    // handle transaction not exists
    @ExceptionHandler(value = {TransactionNotFoundException.class})
    public ResponseEntity handleException(TransactionNotFoundException e){
        log.error("An exception occurred: {}", e.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    // handle method validation error
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity handleException(MethodArgumentNotValidException e){
        log.error("An exception occurred: {}", e.toString());
        // extract error messages
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getDefaultMessage()).append("; ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage.toString());
    }

    // handle group validation error
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity handleException(ConstraintViolationException e){
        log.error("An exception occurred: {}", e.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    // handle reward points calculation overflow exception error
    @ExceptionHandler(value = {RewardPointsCalculationOverflowException.class})
    public ResponseEntity handleException(RewardPointsCalculationOverflowException e){
        log.error("An exception occurred: {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    // handle NegativeTransactionAmountException exception error
    @ExceptionHandler(value = {NegativeTransactionAmountException.class})
    public ResponseEntity handleException(NegativeTransactionAmountException e){
        log.error("An exception occurred: {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
