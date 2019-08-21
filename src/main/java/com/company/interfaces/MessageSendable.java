package com.company.interfaces;

import com.company.Message;

public interface MessageSendable {
    public boolean sendMessageToAll(Message message);

    public boolean sendMessageToChild(Message message);
}
