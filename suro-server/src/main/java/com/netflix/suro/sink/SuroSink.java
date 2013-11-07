package com.netflix.suro.sink;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netflix.suro.client.SuroClient;
import com.netflix.suro.message.Message;

import java.util.Properties;

public class SuroSink implements Sink {
    public static final String TYPE = "suro";

    private SuroClient client;
    private final Properties props;

    @JsonCreator
    public SuroSink(@JsonProperty("properties") Properties props) {
        this.props = props;
    }

    @Override
    public void writeTo(Message message) {
        client.send(message);
    }

    @Override
    public void open() {
        client = new SuroClient(props);
    }

    @Override
    public void close() {
        client.shutdown();
    }

    @Override
    public String recvNotify() {
        return "";
    }

    @Override
    public String getStat() {
        StringBuilder sb = new StringBuilder();
        sb.append("sent: ").append(client.getSentMessageCount()).append('\n');
        sb.append("lost: ").append(client.getLostMessageCount());
        return sb.toString();
    }
}