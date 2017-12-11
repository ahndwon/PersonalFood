package com.team11.personalfood.Post.Events;

import com.team11.personalfood.Post.ServerResponse;

/**
 * Created by Theodhor Pandeli on 2/11/2016.
 */
public class ServerEvent {
    private ServerResponse serverResponse;

    public ServerEvent(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

}
