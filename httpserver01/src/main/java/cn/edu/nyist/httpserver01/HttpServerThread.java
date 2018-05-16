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
			// 0:����
						/* 
						 * ע��:sp��ʾ�ո񣡣� 
						 * ��������Ÿ�����������ʽΪ�� 
						 * ��һ�� --->������,��ʽΪ:���󷽷�sp������ԴspЭ��/�汾��\r\n
						 * ����������ͷ����ʽΪ-->����ͷ:ֵ\r\n 
						 * �հ���,�������������������������ͷ 
						 * ��Ӧ��
						 */
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 0.1 ��ȡ��һ�еõ����󷽷�����������͹�����Ҫ��GET��POST����
			String requestLine=br.readLine();
			String method=requestLine.split(" ")[0];
			System.out.println(requestLine);
			// 0.2 ��ȡ����������ͷ,һֱ����ȡ����һ���հ���
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
			// 1 ����
						/* 
						 * ע��:sp��ʾ�ո񣡣� 
						 * ���������Ÿ����������ʽΪ�� 
						 * ��һ�� --->��Ӧ��,��ʽΪ:Э��/�汾��sp��Ӧ����sp��Ӧ����\r\n
						 * ��������Ӧͷ����ʽΪ-->��Ӧͷ:ֵ\r\n 
						 * �հ���,����������Ӧ�����������Ӧͷ ��������
						 */
			PrintWriter out=new PrintWriter(socket.getOutputStream());
			// 1.1 ��Ӧ�У���ʽΪ:Э��/�汾��sp��Ӧ����sp��Ӧ����\r\n
			out.println("HTTP/1.1 200 ok");
			// 1.2 ������Ӧͷ����ʽΪ-->��Ӧͷ:ֵ\r\n
			out.println("Content-Type:text/html;charset=utf-8");
			// 1.3 �հ���,���ڷָ���������Ӧͷ����Ӧ��
			out.println();
			// 1.4 ��Ӧ��
			out.println("<html>");
			out.println("<head><title>�ҵĵ�һ��html</title></head>");
			out.println("<body>");
			out.println("<b>��ǰʱ��Ϊ��");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			out.println(sdf.format(new Date()));
			out.println("</b>");
			out.println("</body>");
			out.println("</html>");
			//ǿ�����
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
