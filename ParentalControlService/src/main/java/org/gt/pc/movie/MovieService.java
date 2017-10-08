package org.gt.pc.movie;

import java.util.Optional;

public interface MovieService {
    Optional<String> getParentalControlLevel(String movieId)
            throws TitleNotFoundException,
            TechnicalFailureException;
}
