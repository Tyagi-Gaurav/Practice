/**
 *
 * @author: tyagig
 */
def pascal(c:Int, r: Int) : Int = {
  def pascalGenerate(c:Int, r:Int) : Int = {
    if (c <=r) {
      if (r == 0) 1 else {
        if (c == 0) 1 else
          pascalGenerate(c-1, r-1) + pascalGenerate(c, r-1)
      }
    } else 0
  }

  pascalGenerate(c,r)
}

println(pascal(0,0))
println(pascal(0,1))
println(pascal(1,1))
println(pascal(1,2))
println(pascal(1,3))
println(pascal(2,4))
println(pascal(3,6))
