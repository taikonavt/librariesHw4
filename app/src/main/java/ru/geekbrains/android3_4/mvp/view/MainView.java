package ru.geekbrains.android3_4.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.geekbrains.android3_4.mvp.model.entity.Repo;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    void showMessage(String text);
    void setImageUrl(String url);
    void setUserNameText(String text);
    void updateRepoList();
}
