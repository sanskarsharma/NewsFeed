package com.dev.sanskar.newscgpioneer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.yalantis.phoenix.PullToRefreshView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mBlogList;
    private LinearLayoutManager linlayMan;
    private String LIST_STATE_KEY = "LSK";
    private static Parcelable listState;
    private static int pos,top;

    public static MenuItem cat_no ;


    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    private static DatabaseReference mDatabase,mDatabase2;

    private FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter;

    PullToRefreshView mPullToRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_papa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("Home");


        //navigation drawer shit starts
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //navigation drawer shit ends

        mDatabase= FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Sports");

        mBlogList = (RecyclerView)findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(false);
        linlayMan = new LinearLayoutManager(this);
        linlayMan.setOrientation(LinearLayoutManager.VERTICAL);
        linlayMan.canScrollVertically();

        mBlogList.setLayoutManager(linlayMan);


        final int REFRESH_DELAY = 1000;
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });


    }

    // for nav drawer shit
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(MainActivity.this.getTitle() != "Home"){
            MainActivity.this.setTitle("Home");

            mBlogList.getRecycledViewPool().clear();
            adaptFire(mDatabase);


        }
        else {
            if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                super.onBackPressed();
                } else {
                Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
                }
            back_pressed = System.currentTimeMillis();
        }
    }



    @Override
    protected void onStart() {
        super.onStart();

        adaptFire(mDatabase);

    }


    private void adaptFire(DatabaseReference mDB){

        //mBlogList.getRecycledViewPool().clear();


       firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDB
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, final int position) {

                final String post_key = getRef(position).getKey();
                final String category_key = getRef(position).getParent().getKey();

                viewHolder.setTitleo(model.getTitle());
                viewHolder.setDescrip(model.getDesc());
                viewHolder.setPicasso(getApplicationContext(),model.getImage());


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        pos = linlayMan.findFirstVisibleItemPosition();
                        View vo = linlayMan.getChildAt(0);
                        top = (vo == null) ? 0 : (vo.getTop() - linlayMan.getPaddingTop());


                        //Bundle boo= new Bundle();
                        //onSaveInstanceState(boo);
                        Intent intobj = new Intent(MainActivity.this,Blog_View.class);
                        intobj.putExtra("POST_KEY",post_key);
                        intobj.putExtra("CATEGORY_KEY",category_key);

                        startActivity(intobj);

                    }
                });
            }
       };

        mBlogList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();





    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        cat_no = menuItem;

        switch (menuItem.getItemId()) {
            case R.id.nav_home:

                MainActivity.this.setTitle("Home");

                mBlogList.getRecycledViewPool().clear();
               adaptFire(mDatabase);

                break;

            case R.id.nav_chattisgarh:


                MainActivity.this.setTitle("Chhatisgarh");
                mBlogList.getRecycledViewPool().clear();


                adaptFire(mDatabase2);





                break;

            case R.id.nav_india:

                mBlogList.getRecycledViewPool().clear();

                adaptFire(mDatabase);
                break;

            case R.id.nav_international:

                mBlogList.getRecycledViewPool().clear();
                adaptFire(mDatabase2);

                break;

            case R.id.nav_sports:

                mBlogList.getRecycledViewPool().clear();
                adaptFire(mDatabase);

                break;

            case R.id.nav_entertainment:

                mBlogList.getRecycledViewPool().clear();
                adaptFire(mDatabase2);

                break;

            case R.id.nav_politics:

                mBlogList.getRecycledViewPool().clear();
                adaptFire(mDatabase);

                break;

            case R.id.nav_bookmarks:


                break;

            case R.id.nav_polls:


                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);




        return true;
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView= itemView;


        }

        public void setTitleo(String title){

            TextView post_title= (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);


        }

        public void setDescrip(String descrip){

            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(descrip);

        }

        public void setPicasso(Context ctx,String picture){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(picture).into(post_image);



        }


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




    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(state);

        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_STATE_KEY, linlayMan.onSaveInstanceState());

        Toast.makeText(getApplicationContext(), "onsaveinstancestate",Toast.LENGTH_SHORT).show();
        //listState = linlayMan.onSaveInstanceState();
        //state.putParcelable(LIST_STATE_KEY, listState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        if(state != null)
        {
            Parcelable savedRecyclerLayoutState = state.getParcelable(LIST_STATE_KEY);
            linlayMan.onRestoreInstanceState(savedRecyclerLayoutState);
        }

        Toast.makeText(getApplicationContext(), "onrestoreeeeeeeeEEEEEinstancestate", Toast.LENGTH_SHORT).show();

       // listState = state.getParcelable(LIST_STATE_KEY);
    }


    @Override
    protected void onResume() {
        super.onResume();

        try {
            onNavigationItemSelected(cat_no);
        }catch (Exception e){
            //DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout);
            //drawer1.openDrawer(GravityCompat.START);

        }



    }







}



