package com.company.interfaces;

public interface Failable {
    public int getId();

    public boolean isFailed();

    public Failable getFailable(int index);

    public int getSize();
}
