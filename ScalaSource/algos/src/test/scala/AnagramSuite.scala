import org.scalatest.Suite

class AnagramSuite extends Suite {
	def testValidAnagram() {
		val anagramWord = Anagram("areallylongword")
		assert(anagramWord.verify("no") == true)
	}
	
	def testInvalidAnagram() {
		val anagramWord = Anagram("areallylongword")
		assert(anagramWord.verify("adder") == false)
	}
}