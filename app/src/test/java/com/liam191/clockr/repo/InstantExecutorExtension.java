package com.liam191.clockr.repo;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

class InstantExecutorExtension implements BeforeEachCallback, AfterEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
            @Override public void executeOnDiskIO(@NonNull Runnable runnable) { runnable.run(); }
            @Override public void postToMainThread(@NonNull Runnable runnable) { runnable.run(); }
            @Override public boolean isMainThread() { return true; }
        });
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        ArchTaskExecutor.getInstance().setDelegate(null);
    }
}
