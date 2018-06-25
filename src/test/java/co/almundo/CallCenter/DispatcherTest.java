package co.almundo.CallCenter;

import java.util.List;
import java.util.stream.Collectors;
import junit.framework.Assert;
import org.junit.Test;
import co.almundo.CallCenter.util.*;
import co.almundo.CallCenter.bo.Employee;

public class DispatcherTest {
	
	
	@Test
	public void testEmployeesNotNull(){
		Dispatcher.createData();
		Assert.assertNotNull(Dispatcher.employees);
	}
	
	@Test
	public void testOnHollCallsNotNull(){
		Dispatcher.createData();
		Assert.assertNotNull(Dispatcher.onHoldCalls);
	}
	
	@Test
	public void testAttendedCallsNotNull(){
		Dispatcher.createData();
		Assert.assertNotNull(Dispatcher.attendedCalls);
	}
	
	@Test
	public void testEmployeesNotEmpty(){
		Dispatcher.createData();
		Assert.assertEquals(Dispatcher.employees.size(), (Constants.MAXIMUM_THREADS));
	}
	
	@Test
	public void testAmountOperators(){
		Dispatcher.createData();
		List <Employee> employees = Dispatcher.employees.values().stream().filter(employee -> (employee.getPosition().
				equals(Position.OPERATOR))).
				collect(Collectors.toList());
		Assert.assertEquals(employees.size(), Constants.OPERATORS_AMOUNT);
	}
	
	@Test
	public void testAmountSupervisor(){
		Dispatcher.createData();
		List <Employee> employees = Dispatcher.employees.values().stream().filter(employee -> (employee.getPosition().
				equals(Position.SUPERVISOR))).
				collect(Collectors.toList());
		Assert.assertEquals(employees.size(), Constants.SUPERVISORS_AMOUNT);
	}
	
	@Test
	public void testAmountDirector(){
		Dispatcher.createData();
		List <Employee> employees = Dispatcher.employees.values().stream().filter(employee -> (employee.getPosition().
				equals(Position.DIRECTOR))).
				collect(Collectors.toList());
		Assert.assertEquals(employees.size(), Constants.DIRECTORS_AMOUNT);
	}
	
	@Test
	public void testFindOperator(){
		Dispatcher.createData();
		Employee employee = Dispatcher.findEmployeeAvailable(Position.OPERATOR);
		Assert.assertEquals(employee.getPosition(), Position.OPERATOR);
	}
	
	@Test
	public void testFindSupervisor(){
		Dispatcher.createData();
		Employee employee = Dispatcher.findEmployeeAvailable(Position.SUPERVISOR);
		Assert.assertEquals(employee.getPosition(), Position.SUPERVISOR);
	}

	@Test
	public void testFindDirector(){
		Dispatcher.createData();
		Employee employee = Dispatcher.findEmployeeAvailable(Position.DIRECTOR);
		Assert.assertEquals(employee.getPosition(), Position.DIRECTOR);
	}
	
	@Test
	public void testAttendCalls(){
		int amountCalls = Constants.VALUE_FOUR;
		Dispatcher.createData();
		Dispatcher.distpachCall(4);
		Assert.assertEquals(Dispatcher.attendedCalls.size(), amountCalls);
	}
	
	@Test
	public void testNotBusyEmployees(){
		Dispatcher.createData();
		Dispatcher.distpachCall(Constants.MAXIMUM_THREADS);
		List<Employee> employees = Dispatcher.employees.values().stream().filter(employee -> (
				employee.getStatus().equals(EmployeeStatus.BUSY))).
				collect(Collectors.toList());
		Assert.assertEquals(employees.size(), Constants.VALUE_ZERO);
	}
	
	@Test
	public void testNotOnHoldCalls(){
		Dispatcher.createData();
		Dispatcher.distpachCall(Constants.MAXIMUM_THREADS * Constants.VALUE_TWO);
		Assert.assertEquals(Dispatcher.onHoldCalls.size(), Constants.VALUE_ZERO);
	}
}
