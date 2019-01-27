package ru.geekbrains.android3_4.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.android3_4.R;
import ru.geekbrains.android3_4.mvp.model.image.IImageLoader;
import ru.geekbrains.android3_4.mvp.presenter.MainPresenter;
import ru.geekbrains.android3_4.mvp.view.MainView;
import ru.geekbrains.android3_4.ui.image.GlideImageLoader;

public class MainActivity extends MvpAppCompatActivity implements MainView
{
    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.tv_username)
    TextView usernameTextView;

    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;

    IImageLoader<ImageView> imageLoader = new GlideImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
        imageLoader.loadInto(url,avatarImageView);
    }

    @Override
    public void setUsernametext(String text) {
        usernameTextView.setText(text);
    }
}
