package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Robin Duda
 *         <p/>
 *         Contains a complete voting result for transmission to distribution clients.
 */
public class VotingResult {
    private String owner;
    private String topic;
    private String id;
    private Duration duration;
    private ArrayList<Value> options = new ArrayList<>();

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getOwner() {
        return owner;
    }

    public VotingResult setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getTopic() {
        return topic;
    }

    public VotingResult setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public String getId() {
        return id;
    }

    public VotingResult setId(String id) {
        this.id = id;
        return this;
    }

    public ArrayList<Value> getOptions() {
        return options;
    }

    public VotingResult setOptions(ArrayList<Value> options) {
        this.options = options;
        return this;
    }
}
