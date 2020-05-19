package com.liam191.clockr.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public final class ClockingDayViewModelProvider extends ViewModelProvider {
    public ClockingDayViewModelProvider(@NonNull ViewModelStoreOwner owner, @NonNull Factory factory) {
        super(owner, factory);
    }
}
