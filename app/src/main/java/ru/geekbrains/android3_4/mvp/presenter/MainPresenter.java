package ru.geekbrains.android3_4.mvp.presenter;

import android.annotation.SuppressLint;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.geekbrains.android3_4.mvp.model.api.ApiHolder;
import ru.geekbrains.android3_4.mvp.model.repo.UsersRepo;
import ru.geekbrains.android3_4.mvp.view.MainView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    Scheduler mainThreadScheduler;
    UsersRepo usersRepo;

    public MainPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.usersRepo = new UsersRepo();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadUser();
    }

    @SuppressLint("CheckResult")
    private void loadUser() {
        usersRepo.getUser("googlesamples")
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {
                            getViewState().setUsernametext(user.getLogin());
                            getViewState().setImageUrl(user.getAvatarUrl());
                        },
                        throwable -> {
                            Timber.e(throwable);
                        });
    }


    @SuppressLint("CheckResult")
    private void loadDataWithOkHttp() {
        Single<String> single = Single.fromCallable(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/users/googlesamples")
                    .build();
            return client.newCall(request).execute().body().string();
        });

        single.subscribeOn(Schedulers.io())
                .subscribe(string -> Timber.d(string));
    }
}
