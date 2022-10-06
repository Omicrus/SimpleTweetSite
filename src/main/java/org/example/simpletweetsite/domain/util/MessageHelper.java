package org.example.simpletweetsite.domain.util;

import org.example.simpletweetsite.domain.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author){
        return author != null ? author.getUsername() : "<none>";
    }
}
