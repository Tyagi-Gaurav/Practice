package org.gt.pc;

import org.gt.pc.exception.InternalServerException;
import org.gt.pc.exception.MovieNotFoundException;
import org.gt.pc.exception.UnSupportedParentalControlLevelException;
import org.gt.pc.movie.MovieService;
import org.gt.pc.movie.TechnicalFailureException;
import org.gt.pc.movie.TitleNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.gt.pc.ParentalControlLevel.*;
import static org.mockito.Mockito.when;

public class ParentalControlServiceTest {
    private MovieService movieService = Mockito.mock(MovieService.class);
    private ParentalControl parentalControl = new ParentalControlService(movieService);
    private final String MOVIE_NOT_FOUND = "The movie\n" +
            "service could not\n" +
            "find the given\n" +
            "movie";
    private String RANDOM_MOVIE_ID = "random_movie_id";

    @Test
    void throwErrorWhenNoMovieFound() throws TechnicalFailureException, TitleNotFoundException {

        //When
        when(movieService.getParentalControlLevel(RANDOM_MOVIE_ID)).thenThrow(new TitleNotFoundException(MOVIE_NOT_FOUND));

        //Then
        assertThatThrownBy(() -> parentalControl.isAccessAllowedFor(RANDOM_MOVIE_ID, U))
                .isInstanceOf(MovieNotFoundException.class)
                .hasCauseInstanceOf(TitleNotFoundException.class);
    }

    @Test
    void throwErrorWhenNullMovieLevelIsReturned() throws TechnicalFailureException, TitleNotFoundException {

        //When
        when(movieService.getParentalControlLevel(RANDOM_MOVIE_ID)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> parentalControl
                .isAccessAllowedFor(RANDOM_MOVIE_ID, U))
                .isInstanceOf(UnSupportedParentalControlLevelException.class)
                .hasNoCause();
    }

    @Test
    void cannotWatchWhenTechnicalFailure() throws TechnicalFailureException, TitleNotFoundException {
        //When
        when(movieService.getParentalControlLevel(RANDOM_MOVIE_ID)).thenThrow(new TechnicalFailureException("System Error"));

        //Then
        assertThat(parentalControl.isAccessAllowedFor(RANDOM_MOVIE_ID, U))
                .isEqualTo(false);
    }

    @Test
    void allowToWatchIfUserHasExactlySameLevel() {
        //When
        when(movieService.getParentalControlLevel(RANDOM_MOVIE_ID))
                .thenReturn(Optional.of("U"));

        //Then
        assertThat(parentalControl.isAccessAllowedFor(RANDOM_MOVIE_ID, U))
                .isEqualTo(true);
    }

    @Test
    void doNotAllowToWatchIfUserHasLowerLevelThanMovieLevel() {
        //When
        when(movieService.getParentalControlLevel(RANDOM_MOVIE_ID))
                .thenReturn(Optional.of("12"));

        //Then
        assertThat(parentalControl.isAccessAllowedFor(RANDOM_MOVIE_ID, U))
                .isEqualTo(false);
    }

    @Test
    void doNotAllowToWatchIfUserHasHigherLevelThanMovieLevel() {
        //When
        when(movieService.getParentalControlLevel(RANDOM_MOVIE_ID))
                .thenReturn(Optional.of("12"));

        //Then
        assertThat(parentalControl.isAccessAllowedFor(RANDOM_MOVIE_ID, EIGHTEEN))
                .isEqualTo(true);
    }

    @Test
    void returnErrorWhenInvalidParentalControlIsReturnedFromMovieService() {
        //When
        when(movieService.getParentalControlLevel(RANDOM_MOVIE_ID))
                .thenReturn(Optional.of(""));

        //Then
        assertThatThrownBy(() -> parentalControl.isAccessAllowedFor(RANDOM_MOVIE_ID, U))
                .isInstanceOf(InternalServerException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }
}
