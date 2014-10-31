package com.nationsky.push;

import java.util.List;

import javapns.Push;
import javapns.notification.PushNotificationPayload;

import com.nationsky.model.Message;

public class PushThread extends Thread {
	private Message message;
	private String p12FilePath;
	private String password;
	private List<String> deviceList;

	public PushThread(Message message, String p12FilePath, String password, List<String> deviceList) {
		super();
		this.message = message;
		this.p12FilePath = p12FilePath;
		this.password = password;
		this.deviceList = deviceList;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			send(message, p12FilePath, password, deviceList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 推送消息
	 * @param message
	 * @param p12FilePath
	 * @param password
	 * @param deviceList
	 * @throws Exception
	 */
	private void send(Message message, String p12FilePath, String password, List<String> deviceList) throws Exception {
		PushNotificationPayload payload = PushNotificationPayload.complex();
		payload.addBadge(message.getBadge());
		payload.addAlert(message.getAlert());
		payload.addSound(message.getSound());
		boolean production = false;
		int size = deviceList.size();
		int threads = (size / 10000) + 1;
		Push.payload(payload, p12FilePath, password, production, threads, deviceList);
	}

}
