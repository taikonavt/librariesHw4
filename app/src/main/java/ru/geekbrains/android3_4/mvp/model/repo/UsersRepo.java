package ru.geekbrains.android3_4.mvp.model.repo;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_4.mvp.model.api.ApiHolder;
import ru.geekbrains.android3_4.mvp.model.entity.User;

public class UsersRepo {
    public Single<User> getUser(String username){
        return ApiHolder.getApi().getUser(username).subscribeOn(Schedulers.io());
    }
}
