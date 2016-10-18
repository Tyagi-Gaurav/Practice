import org.scalatest.FunSuite

class AnagramFunSuite extends FunSuite {

	test("Should test for valid Anagram") {
		val anagramWord = Anagram("areallylongword")
		assert(anagramWord.verify("no") == true)
	}
		
	test("Should fail on Invalid Anagram") {
		val anagramWord = Anagram("areallylongword")
		assert(anagramWord.verify("adder") == false)
	}
}