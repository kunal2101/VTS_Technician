package com.rtl.vts_technician.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rtl.vts_technician.Constants.Utility;
import com.rtl.vts_technician.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ActivityDetailListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, ArrayList<HashMap<String, String>>> _listDataChild;

	int[] colorArr = {Color.parseColor("#2ac6b1"), Color.parseColor("#edcc4d"), Color.parseColor("#eb7e62")};

	public ActivityDetailListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, ArrayList<HashMap<String, String>>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {

		//final String childText = (String) getChild(groupPosition, childPosition);
		final HashMap<String, String> map = (HashMap<String, String>) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_clild_list, null);
		}

		LinearLayout childTableHead = (LinearLayout)convertView.findViewById(R.id.childTableHead);
		TextView txtInstall    = (TextView) convertView.findViewById(R.id.txtInstall);
		TextView txtUninstall      = (TextView) convertView.findViewById(R.id.txtUninstall);
		TextView txtmaintain      = (TextView) convertView.findViewById(R.id.txtmaintain);
		TextView txtReplace     = (TextView) convertView.findViewById(R.id.txtReplace);
		TextView txttot  = (TextView) convertView.findViewById(R.id.txttot);
		TextView txtName 		= (TextView) convertView.findViewById(R.id.txtName);
		//For marquee
		TextView txtxxx = convertView.findViewById(R.id.txtxxx);
		txtxxx.setSelected(true);


		if(childPosition == 0){
			childTableHead.setVisibility(View.VISIBLE);
		}else{
			childTableHead.setVisibility(View.GONE);
		}

		txtInstall.setText(map.get("INSTALL"));
		txtmaintain.setText(map.get("MAINTAINCE"));
		txtReplace.setText(map.get("REPLACE"));
		txtUninstall.setText(map.get("UNINSTALL"));
		int total = Integer.parseInt(map.get("INSTALL"))+Integer.parseInt(map.get("MAINTAINCE"))+Integer.parseInt(map.get("REPLACE"))+Integer.parseInt(map.get("UNINSTALL"));
		txttot.setText(""+total);
		txtName.setText(map.get("TECH_NAME"));

		if(childPosition %2 ==0){
			/*txtInstall.setBackgroundColor(Color.parseColor("#b5e3e7"));
			txtUninstall.setBackgroundColor(Color.parseColor("#b5e3e7"));
			txtmaintain.setBackgroundColor(Color.parseColor("#b5e3e7"));
			txtReplace.setBackgroundColor(Color.parseColor("#b5e3e7"));
			txttot.setBackgroundColor(Color.parseColor("#b5e3e7"));
			txtName.setBackgroundColor(Color.parseColor("#b5e3e7"));*/
			txtInstall.setBackgroundColor(Color.parseColor("#EAFAF1"));
			txtUninstall.setBackgroundColor(Color.parseColor("#EAFAF1"));
			txtmaintain.setBackgroundColor(Color.parseColor("#EAFAF1"));
			txtReplace.setBackgroundColor(Color.parseColor("#EAFAF1"));
			txttot.setBackgroundColor(Color.parseColor("#EAFAF1"));
			txtName.setBackgroundColor(Color.parseColor("#EAFAF1"));
		}else{
			/*txtInstall.setBackgroundColor(Color.parseColor("#ffe8c7"));
			txtUninstall.setBackgroundColor(Color.parseColor("#ffe8c7"));
			txtmaintain.setBackgroundColor(Color.parseColor("#ffe8c7"));
			txtReplace.setBackgroundColor(Color.parseColor("#ffe8c7"));
			txttot.setBackgroundColor(Color.parseColor("#ffe8c7"));
			txtName.setBackgroundColor(Color.parseColor("#ffe8c7"));*/
			txtInstall.setBackgroundColor(Color.parseColor("#FEF5E7"));
			txtUninstall.setBackgroundColor(Color.parseColor("#FEF5E7"));
			txtmaintain.setBackgroundColor(Color.parseColor("#FEF5E7"));
			txtReplace.setBackgroundColor(Color.parseColor("#FEF5E7"));
			txttot.setBackgroundColor(Color.parseColor("#FEF5E7"));
			txtName.setBackgroundColor(Color.parseColor("#FEF5E7"));
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_header_list, null);
		}

		TextView txtSr 			= (TextView) convertView.findViewById(R.id.txtSr);
		TextView txtDate 	= (TextView) convertView.findViewById(R.id.txtDate);
		ImageView icon         		= (ImageView)convertView.findViewById(R.id.icon);
		View viewLine 				= (View)convertView.findViewById(R.id.viewLine);
		RelativeLayout btnlayout    = (RelativeLayout)convertView.findViewById(R.id.btnlayout);
		String[] headerArr = headerTitle.split("'");

		SimpleDateFormat HHmmFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

		SimpleDateFormat hhmmampmFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

		String arrTime  = Utility.parseDate(headerArr[1], HHmmFormat, hhmmampmFormat);

		txtSr.setText(headerArr[0]);
		txtDate.setText(arrTime);

		if (isExpanded) {
			icon.setImageResource(R.drawable.ic_arrow_up);
		} else {
			icon.setImageResource(R.drawable.ic_arrow_down);
		}

		if(groupPosition %2 == 0){
			/*txtSr.setBackgroundColor(Color.parseColor("#e9e4e4"));
			txtDate.setBackgroundColor(Color.parseColor("#e9e4e4"));
			btnlayout.setBackgroundColor(Color.parseColor("#e9e4e4"));*/
			txtSr.setBackgroundColor(Color.parseColor("#F2F3F4"));
			txtDate.setBackgroundColor(Color.parseColor("#F2F3F4"));
			btnlayout.setBackgroundColor(Color.parseColor("#F2F3F4"));
		}else{
			/*txtSr.setBackgroundColor(Color.parseColor("#f1f1f1"));
			txtDate.setBackgroundColor(Color.parseColor("#f1f1f1"));
			btnlayout.setBackgroundColor(Color.parseColor("#f1f1f1"));*/
			txtSr.setBackgroundColor(Color.parseColor("#ffffff"));
			txtDate.setBackgroundColor(Color.parseColor("#ffffff"));
			btnlayout.setBackgroundColor(Color.parseColor("#ffffff"));
		}

		Random random = new Random(); // or create a static random field...
		int colorString = colorArr[random.nextInt(colorArr.length)];
		viewLine.setBackgroundColor(colorString);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
