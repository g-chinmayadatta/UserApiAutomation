package api.test;

import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.payload.User;
import api.utilites.DataProviders;
import api.endpoints.UserEndPoints;
import io.restassured.response.Response;

public class DDTests {
	
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testPostUser(String userID,String userName,String fname,String lname,String userEmail,String pwd,String ph) {
		User userPayload=new User();
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setEmail(userEmail);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		userPayload.setUsername(userName);
		System.out.println("Creating a User");
		Response response=UserEndPoints.createUser(userPayload);
		response.then()
			.log().all();
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	@Test(priority=2,dataProvider="UserNames",dataProviderClass=DataProviders.class)
	public void testGetUserByName(String uname) {
		System.out.println("Getting a User");
		Response response = UserEndPoints.readUser(uname);
		//userPayload = response.then().
		response.then().log().all();
		Assert.assertEquals(response.statusCode(), 200);
	}
	//@Test(priority=3,dataProvider="UserNames",dataProviderClass=DataProviders.class)
//	public void testUpdateUserByName(String uname) {
//		System.out.println("Updating a User");
//		// update the data
//		User userPayload= new User();
//		userPayload.setUserStatus(1);
//		Response response=UserEndPoints.updateUser(userPayload, this.userPayload.getUsername());
//		response.then()
//			.log().all();
//		Assert.assertEquals(response.statusCode(), 200);
//		
//		Response responseAfterUpdate=UserEndPoints.readUser(this.userPayload.getUsername());
//		Assert.assertEquals(responseAfterUpdate.jsonPath().get("username"),this.userPayload.getUsername());
//	}
	@Test(priority=4,dataProvider="UserNames",dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String uname) {
		System.out.println("Deleting a User");
		Response response=UserEndPoints.deleteUser(uname);
		response.then()
			.body("message", equalTo(uname))
			.log().body();
		Assert.assertEquals(response.statusCode(), 200);
		//System.out.println(response.jsonPath().get("message"));
	}
}
