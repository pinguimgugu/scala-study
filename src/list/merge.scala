package list

import scala.collection.immutable.{AbstractSeq, LinearSeq}

object merge extends App {
  val users: Seq[User] = Seq(
    User(1, "Claudio", 20),
    User(2, "Jose", 30),
    User(3, "Mary", 40),
    User(4, "Ana", 24),
    User(5, "Patty", 18)
  )

  val usersBankAccounts: Seq[UserBankAccount] = Seq(
    UserBankAccount(1, 20, 341),
    UserBankAccount(3, 500, 104),
    UserBankAccount(5, 10, 1)
  )

  def builder(user: User)(userBank: Option[UserBankAccount]): UserReport = {
    val userReport = UserReport(user.id, user.name, user.age, 0, 0)

    userBank.map(ub => userReport.copy(amount = ub.amount, bankNumber = ub.bankNumber))
        .getOrElse(userReport)
  }


  val report = users.map(user => {
    val report = builder(user) _
    usersBankAccounts.find(ub => ub.userId == user.id)
      .map(userBankAccount => report(Some(userBankAccount)))
      .getOrElse(report(None))
  })

  println(report)
}

case class User(id: Int, name: String, age: Int)

case class UserBankAccount(userId: Int, amount: Int, bankNumber: Int)

case class UserReport(id: Int, name: String, age: Int, amount: Int, bankNumber: Int)