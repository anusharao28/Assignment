package test.Sample;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.json.JSONException;


import org.testng.annotations.Test;

import io.restassured.RestAssured;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;  

import org.json.JSONObject;

import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
public class ZestAssignment {

	@Test(priority=1)
	public static void main() throws IOException, JSONException 
	{  

		//displaying the contents/API stored in File 1-Input1.txt
		List<String> lines = Collections.emptyList();
		try { 
			lines = Files.readAllLines(Paths.get("Input1.txt"), StandardCharsets.UTF_8); 
		} catch (IOException e) {
			e.printStackTrace(); }

	
		//displaying the contents/API stored in File 2-Input2.txt
		List<String> lines2 = Collections.emptyList();
		try { 
			lines2 = Files.readAllLines(Paths.get("Input2.txt"), StandardCharsets.UTF_8); 
		} catch (IOException e) {
			e.printStackTrace(); }


		String api1,api2 ;
		List<JSONCompareResult> list= new ArrayList<JSONCompareResult>();
		for(int i=0;i<lines.size();i++) {
			api1=lines.get(i);

			api2=lines2.get(i);

			RequestSpecification httpRequest = RestAssured.given().contentType("application/json");
			Response response = httpRequest.get(api1);
			String body = response.getBody().asString();

			JSONObject jsonObject = new JSONObject(body);


			RequestSpecification httpRequest2 = RestAssured.given().contentType("application/json");
			Response response2 = httpRequest2.get(api2);
			String body2 = response2.getBody().asString();

			JSONObject jsonObject2 = new JSONObject(body2);

			JSONCompareResult result = JSONCompare.compareJSON(jsonObject, jsonObject2, JSONCompareMode.STRICT);
			list.add(result);
			if(result.failed()) {
				System.out.println(api1+" not equals "+api2);
			}else {
				System.out.println(api1+" equals "+api2);
			}

		}

	}



}
