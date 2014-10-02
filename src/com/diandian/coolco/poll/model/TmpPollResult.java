package com.diandian.coolco.poll.model;

public class TmpPollResult {
	public String choice;
	public int resultNum;
	public float resultFraction;
	
	public TmpPollResult(String choice, int resultNum, float resultFraction){
		this.choice = choice;
		this.resultNum = resultNum;
		this.resultFraction = resultFraction;
	}
	
}
