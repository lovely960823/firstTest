package cn.edu.nyist.httpserver01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class HttpServer {

	/**
	 * git提交更新测试
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket serverSocket=null;
		try {
			serverSocket=new ServerSocket(80);
			while(true) {
			Socket socket=serverSocket.accept();
			new HttpServerThread(socket).start();
			}
		} catch (IOException e) {
		
			e.printStackTrace();
		}finally {
			if(serverSocket!=null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}

	}

}
