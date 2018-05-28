package com.example.prastabdkl.bmap;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static com.example.prastabdkl.bmap.TokenAndId.is_admin;
import static com.example.prastabdkl.bmap.TokenAndId.token;
import static com.example.prastabdkl.bmap.TokenAndId.userId;

/**
 * Created by prastab dhakal on 2/26/17.
 */

public class SyncDatabase {
    private Context context;
    private int user_id;

    public SyncDatabase(Context context){
        this.context = context;
    }

    public void sync(){
        authenticate_user();

        System.out.println(TokenAndId.getTokenAndIdInstance());
        if (token != null){
            System.out.println(token);
            if (is_admin){
                get_all_users();
            }
            get_user_and_account_information();
            get_capital_and_transaction_information();
        }

    }

    /**
     * authenticate user using user credentials
     */

    private void authenticate_user(){
        JsonObject json = new JsonObject();
        json.addProperty("email", "admin@example.com"); // ** get email and password from preference settings
        json.addProperty("password", "password");

        Ion.with(context)
                .load("PUSH", "https://serene-badlands-22797.herokuapp.com/api/v1/authenticate")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null){
                            if (result.get("auth_token") != null){
                                String str = result.get("auth_token").getAsString();
                                int req_id = result.get("req_id").getAsInt();
                                TokenAndId tokenAndId = new TokenAndId(str, req_id); // used for making an singleton object
                                Toast.makeText(context, "authenticated successfully.", Toast.LENGTH_LONG).show();
                                Toast.makeText(context, "Please wait", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    /**
     * get all users informations
     */
    private void get_all_users(){
        Ion.with(context)
                .load("GET", "https://serene-badlands-22797.herokuapp.com/api/v1/" + userId)
                .setHeader("Authorization", token)
                .asJsonArray().setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray response_result) {
                if (response_result.size() != 0){
                    for (int i = 0; i < response_result.size(); i++){
                        JsonObject result = response_result.get(i).getAsJsonObject();

                        String name = TokenAndId.getTokenAndIdInstance().check_if_null(result, "name");
                        String email = TokenAndId.getTokenAndIdInstance().check_if_null(result, "email");
                        String address = TokenAndId.getTokenAndIdInstance().check_if_null(result, "address");
                        String bank_name = TokenAndId.getTokenAndIdInstance().check_if_null(result, "bank_name");
                        String account_number = TokenAndId.getTokenAndIdInstance().check_if_null(result, "account_number");
                        String Nationality = TokenAndId.getTokenAndIdInstance().check_if_null(result, "nationality");
                        String mobile = TokenAndId.getTokenAndIdInstance().check_if_null(result, "mobile"); // int
                        String home = TokenAndId.getTokenAndIdInstance().check_if_null(result, "home"); //int
                        String work = TokenAndId.getTokenAndIdInstance().check_if_null(result, "work"); // int

                        // INSERT these data into User database

                        // getting account information
                        JsonObject account_info = result.get("account").getAsJsonObject();
                        String post = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "post");
                        String salary = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "salary"); //decimal
                        String joining_date = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "joining_date"); //Datetime
                        String working_plan = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "working_plan");
                        String addition_holiday = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "addition_holiday");
                        String addition_overtime = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "addition_overtime");
                        String addition_misc = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "addition_miscellaneous");
                        String deduction_loan = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "deduction_loan");
                        String deduction_late = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "deduction_late");
                        String decuction_misc = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "deduction_miscellaneous");
                        String company_deduction_absent = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "company_deduction_absent");
                        String company_deduction_wtax = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "company_deduction_wtax");
                        String net_total_addition = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "net_total_addition");
                        String net_total_deduction = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "net_total_deduction");
                        String net_pay = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "net_pay");

                        // INSERT these data into account database
                    }

                }
            }
        });
    }

    /**
     * get user information
     */

    private void get_user_and_account_information(){
        Ion.with(context)
                .load("GET", "https://serene-badlands-22797.herokuapp.com/api/v1/" + userId)
                .setHeader("Authorization", token)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null){
                            String name = TokenAndId.getTokenAndIdInstance().check_if_null(result, "name");
                            String email = TokenAndId.getTokenAndIdInstance().check_if_null(result, "email");
                            String address = TokenAndId.getTokenAndIdInstance().check_if_null(result, "address");
                            String bank_name = TokenAndId.getTokenAndIdInstance().check_if_null(result, "bank_name");
                            String account_number = TokenAndId.getTokenAndIdInstance().check_if_null(result, "account_number");
                            String Nationality = TokenAndId.getTokenAndIdInstance().check_if_null(result, "nationality");
                            String mobile = TokenAndId.getTokenAndIdInstance().check_if_null(result, "mobile"); // int
                            String home = TokenAndId.getTokenAndIdInstance().check_if_null(result, "home"); //int
                            String work = TokenAndId.getTokenAndIdInstance().check_if_null(result, "work"); // int

                            // INSERT these data into User database

                            // getting account information
                            JsonObject account_info = result.get("account").getAsJsonObject();
                            String post = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "post");
                            String salary = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "salary"); //decimal
                            String joining_date = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "joining_date"); //Datetime
                            String working_plan = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "working_plan");
                            String addition_holiday = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "addition_holiday");
                            String addition_overtime = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "addition_overtime");
                            String addition_misc = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "addition_miscellaneous");
                            String deduction_loan = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "deduction_loan");
                            String deduction_late = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "deduction_late");
                            String decuction_misc = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "deduction_miscellaneous");
                            String company_deduction_absent = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "company_deduction_absent");
                            String company_deduction_wtax = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "company_deduction_wtax");
                            String net_total_addition = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "net_total_addition");
                            String net_total_deduction = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "net_total_deduction");
                            String net_pay = TokenAndId.getTokenAndIdInstance().check_if_null(account_info, "net_pay");

                            // INSERT these data into account database
                        }
                    }
                });
    }

    /**
     * get capitals and their respective transactions
     */
    private void get_capital_and_transaction_information(){
        // for payable capitals and its transaction
        Ion.with(context)
                .load("GET", "https://serene-badlands-22797.herokuapp.com/api/v1/capitals?capi_type=Payable")
                .setHeader("Authorization", token)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result.size() != 0){
                            for (int i = 0; i < result.size(); i++){
                                JsonObject single_cap_obj = result.get(i).getAsJsonObject();
                                int capital_id = single_cap_obj.get("id").getAsInt(); // required for transactions
                                String name = single_cap_obj.get("name").getAsString();
                                String description = TokenAndId.getTokenAndIdInstance().check_if_null(single_cap_obj, "description");
                                String phone_no = TokenAndId.getTokenAndIdInstance().check_if_null(single_cap_obj, "phone_no");
                                String address = TokenAndId.getTokenAndIdInstance().check_if_null(single_cap_obj, "address");
                                String total_amount = TokenAndId.getTokenAndIdInstance().check_if_null(single_cap_obj, "total_amount");
                                String capital_type = "Payable";

                                // insert these data into capital table

                                // getting corresponding capital's transactions
                                JsonArray transaction_array = single_cap_obj.get("transactions").getAsJsonArray();
                                for (int j = 0; j < transaction_array.size(); j++){
                                    JsonObject transaction_object = transaction_array.get(i).getAsJsonObject();
                                    String date = transaction_object.get("date").getAsString();
                                    String amount = transaction_object.get("amount").getAsString();
                                    String cash_type = transaction_object.get("cash_type").getAsString();

                                    // insert these data into transaction table along with capital_id
                                }
                            }
                        }
                    }
                });

        // for receivable capitals and its transactions
        Ion.with(context)
                .load("GET", "https://serene-badlands-22797.herokuapp.com/api/v1/capitals?capi_type=Receivable")
                .setHeader("Authorization", token)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result.size() != 0){
                            for (int i = 0; i < result.size(); i++){
                                JsonObject single_cap_obj = result.get(i).getAsJsonObject();
                                int capital_id = single_cap_obj.get("id").getAsInt(); // required for transactions
                                String name = single_cap_obj.get("name").getAsString();
                                String description = TokenAndId.getTokenAndIdInstance().check_if_null(single_cap_obj, "description");
                                String phone_no = TokenAndId.getTokenAndIdInstance().check_if_null(single_cap_obj, "phone_no");
                                String address = TokenAndId.getTokenAndIdInstance().check_if_null(single_cap_obj, "address");
                                String total_amount = TokenAndId.getTokenAndIdInstance().check_if_null(single_cap_obj, "total_amount");
                                String capital_type = "Receivable";

                                // insert these data into capital table

                                // getting corresponding capital's transactions
                                JsonArray transaction_array = single_cap_obj.get("transactions").getAsJsonArray();
                                for (int j = 0; j < transaction_array.size(); j++){
                                    JsonObject transaction_object = transaction_array.get(i).getAsJsonObject();
                                    String date = transaction_object.get("date").getAsString();
                                    String amount = transaction_object.get("amount").getAsString();
                                    String cash_type = transaction_object.get("cash_type").getAsString();

                                    // insert these data into transaction table along with capital_id
                                }
                            }
                        }
                    }
                });
    }

    /**
     * Handeling updates
     */
    private void update_user_data(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "name"); // getting name from database
        jsonObject.addProperty("email", "email"); // getting email from sqlite database
        jsonObject.addProperty("address", "address"); // getting address from database
        jsonObject.addProperty("bank_name", "bank_name"); // getting bank_name from database
        jsonObject.addProperty("account_number", "account_number"); // getting account number from database
        jsonObject.addProperty("nationality", "nationality"); // getting nationality from database
        jsonObject.addProperty("mobile", "mobile"); // getting mobile contact from database
        jsonObject.addProperty("home","home"); // getting home contact from database
        jsonObject.addProperty("work", "work"); // getting work contact from database

        Ion.with(context)
                .load("PUT", "https://serene-badlands-22797.herokuapp.com/api/v1/users" + userId)
                .setHeader("Authorization", token)
                .setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null){
                            Toast.makeText(context, "updated user", Toast.LENGTH_LONG).show();
                        }
                        else{
                            System.out.println("Something went wrong");
                        }

                    }
                });
     };

    private void update_account_data(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("post", "post"); // get post from database
        jsonObject.addProperty("salary", "salary");
        jsonObject.addProperty("joining_date", "post");
        jsonObject.addProperty("working_plan", "post");
        jsonObject.addProperty("addition_holiday", "post");
        jsonObject.addProperty("addition_overtime", "post");
        jsonObject.addProperty("addition_miscellenous", "post");
        jsonObject.addProperty("deduction_loan", "post");
        jsonObject.addProperty("deduction_late", "post");
        jsonObject.addProperty("deduction_miscellenous", "post");
        jsonObject.addProperty("company_deduction_absent", "post");
        jsonObject.addProperty("company_deduction_wtax", "post");
        jsonObject.addProperty("net_addition", "post");
        jsonObject.addProperty("net_deduction", "post");
        jsonObject.addProperty("net_pay", "post");

        Ion.with(context)
                .load("PUT", "https://serene-badlands-22797.herokuapp.com/api/v1/" + user_id)
                .setHeader("Authorization", token)
                .setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null){
                            Toast.makeText(context, "updated user", Toast.LENGTH_LONG).show();
                        }
                        else{
                            System.out.println("Something went wrong");
                        }
                    }
                });
    }
}
