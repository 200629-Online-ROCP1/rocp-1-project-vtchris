package com.changeBank.services;

import com.changeBank.models.app.MessageDTO;

public class MessageService {
	
	public MessageDTO getMessageDTO(String m) {
		
		MessageDTO mdto = new MessageDTO();
		mdto.message = m;
		
		return mdto;
		
	}
	
public MessageDTO getUnauthorizedMessageDTO() {
		
		MessageDTO mdto = new MessageDTO();
		mdto.message = "The requested action is not permitted.";
		
		return mdto;
		
	}
}