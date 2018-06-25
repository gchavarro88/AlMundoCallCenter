package co.almundo.CallCenter.bs;

import org.apache.log4j.Logger;

import co.almundo.CallCenter.Dispatcher;
import co.almundo.CallCenter.bo.Call;
import co.almundo.CallCenter.bo.CallByEmployee;
import co.almundo.CallCenter.bo.Employee;
import co.almundo.CallCenter.util.CallStatus;
import co.almundo.CallCenter.util.Constants;
import co.almundo.CallCenter.util.EmployeeStatus;
import co.almundo.CallCenter.util.Position;

public class DispatcherProcess implements Runnable{

	final static Logger log = Logger.getLogger(DispatcherProcess.class);
	private Call attendedCall;
	
	public DispatcherProcess(boolean incommingCall)
	{
		if(incommingCall){
			Dispatcher.onHoldCalls.add(new Call());
		}
	}
	
	@Override
	public void run() {
		try {
			if(!Dispatcher.employees.isEmpty()){
				log.debug("finding a employee available");
				Employee employee = Dispatcher.findEmployeeAvailable(Position.OPERATOR);
				if(null == employee){
					log.info("At this time there are not "+Position.OPERATOR+" available, it will be attended by a "+Position.SUPERVISOR);
					employee = Dispatcher.findEmployeeAvailable(Position.SUPERVISOR);
				}
				if(null == employee){
					log.info("At this time there are not "+Position.SUPERVISOR+" available, it will be attended by a "+Position.DIRECTOR);
					employee = Dispatcher.findEmployeeAvailable(Position.DIRECTOR);
				}	
				setAttendedCall(Dispatcher.onHoldCalls.poll());	//Take the first call in the queue
				log.info("Calls incoming: "+Dispatcher.onHoldCalls.size());
				changeCallStatus(getAttendedCall(), CallStatus.ATTENDED); //change the call status
				changeEmployeeStatus(employee, EmployeeStatus.BUSY); //Change the employee status
				Dispatcher.attendedCalls.put(getAttendedCall().getId(), new CallByEmployee(employee, getAttendedCall()));
				log.info("The call N°"+getAttendedCall().getId()+" begins with "+getAttendedCall().getDuration()+
						" seconds and the employee N° "+employee.getId()+" and Position: "+employee.getPosition());
				log.info("Calls attended "+Dispatcher.attendedCalls.size());
				Thread.sleep(getAttendedCall().getDuration() * Constants.MILISECONDS);//call time
				log.info("Finish the call N°"+getAttendedCall().getId());
				changeEmployeeStatus(employee, EmployeeStatus.AVAILABLE); //Change the employee status
				Dispatcher.employees.put(employee.getId(), employee);
				log.info("Employees availables: "+Dispatcher.employees.size());
			}
			else{
				log.info("At this time there are nobody available, it will be continue on hold");
				changeCallStatus(Dispatcher.onHoldCalls.peek(), CallStatus.ON_HOLD);//Change call status
			}
		} catch (InterruptedException e) {
			log.error("Error call assignment "+e.getMessage());
		}
		
	}
	
	/**
	 * Responsible to change the state of a call
	 * @param call
	 * @param callStatus
	 * @author gustavochavarro
	 **/
	public synchronized void changeCallStatus(Call call, CallStatus callStatus){
		call.setStatus(callStatus);
	}
	
	/**
	 * Responisble to change the state of a employee
	 * @param employee
	 * @param employeeStatus
	 * @author gustavochavarro
	 **/
	public synchronized void changeEmployeeStatus(Employee employee, EmployeeStatus employeeStatus){
		employee.setStatus(employeeStatus);
	}

	public Call getAttendedCall() {
		return attendedCall;
	}

	public void setAttendedCall(Call attendedCall) {
		this.attendedCall = attendedCall;
	}
}
