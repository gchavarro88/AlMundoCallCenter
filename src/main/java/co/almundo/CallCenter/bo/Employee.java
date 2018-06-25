package co.almundo.CallCenter.bo;

import co.almundo.CallCenter.util.*;

public class Employee {
	
	private Position position;
	private EmployeeStatus status;
	private long id;
	
	public Employee(int id, Position position, EmployeeStatus status) {
		this.id = id;
		this.position = position;
		this.status = status;
	}
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public EmployeeStatus getStatus() {
		return status;
	}

	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		if (position != other.position)
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	
}
