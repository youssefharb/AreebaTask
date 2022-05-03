package com.youssef.validator.services;

import com.youssef.validator.response.ValidationResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service

public class Validate {
	private Content makeAbstractRequest(String phone) {

		try {

			Content content = Request
					.Get("https://phonevalidation.abstractapi.com/v1/?api_key=c166d60967b2482e8f3da556ec7e3b99&phone="
							+ phone)

					.execute().returnContent();

			return content;
		}

		catch (IOException error) {
			System.out.println(error);
			return null;
		}
	}

	public ValidationResponse check(String phoneNumber) {
		Content c = makeAbstractRequest(phoneNumber);
		JSONObject jsonObject = new JSONObject(c.asString());
		String operator = jsonObject.getString("carrier");
		ValidationResponse validationResponse = new ValidationResponse();
		validationResponse.setOperatorName(operator);
		JSONObject countryObject = jsonObject.getJSONObject("country");
		String countryName = countryObject.getString("name");
		String countryCode = countryObject.getString("code");
		validationResponse.setCountryCode(countryCode);

		validationResponse.setCountryName(countryName);
		return validationResponse;

	}
}
