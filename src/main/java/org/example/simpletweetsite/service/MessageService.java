package org.example.simpletweetsite.service;

import org.example.simpletweetsite.domain.User;
import org.example.simpletweetsite.domain.dto.MessageDto;
import org.example.simpletweetsite.repositories.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Page<MessageDto> messageList(User currentUser, String filter, Pageable pageable) {

        if (filter != null && !filter.isEmpty()) {
            return messageRepository.findByTag(currentUser, filter, pageable);
        } else {
            return messageRepository.findAll(currentUser, pageable);
        }
    }

    public Page<MessageDto> messageListForUser(User currentUser, User author, Pageable pageable) {
        return messageRepository.findByUser(currentUser, author, pageable);
    }
}
