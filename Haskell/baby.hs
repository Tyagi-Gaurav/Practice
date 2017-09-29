double x = x + x

doubleus x y = x *2 + y * 2

boomBang xs = [ if x < 10 then "Boom!" else "Bang!" | x <- xs, odd x ]

length' xs = sum [1 | _ <- xs]

-- Which right triangle that has integers for all sides and all sides equal to or smaller than 10 has a
-- perimeter of 24?
allTriangles = [ (a,b,c) | c <- [1..10], b <- [1..c], a <- [1..b] ]
allTriangles2 = [(a,b,c) | c <- [1..10], b <- [1..c], a <- [1..b], a+b+c == 24]
allTriangles3 = [(a,b,c) | c <- [1..10], b <- [1..c], a <- [1..b], a+b+c == 24, a*a + b*b == c*c]

-- Quick Sort
mysort' [] = []
mysort' (x:xs) = mysort' smaller ++ [x] ++ mysort' larger
              where
                smaller = [a | a <- xs, a <= x]
                larger = [b | b <- xs, b > x]

-- Reverse Quick Sort
rqsort' [] = []
rqsort' (x:xs) = rqsort' larger ++ [x] ++ rqsort' smaller
              where
                smaller = [a | a <- xs, a <= x]
                larger = [b | b <- xs, b > x]


-- Find product of a list of numbers
prod' (x:[]) = x
prod' (x:xs) = x * prod'(xs)

-- Functions using guards
signum' n | n < 0 = -1
         | n == 0 = 0
         | otherwise = 1

-- Halve :: [a] -> ([a], [a]) that splits even length lists into 2 halves
halve (xs) | length (xs) == 0 = ([],[])
halve (xs) | length (xs) `mod` 2 == 0 = (take (length (xs) `div` 2) xs, drop (length (xs) `div` 2) xs)
halve (xs) | otherwise = (xs,[])

-- Example
newN = a `div` length xs
  where
    a = 10
    xs = [1,2,3,4,5]

-- Defining last function in terms of other functions
last1 xs = head (reverse xs)
last2 xs = xs !! ((length xs) - 1)
last3 xs = drop ((length xs) - 1) xs -- Returns the number as a list

-- Define init function in 2 other ways
init1 xs = take ((length xs) - 1) xs

init2 (x:[]) = []
init2 (x:xs) = [x] ++ init2(xs)

-- Check if number is even
even' :: Integral a => a -> Bool
even' n = n `mod` 2 == 0

-- Split a list at the nth element
split' :: Int -> [a] -> ([a],[a])
split' n xs = (take n xs, drop n xs)

-- reciprocal
reci' :: Fractional x => x -> x
reci' x = 1/x

-- Print n odd numbers
odds :: Int -> [Int]
odds n = map f [0..n-1]
         where f x = 2 * x + 1

halve' :: [n] -> ([n], [n])
halve' xs = (take (f xs) xs, drop (f xs) xs)
      where f xs = length(xs) `div` 2


-- Define a function that returns the 3rd element in the list
-- Using head and tail
third1 :: Num a => [a] -> a
third1 xs | length (xs) < 3 = 0
          | otherwise = head (tail (tail xs))

third2 :: Num a => [a] -> a
third2 xs | length (xs) < 3 = 0
          | otherwise = xs !! 2

-- Pattern Matching
third3 :: Num a => [a] -> a
third3 [x] = 0
third3 [x, y] = 0
third3 [x, y, z] = z
third3 [_, _, z, _] = z

null' :: [a] -> Bool
null' xs | (length(xs) == 0) = True
         | otherwise = False

-- Maps empty list to itself using guarded equations
safeTail1 :: [a] -> [a]
safeTail1 xs | null' xs = xs
            | otherwise = tail(xs)

-- Maps empty list to itself using conditional expression
safeTail2 :: [a] -> [a]
safeTail2 xs = if (null' xs) then xs else tail(xs)

-- Maps empty list to itself using pattern matching
safeTail3 :: [a] -> [a]
safeTail3 [] = []
safeTail3 (x:xs) = xs

-- Find the largest number under 100,000 that's divisible by 3829
head (reverse [x | x <- [3829..100000], x `mod` 3829 == 0])
head [x | x <- [100000,99999..], x `mod` 3829 == 0]
