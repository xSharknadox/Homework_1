package com.company;

public interface MessageSendable {
    public boolean sendMessageToAll(Message message);

    public boolean sendMessageToChild(Message message);
}
