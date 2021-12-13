package checkWriterApi;


import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import groovy.json.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Member;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.*;


import io.restassured.RestAssured;
import io.restassured.response.Response;


import org.apache.http.HttpEntity;
import org.json.JSONArray;
import org.json.JSONObject;

//import for GSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Member;

//category and payee a lot, 100. 200 

public class postCheck {

	private static String BASE_URL_SANDBOX = "https://test.onlinecheckwriter.com/api/v3";
	private static String AUTH_TOKEN = "3mKuEJ94Jqzn15B0bt0X1agYP2UVGLHXMqaKEtpYk7qw9qj4pJFV99sVDuhY";
	private static String AUTHORIZATION = "Bearer "+ AUTH_TOKEN; 
	private static RequestSpecification httpRequest;


	public static void main(String[] args) throws IOException {		
		//JSONArray ab = getAllCategories();
		//System.out.println(ab.length());
		
		///getAllChecks();
		//getAllBankAccounts();
		//int i = checkTotalNumberRecord("bankAccounts", "/bankAccounts?perPage=&page=&term=");
		//System.out.println(i);

		//deleteCheck("6VoW41vnJMG589l");
		//deleteAllChecks();
		//checkPayeeExist("ronbin@test.com","ronbin");
		//createPayee("hi2@gmail.com","hi2");
		//System.out.println("hi");
		//new Check("KaVPXre6D6om4zl9ZLE", "dJM5GbkpDXeY329","mQ0bRG2Y8y4ZdP3",10.0);
		
		//Check c2 = writeCheck("1111111111", "test1", "test1@test.com", "categ1", "cate2", 10.0, "test1 new hahaha", null);
		//c2.toString();
		
		//System.out.println(checkPayeeExist("testnew", "testnew@gmail.com"));
		//System.out.println(createPayee("testnew2", "hi2@gmail.com"));
		
		
		String createFilePath = "C:\\Users\\seren\\OneDrive\\Desktop\\git\\checkWriterApi\\src\\test\\resources\\checkstest.txt";
		List<Check> check = createFromFile(createFilePath);
		//writeFromPrompt();

		
	}


	//create a list of checks from txt file read.
	//return List<Check> checks 
	public static List<Check> createFromFile(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file) );
		String st;
		List<Check> checks = new ArrayList<Check>();
		try {
			String first = br.readLine();
			while(( st = br.readLine()) != null) {
				System.out.println(st);
				String[] sarr = st.split(" ");
				writeCheck(sarr[0], sarr[1], sarr[2], sarr[3], sarr[4], Double.parseDouble(sarr[5]), sarr[6], sarr[7]);
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return checks;
	}
	
	public static List<String> createDeleteCheck(String filePath) throws IOException {
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		List<String> ids = new ArrayList<>();
		while( (st = br.readLine()) != null) {
			System.out.println(st);
			ids.add(st);
		}
		return ids;
	}

	public static void connect(String basePath) {
		httpRequest = null;
		System.out.println("connecting...");
		RestAssured.baseURI = BASE_URL_SANDBOX;
		RestAssured.basePath = basePath;
		Header contentType = new Header("Content-Type", "application/json");
		Header authorization = new Header("Authorization", AUTHORIZATION);
		Header accept = new Header("Accept","appplication/json");
		
		List<Header> headers = new ArrayList<>();
		headers.add(accept);
		headers.add(contentType);
		headers.add(authorization);
		
		Headers allHeaders = new Headers(headers);
		
		httpRequest = RestAssured.given().headers(allHeaders);
	}
	
	
	public static int checkTotalNumberRecord(String name, String path) {
		connect(path);
		System.out.println("checking number of records");
		Response response = httpRequest.get();
		int statusCode = response.getStatusCode();
		if(statusCode == 200) {
		JSONObject json = new JSONObject(response.asPrettyString());
		int total = json.getJSONObject("data").getJSONObject("meta").getInt("total");
		return total;
		}
		
		return -1;
	}
	
	public static JSONArray getAll(String name, String path) {
		connect(path);
		System.out.println("getting all "+ name);
		Response response = httpRequest.get(path);
		int statusCode = response.getStatusCode();
		if(statusCode == 200) {
			System.out.println(statusCode + "success");
			//System.out.println(response.asPrettyString());
			String jsonString = response.asString();
			JSONObject jsono = new JSONObject(jsonString);
			JSONArray arr = jsono.getJSONObject("data").getJSONArray(name);
			return arr;
		} else {
			System.out.println(statusCode + "fail");
			System.out.println(response.asPrettyString());
			return null;
		}
	}
	
	public static JSONArray getAllCategories() {
		int numPerPage = checkTotalNumberRecord("categories", "/categories");
		if(numPerPage == -1 ) {
			return null;
		}
		String link = "/categories?perPage=" + numPerPage ;
		JSONArray source = getAll("categories", link);
		System.out.println(numPerPage);
		return source;
	}
	
	
	//retrieve all payees from connection
	//return an array of payee jsonarray objects
	public static JSONArray getAllPayees() {
		int numPerPage = checkTotalNumberRecord("payees", "/payees?perPage&page&term");
		if(numPerPage == -1 ) {
			return null;
		}
		String link = "/payees?perPage=" + numPerPage + "&page&term";
		JSONArray source = getAll("payees", link);
		System.out.println(numPerPage);
		return source;
	}
	
	public static JSONArray getAllChecks() {
		int numPerPage = checkTotalNumberRecord("checks", "/checks?perPage=&page=&status=&term=");
		System.out.println(numPerPage);
		//int numOfRequest = (numPerPage / 10) + 1;
		//System.out.println(numOfRequest);
		//JSONArray destinationArray = new JSONArray();
		
		if(numPerPage == -1 ) {
			return null;
		}
		/*for (int i= 1; i< numOfRequest+1; i ++) {
			
			String link = "/checks?perPage=&page=" + i + "&/checks?perPage=&page=";
			JSONArray source = getAll("checks", link);
			for (int j = 0; j<source.length();j++) {
				destinationArray.put(source.getJSONObject(i));
			}
		}*/
		String link = "/checks?perPage=" + numPerPage + "&page=&status=&term";
		JSONArray source = getAll("checks", link);
		System.out.println(source.length());
		System.out.println(link);
		return source;
	}
	
	public static JSONArray getAllBankAccounts() {
		int numPerPage = checkTotalNumberRecord("bankAccounts", "/bankAccounts?perPage=&page=&term=");
		if(numPerPage == -1 ) {
			return null;
		}
		String link = "/bankAccounts?perPage=" + numPerPage + "&page=&term=";
		return getAll("bankAccounts", link);
	}
	
	//check if payee exists by using email, 
	//if exist, return payee id, if not, return null
	public static String checkPayeeExist(String email, String name) {
		System.out.println("checking if payee " + name + " exists");
		JSONArray arr = getAllPayees();
		if(arr == null) {
			System.out.println("connection error or does not exist");
			return null;
		} else {
			for (int i = 0; i<arr.length(); i++) {
				String payeeId = arr.getJSONObject(i).getString("payeeId");
				String emailRetrieved = arr.getJSONObject(i).getString("email");
				String nameR = arr.getJSONObject(i).getString("name");
				if(emailRetrieved.equals( email) && nameR.equals(name)) {
						System.out.println("payee " + name + " exists,  with id: " + payeeId);
						return payeeId;
					
				}
				System.out.println(payeeId + " " + emailRetrieved);
			}
		}
		return null;
	}
	
	public static String checkCategoryExist(String name, String type) {
		String result = null; 
		JSONArray arr = getAllCategories();
		if(arr == null) {
			System.out.println("connection error");
			return null;
		} else {
			for (int i = 0; i<arr.length(); i++) {
				String categoryId = arr.getJSONObject(i).getString("categoryId");
				String typeR = arr.getJSONObject(i).getString("type");
				String nameR = arr.getJSONObject(i).getString("name");
				if(typeR == type || nameR == name) {
						return categoryId;
					
				}
				//System.out.println(payeeId + " " + emailRetrieved);
			}
		}
		
		return result;
	}
	
	public static String checkBankAccountExist(String accountNumber) {
		String result = null; 
		JSONArray arr = getAllBankAccounts();
		if(arr == null) {
			System.out.println("connection error");
			return null;
		} else {
			for (int i = 0; i<arr.length(); i++) {
				String bankAccountId = arr.getJSONObject(i).getString("bankAccountId");
				String accountNumberR = arr.getJSONObject(i).getString("accountNumber");
				if(accountNumberR.equals(accountNumber) ) {
					System.out.println("accountExist");
					return bankAccountId;
				}
				//System.out.println(payeeId + " " + emailRetrieved);
			}
		}
		
		return result;
	}
	
	
	//create a payee if the email does not exist, return the id of the created id, else return null
	public static String createPayee(String name, String email) {
		String id = checkPayeeExist(email, name);
		if(id == null) { //payee does not exist, create 
			System.out.println("payee does not exist");
			connect("/payees");
			JSONObject jo =  new JSONObject();
			JSONArray arr = new JSONArray();
			jo.put("name", name);
			jo.put("email", email);
			arr.put(jo);
			JSONObject jsonBody = new JSONObject();
			jsonBody.put("payees", arr);
			System.out.println(jsonBody.toString());
			httpRequest.body(jsonBody.toString());
			Response response = httpRequest.post();
			if(response.getStatusCode() == 201) {//CREATE PAYEE
				System.out.println("payee [" + name + "] sucessfully created");
				System.out.println(response.asPrettyString());
				String jsonString = response.asString();
				JSONObject jsono = new JSONObject(jsonString);
				JSONArray arrR = jsono.getJSONObject("data").getJSONArray("payees");
				String id2 = arrR.getJSONObject(0).getString("payeeId");
				///String id = response.getString("payeeId");
				System.out.println("payee [" + name + "] created");
				return id2;
			}else {
				System.out.println("creating payee [" +  name + "] failed");
				System.out.println(response.asPrettyString());
				return null;
			}
		}
		return id;
	}
	
	public static String createCategory(String name, String type) {
		String id = checkCategoryExist(name, type);
		if(id == null) { //category does not exist, create 
			System.out.println("category [" + name + "] does not exist");
			connect("/categories");
			JSONObject jo =  new JSONObject();
			jo.put("name", name);
			jo.put("type", type);
			System.out.println(jo.toString());
			httpRequest.body(jo.toString());
			Response response = httpRequest.post();
			if(response.getStatusCode() == 201) {//CREATE category sucessfully
				//System.out.println(response.asPrettyString());
				String jsonString = response.asString();
				JSONObject jsono = new JSONObject(jsonString);
				id = jsono.getJSONObject("data").getString("categoryId");
				///String id = response.getString("payeeId");
				System.out.println("category [" + name + "] with id: " + id+" sucessfully created");
				return id;
			}else {
				System.out.println(response.getStatusCode());
				System.out.println("creat category" + name + " failed");
				System.out.println(response.asPrettyString());
			}
		}
		return id;
	}
	
	//public static String check
	
	
	//check if payee and category exists, if so, createCheck, 
	//if not, ask them if they want to create payee, or create category
	//if so, create the payee, create the category 
	//and then create the check..  
	public static Check createCheck(Check c){
		connect("/chwqaecks");
		
		Gson g = new Gson();
		String cgson = g.toJson(c);
		JSONObject jo = new JSONObject(cgson);
		System.out.println(jo.toString());
		JSONArray ja = new JSONArray();
		ja.put(jo);
		JSONObject j = new JSONObject();
		j.put("checks", ja);
		
		System.out.println(j.toString());
		httpRequest.body(j.toString());
		Response response = httpRequest.post();
		int statusCode = response.getStatusCode();
		if( statusCode  != 201) {
			System.out.println(statusCode + "error");
			System.out.println(response.asPrettyString());
			return null;
		} else {
			System.out.println(statusCode + "check successfully created");
			String jsonString = response.asString();
			JSONObject jsono = new JSONObject(jsonString);
			System.out.println(response.asPrettyString());
			JSONArray arr = jsono.getJSONObject("data").getJSONArray("checks");
				String id = arr.getJSONObject(0).getString("checkId");
				System.out.println(id);
				c.setCheckId(id);
				return c;
		}
	}
	
	//write check and return checkID
	public static Check writeCheck(String AccountNumber, String payeeName, String payeeEmail, String categoryName, String categoryType, Double amount, String memo, String date) {
		String bank = checkBankAccountExist(AccountNumber);
		String payee = createPayee(payeeName, payeeEmail);
		String category  = createCategory(categoryName, categoryType);
		if(bank == null) {
			System.out.println("bank account does not exist");
			return null;
		} 
		
		
		System.out.println(bank + " " + payee + " " + category );
		if(bank != null) {
			Check c = new Check(bank, payee, category, amount);
			c.setDate(date);
			if(memo != null) {
				c.setMemo(memo);
			} else if (date != null) {
				c.setDate(date);
			}
			Check ccreated = createCheck(c);
			return ccreated;
		}
		
		
		return null;
	}
	
	
	public static void deleteCheck(String id) {
		connect("/checks");
		System.out.println("deleting check " + id + " ...");
		Response response = httpRequest.delete("/"+id);
			int statusCode = response.getStatusCode();
			System.out.println(statusCode);
			if(statusCode == 201) {
				System.out.println(statusCode + "successfully deleted check " + id );
				System.out.println(response.asPrettyString());
			} else {
				System.out.println(response.asPrettyString());
			}
	}
	
	
	public static void deleteAllChecks() { //only delete 9, getall only has 9
		JSONArray arr = getAllChecks();
		for (int i = 0; i< arr.length(); i++) {
			System.out.println(i);
			JSONObject o = arr.getJSONObject(i);
			String id = o.getString("checkId");
			System.out.println(o.toString());
			System.out.println(id);
			deleteCheck(id);
		}
	}
	public static void writeFromPrompt() throws IOException {
		String bankAccountId, categoryName, categoryType, payeeName, payeeEmail, memo, date;
		Double amount;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Hi, You can write a check here! ");
		System.out.println("which bank account number would you like to use?  ");
		bankAccountId = br.readLine();
		System.out.println("Enter payee name: ");
		payeeName = br.readLine();
		System.out.println("Enter payee email: ");
		payeeEmail = br.readLine();
		
		System.out.println("Enter category name: ");
		categoryName = br.readLine();;
		
		System.out.println("Enter category Type: ");
		categoryType = br.readLine();;
		
		
		System.out.println("enter the amount you would like to transfer");
		amount = Double.parseDouble(br.readLine());
		System.out.println("What would you like to leave as a memo?(enter if not): ");
		memo = br.readLine();
		
		System.out.println("Enter the date of the check(empty means right now( ex: 11/02/2020)");
		date = br.readLine();
		Check c = writeCheck(bankAccountId, payeeName, payeeEmail, categoryName, categoryType, amount, memo, date);
		c.toString();
		
	}




}


