package ru.geekbrains.android3_4.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.android3_4.R;
import ru.geekbrains.android3_4.mvp.presenter.list.IRepoListPresenter;
import ru.geekbrains.android3_4.mvp.view.item.IRepoItemView;

public class RepoAdapter extends RecyclerView.Adapter {

    private IRepoListPresenter presenter;

    public RepoAdapter(IRepoListPresenter presenter){
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        IRepoViewHolder repoViewHolder =
                new IRepoViewHolder(LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.item, viewGroup, false));
        return repoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        IRepoViewHolder repoViewHolder = (IRepoViewHolder) viewHolder;
        RxView.clicks(repoViewHolder.itemView)
                .map(obj -> repoViewHolder)
                .subscribe(presenter.getClickSubject());
        repoViewHolder.pos = position;
        presenter.bindView(repoViewHolder);
    }

    @Override
    public int getItemCount() {
        return presenter.getRepoItemCount();
    }

    public class IRepoViewHolder extends RecyclerView.ViewHolder
                implements IRepoItemView {

        @BindView(R.id.tv_item)
        TextView textView;

        int pos = 0;

        public IRepoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setName(String name) {
            textView.setText(name);
        }
    }
}
