-- Concat List of Lists into a single list
concat' :: [[a]] -> [a]
concat' xss = [x | xs <- xss, x <- xs]

-- Sum all elements of an array
x = sum [1..5]

-- Sum of first 100 squares
sumOfSquares =  sum [ x * x | x <- [1..100]]

-- Grid
grid :: Int -> Int -> [(Int, Int)]
grid x y = [(a,b) | a <- [0..x], b <- [0..y]]

-- Return square grid withour diagonals
squareGridND :: Int  -> [(Int, Int)]
squareGridND x = [(a,b) | (a,b) <- grid x x, a /= b]

-- Replicate
replicate' :: Int -> a -> [a]
replicate' n c = [c | _ <- [1..n]]

-- Pyhtagoras theorem
pythos :: Int -> [(Int, Int, Int)]
pythos x = [(a, b, c) | a <- [1..x], b <- [1..x], c <- [1..x], a^2 + b^2 == c^2]

-- Find the factors of a number
factors' :: Int -> [Int]
factors' n = [x | x <- [1..n], n `mod` x == 0]

-- Perfect Number
perfect :: Int -> Bool
perfect n = sum [x | x <- factors' n, x /= n] == n

perfects :: Int -> [Int]
perfects n = [x | x <- [1..n], perfect x]

-- Two List Comprehensions with single generator
tl2wsg = concat [[(x, y) | x <- [1,2]] | y <- [3,4]]

-- Find function
find :: Eq a => a -> [(a,b)] -> [b]
find k t = [v | (k',v) <- t, k == k']

-- Positions using Finds
positions :: Eq a => a -> [a] -> [Int]
positions x xs = [v | v <- find x (zip xs [0..])]

-- Scalar Product
scalaProduct :: [Int] -> [Int] -> Int
scalaProduct xs ys = sum [x * y | (x, a) <- zip xs [1..], (y, b) <- zip ys [1..], a == b]

scalaProduct1 :: [Int] -> [Int] -> Int
scalaProduct1 xs ys = sum [x * y | (x, y) <- zip xs ys]