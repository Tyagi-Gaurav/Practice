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