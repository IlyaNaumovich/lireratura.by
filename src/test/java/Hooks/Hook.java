package Hooks;

import cucumber.api.java.Before;

public class Hook {
    @Before
    public void start(){
        System.out.println("I start");
    }
}
