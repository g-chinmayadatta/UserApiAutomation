package api.test;

import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload; // creating the global level userpayload
	@BeforeClass
	public void steupData() {
		// generate the data
		faker=new Faker();
		userPayload=new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().fullName());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		System.out.println("this is the user name of the user "+this.userPayload.getUsername());
		
	}
	@Test(priority=1)
	public void testPostUser() {
		System.out.println("Creating a User");
		Response response=UserEndPoints2.createUser(userPayload);
		response.then()
			.log().all();
		Assert.assertEquals(response.statusCode(), 200);
	}
	@Test(priority=2)
	public void testGetUser() {
		System.out.println("Getting a User");
		Response response = UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.statusCode(), 200);
	}
	@Test(priority=3)
	public void testUpdateUser()
	{
		System.out.println("Updating a User");
		// update the data
		userPayload.setUserStatus(1);
		userPayload.setEmail(faker.internet().emailAddress());
		Response response=UserEndPoints2.updateUser(userPayload, this.userPayload.getUsername());
		response.then()
			.log().all();
		Assert.assertEquals(response.statusCode(), 200);
		
		Response responseAfterUpdate=UserEndPoints2.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.jsonPath().get("username"),this.userPayload.getUsername());
	}
	@Test(priority=4)
	public void testDeleteUser()
	{
		System.out.println("Deleting a User");
		Response response=UserEndPoints2.deleteUser(this.userPayload.getUsername());
		response.then()
			.body("message", equalTo(this.userPayload.getUsername()))
			.log().body();
		Assert.assertEquals(response.statusCode(), 200);
		//System.out.println(response.jsonPath().get("message"));
	}
}
