package in.co.poonam.expandablelistview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static in.co.poonam.expandablelistview.util.Constants.apple_device_type;
import static in.co.poonam.expandablelistview.util.Constants.deviceType;
import static in.co.poonam.expandablelistview.util.Constants.samsung_device_type;

public class MainActivity extends AppCompatActivity {

    private String[] items;
    private ExpandableListView mExpandableListView;
    private List<String> mExpandableListTitle;
    private Map<String, List<String>> mExpandableListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        mExpandableListView = findViewById(R.id.expandableList);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initItems();
        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        addItems();

    }


    private void initItems() {
        items = getResources().getStringArray(R.array.mobile_list_array);
    }

    private void addItems() {
        //Set adapter to expandable list view
        ExpandableListAdapter mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                // getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle(R.string.app_name);
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition))))
                        .get(childPosition).toString();
                // getSupportActionBar().setTitle(selectedItem);


                if (items[0].equals(mExpandableListTitle.get(groupPosition))) {
                    //click on Apple
                    String[] itemList = getResources().getStringArray(R.array.apple_array);
                    //click on apple sub items
                    if (selectedItem.equals(itemList[0])) {
                        Intent i = new Intent(MainActivity.this, ListItemDetailActivity.class);
                        i.putExtra(deviceType, apple_device_type);
                        startActivity(i);
                    } else if (selectedItem.equals(itemList[1])) {
                        Intent i = new Intent(MainActivity.this, ListItemDetailActivity.class);
                        i.putExtra(deviceType, apple_device_type);
                        startActivity(i);
                    } else if (selectedItem.equals(itemList[2])) {
                        Intent i = new Intent(MainActivity.this, ListItemDetailActivity.class);
                        i.putExtra(deviceType, apple_device_type);
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "Error for expandable list view", Toast.LENGTH_LONG).show();

                    }
                } else if (items[1].equals(mExpandableListTitle.get(groupPosition))) {
                    //click on Samsung
                    String[] itemList = getResources().getStringArray(R.array.samsung_array);
                    //click on samsung sub items
                    if (selectedItem.equals(itemList[0])) {
                        Intent i = new Intent(MainActivity.this, ListItemDetailActivity.class);
                        i.putExtra(deviceType, samsung_device_type);
                        startActivity(i);
                    } else if (selectedItem.equals(itemList[1])) {
                        Intent i = new Intent(MainActivity.this, ListItemDetailActivity.class);
                        i.putExtra(deviceType, samsung_device_type);
                        startActivity(i);
                    } else if (selectedItem.equals(itemList[2])) {
                        Intent i = new Intent(MainActivity.this, ListItemDetailActivity.class);
                        i.putExtra(deviceType, samsung_device_type);
                        startActivity(i);
                    } else if (selectedItem.equals(itemList[3])) {
                        Intent i = new Intent(MainActivity.this, ListItemDetailActivity.class);
                        i.putExtra(deviceType, samsung_device_type);
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "Error for expandable list view", Toast.LENGTH_LONG).show();
                    }
                } else {
                    throw new IllegalArgumentException("Not supported");
                }

                return false;
            }
        });
    }


}
