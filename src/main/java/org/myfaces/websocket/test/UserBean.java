package org.myfaces.websocket.test;


import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int notifCount;

	public Long getUserId() {
		return 1L;
	}

	public void onNotification() {
		System.out.println("hallo.");
		notifCount ++;
	}

	public int getNotiCount() {
		return notifCount;
	}
}
