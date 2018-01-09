package app.myguitar.com.myguitar;

import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusDetail extends AppCompatActivity implements android.view.View.OnClickListener{

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextTitle;
    EditText editTextContent;
    EditText editTextFeeling;
    private int _Status_Id =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextContent = (EditText) findViewById(R.id.editTextContent);
        editTextFeeling = (EditText) findViewById(R.id.editTextFeeling);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Status_Id =0;
        Intent intent = getIntent();
        _Status_Id =intent.getIntExtra("status_Id", 0);
        StatusReport repo = new StatusReport(this);
        Status status = new Status();
        status = repo.getStudentById(_Status_Id);

        editTextFeeling.setText(status.feeling);
        editTextTitle.setText(status.title);
        editTextContent.setText(status.content);
    }



    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            StatusReport repo = new StatusReport(this);
            Status status = new Status();
            status.feeling= editTextFeeling.getText().toString();
            status.content= editTextContent.getText().toString();
            status.title= editTextTitle.getText().toString();
            status.student_ID= _Status_Id;

            if (_Status_Id ==0){
                _Status_Id = repo.insert(status);

                Toast.makeText(this,"New Status Insert",Toast.LENGTH_SHORT).show();
            }else{

                repo.update(status);
                Toast.makeText(this,"Status Record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            StatusReport repo = new StatusReport(this);
            repo.delete(_Status_Id);
            Toast.makeText(this, "Status Record Deleted", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        }


    }

}
