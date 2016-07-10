package org.digitalgreen.www.loopadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import org.digitalgreen.www.loopadmin.Adapters.ViewCropAdapter;
import org.digitalgreen.www.loopadmin.Models.Crop;

import java.util.ArrayList;

public class ViewCrop extends AppCompatActivity {
    private ListView view_crop_list;
    private FloatingActionButton view_crop_add;
    private ArrayList<Crop> cropList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_crop);

        cropList = new Crop().getAllCrop();

        view_crop_list = (ListView)findViewById(R.id.view_crop_list);
        view_crop_add = (FloatingActionButton)findViewById(R.id.view_crop_add);

        ViewCropAdapter viewCropAdapter = new ViewCropAdapter(cropList, this, new ViewCropAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(Crop c, int position) {
                Intent intent = new Intent(ViewCrop.this, AddCrop.class);
                intent.putExtra("crop_id", c.getId());
                startActivity(intent);
                finish();
            }
        });
        view_crop_list.setAdapter(viewCropAdapter);

        view_crop_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewCrop.this, AddCrop.class);
                startActivity(i);
                finish();
            }
        });

    }
}
