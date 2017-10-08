package org.gt.pc;

import org.gt.pc.exception.MovieNotFoundException;

public interface ParentalControl {
    boolean isAccessAllowedFor(String movieId, ParentalControlLevel userLevel) throws MovieNotFoundException;
}
