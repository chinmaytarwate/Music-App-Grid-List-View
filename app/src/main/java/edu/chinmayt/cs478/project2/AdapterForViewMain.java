package edu.chinmayt.cs478.project2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForViewMain extends RecyclerView.Adapter<AdapterForViewMain.ViewHolder> {
    private ArrayList<String> nameList; //data: the names displayed
    private ArrayList<String> bandList;
    private  ArrayList<Integer> imageList;
    private  ArrayList<String> urlsList;
    private  ArrayList<String> songWikiList;
    private  ArrayList<String> bandWikiList;

    public AdapterForViewMain(ArrayList<String> names, ArrayList<String> bands, ArrayList<Integer> images, ArrayList<String> URLS, ArrayList<String> songWiki,
                              ArrayList<String> bandWiki){
        //Setting all the list items to be used for the view
        nameList = names;
        bandList = bands;
        imageList = images;
        urlsList = URLS;
        songWikiList = songWiki;
        bandWikiList = bandWiki;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflating recycler view from xml file
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view); //Calling ViewHolder and passing view as parameter
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Setting Text and Images to be displayed in each view created
        holder.name.setText(nameList.get(position));
        holder.bands.setText(bandList.get(position));
        holder.image.setImageResource(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {
        public TextView name;
        public TextView bands;
        public ImageView image;
        private View itemView;

        public ViewHolder(@NonNull View itemView/*, RVClickListener passedListener*/) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView1);
            bands = (TextView) itemView.findViewById(R.id.textView2);
            image = (ImageView) itemView.findViewById(R.id.imgView);

            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this); //set context menu for each list item (long click)
            //this.getAdapterPosition();
            itemView.setOnClickListener(this); //set short click listener
        }

        @Override
        public void onClick(View v) {
            Context context1 = v.getContext();
            Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlsList.get(getAdapterPosition())));
            // Starting Browser activity in default browser along with the URL for the specific song
            context1.startActivity(videoIntent);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Context context2 = v.getContext();
            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.wiki_menu,menu); // Inflating the context menu options on long click
            //Setting view for each index to create Context in onMenuItemClick method
            menu.getItem(0).setActionView(v);
            menu.getItem(0).setOnMenuItemClickListener(onMenu); //Setting click listener for menu items
            menu.getItem(1).setActionView(v);
            menu.getItem(1).setOnMenuItemClickListener(onMenu);
            menu.getItem(2).setActionView(v);
            menu.getItem(2).setOnMenuItemClickListener(onMenu);
        }

        private final MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                //Getting Context from View set for each index above
                Context contextForIntents = item.getActionView().getContext();
                switch (item.getItemId()) {
                    case R.id.view_song:
                        int songPos = getAdapterPosition(); //Getting position for each item
                        Intent vidIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlsList.get(songPos)));
                        //Starting video on clicking view video on the menu
                        contextForIntents.startActivity(vidIntent);
                        break;
                    case R.id.wiki_song:
                        int songWikiPos = getAdapterPosition(); //Getting position for each item
                        Intent songWikiIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(songWikiList.get(songWikiPos)));
                        //Opening Wikipedia page for the song on clicking View Song's Wiki on the menu
                        contextForIntents.startActivity(songWikiIntent);
                        break;
                    case R.id.band_wiki:
                        int bandWikiPos = getAdapterPosition();
                        Intent bandWikiIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bandWikiList.get(bandWikiPos)));
                        //Opening Wikipedia page for the song on clicking View Band's Wiki on the menu
                        contextForIntents.startActivity(bandWikiIntent);
                        break;
                }
                return true;
            }
        };

    }

}

