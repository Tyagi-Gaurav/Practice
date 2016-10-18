package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
	trait TestTrees {
		val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
		val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
		val bigTree = Fork(Leaf('a',8),
											Fork(
													Fork(Leaf('b',3),
																Fork(Leaf('c',1), Leaf('d',1), List('c','d'), 2),
																List('b','c','d'), 5),
													Fork(Fork(Leaf('e',1), Leaf('f',1), List('e','f'), 2),
															Fork(Leaf('g',1), Leaf('h',1), List('g','h'), 2),
															List('e','f','g','h'), 4),
													List('b','c','d', 'e','f','g','h'), 9),
										List('a','b','c','d', 'e','f','g','h'), 17)

		val bigCodeTable = List(
														('a', List(0)),
														('b', List(1,0,0)),
														('c', List(1,0,1,0)),
														('d', List(1,0,1,1)),
														('e', List(1,1,0,0)),
														('f', List(1,1,0,1)),
														('g', List(1,1,1,0)),
														('h', List(1,1,1,1))
													)
	}

	test("weight of a larger tree") {
		new TestTrees {
			assert(weight(t1) === 5)
		}
	}

	test("weight of big tree") {
		new TestTrees {
			assert(weight(bigTree) === 17)
		}
	}

	test("chars of big tree") {
		new TestTrees {
			assert(chars(bigTree) === List('a','b','c','d', 'e','f','g','h')	)
		}
	}

	test("chars of a larger tree") {
		new TestTrees {
			assert(chars(t2) === List('a','b','d'))
		}
	}

	test("string2chars(\"hello, world\")") {
		assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
	}

	test("times for small List of chars") {
		val timeResult = times(List('a', 'b', 'a'))
		assert(timeResult == List(('b',1), ('a',2)))
	}

	test("makeOrderedLeafList for some frequency table") {
		assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
	}

	test("combine of some leaf list") {
		val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
		assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
	}

	test("Encoding small list of characters") {
		new TestTrees {
			assert(List(1,0,0,0,1,0,1,0) === encode(bigTree)(string2Chars("bac")))
		}
	}

	test("Decoding small list of characters") {
		new TestTrees {
			assert(string2Chars("bac") === decode(bigTree, List(1,0,0,0,1,0,1,0)))
		}
	}

	test("Converting from bigTree to Code Table") {
		new TestTrees {
			assert(convert(bigTree) === bigCodeTable)
		}
	}

	test("decode and encode a very short text should be identity") {
		new TestTrees {
			assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
		}
	}

	test("QuickEncode test with bigTree") {
		new TestTrees {
			assert(List(1,0,0,0,1,0,1,0) === quickEncode(bigTree)(string2Chars("bac")))
		}
	}
}
