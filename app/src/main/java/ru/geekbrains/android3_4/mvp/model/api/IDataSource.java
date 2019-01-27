package ru.geekbrains.android3_4.mvp.model.api;


import io.reactivex.Single;
import retrofit2.http.*;
import ru.geekbrains.android3_4.mvp.model.entity.User;

public interface IDataSource {
    @GET("/users/{user}")
    Single<User> getUser(@Path("user") String username);

//    @GET
//    Single<List<Repository>> getUserRepos(@Url String url);
}
