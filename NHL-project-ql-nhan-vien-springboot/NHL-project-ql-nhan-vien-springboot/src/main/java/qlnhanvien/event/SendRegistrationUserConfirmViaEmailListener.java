package qlnhanvien.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import qlnhanvien.service.IAccountService;

@Component
public class SendRegistrationUserConfirmViaEmailListener
		implements ApplicationListener<OnSendRegistrationAccontConfirmViaEmailEvent> {

	@Autowired
	private IAccountService service;
	
	@Override
	public void onApplicationEvent(OnSendRegistrationAccontConfirmViaEmailEvent event) {
		sendConfirmViaEmail(event.getEmail());
	}

	private void sendConfirmViaEmail(String email) {
		service.sendRegistrationAccountConfirm(email);
	}
}
