package synapsesystems.mine;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SerialCom extends AppCompatActivity {


    TextView textView;
    EditText editText;
    UsbManager usbManager;
    UsbDevice device;
    UsbSerialDevice serialPort;
    UsbDeviceConnection connection;



public void connect(View view){

    try {



        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        UsbDevice device = null;
        UsbDeviceConnection connection = null;
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {

            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();

                    connection = usbManager.openDevice(device);

            }


        }

        serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
        if (serialPort != null) {



            if (serialPort.open()) {


                serialPort.setBaudRate(4800);
                serialPort.setDataBits(UsbSerialInterface.DATA_BITS_7);
                serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                serialPort.setParity(UsbSerialInterface.PARITY_EVEN);
                serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(this, "Not Open", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(this, "No Driver", Toast.LENGTH_SHORT).show();

        }

    }
    catch (Exception cc){
        Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
    }





}
public void clear(View view){
    try {


    TextView Text=(TextView) findViewById(R.id.textView);
    Text.setText("");
}
catch (Exception e){}}
public  void disconnect(View view){

try {
    serialPort.close();
}
catch (Exception e){
    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
}




}
public  void click(View view){
    try {
       EditText mEdit = (EditText) findViewById(R.id.edittext);
        String ss = mEdit.getText().toString();
serialPort.write(ss.getBytes());
        serialPort.read(mCallback);
    }
    catch (Exception cc){
        Toast.makeText(this, "Write/Read Error", Toast.LENGTH_SHORT).show();
    }



}
   public UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback()
    {
        @Override
        public void onReceivedData(byte[] arg0)
        {try {
            String data = new String(arg0, "UTF-8");

            String ss = "";
            tvAppend(ss, data);
        }
        catch (Exception e){
            Toast.makeText(SerialCom.this, "Receive Error", Toast.LENGTH_SHORT).show();
        }
        }
    };



    private void tvAppend(String tv, final CharSequence text) {
        //final TextView ftv = tv;
        //final CharSequence ftext = text;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{

             TextView Text=(TextView) findViewById(R.id.textView);
                Text.setText(Text.getText()+""+text);

            }
            catch (Exception e){
                Toast.makeText(SerialCom.this, "Error", Toast.LENGTH_SHORT).show();
            }}
        });
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_com);

    }





}
