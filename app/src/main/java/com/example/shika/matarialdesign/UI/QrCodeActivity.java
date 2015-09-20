package com.example.shika.matarialdesign.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.shika.matarialdesign.R;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.core.vcard.VCard;

import java.io.File;


public class QrCodeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        Bitmap myBitmap = QRCode
                .from("www.example.org").bitmap();
        ImageView myImage = (ImageView) findViewById(R.id.qrcode);
        //myImage.setImageBitmap(myBitmap);

        VCard mycard = new VCard("ahmed mahmoud nady");
        mycard.setEmail("am1560857@gmail.com");
        mycard.setAddress("cairo - egypt");
        mycard.setCompany("AMN");
        mycard.setPhoneNumber("01118097528");
        mycard.setWebsite("AMNANDROID.COM");
        mycard.setTitle("android developer");

        QRCode.from(mycard).withCharset("UTF-8").to(ImageType.JPG).stream();

        byte[] bytes = QRCode.from(mycard).withCharset("UTF-8").to(ImageType.JPG).stream().toByteArray();

        myImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qr_code, menu);
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
}
