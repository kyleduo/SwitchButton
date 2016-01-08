package com.kyleduo.switchbutton.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 16/1/8.
 */
public class RecyclerActivity extends AppCompatActivity {

	private RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler);

		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		SwitchRecyclerAdapter adapter = new SwitchRecyclerAdapter();
		mRecyclerView.setAdapter(adapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
	}

	private class SwitchViewHolder extends RecyclerView.ViewHolder {

		TextView tv;
		SwitchButton sb;

		public SwitchViewHolder(View itemView) {
			super(itemView);
			tv = (TextView) itemView.findViewById(R.id.recycler_item_tv);
			sb = (SwitchButton) itemView.findViewById(R.id.recycler_item_sb);
		}
	}

	private class SwitchRecyclerAdapter extends RecyclerView.Adapter<SwitchViewHolder> {

		private List<Boolean> mSbStates;

		public SwitchRecyclerAdapter() {
			mSbStates = new ArrayList<>(getItemCount());
			for (int i = 0; i < getItemCount(); i++) {
				mSbStates.add(false);
			}
		}

		@Override
		public SwitchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_item, parent, false);
			return new SwitchViewHolder(v);
		}

		@Override
		public void onBindViewHolder(SwitchViewHolder holder, final int position) {
			holder.sb.setOnCheckedChangeListener(null);
			holder.sb.setCheckedImmediately(mSbStates.get(position));
			holder.tv.setText("SwitchButton can be used in RecyclerView.");
			holder.sb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					mSbStates.set(position, isChecked);
				}
			});
		}

		@Override
		public int getItemCount() {
			return 30;
		}
	}
}
