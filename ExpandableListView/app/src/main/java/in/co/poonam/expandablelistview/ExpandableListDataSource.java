package in.co.poonam.expandablelistview;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ExpandableListDataSource {

    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListData = new TreeMap<>();

        List<String> mobileList = Arrays.asList(context.getResources().getStringArray(R.array.mobile_list_array));

        List<String> appleList = Arrays.asList(context.getResources().getStringArray(R.array.apple_array));
        List<String> sansungList = Arrays.asList(context.getResources().getStringArray(R.array.samsung_array));

        expandableListData.put(mobileList.get(0), appleList);
        expandableListData.put(mobileList.get(1), sansungList);

        return expandableListData;
    }

}
