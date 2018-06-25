package co.almundo.CallCenter.bs;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import co.almundo.CallCenter.Dispatcher;
import co.almundo.CallCenter.bo.*;
import co.almundo.CallCenter.util.*;

public class DispatcherProcessTest {
	
	@Test
	public void testCallAttend() throws InterruptedException{
		Dispatcher.createData();
		Dispatcher.distpachCall(Constants.VALUE_ONE);
		Assert.assertEquals(Dispatcher.attendedCalls.size(), Constants.VALUE_ONE);
	}
	
	@Test
	public void testCallAttendByOperator() throws InterruptedException{
		Dispatcher.createData();
		Dispatcher.distpachCall(Constants.VALUE_ONE);
		Assert.assertEquals(Dispatcher.attendedCalls.values().stream().collect(Collectors.toList()).get(0) 
				.getEmployee().getPosition(), Position.OPERATOR);
	}
	
	@Test
	public void testCallAttendBySupervisor(){
		CallByEmployee result = null;
		Employee employee = null;
		Dispatcher.createData();
		Dispatcher.distpachCall(Constants.MAXIMUM_THREADS);
		List<CallByEmployee> callByEmployees = Dispatcher.attendedCalls.values().stream().filter(callByEmployee ->
		(callByEmployee.getEmployee().getPosition().equals(Position.SUPERVISOR))).collect(Collectors.toList());
		if(callByEmployees != null && !callByEmployees.isEmpty()){
			result = callByEmployees.get(0);
			employee = result.getEmployee();
			result.setEmployee(employee);
		}
		Assert.assertEquals(result.getEmployee().getPosition(), Position.SUPERVISOR);
	}
	
	@Test
	public void testCallAttendByDirector(){
		CallByEmployee result = null;
		Call call = null;
		Dispatcher.createData();
		Dispatcher.distpachCall(Constants.MAXIMUM_THREADS);
		List<CallByEmployee> callByEmployees = Dispatcher.attendedCalls.values().stream().filter(callByEmployee ->
		(callByEmployee.getEmployee().getPosition().equals(Position.DIRECTOR))).collect(Collectors.toList());
		if(callByEmployees != null && !callByEmployees.isEmpty()){
			result = callByEmployees.get(0);
			call = result.getCall();
			result.setCall(call);
		}
		Assert.assertEquals(result.getEmployee().getPosition(), Position.DIRECTOR);
	}

	@Test
	public void testChangeCallStatus(){
		Call call = new Call();
		DispatcherProcess dispatcherProcess = new DispatcherProcess(false);
		dispatcherProcess.changeCallStatus(call, CallStatus.ON_HOLD);
		Assert.assertEquals(call.getStatus(), CallStatus.ON_HOLD);
	}
	
	@Test
	public void testChangeEmployeeStatus(){
		Employee employee = new Employee(Constants.VALUE_ONE, Position.SUPERVISOR, EmployeeStatus.AVAILABLE);
		DispatcherProcess dispatcherProcess = new DispatcherProcess(false);
		dispatcherProcess.changeEmployeeStatus(employee, EmployeeStatus.BUSY);
		Assert.assertEquals(employee.getStatus(), EmployeeStatus.BUSY);
	}
}
