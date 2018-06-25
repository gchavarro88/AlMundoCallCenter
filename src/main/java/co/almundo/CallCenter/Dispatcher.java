package co.almundo.CallCenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.*;
import org.apache.log4j.Logger;
import co.almundo.CallCenter.bo.*;
import co.almundo.CallCenter.bs.DispatcherProcess;
import co.almundo.CallCenter.util.Constants;
import co.almundo.CallCenter.util.EmployeeStatus;
import co.almundo.CallCenter.util.Position;

public class Dispatcher {

	final static Logger log = Logger.getLogger(Dispatcher.class); 
	public static HashMap<Long, CallByEmployee> attendedCalls;
	public static Queue<Call> onHoldCalls;
	public static HashMap<Long, Employee> employees;
	public static long idCall = 1;
	
	/**
	 * Responsible to create initial data, like amount of employees, 
	 * and set up the initial data to on hold calls and attended calls.
	 * @author gustavochavarro
	 **/
	public static void createData(){
		//it creates all employees
		employees = new HashMap<>();
		for(int i=1; i <= Constants.MAXIMUM_THREADS; i++)
		{
			if(i <= Constants.OPERATORS_AMOUNT){ //Let's create 6 Operators
				employees.put((new Long(i)), new Employee(i, Position.OPERATOR, EmployeeStatus.AVAILABLE));
				log.info("Creating an Employee N°: "+i+" with position: "+Position.OPERATOR);
			}
			else if((i-Constants.OPERATORS_AMOUNT) <= Constants.SUPERVISORS_AMOUNT){ //Let's create 3 Supervisors
				employees.put((new Long(i)), new Employee(i, Position.SUPERVISOR, EmployeeStatus.AVAILABLE));
				log.info("Creating an Employee N°: "+i+" with position: "+Position.SUPERVISOR);
			}
			else if((i-Constants.OPERATORS_AMOUNT-Constants.SUPERVISORS_AMOUNT) <= Constants.DIRECTORS_AMOUNT){ 
				//Let's create 1 Director
				employees.put((new Long(i)), new Employee(i, Position.DIRECTOR, EmployeeStatus.AVAILABLE));
				log.info("Creating an Employee N°: "+i+" with position: "+Position.DIRECTOR);
			}
		}//Set up anothers collections
		onHoldCalls = new LinkedBlockingQueue();
		attendedCalls = new HashMap<>();
	}
	
	/**
	 * Responsible to asign calls to the employees, just allows to execute 
	 * MAXIMUN_THREADS concurrents, finaly it is checking the employees availability
	 * and the on hold calls
	 * @author gustavochavarro
	 **/
	public static void  distpachCall(int calls)
	{
		ExecutorService executor = Executors.newFixedThreadPool(Constants.MAXIMUM_THREADS);
		//Let's execute 30 calls at same time
		for(int i=0; i<calls; i++){
			executor.execute(new DispatcherProcess(true));
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
			 try {
				Thread.sleep(200);//Each 200 miliseconds it will check the employees availability  and on hold calls
				log.info("Waiting while all calls are attended: "+Dispatcher.attendedCalls.size()+
						", Calls on hold: "+Dispatcher.onHoldCalls.size());
			} catch (InterruptedException e) {
				log.info("Wainting for the finish status "+e.getMessage());
			}
		}
		
		log.info("Finish process, all calls were attended");
		
	}
	
	/**
	 * Responsible to find a employee by position to attend the call
	 * @param position
	 * @return Employee
	 * @author gustavochavarro
	 **/
	public static synchronized Employee findEmployeeAvailable(Position position){
		Employee result = null;
		//Get all of employees with both parameters
		List<Employee> list = employees.values().stream().filter(employee -> (employee.getPosition().
				equals(position) && employee.getStatus().equals(EmployeeStatus.AVAILABLE))).
				collect(Collectors.toList());
		
		if(null != list && !list.isEmpty()){
			result = list.get(0);//Get first result 
			Dispatcher.employees.remove(result.getId()); //Take value of the hashmap
			log.info("found Employee ID: "+result.getId()+" position: "+result.getPosition()+" employees availables: "+employees.size());
		}
		return result;
	}
	
}
