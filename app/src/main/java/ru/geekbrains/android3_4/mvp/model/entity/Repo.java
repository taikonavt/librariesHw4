package ru.geekbrains.android3_4.mvp.model.entity;

import com.google.gson.annotations.Expose;

public class Repo {

    @Expose
    private String name;

    public String getName() {
        return name;
    }
}
