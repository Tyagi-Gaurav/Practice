import behindEnemyLines.CheckPermutation
import org.scalatest.FunSuite

class CheckPermutationSuite extends FunSuite {

  test("Should return false as permutation for a string with diff size") {
    assert(CheckPermutation.isPermutation("b","abc") == false)
  }

  test("Should return false as permutation for a string") {
    assert(CheckPermutation.isPermutation("bda","abc") == false)
  }

  test("Should return true as permutation for a string") {
    assert(CheckPermutation.isPermutation("bca","abc") == true)
  }

  test("Should return false as permutation for a string test n") {
    assert(CheckPermutation.isPermutation("ZVMexI","MexIco") == false)
  }

  test("Return permutation count for test 1") {
    assert(CheckPermutation.findPermutationCount("cAda", "AbrAcadAbRa") == 2)
  }

  test("Return permutation count for test 2") {
    assert(CheckPermutation.findPermutationCount("MexIco",
      "MexIcobMexIcobFILbMexIcobMexIcobjMexIcobvMexIcobMexIcobMexIcobgMexIcobdMexIcobZVMexIcobMexIcobaMexIcob") == 13)
  }
}
