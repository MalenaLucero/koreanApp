package com.example.test;

//import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

//import com.baeldung.rest.GitHubUser;
//import com.baeldung.rest.RetrieveUtil;

public class UserTest {
	

    @Test
    public void givenUserExists_whenUserInformationIsRetrieved_thenRetrievedResourceIsCorrect() throws ClientProtocolException, IOException, JSONException {
        // Given
        final HttpUriRequest request = new HttpGet("http://localhost:8080/demo/all");
//        final HttpPost request2 = new HttpPost("https://api.github.com/users/eugenp");
        
        
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("username", "John"));
//        params.add(new BasicNameValuePair("password", "pass"));
//        request2.setEntity(new UrlEncodedFormEntity(params));
        
        // When
        final HttpResponse response = HttpClientBuilder.create().build().execute(request);
//        final HttpResponse response2 = HttpClientBuilder.create().build().execute(request2);
        System.out.println(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();
        String retSrc = EntityUtils.toString(entity); 
        System.out.println(retSrc);
        
        
        JSONArray result = new JSONArray(retSrc); //Convert String to JSON Object

        JSONObject oj = result.getJSONObject(0);
        String token = oj.getString("name"); 
        
        // Then
//        final List resource = RetrieveUtil.retrieveResourceFromResponse(response, List.class);
//        assertThat("eugenp", Matchers.is(resource.getLogin()));
        assertTrue(entity != null);
    }



}
