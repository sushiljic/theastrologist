package com.theastrologist.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import springfox.documentation.swagger.web.UiConfiguration;

public class SpringfoxUiConfigurationJsonSerializer
        implements JsonSerializer<UiConfiguration> {

    @Override
    public JsonElement serialize(UiConfiguration config, Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("validatorUrl", config.getValidatorUrl());
        jsonObject.addProperty("maxDisplayedTags", config.getMaxDisplayedTags());
        jsonObject.addProperty("docExpansion", config.getDocExpansion().getValue());
        jsonObject.addProperty("defaultModelRendering", config.getDefaultModelRendering().getValue());
        jsonObject.addProperty("deepLinking", config.getDeepLinking());
        jsonObject.addProperty("tagsSorter", config.getTagsSorter().getValue());
        jsonObject.addProperty("defaultModelExpandDepth", config.getDefaultModelExpandDepth());
        jsonObject.addProperty("defaultModelsExpandDepth", config.getDefaultModelsExpandDepth());
        jsonObject.addProperty("displayOperationId", config.getDisplayOperationId());
        jsonObject.addProperty("displayRequestDuration", config.getDisplayRequestDuration());
        jsonObject.addProperty("showExtensions", config.getShowExtensions());
        jsonObject.addProperty("filter", config.getFilter().toString());
        jsonObject.addProperty("operationsSorter", config.getOperationsSorter().getValue());

        JsonArray array = new JsonArray();
        for (String value : config.getSupportedSubmitMethods()) {
            array.add(new JsonPrimitive(value));
        }
        jsonObject.add("supportedSubmitMethods", array);

        return jsonObject;
    }
}
