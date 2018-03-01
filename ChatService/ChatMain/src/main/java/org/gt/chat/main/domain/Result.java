package org.gt.chat.main.domain;

import java.util.Optional;
import java.util.function.Function;

public class Result<T, U extends Throwable> {
    private Optional<U> error;
    private Optional<T> ok;

    private Result(T value, U throwable) {
        ok = Optional.ofNullable(value);
        error = Optional.ofNullable(throwable);
    }

    public static <T> Result ok(T value) {
        return new Result(value, null);
    }

    public static Result error(Throwable throwable) {
        return new Result(null, throwable);
    }

    public boolean isError() {
        return error.isPresent();
    }

    public T getValue() {
        return ok.orElseThrow(() -> new RuntimeException());
    }

    public U getError() {
        return error.orElseThrow(() -> new RuntimeException());
    }

    public<R> Result<R, U> flatmap(Function<T, Result<R, U>> mapper) {
        if (this.isError()) {
            return Result.error(this.getError());
        }

        return mapper.apply(this.getValue());
    }
}
