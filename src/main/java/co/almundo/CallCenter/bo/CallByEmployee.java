package co.almundo.CallCenter.bo;

import org.apache.log4j.Logger;
import co.almundo.CallCenter.bo.*;

public class CallByEmployee {

	private Employee employee;
	private Call call;
		
	public CallByEmployee(Employee employee, Call call) {
		this.employee = employee;
		this.call = call;
	}

	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Call getCall() {
		return call;
	}
	public void setCall(Call call) {
		this.call = call;
	}
}
