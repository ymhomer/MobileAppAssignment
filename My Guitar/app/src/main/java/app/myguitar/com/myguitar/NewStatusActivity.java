package app.myguitar.com.myguitar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

public class NewStatusActivity extends AppCompatActivity {
    EditText STitle, SContent;
    myDbAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_status);
        STitle = (EditText) findViewById(R.id.txt_statusTitle);
        SContent = (EditText) findViewById(R.id.txt_statusSubtitle);
        helper = new myDbAdapter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t1 = STitle.getText().toString();
                String t2 = SContent.getText().toString();
                if (t1.isEmpty() || t2.isEmpty()) {
                    Message.message(getApplicationContext(), "Please don't leave empty of your status.");
                } else {
                    long id = helper.insertData(t1, t2);
                    if (id <= 0) {
                        Message.message(getApplicationContext(), "Insertion Unsuccessful");
                        STitle.setText("");
                        SContent.setText("");
                    } else {
                        Message.message(getApplicationContext(), "Insertion Successful");
                        STitle.setText("");
                        SContent.setText("");
                    }
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
