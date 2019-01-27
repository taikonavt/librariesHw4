package ru.geekbrains.android3_4.mvp.model.repo;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_4.mvp.model.api.ApiHolder;
import ru.geekbrains.android3_4.mvp.model.entity.Repo;
import ru.geekbrains.android3_4.mvp.model.entity.User;

public class UsersRepo {
    public Single<User> getUser(String username){
        return ApiHolder.getApi().getUser(username).subscribeOn(Schedulers.io());
    }

    public Single<List<Repo>> getUserRepos(String username){
        return ApiHolder.getApi().getUserRepos(username).subscribeOn(Schedulers.io());
    }
}
