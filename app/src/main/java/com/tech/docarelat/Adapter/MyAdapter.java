package com.tech.docarelat.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.tech.docarelat.Activity.CareGiver.CreateGiverDetailActivity;
import com.tech.docarelat.App.MySharedPref;
import com.tech.docarelat.R;
import com.tech.docarelat.Constent.StateVO;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<StateVO> {
    public static ArrayList<String> list;
    /* access modifiers changed from: private */
    public boolean isFromView = false;
    String language = "";
    private ArrayList<StateVO> listState;
    private String[] list_main = null;
    private Context mContext;
    private MyAdapter myAdapter;

    public MyAdapter(Context context, int i, List<StateVO> list2) {
        super(context, i, list2);
        this.mContext = context;
        this.listState = (ArrayList) list2;
        this.myAdapter = this;
        if (!MySharedPref.getData(context, "data", "").equals("yes")) {
            list = new ArrayList<>();
        }
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getCustomView(i, view, viewGroup);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return getCustomView(i, view, viewGroup);
    }

    public View getCustomView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.spinner_item, (ViewGroup) null);
            viewHolder = new ViewHolder();
            TextView unused = viewHolder.mTextView = (TextView) view.findViewById(R.id.text);
            CheckBox unused2 = viewHolder.mCheckBox = (CheckBox) view.findViewById(R.id.checkbox);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText(this.listState.get(i).getTitle());
        this.isFromView = true;
        viewHolder.mCheckBox.setChecked(this.listState.get(i).isSelected());
        this.isFromView = false;
        viewHolder.mCheckBox.setTag(Integer.valueOf(i));
        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                int intValue = ((Integer) compoundButton.getTag()).intValue();
                if (!MyAdapter.this.isFromView) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(!MyAdapter.this.isFromView);
                    String str = "";
                    sb.append(str);
                    Log.e("isFromView", sb.toString());
                    if (Boolean.valueOf(viewHolder.mCheckBox.isChecked()).booleanValue()) {
                        MyAdapter.this.language = CreateGiverDetailActivity.language_list[intValue];
                        if (MyAdapter.list.contains(MyAdapter.this.language)) {
                            MyAdapter.list.remove(MyAdapter.this.language);
                        } else {
                            MyAdapter.list.add(MyAdapter.this.language);
                            Log.e("Size", "------> " + MyAdapter.list.size());
                        }
                    } else {
                        MyAdapter.this.language = CreateGiverDetailActivity.language_list[intValue];
                        MyAdapter.list.remove(MyAdapter.this.language);
                        Log.e("Size", "------> " + MyAdapter.list.size());
                    }
                    if (MyAdapter.list != null) {
                        String str2 = str;
                        for (int i = 0; i < MyAdapter.list.size(); i++) {
                            if (str2.equalsIgnoreCase(str)) {
                                str2 = MyAdapter.list.get(i);
                            } else {
                                str2 = str2 + "," + MyAdapter.list.get(i);
                            }
                        }
                        str = str2;
                    }
                    CreateGiverDetailActivity.txt_lang.setVisibility(View.VISIBLE);
                    CreateGiverDetailActivity.txt_lang.setText(str);
                }
            }
        });
        return view;
    }

    private class ViewHolder {
        /* access modifiers changed from: private */
        public CheckBox mCheckBox;
        /* access modifiers changed from: private */
        public TextView mTextView;

        private ViewHolder() {
        }
    }
}
