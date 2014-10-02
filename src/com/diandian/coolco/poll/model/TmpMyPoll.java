package com.diandian.coolco.poll.model;

public class TmpMyPoll {
	public String question;
	public String myChoice;
	public int votesNum;
	public String createPerson;
	public String createDate;
	public TmpMyPoll(String question, String myChoice, int votesNum, String createPerson, String createDate){
		this.question = question;
		this.myChoice = myChoice;
		this.votesNum = votesNum;
		this.createPerson = createPerson;
		this.createDate = createDate;
	}
}
