package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity implements TransactionHistoryFragment
        .OnFragmentInteractionListener, FuturePaymentsFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory  sive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private TransactionAdapter adapter;
    private FuturePaymentAdapter paymentAdapter;
    private FuturePaymentsFragment future;
    public static MainProgressBarFragment progressBarFragment;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static FloatingActionsMenu fabMenu;

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
        mViewPager.setOffscreenPageLimit(2);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0 || position == 2) {
                    MainActivity.fabMenu.setVisibility(View.VISIBLE);
                }

                View progressView = MainActivity.progressBarFragment.getView();
                if (progressView != null) {
                    SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) progressView
                            .findViewById(R.id.sliding_layout);
                    if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState
                            .EXPANDED && position == 1) {
                        MainActivity.fabMenu.setVisibility(View.GONE);
                    }
                } else {
                    Log.i("info", "NULL");
                }
            }
        });

        adapter = new TransactionAdapter(this);
        paymentAdapter = new FuturePaymentAdapter(this);

        future = new FuturePaymentsFragment();
        future.setFuturePaymentAdapter(paymentAdapter);

        // Connect tab layout with ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        final LinearLayout fabLayout = (LinearLayout) findViewById(R.id.fab_container);
        fabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        final FloatingActionButton tfab = (FloatingActionButton) findViewById(R.id.add_transaction);
        final FloatingActionButton pfab = (FloatingActionButton) findViewById(R.id.schedule_payment);

        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                fabLayout.setClickable(true);
                fabLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fabMenu.collapse();
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                fabLayout.setClickable(false);
            }
        });

        tfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewTransactionDialog dialog = new NewTransactionDialog();
                dialog.show(getSupportFragmentManager(), "newTransaction");

                fabMenu.collapse();
            }
        });

        pfab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                NewPaymentScheduleDialog dialog = new NewPaymentScheduleDialog();
                dialog.show(getFragmentManager(), "newPayment");

                fabMenu.collapse();

            }
        });
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
            TransactionManager.clear();
            PaymentManager.clear();
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
                progressBarFragment = new MainProgressBarFragment();
                return progressBarFragment;
            } else {
                return future;
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