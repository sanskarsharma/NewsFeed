package com.dev.sanskar.newscgpioneer;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Blog_View extends AppCompatActivity {

    Toolbar toolbar;
    TextView blog_desc,blog_title;
    ImageView blog_image;

    DatabaseReference myref;
    String title,desc,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog__view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Blog_View.this.setTitle(null);



        blog_title =(TextView) findViewById(R.id.blog_heading);
        blog_desc =(TextView) findViewById(R.id.blog_desc);
        blog_image =(ImageView)findViewById(R.id.image_view);


       


        String post_key = getIntent().getExtras().getString("POST_KEY");
        String category_key = getIntent().getExtras().getString("CATEGORY_KEY");


        myref = FirebaseDatabase.getInstance().getReference(category_key+"/"+post_key);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title =  dataSnapshot.child("title1").getValue().toString();
                desc = dataSnapshot.child("timestamp").getValue().toString();
                image = dataSnapshot.child("image").getValue().toString();


                //Toast.makeText(getApplicationContext(), desc, Toast.LENGTH_SHORT).show();

                render(title,desc,image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }


    private void render(String title,String desc, String image){





        blog_title.setText(title);
        blog_desc.setText(desc);

        Picasso.with(getApplicationContext()).load(image).into(blog_image);


    }



}

