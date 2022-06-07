package com.tech.docarelat.autoaddress;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.google.android.gms.plus.PlusShare;
import com.tech.docarelat.R;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeoAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private Activity context;
    /* access modifiers changed from: private */

    /* renamed from: l2 */
    public List<String> f166l2 = new ArrayList();
    /* access modifiers changed from: private */
    public String lat;
    private LayoutInflater layoutInflater;
    /* access modifiers changed from: private */
    public String lon;
    /* access modifiers changed from: private */

    /* renamed from: wo */
    public WebOperations f167wo;

    public long getItemId(int i) {
        return (long) i;
    }

    public GeoAutoCompleteAdapter(Activity activity, List<String> list, String str, String str2) {
        this.context = activity;
        this.f166l2 = list;
        this.lat = str;
        this.lon = str2;
        this.layoutInflater = LayoutInflater.from(activity);
        this.f167wo = new WebOperations(activity);
    }

    public int getCount() {
        return this.f166l2.size();
    }

    public Object getItem(int i) {
        return this.f166l2.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = this.layoutInflater.inflate(R.layout.geo_search_result, viewGroup, false);
        try {
            ((TextView) inflate.findViewById(R.id.geo_search_result_text)).setText(this.f166l2.get(i));
        } catch (Exception unused) {
        }
        return inflate;
    }

    public Filter getFilter() {
        return new Filter() {
            /* access modifiers changed from: protected */
            public FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence != null) {
                    WebOperations access$200 = GeoAutoCompleteAdapter.this.f167wo;
                    access$200.setUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyATpFdt23Ga2BvfNtYVcYSPSdrabNcdexo&input=" + charSequence.toString().trim().replaceAll(" ", "+") + "&location=" + GeoAutoCompleteAdapter.this.lat + "," + GeoAutoCompleteAdapter.this.lon + "+&radius=20000&types=geocode&sensor=true");
                    PrintStream printStream = System.out;
                    printStream.println("URL***https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyATpFdt23Ga2BvfNtYVcYSPSdrabNcdexo&input=" + charSequence.toString().trim().replaceAll(" ", "+") + "&location=" + GeoAutoCompleteAdapter.this.lat + "," + GeoAutoCompleteAdapter.this.lon + "+&radius=20000&types=geocode&sensor=true");
                    String str = null;
                    try {
                        str = (String) new MyTask(GeoAutoCompleteAdapter.this.f167wo, 3).execute(new String[0]).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e2) {
                        e2.printStackTrace();
                    }
                    GeoAutoCompleteAdapter.this.parseJson(str);
                    PrintStream printStream2 = System.out;
                    printStream2.println("FilterResults===============================" + str);
                    Log.e("Location===========", "Come" + str);
                    filterResults.values = GeoAutoCompleteAdapter.this.f166l2;
                    filterResults.count = GeoAutoCompleteAdapter.this.f166l2.size();
                }
                return filterResults;
            }

            /* access modifiers changed from: protected */
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                PrintStream printStream = System.out;
                printStream.println("publishResults===============================" + filterResults);
                if (filterResults == null || filterResults.count == 0) {
                    GeoAutoCompleteAdapter.this.notifyDataSetInvalidated();
                    return;
                }
                List unused = GeoAutoCompleteAdapter.this.f166l2 = (List) filterResults.values;
                GeoAutoCompleteAdapter.this.notifyDataSetChanged();
            }
        };
    }

    /* access modifiers changed from: private */
    public void parseJson(String str) {
        try {
            this.f166l2 = new ArrayList();
            JSONObject jSONObject = new JSONObject(str);
            Log.e("Check this GACA jk", ">>>" + jSONObject);
            JSONArray jSONArray = jSONObject.getJSONArray("predictions");
            for (int i = 0; i < jSONArray.length(); i++) {
                this.f166l2.add(jSONArray.getJSONObject(i).getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Address> findLocations(Context context2, String str) {
        new ArrayList();
        try {
            return new Geocoder(context2, context2.getResources().getConfiguration().locale).getFromLocationName(str, 15);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
