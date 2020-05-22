package com.liam191.clockr.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import org.threeten.bp.ZonedDateTime;

public final class ClockingDayViewModelFactory implements ViewModelProvider.Factory {

    private final ZonedDateTime ofDate;

    public ClockingDayViewModelFactory(ZonedDateTime ofDate){
        this.ofDate = ofDate;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass != ClockingDayViewModel.class){
            throw new IllegalArgumentException("modelClass was not ");
        }
        return new ClockingDayViewModel.Factory(ofDate);
    }
}
