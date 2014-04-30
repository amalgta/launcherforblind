package net.zaczek.launcherforblind.activitysupport;

import net.zaczek.launcherforblind.listentries.ListEntry;
import android.database.Cursor;
import android.util.Log;

public abstract class AbstractCursorActivity extends AbstractActivity {
	private static final String TAG = "launcherforblind";
	private Cursor mCursor;
	private ListEntry mCurrent;

	protected abstract Cursor getCursor();

	protected abstract ListEntry getListEntry(Cursor c);

	protected abstract void giveFeedback(String label);

	protected ListEntry getCurrentListEntry() {
		return mCurrent;
	}

	@Override
	protected void onResume() {
		super.onResume();

		mCursor = getCursor();
		if (mCursor != null && mCursor.moveToFirst()) {
			select();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void select() {
		mCurrent = getListEntry(mCursor);
		final String label = mCurrent.getLabel();
		Log.i(TAG, "Selecting " + label);

		vibe();
		giveFeedback(label);
		say(label);
	}

	private void execute() {
		if (mCurrent != null) {
			say(mCurrent.getTextToSay());
			mCurrent.onSelected();
		}
	}

	@Override
	protected void onScrollDown() {
		super.onScrollDown();

		if (mCursor == null)
			return;

		if (!mCursor.moveToNext() && !mCursor.moveToFirst()) {
			return;
		}
		select();
	}

	@Override
	protected void onScrollUp() {
		super.onScrollUp();

		if (mCursor == null)
			return;

		if (!mCursor.moveToPrevious() && !mCursor.moveToLast()) {
			return;
		}
		select();
	}

	@Override
	protected void onDoubleTap() {
		super.onDoubleTap();

		execute();
	}
}
