package com.nationsky.push;

import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.transmission.PushQueue;

import com.nationsky.model.Message;

public class Pushing {
	private static List<PushedNotification> notifications;

	/**
	 * 推送消息
	 * @param message
	 * @param p12FilePath
	 * @param password
	 * @param deviceList
	 * @throws Exception
	 */
	public static void send(Message message, String p12FilePath, String password, List<String> deviceList) throws Exception {
		PushThread pushThread = new PushThread(message, p12FilePath, password, deviceList);
		pushThread.start();
	}

	/**
	 * 队列发送
	 */
	public static void send(String token, Object keystore, String password, boolean production) throws InvalidDeviceTokenFormatException, KeystoreException {
		/* 准备一条push信息 */
		PushNotificationPayload payload = PushNotificationPayload.alert("PushQueue!");
		/* 指定线程数 */
		int threads = 200;
		/* 建立队列 */
		PushQueue queue = Push.queue(keystore, password, production, threads);
		/* 启动队列推送(所有的线程和连接将被初始化) */
		queue.start();
		/* 添加一个推送信息 */
		queue.add(payload, token);
	}

	/**
	 * 打印返回结果
	 */
	public static void result() {
		List<PushedNotification> successNotifications = PushedNotification.findSuccessfulNotifications(notifications);
		List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
		System.out.println("[success]" + successNotifications.size());
		System.out.println("[failed]" + failedNotifications.size());
		// for (PushedNotification pushedNotification : successNotifications) {
		// System.out.println("[success]" + pushedNotification.toString());
		// }
		// for (PushedNotification pushedNotification : failedNotifications) {
		// System.out.println("[failed]" + pushedNotification.toString());
		// }
	}
}
