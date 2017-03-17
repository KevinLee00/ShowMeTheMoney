package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements TransactionHistoryFragment
        .OnFragmentInteractionListener, FuturePaymentsFragment.OnFragmentInteractionListener {
    private TransactionAdapter adapter;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);

        // Connect tab layout with ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        final FloatingActionButton tfab = (FloatingActionButton) findViewById(R.id.add_transaction);
        tfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Add Transaction", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        final FloatingActionButton pfab = (FloatingActionButton) findViewById(R.id
                .schedule_payment);
        pfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Schedule Payment", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        adapter = new TransactionAdapter(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            if (position == 0) {
                TransactionHistoryFragment transactionHistoryFragment =
                        TransactionHistoryFragment.newInstance();
                transactionHistoryFragment.setTransactionAdapter(adapter);
                return transactionHistoryFragment;
            } else if (position == 1) {
                return new MainProgressBarFragment();
            } else {
                return new FuturePaymentsFragment();
            }

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
                    return "Transaction History";
                case 1:
                    return "Summary";
                case 2:
                    return "Schedule Transactions";
            }
            return null;
        }
    }
}