package com.example.airtickets.model.api;

import com.example.airtickets.model.Ticket;

import java.util.List;

class TicketRequest {
    String result;
    List<Ticket> data;
    long timestamp;

    public List<Ticket> getData() {
        return data;
    }
}
