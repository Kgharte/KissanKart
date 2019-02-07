package com.example.meet.kissankart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class BuyerFp extends AppCompatActivity {
    Button btnsendnow;
    EditText fpemailbuyer;
    private Context ctx = this;
    DataStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_fp);
        allocate();
        events();
    }

    private void allocate()
    {
        storage = new DataStorage(getResources().getString(R.string.sharedprefname),ctx);
        fpemailbuyer = (EditText) findViewById(R.id.fpemailbuyer);
        btnsendnow = (Button) findViewById(R.id.btnsendnow);

    }

    private void events()
    {
        btnsendnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email;

                email = fpemailbuyer.getText().toString().trim();
                Boolean isError=false;
                if(email.length()==0)
                {
                    fpemailbuyer.setError("Email can not be blank");
                    isError = true;
                }

                if(isError==false)
                {
                    String WebServiceUrl = Common.GetBaseServiceUrl() + "forgot-password.php";
                    Log.d("trace forgotpassword", ""+WebServiceUrl);
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
                                    Toast.makeText(ctx,"Password Send Successfully",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ctx,PRSuccessfulBuyer.class));
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
                            //KeyValuePair.put("id",storage.read("id",DataStorage.INTEGER).toString());
                            KeyValuePair.put("email",email);
                            Log.d("trace email",email);
                            return KeyValuePair;
                        }
                    };

                    request.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    AppController.getInstance().addToRequestQueue(request);
                }
            }
        });
            }

}
