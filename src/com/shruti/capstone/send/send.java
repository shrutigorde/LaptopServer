package com.shruti.capstone.send;

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

	List<String> slaveIps = new ArrayList<String>();
	Map<String, Socket> socketIpsMap = new HashMap<String, Socket>();

	protected void sent() throws IOException {
		ServerSocket serverSocket = null;
		boolean listening = true;

		try {
			InetAddress addr = InetAddress.getLocalHost();
			System.out.println(addr);
			serverSocket = new ServerSocket(4443, 0, addr);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444."
					+ e.getMessage());
			System.exit(-1);
		}
		System.out.println("listening on port: 4444.");
		int countConnection = 0;
		ServerJobs jobService = new ServerJobs();

		while (listening) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("client socket ::"+clientSocket);
			System.out.println("Connection # " + countConnection++
					+ " established");

			String clientIp = clientSocket.getInetAddress().toString();
			System.out.println("Ip = " + clientIp);

			slaveIps.add(clientIp);
			socketIpsMap.put(clientIp, clientSocket);
			System.out.println("slaveips ::"+slaveIps);
			System.out.println("socketipsmap ::"+socketIpsMap);
			ObjectInputStream fromClient = new ObjectInputStream(
					clientSocket.getInputStream());
			Information message = null;
			try {
				message = (Information) fromClient.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			System.out.println(message.getInfo().toString());
			//if(countConnection==2){
			jobService.taskcreate(slaveIps, socketIpsMap);
			jobService.taskimpl(clientSocket);
			//}
		}

		System.out.println("Waiting...");
	}
}
