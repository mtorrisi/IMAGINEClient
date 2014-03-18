package it.infn.ct.imagineclient;

import it.infn.ct.imagineclient.pojos.ActiveInteraction;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActiveInteractionsAdapter extends ArrayAdapter<ActiveInteraction> {

	Context context;

	public ActiveInteractionsAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);

		this.context = context;
	}

	public void setData(ArrayList<ActiveInteraction> data) {
		clear();
		if (data != null) {
			for (ActiveInteraction f : data) {
				add(f);
			}
		}

	}

	private class ViewHolder {
		TextView submissionStarted;
		TextView jobDescription;
		ImageView imgStatus;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		ActiveInteraction a = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {

			int layout = R.layout.interaction_lv_item;
			convertView = mInflater.inflate(layout, null);
			holder = new ViewHolder();

			holder.submissionStarted = (TextView) convertView
					.findViewById(R.id.starTimestamp);
			holder.jobDescription = (TextView) convertView
					.findViewById(R.id.description);
			holder.imgStatus = (ImageView) convertView
					.findViewById(R.id.imgStatus);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.submissionStarted.setText(a.getSubmissionTimestamp());
		holder.jobDescription.setText(a.getUserDescription());
		if (a.getStatus() != null)
			if (a.getStatus().equals("DONE")) 
				holder.imgStatus.setImageResource(R.drawable.done);
			 else
				holder.imgStatus.setImageResource(R.drawable.run);
		return convertView;
	}
}
