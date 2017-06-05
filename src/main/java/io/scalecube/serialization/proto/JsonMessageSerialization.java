package io.scalecube.serialization.proto;

import io.protostuff.JsonIOUtil;
import io.protostuff.Schema;
import io.scalecube.serialization.MessageSerialization;


public class JsonMessageSerialization implements MessageSerialization {

  @Override
  public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {

    Schema<T> schema = SchemaCache.getOrCreate(clazz);
    T message = schema.newMessage();

    JsonIOUtil.mergeFrom(data, message, schema, false);

    return message;
  }

  @Override
  public <T> byte[] serialize(T value, Class<T> clazz) throws Exception {

    Schema<T> schema = SchemaCache.getOrCreate(clazz);
    
    return JsonIOUtil.toByteArray(value, schema, false);
    
  }

}

