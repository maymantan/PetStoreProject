package api.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints1;
import api.payload.User;

import io.restassured.response.Response;

public class UserTests1 {
	
	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setupdata() {
		
		faker = new Faker();
		userPayload = new User();
			
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone() );
		
		logger = LogManager.getLogger(this.getClass());
		
		}
	@Test(priority = 1)
	public void testPostUser() {
		
		logger.info("************************start adding user**********************");
		
		Response response =UserEndpoints1.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("************************ adding user completed**********************");
	}
	
	@Test(priority = 2)
	public void testGetUser() {
		logger.info("************************start getting user**********************");
		Response response = UserEndpoints1.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("************************getting user completed **********************");
	}
	
	@Test(priority = 3)
	public void testPutUser() {
		// we are going to update firstname, lastname and email address
		
		logger.info("************************start updating user**********************");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndpoints1.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		response.then().log().body();
		
		Response res =UserEndpoints1.readUser(this.userPayload.getUsername());
		res.then().log().body();
		
		logger.info("************************upating user completed **********************");
	}
	
	@Test(priority = 4)
	public void testDeleteUser() {
		
		logger.info("************************start deleteing user**********************");
		Response response = UserEndpoints1.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("************************ deleteing user completed **********************");
	}

}
