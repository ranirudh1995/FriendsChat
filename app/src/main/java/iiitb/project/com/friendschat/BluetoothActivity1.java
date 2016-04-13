package iiitb.project.com.friendschat;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.bluetooth.BluetoothAdapter.*;


public class BluetoothActivity1 extends ActionBarActivity {

    BluetoothAdapter b_adapter;

    BroadcastReceiver bluetoothState = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String prevStateExtra = EXTRA_PREVIOUS_STATE;
            String stateExtra = EXTRA_STATE;
            int state = intent.getIntExtra(stateExtra, -1);
            //int previousState = intent.getIntExtra(prevStateExtra,-1);
            String toastText = " ";
            switch(state){
                case(BluetoothAdapter.STATE_TURNING_ON):
                {
                    toastText = "Bluetooth turning ON";
                    Toast.makeText(BluetoothActivity1.this, toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
                case(BluetoothAdapter.STATE_ON):
                {
                    toastText = "Bluetooth ON";
                    Toast.makeText(BluetoothActivity1.this, toastText, Toast.LENGTH_SHORT).show();
                    setupUI();
                    break;
                }
                case(BluetoothAdapter.STATE_OFF):
                {
                    toastText = "Bluetooth OFF";
                    Toast.makeText(BluetoothActivity1.this, toastText, Toast.LENGTH_SHORT).show();
                    setupUI();
                    break;
                }
                case(BluetoothAdapter.STATE_TURNING_OFF):
                {
                    toastText = "Bluetooth turning off";
                    Toast.makeText(BluetoothActivity1.this, toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.blueooth_activity1);
        setupUI();
    }
    private void setupUI()
    {
        //
        final TextView text = (TextView) findViewById(R.id.textView3);
        final Button bluetoothOn = (Button)findViewById(R.id.button6);
        final Button bluetoothOff = (Button)findViewById(R.id.button7);


        //
        b_adapter = getDefaultAdapter();
        if(b_adapter.isEnabled())
        {
            //+text.setText("Bluetooth is already ON");
            bluetoothOn.setVisibility(View.GONE);
            bluetoothOff.setVisibility(View.VISIBLE);
        }
        else
        {

            text.setText("Bluetooth is OFF");
            bluetoothOff.setVisibility(View.GONE);
            bluetoothOn.setVisibility(View.VISIBLE);
        }

        bluetoothOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String actionStateChanged =     ACTION_STATE_CHANGED;
                String actionRequestEnable = ACTION_REQUEST_ENABLE;
                IntentFilter filter = new IntentFilter(actionStateChanged);
                registerReceiver(bluetoothState, filter);
                startActivityForResult(new Intent(actionRequestEnable),0 );
                text.setText("Bluetooth is ON");



            }
        });

        bluetoothOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 b_adapter.disable();
                 bluetoothOff.setVisibility(View.GONE);
                 bluetoothOn.setVisibility(View.VISIBLE);
                 text.setText("Bluetooth is OFF");







            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blueooth_activity1, menu);
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
