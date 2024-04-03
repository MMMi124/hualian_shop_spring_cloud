package com.mme.exception;

import com.mme.domain.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lxin
 * @description 全局异常处理
 * @Date 2024/3/31 23:58
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionResolver {

    @ExceptionHandler(SysException.class)
    public ResponseVo<String> back(SysException sysException) {
        return ResponseVo.backException(sysException.getCode(), sysException.getMsg(), null);
    }


    //处理hibernate-validator框架参数校验抛出的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVo<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> msgList = new ArrayList<>();
        //将错误信息放在msgList
        bindingResult.getFieldErrors().stream().forEach(item->msgList.add(item.getDefaultMessage()));
        //拼接错误信息
        String msg = StringUtils.join(msgList, ",");
        log.info("请求参数异常：{}",msg);
        return ResponseVo.backException(ExceptionEnum.SYS_EXCEPTION.getCode(), msg, ExceptionEnum.SYS_EXCEPTION.getMsg());
    }
    //处理hibernate-validator框架参数校验抛出的异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseVo<String> constraintViolationException(ConstraintViolationException e) {
        String msg = e.getMessage();
        log.info("请求参数异常：{}",msg);
        return ResponseVo.backException(ExceptionEnum.SYS_EXCEPTION.getCode(), msg, ExceptionEnum.SYS_EXCEPTION.getMsg());
    }

}
