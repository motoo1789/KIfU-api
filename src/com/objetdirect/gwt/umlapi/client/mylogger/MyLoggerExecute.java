package com.objetdirect.gwt.umlapi.client.mylogger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.objetdirect.gwt.umlapi.client.helpers.Session;

public class MyLoggerExecute {
	private static String preEditEventType = "";
	//	public static void registEditEvent(String logData, String canvasUrl ){
	//		if( ( Session.getMode() ).equals("drawer")){
	//			MyLoggerServiceAsync async = (MyLoggerServiceAsync)GWT.create(MyLoggerService.class);
	//			ServiceDefTarget entryPoint = (ServiceDefTarget) async;
	//			String entryURL = GWT.getModuleBaseURL() + "registEditEvent";
	//			entryPoint.setServiceEntryPoint(entryURL);
	//
	//			@SuppressWarnings("rawtypes")
	//			AsyncCallback callback = new AsyncCallback(){
	//				public void onSuccess(Object result){
	//
	//				}
	//				public void onFailure(Throwable caught){
	//
	//				}
	//			};
	//
	//			async.registEditEvent(logData, canvasUrl, Session.getStep(), callback);
	//		}
	//		else{
	//
	//		}
	//
	//	}


	public static void registEditEvent(int preEventId, String editEvent, String eventType,
			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
			String targetPart, String beforeEdit, String afterEdit, String canvasUrl, int umlArtifactId ){

		String studentId = Session.studentId;
		int exerciseId = Session.exerciseId;

		if( ( Session.getMode() ).equals("drawer") || ( Session.getMode() ).equals("login")){
			if( !(preEditEventType.equals("Check") && eventType.equals("Check") ) ){
				MyLoggerServiceAsync async = (MyLoggerServiceAsync)GWT.create(MyLoggerService.class);
				ServiceDefTarget entryPoint = (ServiceDefTarget) async;
				String entryURL = GWT.getModuleBaseURL() + "registEditEvent";
				entryPoint.setServiceEntryPoint(entryURL);

				@SuppressWarnings("rawtypes")
				AsyncCallback callback = new AsyncCallback(){
					public void onSuccess(Object result){

					}
					public void onFailure(Throwable caught){
						Window.alert("Connect Error");
					}
				};

				async.registEditEvent( studentId, exerciseId, preEventId, editEvent, eventType, targetType, targetId,
						linkKind,  rightObjectId, leftObjectId, targetPart,  beforeEdit,
						afterEdit, canvasUrl, umlArtifactId, callback);
			}
		}
		else{

		}
		preEditEventType = eventType;
	}

}
