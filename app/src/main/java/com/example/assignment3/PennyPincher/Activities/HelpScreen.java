package com.example.assignment3.PennyPincher.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Screen that shows how to play and sources cited
 */
public class HelpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);

        TextView resourceInfo = findViewById(R.id.resourcesInfo);
        String CITED_IMAGE_LINKS = (
                "https://www.cleanpng.com/png-canada-penny-debate-in-the-united-states-coin-cent-3490295/\n" +
                "https://pnghut.com/png/R2qRTuF1Cz/money-bag-united-states-dollar-image-transparent-png\n" +
                "pinterest.ca/pin/408349891195209253/\n" +
                "https://www.pngitem.com/pimgs/m/121-1219503_pinching-hand-emoji-png-transparent-png.png"
        );

        resourceInfo.setText(CITED_IMAGE_LINKS);
    }

    //make sure to finish when done

    /**
     * Creates the intent to start this activity
     * @param context Context of the current activity
     * @return The intent to start this activity
     */
    public static Intent makeIntent(Context context) {
        return new Intent(context, HelpScreen.class);
    }
}