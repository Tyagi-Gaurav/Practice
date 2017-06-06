import badNeighbor.BadNeighbor
import org.scalatest.WordSpec

class BadNeighborSpec extends WordSpec {
  "Should return max for test 1" in {
    assert(BadNeighbor
      .calcDonation(Array(10, 3, 2, 5, 7, 8)) == 19)
  }
}
