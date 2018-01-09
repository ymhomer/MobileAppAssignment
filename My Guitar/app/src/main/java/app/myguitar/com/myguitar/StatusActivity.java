package app.myguitar.com.myguitar;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class StatusActivity extends ListActivity  implements android.view.View.OnClickListener{

    Button btnAdd,btnGetAll;
    TextView status_Id;

    @Override
    public void onClick(View view) {
        if (view== findViewById(R.id.btnAdd)){

            Intent intent = new Intent(this,StatusDetail.class);
            intent.putExtra("status_Id",0);
            startActivity(intent);

        }else {

            StatusReport repo = new StatusReport(this);

            ArrayList<HashMap<String, String>> statusList =  repo.getStatusList();
            if(statusList.size()!=0) {
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        status_Id = (TextView) view.findViewById(R.id.status_Id);
                        String studentId = status_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(),StatusDetail.class);
                        objIndent.putExtra("status_Id", Integer.parseInt( studentId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter( StatusActivity.this,statusList, R.layout.view_status_entry, new String[] { "id","title"}, new int[] {R.id.status_Id, R.id.status_name});
                setListAdapter(adapter);
            }else{
                Toast.makeText(this,"No record!",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

    }


}