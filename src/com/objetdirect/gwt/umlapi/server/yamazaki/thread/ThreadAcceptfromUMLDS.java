package com.objetdirect.gwt.umlapi.server.yamazaki.thread;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ThreadAcceptfromUMLDS extends Thread {

	private static ThreadAcceptfromUMLDS singleton_ = new ThreadAcceptfromUMLDS("UMLDS受付用Thread");
	private boolean changedCodeState_ = false;


	private ThreadAcceptfromUMLDS(String threadName) {
		// TODO 自動生成されたコンストラクター・スタブ
		super(threadName);
		this.start();
	}
	public static ThreadAcceptfromUMLDS getInstance()
	{
		return singleton_;
	}

	public void run()
	{
		// TODO 自動生成されたメソッド・スタブ
		try (ZContext context = new ZContext()) {
		      //  Socket to talk to clients
			ZMQ.Socket socket = context.createSocket(SocketType.REP);
			socket.bind("tcp://localhost:5555");
			System.out.println("ZContext try野中");
			while(!Thread.currentThread().isInterrupted()) {
				byte[] reply = socket.recv(0);
				System.out.println("Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]");
				String response = "world";
				socket.send(response.getBytes(ZMQ.CHARSET), 0);

				inThisClassChangeBoolean();

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}
	}

	private void inThisClassChangeBoolean()
	{
		this.changedCodeState_ = true;
	}

	public boolean isState()
	{
		return this.changedCodeState_;
	}
}
