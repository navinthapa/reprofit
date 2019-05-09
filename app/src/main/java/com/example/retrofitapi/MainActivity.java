package com.example.retrofitapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.retrofitapi.apiclient.EmployeeAPI;

import java.util.List;

import model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private TextView navindata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navindata = findViewById(R.id.navindata);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

                EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<List<Employee>> listCall= employeeAPI.getEmployee();
        listCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (!response.isSuccessful()){
                    navindata.setText("code:"+response.code());
                    return;
                }
                List<Employee> employeelist = response.body();
                for (Employee employee :employeelist){
                    String content ="";
                    content += "ID :"+employee.getId()+ "\n";
                    content += "employee_name :"+employee.getEmployee_name()+ "\n";
                    content += "Employee_salary :"+employee.getEmployee_salary()+ "\n";
                    content += "Employee_age :"+employee.getEmployee_age()+ "\n";
                    content += "Profile-image :"+employee.getProfile_image()+ "\n";

                    navindata.append(content)  ;
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
navindata.setText(("Error" +t.getMessage()));
            }
        });
    }
}
