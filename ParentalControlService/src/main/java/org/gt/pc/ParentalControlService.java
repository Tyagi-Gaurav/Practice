package org.gt.pc;

import org.gt.pc.exception.InternalServerException;
import org.gt.pc.exception.MovieNotFoundException;
import org.gt.pc.exception.UnSupportedParentalControlLevelException;
import org.gt.pc.movie.MovieService;
import org.gt.pc.movie.TechnicalFailureException;
import org.gt.pc.movie.TitleNotFoundException;

import java.util.Optional;

public class ParentalControlService implements ParentalControl {
    private MovieService movieService;

    public ParentalControlService(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public boolean isAccessAllowedFor(String movieId, ParentalControlLevel userLevel) throws MovieNotFoundException {
        try {
            Optional<String> movieLevel = movieService.getParentalControlLevel(movieId);
            ParentalControlLevel currentMovieLevel =
                    ParentalControlLevel.getFrom(movieLevel.orElseThrow(UnSupportedParentalControlLevelException::new)
                            .toUpperCase());
            return userLevel.isHigherThan(currentMovieLevel);
        } catch (TitleNotFoundException e) {
            throw new MovieNotFoundException(e);
        } catch (TechnicalFailureException e) {
            //Atleast log somewhere
            return false;
        } catch (IllegalArgumentException e) {
            throw new InternalServerException(e);
        }
    }
}
