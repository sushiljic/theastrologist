package com.theastrologist.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import springfox.documentation.swagger.web.SecurityConfiguration;

public class SpringfoxSecurityConfigurationJsonSerializer
		implements JsonSerializer<SecurityConfiguration> {

	@Override
	public JsonElement serialize(SecurityConfiguration config,
								 Type type, JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("clientId", config.getClientId());
		jsonObject.addProperty("realm", config.getRealm());
		jsonObject.addProperty("appName", config.getAppName());
		jsonObject.addProperty("clientSecret", config.getClientSecret());
		jsonObject.addProperty("scopeSeparator", config.scopeSeparator());
		jsonObject.addProperty("useBasicAuthenticationWithAccessCodeGrant", config.getUseBasicAuthenticationWithAccessCodeGrant());

		final JsonElement additionalQueryStringParams = context.serialize(config.getAdditionalQueryStringParams());
		jsonObject.add("additionalQueryStringParams", additionalQueryStringParams);

		return jsonObject;
	}
}
