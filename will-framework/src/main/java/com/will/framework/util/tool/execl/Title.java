package com.will.framework.util.tool.execl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) 
@Target({ ElementType.FIELD, ElementType.METHOD }) 
public @interface Title { 
      
    String value(); 
    
    String keyValue() default "";
} 