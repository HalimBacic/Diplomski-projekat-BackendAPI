package backend.multidbapi.multidbapi.services.mapperconfig;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import backend.multidbapi.multidbapi.models.MediaResponse;

public class MediaResponseSerializer extends JsonSerializer<MediaResponse> {

    @Override
    public void serialize(MediaResponse multiPartResource, JsonGenerator gen, SerializerProvider arg2) throws IOException
    {
        gen.writeStartObject();
        gen.writeObjectField("metadata", multiPartResource.getMetadata());
        gen.writeEndObject();
    }
    
}
