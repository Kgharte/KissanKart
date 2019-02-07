package com.example.meet.kissankart;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryContainer extends AppCompatActivity {
    public GridView gridcategory;
    ArrayList<buyercategory> CategoryList = new ArrayList<buyercategory>();
    Context ctx=this;
    DataStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_container);
        gridcategory = (GridView) this.findViewById(R.id.gridcategory);

        String WebServiceUrl = Common.GetBaseServiceUrl() + "category.php";
        Log.d("trace category",""+WebServiceUrl);
        StringRequest request = new StringRequest(StringRequest.Method.GET, WebServiceUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                try
                {
                    Log.d("trace",response);
                    JSONArray array = new JSONArray(response);
                    JSONObject ErrorObject = array.getJSONObject(0);
                    String error = ErrorObject.getString("error");
                    if(error.equals("no")==false)
                    {
                        Toast.makeText(ctx,error,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //there is no error
                        JSONObject TotalObject = array.getJSONObject(1);
                        Log.d("trace title",""+TotalObject);
                        int total = TotalObject.getInt("total");
                        if(total==0)
                        {
                            Toast.makeText(ctx,"No category found", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            int size = array.length();
                            for(int i=2;i<size;i++)
                            {
                                JSONObject CurrentObject = array.getJSONObject(i);
                                Log.d("trace title",""+CurrentObject.getString("title"));
                                buyercategory CurrentCategory = new buyercategory(Integer.parseInt(CurrentObject.getString("id")),
                                        CurrentObject.getString("title"),CurrentObject.getString("image"));
                                CategoryList.add(CurrentCategory);
                            }
                            BuyerCategoryAdapter adapter = new BuyerCategoryAdapter(ctx,CategoryList);
                            gridcategory.setAdapter(adapter);
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.d("trace error",""+e.getMessage().toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, ""+error, Toast.LENGTH_SHORT).show();
                Log.d("trace volley error",""+error);
            }
        });
        AppController.getInstance().addToRequestQueue(request);


        gridcategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ctx, ""+CategoryList.get(position).getId(), Toast.LENGTH_SHORT).show();
                Intent SwitchIntent = new Intent(CategoryContainer.this,ProductContainer.class);
                SwitchIntent.putExtra("categoryid",CategoryList.get(position).getId());
                startActivity(SwitchIntent);
            }
        });
    }
    @Override

    public boolean onCreateOptionsMenu(Menu MyMenu)
    {
        MenuInflater inflater = new MenuInflater(ctx);
        inflater.inflate(R.menu.toolbarmenu,MyMenu);
        return super.onCreateOptionsMenu(MyMenu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem SelectedItem)
    {
        Intent SwitchIntent;
        if(SelectedItem.getItemId()==R.id.btncart_menu)
        {
            SwitchIntent = new Intent(ctx,CartContainer.class);
            startActivity(SwitchIntent);
        }
        if(SelectedItem.getItemId()==R.id.btnuserprofile_menu)
        {
            SwitchIntent = new Intent(ctx,UserProfile.class);
            startActivity(SwitchIntent);
        }
        if(SelectedItem.getItemId()==R.id.btnchangepassword_menu)
        {
            SwitchIntent = new Intent(ctx,ChangePassword.class);
            startActivity(SwitchIntent);
        }
        if(SelectedItem.getItemId()==R.id.btnorderhistory_menu)
        {
            SwitchIntent = new Intent(ctx,OrderHistoryContainer.class);
            startActivity(SwitchIntent);
        }
        if(SelectedItem.getItemId()==R.id.btnlogout_menu) {
            SharedPreferences myPrefs = getSharedPreferences("easylearn",
                    MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.clear();
            editor.commit();
          //  AppState.getSingleInstance().setLoggingOut(true);
          //  Log.d(TAG, "Now log out and start the activity login");
            Intent intent = new Intent(ctx,
                    BuyerLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(SelectedItem);
    }
}

