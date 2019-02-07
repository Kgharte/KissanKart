package com.example.meet.kissankart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    Button btnchangepassword;
    EditText txtoldpassword,txtnewpassword,txtnewpassword2;
    private Context ctx = this;
    DataStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        allocatememory();
        setevents();
    }

    private void setevents()
    {
        btnchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String oldpassword,newpassword,newpassword2;
                oldpassword = txtoldpassword.getText().toString().trim();
                newpassword = txtnewpassword.getText().toString().trim();
                newpassword2 = txtnewpassword2.getText().toString().trim();
                Boolean isError=false;
                if(oldpassword.length()==0)
                {
                    txtoldpassword.setError("old password can not be blank");
                    isError = true;
                }
                if(newpassword.length()==0)
                {
                    txtnewpassword.setError("new password can not be blank");
                    isError = true;
                }
                if(newpassword2.length()==0)
                {
                    txtnewpassword2.setError("confirm password can not be blank");
                    isError = true;
                }
                if(newpassword.equals(newpassword2)==false)
                {

                    Toast.makeText(ChangePassword.this,"password and confirm password do not match",Toast.LENGTH_LONG).show();
                    isError = true;
                }

                if(isError==false)
                {
                    String WebServiceUrl = Common.GetBaseServiceUrl() + "change-password.php";
                      Log.d("trace changepassword", ""+WebServiceUrl);
                    StringRequest request = new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("trace",response);
                            try
                            {
                                JSONArray array = new JSONArray(response);
                                String error = array.getJSONObject(0).getString("error");
                                if(error.equals("no")==false)
                                {
                                    Toast.makeText(ctx,error,Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(ctx,"Password changed",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ctx,CategoryContainer.class));
                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        Log.e("trace error",error.toString());
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            //id=1&oldpassword=123123&newpassword=112233
                            Map<String,String> KeyValuePair = new HashMap<String, String>();
                            KeyValuePair.put("id", (String) storage.read("id",DataStorage.STRING));
                            KeyValuePair.put("oldpassword",oldpassword);
                            KeyValuePair.put("newpassword",newpassword);
                            Log.d("trace id",storage.read("id",DataStorage.STRING).toString());
                            return KeyValuePair;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(request);
                }
            }
        });
    }

    private void allocatememory()
    {
        storage = new DataStorage(getResources().getString(R.string.sharedprefname),ctx);
        btnchangepassword = (Button) findViewById(R.id.btnchangepassword);
        txtoldpassword = (EditText) findViewById(R.id.txtoldpassword);
        txtnewpassword = (EditText) findViewById(R.id.txtnewpassword);
        txtnewpassword2 = (EditText) findViewById(R.id.txtnewpassword2);
    }
}
