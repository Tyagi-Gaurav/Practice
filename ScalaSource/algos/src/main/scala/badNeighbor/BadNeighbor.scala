package badNeighbor

/**
  * The old song declares "Go ahead and hate your neighbor", and the residents of Onetinville have taken those words to heart. Every resident hates his next-door neighbors on both sides. Nobody is willing to live farther away from the town's well than his neighbors, so the town has been arranged in a big circle around the well. Unfortunately, the town's well is in disrepair and needs to be restored. You have been hired to collect donations for the Save Our Well fund.

Each of the town's residents is willing to donate a certain amount, as specified in the int[] donations, which is listed in clockwise order around the well. However, nobody is willing to contribute to a fund to which his neighbor has also contributed. Next-door neighbors are always listed consecutively in donations, except that the first and last entries in donations are also for next-door neighbors. You must calculate and return the maximum amount of donations that can be collected.


Definition

Class:	BadNeighbors
Method:	maxDonations
Parameters:	int[]
Returns:	int
Method signature:	int maxDonations(int[] donations)
(be sure your method is public)


Constraints
-	donations contains between 2 and 40 elements, inclusive.
-	Each element in donations is between 1 and 1000, inclusive.

Examples
0)

 { 10, 3, 2, 5, 7, 8 }
Returns: 19
The maximum donation is 19, achieved by 10+2+7. It would be better to take 10+5+8 except that the 10 and 8 donations are from neighbors.
1)

{ 11, 15 }
Returns: 15
2)

{ 7, 7, 7, 7, 7, 7, 7 }
Returns: 21
3)

{ 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 }
Returns: 16
4)

{ 94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61,
  6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397,
  52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72 }
Returns: 2926
  */
object BadNeighbor {
  /**
    * Recurrence Relation
    *
    * Let M[n] denote the maximum donation when ith household is selected.
    *
    * M[n] = Max (d[n] + M[n-2], if (n-2 > 0 && n+1 > 0)
    *             M[n-1)
    *
    * M[n-2] = 0 when n-2 > 0
    * It says, Max of when d[i] is selected along with M[i-2] or when d[i] is
    * not selected, then M[i-1].
    */
  def calcDonation(d : Array[Int]) : Int = {

    val size = d.size

    def containsNeighbors(picked : Set[Int], n : Int) =
      picked.contains((size + n + 1)%size) ||
      picked.contains((size + n - 1)%size) || picked.contains(n)

    def addIfNotNeighbor(picked : Set[Int], n : Int) =
      if (containsNeighbors(picked, n)) picked else picked+n

    def m(d : Array[Int], n: Int, picked : Set[Int], sum : Int) : Int = {
      if (n > 0 && containsNeighbors(picked, n)) sum
      else if (n == 0) {
        if (!containsNeighbors(picked, n)) d(n)+sum else sum
      }
      else
        math.max(m(d, (size + n - 2) % size, addIfNotNeighbor(picked, n), sum + d(n)),
          m(d, (size + n-1) % size, picked, sum))
    }

    m(d, d.size - 1, Set.empty, 0)
  }
}
