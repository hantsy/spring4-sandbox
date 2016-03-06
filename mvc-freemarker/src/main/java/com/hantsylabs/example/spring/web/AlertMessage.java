package com.hantsylabs.example.spring.web;

import java.io.Serializable;

public class AlertMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	enum Type {
		SUCCESS, WARNING, DANGER, INFO
	}

	private Type type;

	private String text;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "AlertMessage [type=" + type + ", text=" + text + "]";
	}

	public static AlertMessage info(String text) {
		AlertMessage msg = new AlertMessage();
		msg.setType(Type.INFO);
		msg.setText(text);
		return msg;
	}

	public static AlertMessage success(String text) {
		AlertMessage msg = new AlertMessage();
		msg.setType(Type.SUCCESS);
		msg.setText(text);
		return msg;
	}

	public static AlertMessage danger(String text) {
		AlertMessage msg = new AlertMessage();
		msg.setType(Type.DANGER);
		msg.setText(text);
		return msg;
	}

	public static AlertMessage warning(String text) {
		AlertMessage msg = new AlertMessage();
		msg.setType(Type.WARNING);
		msg.setText(text);
		return msg;
	}

}
