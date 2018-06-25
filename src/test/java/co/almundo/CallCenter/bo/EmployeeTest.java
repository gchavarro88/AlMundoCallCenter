package co.almundo.CallCenter.bo;

import org.junit.Assert;
import org.junit.Test;
import co.almundo.CallCenter.util.*;

public class EmployeeTest {
	Employee employee;
	private static final int ID_EMPLOYEE = 1;
	private final Employee employeeInital = new Employee(ID_EMPLOYEE, Position.OPERATOR, EmployeeStatus.AVAILABLE);
	
	@Test
	public void testEmployeePosition(){
		//Validate create's method, same parameters same object
		Position position = null;
		setEmployee(new Employee(ID_EMPLOYEE, Position.OPERATOR, EmployeeStatus.AVAILABLE));
		position = employeeInital.getPosition();
		getEmployee().setId(ID_EMPLOYEE);
		employeeInital.setPosition(position);
		Assert.assertEquals(getEmployee(), employeeInital);
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
