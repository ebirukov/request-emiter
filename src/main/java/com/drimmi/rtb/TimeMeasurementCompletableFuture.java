package com.drimmi.rtb;

import java.util.concurrent.*;
import java.util.function.*;

public class TimeMeasurementCompletableFuture<T> extends CompletableFuture<T> {
        private final CompletableFuture<T> baseFuture;

        private final long creationTime;

    public TimeMeasurementCompletableFuture(CompletableFuture<T> baseFuture) {
            this.baseFuture = baseFuture;
            this.creationTime = System.nanoTime();
        }

        public Long getElapsedTime() {
            return (System.nanoTime() - creationTime) / 1000_000L;
        }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor) {
        return CompletableFuture.runAsync(runnable, executor);
    }

    public static <U> CompletableFuture<U> completedFuture(U value) {
        return CompletableFuture.completedFuture(value);
    }

    @Override
    public boolean isDone() {
        return baseFuture.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return baseFuture.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return baseFuture.get(timeout, unit);
    }

    @Override
    public T join() {
        return baseFuture.join();
    }

    @Override
    public T getNow(T valueIfAbsent) {
        return baseFuture.getNow(valueIfAbsent);
    }

    @Override
    public boolean complete(T value) {
        return baseFuture.complete(value);
    }

    @Override
    public boolean completeExceptionally(Throwable ex) {
        return baseFuture.completeExceptionally(ex);
    }

    @Override
    public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return baseFuture.thenApplyAsync(fn);
    }

    @Override
    public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor) {
        return baseFuture.thenApplyAsync(fn, executor);
    }

    @Override
    public CompletableFuture<Void> thenAccept(Consumer<? super T> action) {
        return baseFuture.thenAccept(action);
    }

    @Override
    public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action) {
        return baseFuture.thenAcceptAsync(action);
    }

    @Override
    public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor) {
        return baseFuture.thenAcceptAsync(action, executor);
    }

    @Override
    public CompletableFuture<Void> thenRun(Runnable action) {
        return baseFuture.thenRun(action);
    }

    @Override
    public CompletableFuture<Void> thenRunAsync(Runnable action) {
        return baseFuture.thenRunAsync(action);
    }

    @Override
    public CompletableFuture<Void> thenRunAsync(Runnable action, Executor executor) {
        return baseFuture.thenRunAsync(action, executor);
    }

    @Override
    public <U, V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return baseFuture.thenCombine(other, fn);
    }

    @Override
    public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return baseFuture.thenCombineAsync(other, fn);
    }

    @Override
    public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
        return baseFuture.thenCombineAsync(other, fn, executor);
    }

    @Override
    public <U> CompletableFuture<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return baseFuture.thenAcceptBoth(other, action);
    }

    @Override
    public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return baseFuture.thenAcceptBothAsync(other, action);
    }

    @Override
    public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor) {
        return baseFuture.thenAcceptBothAsync(other, action, executor);
    }

    @Override
    public CompletableFuture<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return baseFuture.runAfterBoth(other, action);
    }

    @Override
    public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return baseFuture.runAfterBothAsync(other, action);
    }

    @Override
    public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return baseFuture.runAfterBothAsync(other, action, executor);
    }

    @Override
    public <U> CompletableFuture<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return baseFuture.applyToEither(other, fn);
    }

    @Override
    public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return baseFuture.applyToEitherAsync(other, fn);
    }

    @Override
    public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor) {
        return baseFuture.applyToEitherAsync(other, fn, executor);
    }

    @Override
    public CompletableFuture<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return baseFuture.acceptEither(other, action);
    }

    @Override
    public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return baseFuture.acceptEitherAsync(other, action);
    }

    @Override
    public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor) {
        return baseFuture.acceptEitherAsync(other, action, executor);
    }

    @Override
    public CompletableFuture<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return baseFuture.runAfterEither(other, action);
    }

    @Override
    public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return baseFuture.runAfterEitherAsync(other, action);
    }

    @Override
    public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return baseFuture.runAfterEitherAsync(other, action, executor);
    }

    @Override
    public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        return baseFuture.thenCompose(fn);
    }

    @Override
    public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
        return baseFuture.thenComposeAsync(fn);
    }

    @Override
    public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
        return baseFuture.thenComposeAsync(fn, executor);
    }

    @Override
    public CompletableFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
        return baseFuture.whenComplete(action);
    }

    @Override
    public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action) {
        return baseFuture.whenCompleteAsync(action);
    }

    @Override
    public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor) {
        return baseFuture.whenCompleteAsync(action, executor);
    }

    @Override
    public <U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
        return baseFuture.handle(fn);
    }

    @Override
    public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
        return baseFuture.handleAsync(fn);
    }

    @Override
    public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
        return baseFuture.handleAsync(fn, executor);
    }

    @Override
    public CompletableFuture<T> toCompletableFuture() {
        return baseFuture.toCompletableFuture();
    }

    @Override
    public CompletableFuture<T> exceptionally(Function<Throwable, ? extends T> fn) {
        return baseFuture.exceptionally(fn);
    }

    public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs) {
        return CompletableFuture.allOf(cfs);
    }

    public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs) {
        return CompletableFuture.anyOf(cfs);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return baseFuture.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return baseFuture.isCancelled();
    }

    @Override
    public boolean isCompletedExceptionally() {
        return baseFuture.isCompletedExceptionally();
    }

    @Override
    public void obtrudeValue(T value) {
        baseFuture.obtrudeValue(value);
    }

    @Override
    public void obtrudeException(Throwable ex) {
        baseFuture.obtrudeException(ex);
    }

    @Override
    public int getNumberOfDependents() {
        return baseFuture.getNumberOfDependents();
    }

    @Override
    public String toString() {
        return baseFuture.toString();
    }

    @Override
    public <U> CompletableFuture<U> newIncompleteFuture() {
        return baseFuture.newIncompleteFuture();
    }

    @Override
    public Executor defaultExecutor() {
        return baseFuture.defaultExecutor();
    }

    @Override
    public CompletableFuture<T> copy() {
        return baseFuture.copy();
    }

    @Override
    public CompletionStage<T> minimalCompletionStage() {
        return baseFuture.minimalCompletionStage();
    }

    @Override
    public CompletableFuture<T> completeAsync(Supplier<? extends T> supplier, Executor executor) {
        return baseFuture.completeAsync(supplier, executor);
    }

    @Override
    public CompletableFuture<T> completeAsync(Supplier<? extends T> supplier) {
        return baseFuture.completeAsync(supplier);
    }

    @Override
    public CompletableFuture<T> orTimeout(long timeout, TimeUnit unit) {
        return baseFuture.orTimeout(timeout, unit);
    }

    @Override
    public CompletableFuture<T> completeOnTimeout(T value, long timeout, TimeUnit unit) {
        return baseFuture.completeOnTimeout(value, timeout, unit);
    }

    public static Executor delayedExecutor(long delay, TimeUnit unit, Executor executor) {
        return CompletableFuture.delayedExecutor(delay, unit, executor);
    }

    public static Executor delayedExecutor(long delay, TimeUnit unit) {
        return CompletableFuture.delayedExecutor(delay, unit);
    }

    public static <U> CompletionStage<U> completedStage(U value) {
        return CompletableFuture.completedStage(value);
    }

    public static <U> CompletableFuture<U> failedFuture(Throwable ex) {
        return CompletableFuture.failedFuture(ex);
    }

    public static <U> CompletionStage<U> failedStage(Throwable ex) {
        return CompletableFuture.failedStage(ex);
    }

    @Override
    public <U> CompletableFuture<U> thenApply(Function<? super T, ? extends U> fn) {
        return super.thenApply(fn);
    }

    public TimeMeasurementCompletableFuture<Void> thenAccept(BiConsumer<? super T, Long> action) {
            System.out.println(getElapsedTime());
            return new TimeMeasurementCompletableFuture<>(baseFuture.thenAccept((Consumer<? super T>) action));
        }
    }
