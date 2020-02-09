package in.co.poonam.expandablelistview;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.co.poonam.expandablelistview.adapter.ListAdapter;
import in.co.poonam.expandablelistview.db.AppDatabase;
import in.co.poonam.expandablelistview.model.DataModel;

import static in.co.poonam.expandablelistview.util.Constants.apple_device_type;
import static in.co.poonam.expandablelistview.util.Constants.deviceType;

public class ListItemDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String device_type;
    private ListAdapter myAdapter;
    private LinearLayout emptyLayout;
    private List<DataModel> list;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_detail);
        Intent i = getIntent();
        device_type = i.getStringExtra(deviceType);
        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(device_type);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        emptyLayout = findViewById(R.id.emptyLayout);
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new ListAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(myAdapter);
        getDataFromDatabse();
    }

    private void getDataFromDatabse() {
        if(list.size()<=0){
            mShimmerViewContainer.startShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }

        @SuppressLint("StaticFieldLeak")
        class GetDeviceInfo extends AsyncTask<Void, Void, List<DataModel>> {

            @Override
            protected List<DataModel> doInBackground(Void... voids) {
                List<DataModel> list = AppDatabase
                        .getAppDatabase(getApplicationContext())
                        .deviceDao()
                        .findByDevice(device_type);

                return list;
            }

            @Override
            protected void onPostExecute(List<DataModel> deviceList) {
                super.onPostExecute(deviceList);
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if (deviceList.size() > 0) {
                    emptyLayout.setVisibility(View.GONE);
                    list.clear();
                    list.addAll(deviceList);
                    myAdapter.notifyDataSetChanged();
                } else {
                    showEmptyView();
                }
            }
        }

        GetDeviceInfo gt = new GetDeviceInfo();
        gt.execute();
    }

    private void showEmptyView() {
        emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuAddDeviceInfo:
                if (device_type.equals(apple_device_type)) {
                    //Add apple devices
                    addAppleDevice();
                } else {
                    //Add samsung devices
                    addSamsungDevice();
                }
                break;
            case R.id.menuSortDateIncreasing:
                if (list.size() > 0) {
                    sortByDate(true);
                } else {
                    Toast.makeText(this, "Device information not available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menuSortDateDecreasing:
                if (list.size() > 0) {
                    sortByDate(false);
                } else {
                    Toast.makeText(this, "Device information not available.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menuSortQuantityIncreasing:
                if (list.size() > 0) {
                    sortByQuantity(true);
                } else {
                    Toast.makeText(this, "Device information not available.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menuSortQuantityDecreasing:
                if (list.size() > 0) {
                    sortByQuantity(false);
                } else {
                    Toast.makeText(this, "Device information not available.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menuSortPopularityIncreasing:
                if (list.size() > 0) {
                    sortByPopularity(true);
                } else {
                    Toast.makeText(this, "Device information not available.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menuSortPopularityDecreasing:
                if (list.size() > 0) {
                    sortByPopularity(false);
                } else {
                    Toast.makeText(this, "Device information not available.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.menuDeleteDeviceInfo:
                if (list.size() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Do you want to delete devices information").setTitle("Confirmation");
                    builder.setMessage("Do you want to close this application ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    clearDatabase();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Confirmation");
                    alert.show();
                } else {
                    Toast.makeText(this, "Device information not available.", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void clearDatabase() {
        class ClearInfo extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase.getAppDatabase(getApplicationContext())
                        .deviceDao()
                        .clearTable(device_type);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                list.clear();
                myAdapter.notifyDataSetChanged();
                showEmptyView();
                Toast.makeText(getApplicationContext(), "Deleted Successfully.", Toast.LENGTH_LONG).show();

            }
        }

        ClearInfo dt = new ClearInfo();
        dt.execute();

    }


    private void sortByPopularity(final boolean isIncreasing) {
        @SuppressLint("StaticFieldLeak")
        class SortByQuantity extends AsyncTask<Void, Void, List<DataModel>> {

            @Override
            protected List<DataModel> doInBackground(Void... voids) {
                List<DataModel> list;
                if (isIncreasing) {
                    list = AppDatabase
                            .getAppDatabase(getApplicationContext())
                            .deviceDao()
                            .sortByPopularityIncreasing(device_type);
                } else {
                    list = AppDatabase
                            .getAppDatabase(getApplicationContext())
                            .deviceDao()
                            .sortByPopularityDecreasing(device_type);
                }

                return list;
            }

            @Override
            protected void onPostExecute(List<DataModel> deviceList) {
                super.onPostExecute(deviceList);
                list.clear();
                list.addAll(deviceList);
                myAdapter.notifyDataSetChanged();
            }
        }

        SortByQuantity gt = new SortByQuantity();
        gt.execute();
    }

    private void sortByQuantity(final boolean isIncreasing) {
        @SuppressLint("StaticFieldLeak")
        class SortByQuantity extends AsyncTask<Void, Void, List<DataModel>> {

            @Override
            protected List<DataModel> doInBackground(Void... voids) {
                List<DataModel> list;
                if (isIncreasing) {
                    list = AppDatabase
                            .getAppDatabase(getApplicationContext())
                            .deviceDao()
                            .sortByQuantityIncreasing(device_type);
                } else {
                    list = AppDatabase
                            .getAppDatabase(getApplicationContext())
                            .deviceDao()
                            .sortByQuantityDecreasing(device_type);
                }

                return list;
            }

            @Override
            protected void onPostExecute(List<DataModel> deviceList) {
                super.onPostExecute(deviceList);
                list.clear();
                list.addAll(deviceList);
                myAdapter.notifyDataSetChanged();
            }
        }

        SortByQuantity gt = new SortByQuantity();
        gt.execute();
    }

    private void sortByDate(final boolean isIncreasing) {
        @SuppressLint("StaticFieldLeak")
        class SortByQuantity extends AsyncTask<Void, Void, List<DataModel>> {

            @Override
            protected List<DataModel> doInBackground(Void... voids) {
                List<DataModel> list;
                if (isIncreasing) {
                    list = AppDatabase
                            .getAppDatabase(getApplicationContext())
                            .deviceDao()
                            .sortByDateIncreasing(device_type);
                } else {
                    list = AppDatabase
                            .getAppDatabase(getApplicationContext())
                            .deviceDao()
                            .sortByDateDecreasing(device_type);
                }

                return list;
            }

            @Override
            protected void onPostExecute(List<DataModel> deviceList) {
                super.onPostExecute(deviceList);
                list.clear();
                list.addAll(deviceList);
                myAdapter.notifyDataSetChanged();
            }
        }

        SortByQuantity gt = new SortByQuantity();
        gt.execute();
    }

    private void addSamsungDevice() {
        final String[] deviceTitle = new String[]{"S7", "S8", "S9", "S10", "Note 7", "Note 8", "Note9"};
        final int[] quantity = new int[]{10000, 15000, 20000, 280000, 27000, 21000, 30000};
        final float rating[] = new float[]{3.0f, 4.0f, 5.0f, 2.5f, 5.0f, 4.5f, 5.0f};
        final long[] date = new long[]{981484200, 1551897000, 1583519400, 1525631400, 1462559400, 1525631400, 1551897000};
        final String[] imagePath = new String[]{"https://i.ibb.co/L1M7mJC/samasungs5.jpg",
                "https://i.ibb.co/JrykMKj/galaxy-note.jpg", "https://i.ibb.co/WFbhdFV/galaxyA7.jpg",
                "https://i.ibb.co/S3bWDq4/galaxyS9.jpg", "https://i.ibb.co/7rj6jCG/samsung-galaxy-A60.jpg",
                "https://i.ibb.co/Vv8S1Fp/galaxy-A51.jpg", "https://i.ibb.co/tzj98NP/galaxy-A20.jpg"};
        for (int i = 0; i < deviceTitle.length; i++) {
            final int finalI = i;
            class SaveInfo extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {

                    DataModel dataModel = new DataModel();
                    dataModel.setDevice_type(device_type);
                    dataModel.setDeviceQuantity(quantity[finalI]);
                    dataModel.setDeviceTitle(deviceTitle[finalI]);
                    dataModel.setRating(rating[finalI]);
                    dataModel.setInventoryDate(date[finalI]);
                    dataModel.setImage_path(imagePath[finalI]);
                    //adding to database
                    AppDatabase.getAppDatabase(getApplicationContext())
                            .deviceDao()
                            .insertAll(dataModel);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(getApplicationContext(), "Saved Device Information", Toast.LENGTH_LONG).show();
                }
            }

            SaveInfo st = new SaveInfo();
            st.execute();
            getDataFromDatabse();
        }
    }

    private void addAppleDevice() {
        final String[] deviceTitle = new String[]{"Iphone6", "Iphone6s", "Iphone7", "Iphone7", "Iphone8", "Iphone X", "IphoneXR"};
        final int[] quantity = new int[]{10000, 15000, 20000, 280000, 27000, 21000, 30000};
        final float[] rating = new float[]{3.0f, 4.0f, 5.0f, 2.5f, 5.0f, 4.5f, 5.0f};
        final long[] date = new long[]{981484200, 1551897000, 1583519400, 1525631400, 1462559400, 1525631400, 1551897000};
        final String[] imagePath = new String[]{"https://i.ibb.co/8g6mNSr/iphoneXR.jpg",
                "https://i.ibb.co/NshqyWj/iphone11-Pro.jpg", "https://i.ibb.co/JW1CzWZ/iphone7.jpg",
                "https://i.ibb.co/FwBfg0C/iphone8.jpg", "https://i.ibb.co/Ld80h7L/iphoneS5.jpg",
                "https://i.ibb.co/7rfMbqy/iphone-SE2.jpg", "https://i.ibb.co/r75tpcC/phones6.jpg"};

        for (int i = 0; i < deviceTitle.length; i++) {


            final int finalI = i;
            class SaveInfo extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {

                    //creating a task
                    DataModel dataModel = new DataModel();
                    dataModel.setDevice_type(device_type);
                    dataModel.setDeviceQuantity(quantity[finalI]);
                    dataModel.setDeviceTitle(deviceTitle[finalI]);
                    dataModel.setRating(rating[finalI]);
                    dataModel.setInventoryDate(date[finalI]);
                    dataModel.setImage_path(imagePath[finalI]);
                    //adding to database
                    AppDatabase.getAppDatabase(getApplicationContext())
                            .deviceDao()
                            .insertAll(dataModel);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(getApplicationContext(), "Saved Device Information", Toast.LENGTH_LONG).show();
                }
            }

            SaveInfo st = new SaveInfo();
            st.execute();
            getDataFromDatabse();
        }
    }
}
