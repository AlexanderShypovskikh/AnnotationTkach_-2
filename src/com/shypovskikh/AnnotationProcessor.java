package com.shypovskikh;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mercurius on 30.10.2016.
 */
public class AnnotationProcessor {
    private static Map<String, Object> serviceMap = new HashMap();

    public static void main(String[] args) {
       // inspectService(SimpleService.class);
      //  inspectService(LazyService.class);
      //  inspectService(String.class);

        loadService("com.shypovskikh.SimpleService");
        loadService("com.shypovskikh.LazyService");
        System.out.println("Content of Map:");
        System.out.println(serviceMap.get("Super LazyService").getClass());
        System.out.println(serviceMap.get("Super SimpleService").getClass());

        System.out.println("Invoke Methods with @Init:");
        invokeMethod(serviceMap.get("Super LazyService"));
        invokeMethod(serviceMap.get("Super SimpleService"));

    }

    public static void invokeMethod(Object clazz){
        Method[] methods = clazz.getClass().getMethods();
        for(Method m : methods){
             if(m.isAnnotationPresent(Init.class)){
                // if(m.getAnnotation())
                 try{
                    m.invoke(clazz);
                    }catch (Exception e ){
                     System.err.println(e.getMessage());
                     Init ann = m.getAnnotation(Init.class);
                     if(ann.suppressException()){
                         System.err.println(e.getMessage());
                     }else{
                        throw new RuntimeException(e);
                     }
                 }
            }
        }
    }
    static void loadService(String className){
        try{
            Class<?> clazz = Class.forName(className);
            if(clazz.isAnnotationPresent(Service.class)) {
                Object obj = clazz.newInstance();
                serviceMap.put(clazz.getAnnotation(Service.class).name(), obj);
                System.out.println(serviceMap);
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    static void  inspectService(Class<?> service){
           if(service.isAnnotationPresent(Service.class)){
            Service ann = service.getAnnotation(Service.class);
               System.out.println("class"+service.getName()+" has annotation:");
               System.out.println( " name = "+ann.name());
               Method[] methods = service.getMethods();
               boolean f = false;
               for(Method m : methods){
                   if(m.isAnnotationPresent(Init.class)){
                       System.out.println("Method "+m.getName()+" has annotation Init");
                   } else{
                       System.out.println("Method "+m.getName()+" hasn't annotation Init");
                   }


               }
               System.out.println(" lazyLoad = "+ann.lazyLoad());
        }
        else System.out.println("Class "+service.getName()+" doesn't contains annotation Service");
    }

}
