package com.example.covid_19healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LabTesting extends AppCompatActivity {
ListView listView;
ArrayAdapter<String> arrayAdapter;
ArrayList<String >arrayList=new ArrayList<>();
    covidLoc covidLoc;
 //   String Name,Id;
   // int res;
   // double latitude,longitude;

ArrayList<String>arrayList1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_testing);
        listView=findViewById(R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
       // name= new String[]{"Anurag", "vikas","ram","sp"};
      //  arrayAdapter=new ArrayAdapter<String>(this,R.layout.list,R.id.chek,arrayList1);
       // arrayAdapter=new ArrayAdapter<String>(LabTesting.this, android.R.layout.simple_list_item_1,arrayList);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("loc");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    //Toast.makeText(getApplicationContext(),dataSnapshot.child("latitude").getValue().toString(),Toast.LENGTH_SHORT).show();
                    double latitude = (double) dataSnapshot.child("latitude").getValue();
                     double longitude = (double) dataSnapshot.child("longitude").getValue();
            String   Name= (String) dataSnapshot.child("name").getValue();
           String    Id= (String) dataSnapshot.child("id").getValue();

    arrayList1.add(Id+" "+latitude+" "+" "+longitude+" "+Name);
    // arrayList.add(Name);
    arrayAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.list,R.id.chek,arrayList1);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=((TextView)view).getText().toString();
                if(arrayList.contains(item)){
                  //  covidLoc = new covidLoc(latitude, longitude,Name,Id,0);
                    arrayList.remove(item);

                   // covidLoc = new covidLoc(latitude, longitude,Name,Id,0);

                }else {
                    arrayList.add(item);
                   /* String[] str = new String[arrayList.size()];
                    arrayList.toArray(str);
                    String s1="";
                    for(int i=0;i<str.length;i++){
                        s1+=str[i];
                    }
                    */
                    String[] words=item.split("\\s");
                  // double latitude= Double.valueOf(words[2]);
                    //double longitude= Double.parseDouble(words[3]);
                    String lat =words[1];
                    String lng=words[3];
                   double latitude= Double.valueOf(lat);
                    double longitude= Double.valueOf(lng);
                    String Id=words[0];
                    String Name=words[4];
                   // System.out.println(words[1]+" ");
                    covidLoc = new covidLoc(latitude, longitude,Name,Id,1);

                  // Toast.makeText(getApplicationContext(),Double.toString(latitude)+"/l"+Double.toString(latitude),Toast.LENGTH_LONG).show();
                   //Toast.makeText(getApplicationContext(),Name,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void show(View view) {
    /*   String items="";
        for(String item:arrayList1){
            items+=item+"\n";
        }
        Toast.makeText(getApplicationContext(),items,Toast.LENGTH_SHORT).show();


      //  items=null;



     */
       FirebaseDatabase.getInstance().getReference().child("location").push().setValue(covidLoc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"loc saved",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"loc not saved",Toast.LENGTH_LONG).show();
                }


            }
        });





    }
}