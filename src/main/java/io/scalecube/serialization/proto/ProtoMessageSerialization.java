package io.scalecube.serialization.proto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.scalecube.serialization.MessageSerialization;

public class ProtoMessageSerialization implements MessageSerialization {

  private static final RecyclableLinkedBuffer recyclableLinkedBuffer = new RecyclableLinkedBuffer();

  @Override
  public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {

    Schema<T> schema = SchemaCache.getOrCreate(clazz);
    T message = schema.newMessage();
    ByteBuf bb = Unpooled.copiedBuffer(data, 0, data.length);

    ProtostuffIOUtil.mergeFrom(new ByteBufInputStream(bb), message, schema);

    return message;
  }

  @Override
  public <T> byte[] serialize(T value, Class<T> clazz) throws Exception {
    Schema<T> schema = SchemaCache.getOrCreate(clazz);
    RecyclableLinkedBuffer rlb = recyclableLinkedBuffer.get();
    return ProtostuffIOUtil.toByteArray(value, schema, rlb.buffer());
  }

}
