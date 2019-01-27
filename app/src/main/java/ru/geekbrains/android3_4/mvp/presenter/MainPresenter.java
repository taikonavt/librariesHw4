package ru.geekbrains.android3_4.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.geekbrains.android3_4.mvp.model.entity.Repo;
import ru.geekbrains.android3_4.mvp.model.repo.UsersRepo;
import ru.geekbrains.android3_4.mvp.presenter.list.IRepoListPresenter;
import ru.geekbrains.android3_4.mvp.view.MainView;
import ru.geekbrains.android3_4.mvp.view.item.IRepoItemView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String USER_NAME = "taikonavt";

    private RepoListPresenter repoListPresenter;
    private Scheduler mainThreadScheduler;
    private UsersRepo usersRepo;

    public MainPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.usersRepo = new UsersRepo();
        this.repoListPresenter = new RepoListPresenter();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadUser();
        loadUserRepos();
    }

    @SuppressLint("CheckResult")
    private void loadUser() {
        usersRepo.getUser(USER_NAME)
                .observeOn(mainThreadScheduler)
                .subscribe(user -> {
                    MainPresenter.this.getViewState().setUserNameText(user.getLogin());
                    MainPresenter.this.getViewState().setImageUrl(user.getAvatarUrl());
                },
                        throwable -> {
                            Timber.e(throwable);
                        });
    }

    @SuppressLint("CheckResult")
    private void loadUserRepos() {
        usersRepo.getUserRepos(USER_NAME)
                .observeOn(mainThreadScheduler)
                .subscribe(repos -> {
                    repoListPresenter.repoList = repos;
                    MainPresenter.this.getViewState().updateRepoList();
                        },
                        Timber::e);
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

    public IRepoListPresenter getRepoListPresenter() {
        return repoListPresenter;
    }


    public class RepoListPresenter implements IRepoListPresenter{

        PublishSubject<IRepoItemView> clickSubject = PublishSubject.create();
        List<Repo> repoList = new ArrayList<>();

        @Override
        public PublishSubject<IRepoItemView> getClickSubject() {
            return clickSubject;
        }

        @Override
        public void bindView(IRepoItemView view) {
            Repo repo = repoList.get(view.getPos());
            view.setName(repo.getName());
        }

        @Override
        public int getRepoItemCount() {
            return repoList.size();
        }
    }
}





