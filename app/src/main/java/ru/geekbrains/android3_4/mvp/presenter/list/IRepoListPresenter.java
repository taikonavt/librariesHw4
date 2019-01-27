package ru.geekbrains.android3_4.mvp.presenter.list;

import io.reactivex.subjects.PublishSubject;
import ru.geekbrains.android3_4.mvp.view.item.IRepoItemView;
import ru.geekbrains.android3_4.ui.adapters.RepoAdapter;

public interface IRepoListPresenter {
    PublishSubject<IRepoItemView> getClickSubject();

    void bindView(IRepoItemView view);

    int getRepoItemCount();
}
