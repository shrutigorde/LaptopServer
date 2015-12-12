package com.shruti.capstone.Jobtracker;
import java.net.Socket;

public class JobType {
	
	private String nodeIP = null;
	private String taskPathinput = null;
	private Socket AndroidIP = null;
	private TaskState taskState = null;
	private String jobName = null;
	private TaskPerfom task = TaskPerfom.MAP;

	private int jobnumber;
	

	public JobType(String jobName) {
		setName(jobName);
	}

	public String getPathinput() {
		return taskPathinput;
	}

	public void setPathinput(String taskPathinput) {
		this.taskPathinput = taskPathinput;
	}

	public int getJobNumber() {
		return jobnumber;
	}

	public void setJobNumber(int jobnumber) {
		this.jobnumber = jobnumber;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}
	
	public Socket getAndroidIP() {
		return AndroidIP;
	}

	public void setAndroidIP(Socket AndroidIP) {
		this.AndroidIP = AndroidIP;
	}
	
	public TaskPerfom getTaskPerform() {
		return task;
	}

	public void setTaskPerform(TaskPerfom task) {
		this.task = task;
	}

	public String getName() {
		return jobName;
	}

	public void setName(String jobName) {
		this.jobName = jobName;
	}

	public String getNodeIPAdd() {
		return nodeIP;
	}

	public void setNodeIPAdd(String nodeIP) {
		this.nodeIP = nodeIP;
	}

}
