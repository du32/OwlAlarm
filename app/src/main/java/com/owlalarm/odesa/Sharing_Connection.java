package com.owlalarm.odesa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Sharing_Connection extends Intent {

    public Context context;


    public Sharing_Connection (Context context)
    {this.context=context;}


    public void Rate_App(){


        Intent Rate_App =new Intent(Intent.ACTION_VIEW);
        Rate_App.setData(Uri.parse("market://details?id="+context.getPackageName()));
        context.startActivities(new Intent[]{Rate_App});
    }


    public void Share_App(){


        Intent share =new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT,"Sharing_Connection"+"\n"+"http://"+context.getPackageName());
        Intent.createChooser(share,"Share App");
        context.startActivities(new Intent[]{share});
    }








}
