package abstractTypes

class Food
abstract class Animal {
  type SuitableFood <: Food
  def eat(food : SuitableFood)
}

class Grass extends Food
class Cow extends Animal {
  override type SuitableFood = Grass
  override def eat(food: Grass): Unit = ???
}
