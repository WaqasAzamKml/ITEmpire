package com.example.mirsaab.itempire2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    WebView myWebView;
    ProgressBar progressBar;
    public String currentURL  = "";
    private SwipeRefreshLayout mySwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myWebView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new AppWebViewClient(progressBar));
        myWebView.loadUrl("http://itempire.net");

        mySwipe = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refresh_layout);
        mySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                //fetchTimelineAsync(0);

                mySwipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable (){
                    @Override
                    public void run(){
                        mySwipe.setRefreshing(false);
                        currentURL = myWebView.getUrl();
                        myWebView.loadUrl(currentURL);
                    }
                },4000);
            }
        });
        mySwipe.setColorSchemeResources(android.R.color.holo_blue_light, R.color.colorAccent, R.color.colorPrimaryDark);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            myWebView.loadUrl("http://itempire.net");
        } else if (id == R.id.nav_about) {
            myWebView.loadUrl("http://itempire.net/about");
        } else if (id == R.id.nav_portfolio) {
            myWebView.loadUrl("http://itempire.net/portfolio");
        } else if (id == R.id.nav_gallery) {
            myWebView.loadUrl("http://itempire.net/loggia");
        } else if (id == R.id.nav_clients) {
            myWebView.loadUrl("http://itempire.net/clients");
        } else if (id == R.id.nav_trainings) {
            myWebView.loadUrl("http://itempire.net/skills");
        } else if (id == R.id.nav_forms) {
            myWebView.loadUrl("http://itempire.net/forms");
        } else if (id == R.id.nav_contact) {
            myWebView.loadUrl("http://itempire.net/contact");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class AppWebViewClient extends WebViewClient {
        private ProgressBar progressBar;


        public AppWebViewClient(ProgressBar progressBar) {
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.GONE);
            MainActivity.this.progressBar.setProgress(100);
            super.onPageFinished(view, url);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            MainActivity.this.progressBar.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }
    }
}
