package me.germanubuntu.clansystem.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class JsonSocketServer extends Thread{
	
	private ServerSocket server;
	
	public JsonSocketServer(){
		try {
			server = new ServerSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startServer(String host, int port) throws IOException{
		if(!host.equalsIgnoreCase("*")){
			InetSocketAddress addr = new InetSocketAddress(host, port);
			server.bind(addr);
		}else{
			InetSocketAddress addr = new InetSocketAddress(port);
			server.bind(addr);
		}
		this.start();
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Socket client = server.accept();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
