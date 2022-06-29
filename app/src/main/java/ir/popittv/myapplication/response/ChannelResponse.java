package ir.popittv.myapplication.response;

import java.util.List;

import ir.popittv.myapplication.models.ChannelDataModel;

public class ChannelResponse {

    private List<ChannelDataModel> channel;

    public List<ChannelDataModel> getChannel() {
        return channel;
    }

    private List<ChannelDataModel> channel_all;

    public List<ChannelDataModel> getChannel_all() {
        return channel_all;
    }
}
