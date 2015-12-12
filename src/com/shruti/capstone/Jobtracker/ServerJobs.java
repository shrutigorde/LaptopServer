package com.shruti.capstone.Jobtracker;
/*
 * Capstone project by Shruti Gorde
 * 
 * 
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shruti.capstone.Information;
import com.shruti.capstone.Infotype;

public class ServerJobs {
	public final static String SEND_TEXT_FILE = "/Users/Apple/Desktop/finalcode/LaptopServer/src/com/shruti/capstone/test.txt";
	public int j=0;
	List<JobType> tasks = new ArrayList<JobType>();
	
	//create task
	public void taskcreate(List<String> NodeIps, Map<String, Socket> AndroidIPs) {
		JobType task1 = new JobType("occurences of words");
		task1.setJobNumber(1);
		task1.setTaskPerform(TaskPerfom.MAP);
		int Nodeno = NodeIps.size() - 1;
		task1.setNodeIPAdd(NodeIps.get(Nodeno));
		task1.setTaskState(TaskState.NEW);
		task1.setAndroidIP(AndroidIPs.get(NodeIps.get(Nodeno)));
		task1.setPathinput(InputFiles.INPUT_DIR
				+ InputFiles.INPUT_FILE);
	
		tasks.add(task1);
	
	}
// implementation of task
	public void taskimpl(Socket AndroidClient) {
		for (JobType task : tasks) {
			
			if (TaskState.NEW.equals(task.getTaskState())) {
				task_map(task);
				Result_map(task,AndroidClient);
			}
		}
	}
//send text file and xml file
	private void task_map(JobType task) {
		try {
			Information msg = new Information();
			msg.setInfo(Infotype.MAPPER);
			Socket AndroidClient = task.getAndroidIP();
			System.out.println("client socket ::"+AndroidClient);
			File mapin = new File(getPath(task));
			System.out.println("path to the file::"+mapin);
			byte[] bytesbuffer = new byte[(int) mapin.length()];
			FileInputStream fis = new FileInputStream(mapin);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(bytesbuffer, 0, bytesbuffer.length);
			msg.setContent(bytesbuffer);
			System.out.println("contents of the file are::"+bytesbuffer);

			ObjectOutputStream oos = new ObjectOutputStream(
					AndroidClient.getOutputStream());
			oos.reset();
			oos.writeObject(msg);
			System.out.println("msg is ::"+msg);
			task.setTaskState(TaskState.INPROGRESS);
			
			
			File file = new File (SEND_TEXT_FILE);
	          byte [] arr  = new byte [(int)file.length()];
	          FileInputStream   fistream = new FileInputStream(file);
	          BufferedInputStream  bistream = new BufferedInputStream(fistream);
	          bistream.read(arr,0,arr.length);
	          OutputStream output = null;
	        		output=  AndroidClient.getOutputStream();
	          System.out.println("Sending " + SEND_TEXT_FILE + "(" + arr.length + " bytes)");
	          output.write(arr,0,arr.length);
	          output.flush();
	          System.out.println("Sent");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
//accept the result file from the device
	private void Result_map(JobType task, Socket AndroidClient) {
		System.out.println("waiting for output file from android device ... ");
		System.out.println(AndroidClient);
		String resdir = "/Users/Apple/Desktop/finalcode/LaptopServer/src/com/shruti/capstone/mapreduce/res/";
		File f = new File(resdir);
		f.mkdirs();

		File resFile = new File(f, "result.txt");
		FileOutputStream fos;
		try {
			
			fos = new FileOutputStream(resFile);
			
			ObjectInputStream ois = new ObjectInputStream(AndroidClient.getInputStream());
			System.out.println(ois);
			Information msg = (Information) ois.readObject();
			System.out.println(msg);

			fos.write(msg.getContent());
			fos.close();
			System.out.println("Result recieved and stored at "
					+ resdir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String getPath(JobType task) {
		String fp = "";
		switch (task.getTaskPerform()) {
		case MAP:

			fp = InputFiles.INPUT_DIR
					+ InputFiles.INPUT_FILE;
			break;
		case REDUCE:
			fp = InputFiles.RESULT_DIR
					+ InputFiles.RESULT_FILE;
			break;
		}

		return fp;
	}
}
