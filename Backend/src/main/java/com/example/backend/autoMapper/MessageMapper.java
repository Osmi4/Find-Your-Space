package com.example.backend.autoMapper;

import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.entity.Message;
import com.example.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    @Mapping(target = "messageId", ignore = true)
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(expression = "java(new java.util.Date())", target = "messageDateTime")
    Message mapAddMessageToMessage(AddMessage message, User sender, User receiver);

    @Mapping(source = "sender.userId", target = "senderId")
    @Mapping(source = "receiver.userId", target = "receiverId")
    MessageResponse mapMessageToMessageResponse(Message message);
}
