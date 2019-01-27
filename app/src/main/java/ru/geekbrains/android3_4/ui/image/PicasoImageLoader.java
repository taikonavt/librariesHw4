package ru.geekbrains.android3_4.ui.image;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import ru.geekbrains.android3_4.mvp.model.image.IImageLoader;

public class PicasoImageLoader implements IImageLoader<ImageView> {

    @Override
    public void loadInto(String url, ImageView container) {
        Picasso.get().load(url).into(container);
    }
}
