package com.shypovskikh;

@Service(name = "Super SimpleService", lazyLoad = true)
public class SimpleService {

    public SimpleService(){

    }
    @Init()
    public void initService(){
        System.out.println("initService Method");
    }

    public void simpleServiceDo(){
        System.out.println("Simple Service do");
    }
}
