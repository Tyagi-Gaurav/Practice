package behindEnemyLines

object CheckPermutation {
  def isPermutation(perm : String, source : String): Boolean = {
    val permSize = perm.size

    if (permSize != source.size) false
    var checker = 0

    val initSet = perm.toSet
    source.filter(x => !initSet.contains(x)).size == 0
  }

  def findPermutationCount(perm : String, source : String) : Int = {
    val sourceSize = source.size
    val permSize = perm.size

    if (permSize > sourceSize) 0
    var count = 0

    for (i <- 0 to sourceSize - permSize) {
      if (isPermutation(source.substring(i, i + permSize), perm)) count += 1
    }

    count
  }
}
