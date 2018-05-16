package cn.edu.nyist.httpserver01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpServerThread extends Thread{
	private Socket socket;

	public HttpServerThread(Socket socket) {
		super();
		this.socket = socket;
	}
	@Override
	public void run() {
		try {
			// 0:读信
						/* 
						 * 注意:sp表示空格！！ 
						 * 浏览器发信给服务器，格式为： 
						 * 第一行 --->请求行,格式为:请求方法sp请求资源sp协议/版本号\r\n
						 * 若干行请求头，格式为-->请求头:值\r\n 
						 * 空白行,用于区分请求体和若干行请求头 
						 * 响应体
						 */
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 0.1 读取第一行得到请求方法，浏览器发送过来主要是GET和POST请求
			String requestLine=br.readLine();
			String method=requestLine.split(" ")[0];
			System.out.println(requestLine);
			// 0.2 读取若干行请求头,一直到读取到第一个空白行
			String line=null;
			while((line=br.readLine())!=null&&!line.equals(" ")) {
				System.out.println(line);
			}
			System.out.println();
			if("POST".equals(method)) {
				char[] cbuf=new char[1024];
				int len=-1;
				while((len=br.read(cbuf))!=-1) {
					System.out.println(new String(cbuf,0,len));
				}
			}
			System.out.println("=======");
			// 1 回信
						/* 
						 * 注意:sp表示空格！！ 
						 * 服务器发信给浏览器，格式为： 
						 * 第一行 --->响应行,格式为:协议/版本号sp响应代码sp响应描述\r\n
						 * 若干行响应头，格式为-->响应头:值\r\n 
						 * 空白行,用于区分响应体和若干行响应头 请求正文
						 */
			PrintWriter out=new PrintWriter(socket.getOutputStream());
			// 1.1 响应行，格式为:协议/版本号sp响应代码sp响应描述\r\n
			out.println("HTTP/1.1 200 ok");
			// 1.2 若干响应头，格式为-->响应头:值\r\n
			out.println("Content-Type:text/html;charset=utf-8");
			// 1.3 空白行,用于分割若干行响应头和响应体
			out.println();
			// 1.4 响应体
			out.println("<html>");
			out.println("<head><title>我的第一个html</title></head>");
			out.println("<body>");
			out.println("<b>当前时间为：");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			out.println(sdf.format(new Date()));
			out.println("</b>");
			out.println("</body>");
			out.println("</html>");
			//强制输出
			out.flush();
		} catch (IOException e) {	
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
