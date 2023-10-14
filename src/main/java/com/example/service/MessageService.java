package com.example.service;


import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import java.util.Optional;
import java.util.List;

@Service
public class MessageService {

    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }else{
            return null;
        }
    }

    public Message persistMessage(Message message) {
        return messageRepository.save(message);
    }

    public Integer deleteMessage(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    public Integer updateMessage(int id, String replacement) {
        if (replacement.isBlank() || replacement.length() > 255) {
            return null;
        }
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            Message message = optionalMessage.get();
            message.setMessage_text(replacement);
            messageRepository.save(message);
            return 1;
        }
        return null;
    }

    public List<Message> getMessagesByPostedBy(int id) {
        return messageRepository.findByPostedBy(id);
    }
}
