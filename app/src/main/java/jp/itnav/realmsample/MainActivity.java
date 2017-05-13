package jp.itnav.realmsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import jp.itnav.realmsample.model.UserRObject;
import jp.itnav.realmsample.util.manager.RealmManager;

public class MainActivity extends AppCompatActivity {

    private RealmManager manager;

    private TextView text;
    private EditText edit;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = RealmManager.getInstance(this);

        text = (TextView) findViewById(R.id.text);
        edit = (EditText) findViewById(R.id.edit);
        button = (Button) findViewById(R.id.button);

        // よみこみ
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserRObject userRObject = (UserRObject) manager.loadRObject(UserRObject.class);
                // Realmは異なるスレッドからアクセスできない
                if (userRObject != null) {
                    final String name = userRObject.getName();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(name);
                        }
                    });
                }
            }
        });
        thread.start();
    }

    public void onClick(View v) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final UserRObject userRObject = new UserRObject();
                userRObject.setName(edit.getText().toString());
                manager.saveRObject(userRObject);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(userRObject.getName());
                    }
                });
            }
        });

        thread.start();
    }

}
