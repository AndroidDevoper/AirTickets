package com.example.airtickets.model.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AirTicketsApi {

    @GET("auth?")
    Single<LoginRequest> getAccount(@Query("login") String login,
                                    @Query("password") String password);

    @GET("get?")
    Single<TicketRequest> getTickets(@Query("token") String token);
}
