package co.almundo.CallCenter.bo;

import java.util.Random;

import co.almundo.CallCenter.Dispatcher;
import co.almundo.CallCenter.util.CallStatus;
import co.almundo.CallCenter.util.Constants;

public class Call {
	
	private long duration;
	private CallStatus status;
	private long id;
	private Random random = new Random();
	
	public Call() {
		this.id = Dispatcher.idCall++;
		this.status = CallStatus.INCOMING;
		this.duration = Constants.CALL_DURATION_INTERVAL + random.nextInt(Constants.CALL_DURATION_INTERVAL);
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public CallStatus getStatus() {
		return status;
	}

	public void setStatus(CallStatus status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}	
}
