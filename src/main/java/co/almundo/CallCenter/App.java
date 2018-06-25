package co.almundo.CallCenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.almundo.CallCenter.util.Constants;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	int callsAmount = 30;
    	Dispatcher.createData();
    	Dispatcher.distpachCall(callsAmount);
    	
    }
}
