import badNeighbor.BadNeighbor
import org.scalatest.WordSpec

class BadNeighborSpec extends WordSpec {
  "Should return max for test 1" in {
    assert(BadNeighbor
      .calcDonation(Array(10, 3, 2, 5, 7, 8)) == 19)
  }

  "Should return max for test 2" in {
    assert(BadNeighbor
      .calcDonation(Array(11, 15)) == 15)
  }

  "Should return max for test 3" in {
    assert(BadNeighbor
      .calcDonation(Array(7, 7, 7, 7, 7, 7, 7)) == 21)
  }

  "Should return max for test 4" in {
    assert(BadNeighbor
      .calcDonation(Array(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)) == 16)
  }

  "Should return max for test 5" in {
    assert(BadNeighbor
      .calcDonation(Array(94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61,
        6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397,
        52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72)) == 2926)
  }
}
