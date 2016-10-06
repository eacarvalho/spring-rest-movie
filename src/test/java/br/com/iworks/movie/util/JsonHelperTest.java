package br.com.iworks.movie.util;

import static org.hamcrest.Matchers.is;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.iworks.movie.config.JsonConfiguration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class JsonHelperTest {

    private JsonHelper jsonHelper;

    @Before
    public void init(){
        jsonHelper = new JsonHelper(new JsonConfiguration().getObjectMapper());
    }

    @Test
    public void shouldSerializeObjectToJson() {
        JsonObject object = new JsonObject();

        object.setId("1");
        object.setName("Test");

        String json = jsonHelper.toJson(object);

        Assert.assertNotNull(json);
        Assert.assertThat(json, CoreMatchers.containsString("Test"));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionOnError() throws JsonProcessingException {
        JsonObject object = new JsonObject();
        ObjectMapper mock = Mockito.mock(ObjectMapper.class);

        Mockito.when(mock.writeValueAsString(Mockito.eq(object))).thenThrow(new RuntimeException(""));

        new JsonHelper(mock).toJson(object);
    }

    @Test
    public void should_parse_fromJson() {
        String json = "{\"id\":\"1\", \"name\":\"Test\"}";

        JsonObject object = jsonHelper.fromJson(json, JsonObject.class);

        Assert.assertThat(object.getId(), is("1"));
        Assert.assertThat(object.getName(), is("Test"));
    }

    @NoArgsConstructor
    private static class JsonObject {
        @Setter @Getter
        private String id;

        @Setter @Getter
        private String name;
    }
}