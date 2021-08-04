package edu.chinmayt.cs478.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> songs;
    ArrayList<String> songArtists;
    ArrayList<Integer> imgs;
    ArrayList<String> urls;
    ArrayList<String> songWikiList;
    ArrayList<String> bandWikiList;
    private RecyclerView mainView;
    //Default values for views
    public boolean gridVal = false;
    public boolean listVal = false;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Layout from xml file
        //Setting up recycler view with the recycler_view_item.xml
        mainView = (RecyclerView) findViewById(R.id.recyclerView);

        //Getting all the strings for Song names, artists, URLs, Wikipedia URLs from strings.xml in ArrayList
        List<String> songNames = Arrays.asList(getResources().getStringArray(R.array.song_names));
        songs = new ArrayList<>(songNames);
        List<String> artists = Arrays.asList(getResources().getStringArray(R.array.artists));
        songArtists = new ArrayList<>(artists);

        //Creating list for all the thumbnails of songs
        List<Integer> songImages = Arrays.asList(R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,
                R.drawable.img_4,R.drawable.img_5,R.drawable.img_6,R.drawable.img_7,R.drawable.img_8,
                R.drawable.img_9, R.drawable.img_10); // Getting images from Drawable folder
        imgs = new ArrayList<>(songImages);
        List<String> songURLS = Arrays.asList(getResources().getStringArray(R.array.song_urls));
        urls = new ArrayList<>(songURLS);
        List<String> songWiki = Arrays.asList(getResources().getStringArray(R.array.songWiki));
        songWikiList = new ArrayList<>(songWiki);
        List<String> bandWiki = Arrays.asList(getResources().getStringArray(R.array.bandWiki));
        bandWikiList = new ArrayList<>(bandWiki);

        //Calling Adapter class and passing all parameters required in a view
        AdapterForViewMain adapter = new AdapterForViewMain(songs, songArtists, imgs, urls,songWikiList, bandWikiList);
        mainView.setHasFixedSize(true);
        //Setting adapter
        mainView.setAdapter(adapter);
        //Checking for any saved instance if any configuration changes
        if(savedInstanceState == null) {
            //Setting default view as List view for the user
            mainView.setLayoutManager(new LinearLayoutManager(this));
            listVal = true;
        } else {
            //Getting values from saved instance to set up view same as before
            listVal = savedInstanceState.getBoolean("ListVAL");
            gridVal = savedInstanceState.getBoolean("GridVAL");
            //Log.i("onCreate", "onCreate: list val: "+ listVal + "grid: " +gridVal);

            //Restoring earlier view
            if (listVal == true) {
                mainView.setLayoutManager(new LinearLayoutManager(this));
                listVal = true; //Setting current view to list
            } else {
                mainView.setLayoutManager(new GridLayoutManager(this, 2));
                gridVal = true; // setting current view to grid
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu, menu);//Inflating menu on ActionBar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //RecyclerView mainView = (RecyclerView) findViewById(R.id.recyclerView);
        switch (item.getItemId()){
            case R.id.list:
                if(gridVal == true) {
                    //Setting Layout to List when List view is clicked and current view is Grid view
                    layoutManager = new LinearLayoutManager(this);
                    mainView.setLayoutManager(layoutManager);
                    gridVal = false;
                    listVal = true;
                }
                break;
            case R.id.grid:
                if(listVal == true) {
                    //Setting Layout to Grid when Grid view is clicked and current view is List view
                    layoutManager = new GridLayoutManager(this, 2);
                    mainView.setLayoutManager(layoutManager);
                    gridVal = true;
                    listVal = false;
                }
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Saving current state status of view displayed
        boolean listV = listVal;
        boolean gridV = gridVal;
        //Log.i("onSave", "onSaveInstanceState: listV:" + listV + "gridV: "+ gridV);
        outState.putBoolean("ListVAL", listV);
        outState.putBoolean("GridVAL", gridV);
    }

}