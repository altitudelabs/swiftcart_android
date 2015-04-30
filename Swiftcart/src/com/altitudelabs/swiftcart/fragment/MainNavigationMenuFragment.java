package com.altitudelabs.swiftcart.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.altitudelabs.swiftcart.R;

public class MainNavigationMenuFragment extends Fragment {
	
	public static String MENU_TITLE_MY_ACCT = "My Account";
	public static String MENU_TITLE_FRESH_PRODUCE = "Fresh Produce";
	public static String MENU_TITLE_MEATS = "Meats";
	public static String MENU_TITLE_SNACKS = "Snack";
	public static String MENU_TITLE_DRY_GOODS = "Dry Goods";
	public static String MENU_TITLE_HOUSEHOLD = "Household";
	public static String MENU_TITLE_DAIRY = "Dairy";
	public static String MENU_TITLE_FROZEN_ITEMS = "Frozen Items";
	
	public static String MENU_SUBTITLE_FRUITS = "Vegetables";
	public static String MENU_SUBTITLE_VEGETABLES = "Fruits";
	
	private ExpandableListView mMenuList;
	private OnListItemClickListener mOnListItemClickListener;
	
	private int[] mMenuItemIcons = {
		R.drawable.ic_my_account, 
		R.drawable.ic_fresh_produce, 
		R.drawable.ic_meat, 
		R.drawable.ic_snack, 
		R.drawable.ic_dry_goods, 
		R.drawable.ic_household, 
		R.drawable.ic_dairy, 
		R.drawable.ic_frozen
		};

	private String[] mMenuItemGroupTitle = {MENU_TITLE_MY_ACCT, MENU_TITLE_FRESH_PRODUCE, MENU_TITLE_MEATS, MENU_TITLE_SNACKS, MENU_TITLE_DRY_GOODS, MENU_TITLE_HOUSEHOLD, MENU_TITLE_DAIRY, MENU_TITLE_FROZEN_ITEMS};
	private String[] mItemsOfFreshProduce = {MENU_SUBTITLE_FRUITS, MENU_SUBTITLE_VEGETABLES};
	
	// Test
	private HashMap<String, List<String>> mListData;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.main_navigation_menu, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mMenuList = (ExpandableListView) getActivity().findViewById(R.id.main_navigation_menu_list_view);
		
		// Make list
		mListData = new HashMap<String, List<String>>();
		
		mListData.put(mMenuItemGroupTitle[0], new ArrayList<String>());
		mListData.put(mMenuItemGroupTitle[1], new ArrayList<String>(Arrays.asList(mItemsOfFreshProduce)));
		mListData.put(mMenuItemGroupTitle[2], new ArrayList<String>());
		mListData.put(mMenuItemGroupTitle[3], new ArrayList<String>());
		mListData.put(mMenuItemGroupTitle[4], new ArrayList<String>());
		mListData.put(mMenuItemGroupTitle[5], new ArrayList<String>());
		mListData.put(mMenuItemGroupTitle[6], new ArrayList<String>());
		mListData.put(mMenuItemGroupTitle[7], new ArrayList<String>());
		
		mMenuList.setAdapter(new ExpandableListAdapter(getActivity(), Arrays.asList(mMenuItemGroupTitle), mListData));
		mMenuList.setOnGroupClickListener(new ListGroupClickListener());
		mMenuList.setOnChildClickListener(new ListChildClickListener());
		
//		mMenuList.expandGroup(1);
	}
	
	public class ExpandableListAdapter extends BaseExpandableListAdapter {
		 
	    private Context _context;
	    private List<String> _listDataHeader; // header titles
	    // child data in format of header title, child title
	    private HashMap<String, List<String>> _listDataChild;
	    
	    class ViewHolder {
	    	public TextView title;
	    	public ImageView icon;
	    	public ImageView expandedIcon;
	    }
	 
	    public ExpandableListAdapter(Context context, List<String> listDataHeader,
	            HashMap<String, List<String>> listChildData) {
	        this._context = context;
	        this._listDataHeader = listDataHeader;
	        this._listDataChild = listChildData;
	    }
	 
	    @Override
	    public Object getChild(int groupPosition, int childPosititon) {
	        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	                .get(childPosititon);
	    }
	 
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	 
	    @Override
	    public View getChildView(int groupPosition, final int childPosition,
	            boolean isLastChild, View convertView, ViewGroup parent) {
	 
	        final String childText = (String) getChild(groupPosition, childPosition);
	 
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this._context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.main_navigation_menu_list_item, null);
	        }
	 
	        TextView txtListChild = (TextView) convertView
	                .findViewById(R.id.main_navigation_menu_list_item_title);
	 
	        txtListChild.setText(childText);
	        return convertView;
	    }
	 
	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	                .size();
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
	        
	        ViewHolder viewHolder;
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this._context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.main_navigation_menu_list_group, null);
	            
	            viewHolder = new ViewHolder();
	            viewHolder.title = (TextView) convertView
		                .findViewById(R.id.main_navigation_menu_list_group_title);
	            viewHolder.icon = (ImageView) convertView.findViewById(R.id.main_navigation_menu_list_group_icon);
	            viewHolder.expandedIcon = (ImageView) convertView.findViewById(R.id.main_navigation_menu_arrow);
	            
	            convertView.setTag(viewHolder);
	        } else {
	        	viewHolder = (ViewHolder) convertView.getTag();
	        }
	        viewHolder.title.setText(headerTitle);
	        viewHolder.icon.setImageResource(mMenuItemIcons[groupPosition]);
	        viewHolder.expandedIcon.setVisibility(isExpanded && getChildrenCount(groupPosition) != 0? View.VISIBLE : View.GONE);
	        
	        if (groupPosition > 1) {
	        	viewHolder.title.setAlpha(0.3f);
	        	viewHolder.icon.setAlpha(0.3f);
//	        	viewHolder.title.setText(viewHolder.title.getText() + "  (Coming soon)");
	        } else {
	        	viewHolder.title.setAlpha(1.0f);
	        	viewHolder.icon.setAlpha(1.0f);
	        }
	 
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
	
	public class ListGroupClickListener implements OnGroupClickListener {

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			if (mOnListItemClickListener != null) {
				return mOnListItemClickListener.onGroupItemClicked(v, mMenuItemGroupTitle[groupPosition], groupPosition);
			}
			return false;
		}
	}
	
	public class ListChildClickListener implements OnChildClickListener {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			if (mOnListItemClickListener != null) {
				ArrayList<String> childItems = (ArrayList<String>)mListData.get(mMenuItemGroupTitle[groupPosition]);
				String itemTitle = null;
				if (childItems != null) {
					itemTitle = (String) childItems.get(childPosition);
				}
				mOnListItemClickListener.onChildItemClicked(v, itemTitle, groupPosition, childPosition);
			}
			return true;
		}
	}
	
	public interface OnListItemClickListener {
		boolean onGroupItemClicked(View v, String itemTitle, int groupItemIndex);
		void onChildItemClicked(View v, String itemTitle, int groupItemIndex, int childItemIndex);
	}
	
	public void setOnListItemClickListener(OnListItemClickListener listener) {
		mOnListItemClickListener = listener;
	}
	
	public OnListItemClickListener getOnListItemClickListener() {
		return mOnListItemClickListener;
	}
}
