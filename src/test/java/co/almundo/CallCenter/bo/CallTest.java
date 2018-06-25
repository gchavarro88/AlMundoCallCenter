package co.almundo.CallCenter.bo;

import org.junit.Assert;
import org.junit.Test;
import co.almundo.CallCenter.util.*;

public class CallTest {
	
	Call call;

	@Test
	public void testCallDurationInterval(){
		setCall(new Call());
		//Validate if duration is between duration intervals (5 to 10) seconds
		boolean result = (getCall().getDuration() >= Constants.CALL_DURATION_INTERVAL 
				&& getCall().getDuration() <=(Constants.CALL_DURATION_INTERVAL*2));
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void testCallIntialStatus(){
		setCall(new Call()); //Validate initial status incoming
		Assert.assertEquals(getCall().getStatus(), CallStatus.INCOMING);
	}
	
	@Test
	public void testCallFinalStatus() {
		setCall(new Call());
		getCall().setStatus(CallStatus.ATTENDED);
		Assert.assertEquals(getCall().getStatus(), CallStatus.ATTENDED);
	}
	
	@Test
	public void testCallDuration() {
		setCall(new Call());
		getCall().setDuration(Constants.CALL_DURATION_INTERVAL);
		Assert.assertEquals(getCall().getDuration(), Constants.CALL_DURATION_INTERVAL);
	}
	
	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
	}
}
