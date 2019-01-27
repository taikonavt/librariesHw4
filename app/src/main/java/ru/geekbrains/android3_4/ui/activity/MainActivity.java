package ru.geekbrains.android3_4.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.android3_4.R;
import ru.geekbrains.android3_4.mvp.model.entity.Repo;
import ru.geekbrains.android3_4.mvp.model.image.IImageLoader;
import ru.geekbrains.android3_4.mvp.presenter.MainPresenter;
import ru.geekbrains.android3_4.mvp.view.MainView;
import ru.geekbrains.android3_4.ui.adapters.RepoAdapter;
import ru.geekbrains.android3_4.ui.image.GlideImageLoader;

public class MainActivity extends MvpAppCompatActivity implements MainView
{
    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.tv_username)
    TextView usernameTextView;

    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    RepoAdapter adapter;

    IImageLoader<ImageView> imageLoader = new GlideImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new RepoAdapter(presenter.getRepoListPresenter());
        recyclerView.setAdapter(adapter);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter(){
        return new MainPresenter(AndroidSchedulers.mainThread());
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImageUrl(String url) {
        imageLoader.loadInto(url, avatarImageView);
    }

    @Override
    public void setUserNameText(String text) {
        usernameTextView.setText(text);
    }

    @Override
    public void updateRepoList() {
        adapter.notifyDataSetChanged();
    }
}
