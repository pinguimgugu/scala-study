import CalculadorDeposito.regraDeposito

object Main extends App {
  println(Deposito.depositar(new Poupanca, 10))
}

case object CalculadorDeposito {
  type regraDeposito = (Int) => Int

  def apply(regra: regraDeposito): CalculadorDeposito = new CalculadorDeposito(regra)
}

case class CalculadorDeposito(fn: regraDeposito) {
  def calcular(valor: Int) = fn(valor)
}

object Deposito {
  def depositar(conta: Conta, valor: Int): Int = CalculadorDeposito(getCalculo(conta)).calcular(valor)

  def getCalculo(conta: Conta): regraDeposito = {
    conta match {
      case _: Corrente => corrente
      case _: Poupanca => poupanca
      case _ => semConta
    }
  }

  val corrente = (valor: Int) => valor * 2

  val poupanca = (valor: Int) => valor * 3

  val semConta = (valor: Int) => valor
}

trait Conta
class Corrente extends Conta
class Poupanca extends Conta

