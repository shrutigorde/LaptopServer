package com.shruti.capstone.send;
/*
 * Capstone project by Shruti Gorde
 * network connections
 * 
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shruti.capstone.Information;
import com.shruti.capstone.Jobtracker.ServerJobs;

public class send {

	List<String> nodes = new ArrayList<String>();
	Map<String, Socket> mapping = new HashMap<String, Socket>();

	protected void sent() throws IOException {
		ServerSocket ss = null;
		boolean flag = true;

		try {
			InetAddress addrs = InetAddress.getLocalHost();
			System.out.println(addrs);
			ss = new ServerSocket(4443, 0, addrs);
		} catch (IOException e) {
			System.err.println("Already listening on 4443 port number"
					+ e.getMessage());
			System.exit(-1);
		}
		System.out.println("port 443 is listening");
		int noOfConnections = 0;
		ServerJobs sj = new ServerJobs();

		while (flag) {
			Socket androidd = ss.accept();
			System.out.println("client socket ::"+androidd);
			System.out.println("connection number" + noOfConnections++
					+ "succesful");

			String androidDeviceIP = androidd.getInetAddress().toString();
			System.out.println("Ip = " + androidDeviceIP);

			nodes.add(androidDeviceIP);
			mapping.put(androidDeviceIP, androidd);
			System.out.println("nodes ::"+nodes);
			System.out.println("address ::"+mapping);
			ObjectInputStream androidclient = new ObjectInputStream(
					androidd.getInputStream());
			Information info = null;
			try {
				info = (Information) androidclient.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			System.out.println(info.getInfo().toString());
		
			sj.taskcreate(nodes, mapping);
			sj.taskimpl(androidd);
			
		}

		System.out.println("Waiting for result file...");
	}
}
