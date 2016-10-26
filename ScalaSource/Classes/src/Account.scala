class Account {
  val id = Account.newUniqueNumber()

}

object Account {
  private var lastUniqueNumber = 0

  private def newUniqueNumber() {
    lastUniqueNumber += 1
    lastUniqueNumber
  }

}
