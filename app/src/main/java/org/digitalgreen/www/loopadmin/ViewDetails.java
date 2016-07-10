package org.digitalgreen.www.loopadmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.digitalgreen.www.loopadmin.Adapters.ViewAggregatorAdapter;
import org.digitalgreen.www.loopadmin.Adapters.ViewMandiAdapter;
import org.digitalgreen.www.loopadmin.Adapters.ViewVillageAdapter;
import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.Models.Block;
import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.Models.LoopUser;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.Models.Village;

import java.util.List;

public class ViewDetails extends AppCompatActivity { // implements ViewMandiAdapter.OnViewMandiEditClickListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.view_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.view_tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GeneralConstants.MANDI_EDIT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(ViewDetails.this, "The mandi has been successfully Edited!! ", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == GeneralConstants.VILLAGE_EDIT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(ViewDetails.this, "The Village has been successfully Edited!! ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            ExpandableListView view_list;
            FloatingActionButton view_add;
            View rootView = inflater.inflate(R.layout.fragment_view_details, container, false);

            int temp = getArguments().getInt(ARG_SECTION_NUMBER);

            if (temp == 1) {
                List<Mandi> mandiList = new Mandi().getAllMandis();
                view_list = (ExpandableListView) rootView.findViewById(R.id.view_list);

                ViewMandiAdapter viewMandiAdapter = new ViewMandiAdapter(mandiList, getContext(), new ViewMandiAdapter.OnViewMandiEditClickListener() {
                    @Override
                    public void onEditClick(Mandi mandi) {
                        Intent intent = new Intent(getActivity(), AddMandi.class);
                        intent.putExtra("mandi_id", mandi.getId());
                        startActivityForResult(intent, GeneralConstants.MANDI_EDIT_REQUEST);
                    }
                });
                view_list.setAdapter(viewMandiAdapter);

                view_add = (FloatingActionButton) rootView.findViewById(R.id.view_add);
                view_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), AddMandi.class);
                        startActivity(i);
                    }
                });
            } else if (temp == 2) {
                List<Block> blockList = new Block().getAllBlocks();
                view_list = (ExpandableListView) rootView.findViewById(R.id.view_list);

                ViewVillageAdapter viewVillageAdapter = new ViewVillageAdapter(blockList, getContext(), new ViewVillageAdapter.OnViewVillageEditClickListener() {
                    @Override
                    public void onEditClick(Village village) {
                        Intent intent = new Intent(getActivity(), AddVillage.class);
                        intent.putExtra("village_id", village.getId());
                        startActivityForResult(intent, GeneralConstants.VILLAGE_EDIT_REQUEST);
                    }
                });
                view_list.setAdapter(viewVillageAdapter);

                view_add = (FloatingActionButton) rootView.findViewById(R.id.view_add);
                view_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), AddVillage.class);
                        startActivity(i);
                    }
                });
            } else if(temp==3){
                List<LoopUser> aggregatorList = new LoopUser().getAllAggregators();
                view_list = (ExpandableListView) rootView.findViewById(R.id.view_list);

                ViewAggregatorAdapter viewAggregatorAdapter = new ViewAggregatorAdapter(aggregatorList, getContext(), new ViewAggregatorAdapter.OnViewAggregatorEditClickListener() {
                    @Override

                    public void onEditClick(LoopUser loopUser) {
                        Intent intent = new Intent(getActivity(), AddAggregator.class);
                        intent.putExtra("aggregator_id", loopUser.getId());
                        startActivityForResult(intent, GeneralConstants.AGGREGATOR_EDIT_REQUEST);
                    }
                });
                view_list.setAdapter(viewAggregatorAdapter);

                view_add = (FloatingActionButton) rootView.findViewById(R.id.view_add);
                view_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), AddAggregator.class);
                        startActivity(i);
                    }
                });
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Mandi";
                case 1:
                    return "Village";
                case 2:
                    return "Aggregator";
            }
            return null;
        }
    }
}
