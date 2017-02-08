package com.akash.thirdeye.listentries;

import com.akash.thirdeye.Helper;
import com.akash.thirdeye.MyApplication;

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
