
val nums = List(-1,2,3,4,5,6,  -4)

//Sum of all elements
val sum1 = 0::nums reduceLeft(_+_)
val sum2 = (nums foldLeft 0)(_+_)
val product1 = 1::nums reduceLeft(_*_)
val product2 = (nums foldLeft 1)(_*_)
