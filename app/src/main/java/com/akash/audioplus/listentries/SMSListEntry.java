package com.akash.audioplus.listentries;

import com.akash.audioplus.Helper;
import com.akash.audioplus.MyApplication;

public class SMSListEntry extends AbstractListEntry {
	String mNumber;
	String mDate;
	String mMessage;

	public SMSListEntry(String number, String date, String msg) {
		super(date);
		mNumber = number;
		mDate = date;
		mMessage = msg;
	}

	public String getName() {
		return Helper.getContactName(MyApplication.getAppContext(), mNumber);
	}

	@Override
	public String getLabelToSay() {
		return getName() + ", " + mDate + ".\n" + mMessage;
	}

	public String getNumber() {
		return mNumber;
	}
}
