package com.example.spacejuice.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacejuice.Habit;
import com.example.spacejuice.MainActivity;
import com.example.spacejuice.Member;
import com.example.spacejuice.R;
import com.example.spacejuice.controller.FollowController;
import com.example.spacejuice.controller.HabitController;
import com.example.spacejuice.controller.LoginController;

import java.util.ArrayList;

public class FollowerRequestsActivity extends AppCompatActivity {
   /*
   This Activity is used to display all my pending incoming follow requests
    */
   private ImageButton back_button;
   private ListView followerList;
   private FollowRequestAdapter followRequestAdapter;
   private ArrayList<Member> requestingMembers;
   private EditText requestName;
   private Button send;
   private FollowController followController;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Log.d("debugInfo", "Follower Requests Activity Created from FollowerRequestsActivity.java");
      setContentView(R.layout.follower_requests);
      back_button = findViewById(R.id.backButtonFollowerRequests);

      followerList = findViewById(R.id.followersList);
      followController = new FollowController();
      requestingMembers = new ArrayList<>();
      followController.getRequests(new LoginController.OnCompleteCallback() {
         @Override
         public void onComplete(boolean suc) {
            if (suc){
               ArrayList<String> list = MainActivity.getUser().getFollow().getRequests();
               for (int i = 0; i < list.size(); i++){
                  requestingMembers.add(new Member(list.get(i)));
               }
               followRequestAdapter = new FollowRequestAdapter(FollowerRequestsActivity.this, R.layout.follow_request_content, requestingMembers);
               followerList.setAdapter(followRequestAdapter);
            }
            else{
               followRequestAdapter = new FollowRequestAdapter(FollowerRequestsActivity.this, R.layout.follow_request_content, requestingMembers);
               followerList.setAdapter(followRequestAdapter);
            }
         }
      });


      followRequestAdapter = new FollowRequestAdapter(this, R.layout.follow_request_content, requestingMembers);
      followerList.setAdapter(followRequestAdapter);

      back_button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            finish();
         }
      });
      requestName = findViewById(R.id.requestingName);
      send = findViewById(R.id.sendRequestButton);
      send.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if(!requestName.getText().toString().matches("")){
               FollowController followController = new FollowController();
               followController.sendRequest(requestName.getText().toString(), new LoginController.OnCompleteCallback() {
                  @Override
                  public void onComplete(boolean suc) {
                     //MainActivity.setUser(member);
                     if (suc) {
                        Toast.makeText(FollowerRequestsActivity.this, "Sent request successfully!", Toast.LENGTH_SHORT).show();
                     }
                     else {
                        if (requestName.getText().toString().equals(MainActivity.getUser().getMemberName())){
                           Toast.makeText(FollowerRequestsActivity.this, "Please don't send follow request to yourself!",
                                   Toast.LENGTH_SHORT).show();
                        }
                        else {
                           Toast.makeText(FollowerRequestsActivity.this, "No such user exists!",
                                   Toast.LENGTH_SHORT).show();
                        }
                     }
                  }
               });
            }


         }
      });
   }

   public void acceptFollowRequest(int position) {

      Context context = getApplicationContext();
      String memName = requestingMembers.get(position).getMemberName();
      followController.responseRequest(memName, true, new LoginController.OnCompleteCallback() {
         @Override
         public void onComplete(boolean suc) {
            CharSequence text = memName + " is now following you";
            int duration = Toast.LENGTH_SHORT;
            MediaPlayer song = MediaPlayer.create(context, R.raw.pop);
            song.start();

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            requestingMembers.remove(position);
            Log.d("debugInfo", "acceptFollowRequest run at position " + position);
            followRequestAdapter.notifyDataSetChanged();
         }
      });
   }

   public void denyFollowRequest(int position) {
      String memName = requestingMembers.get(position).getMemberName();
      Context context = getApplicationContext();
      followController.responseRequest(memName, false, new LoginController.OnCompleteCallback() {
         @Override
         public void onComplete(boolean suc) {
            requestingMembers.remove(position);
            MediaPlayer song = MediaPlayer.create(context, R.raw.pop2);
            song.start();

            Log.d("debugInfo", "denyFollowRequest run at position " + position);
            followRequestAdapter.notifyDataSetChanged();
         }
      });

   }





}
