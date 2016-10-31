package com.shypovskikh;

@Service(name = "Super LazyService")
public class LazyService {

    @Init(suppressException = true)
    public void lazyInit()throws Exception{
        System.out.println("LazyInit Method");
    }
}
