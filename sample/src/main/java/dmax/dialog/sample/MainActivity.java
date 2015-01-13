package dmax.dialog.sample;

import android.app.Activity;
import android.os.Bundle;

import dmax.dialog.SpotsDialog;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 13.01.15 at 14:32
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        new SpotsDialog(this).show();
        new SpotsDialog(this, R.style.Custom).show();
    }
}
