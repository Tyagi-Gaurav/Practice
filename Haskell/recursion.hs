-- Zip using recursion
zipx :: [a] -> [b] -> [(a,b)]
zipx [] _ = []
zipx _ [] = []
zipx (x:xs) (y:ys) = (x,y) : zip xs ys

-- Drop using recursion
dropx :: Int -> [a] -> [a]
dropx _ [] = []
dropx 0 xs = xs
dropx n (x:xs) = dropx (n-1) xs

-- Product of a list of numbers
productx :: [Int] -> Int
productx [] = 1
productx (x:xs) = x * product xs

-- Factorial
fact :: Int -> Int
fact x | x <= 0 = 1
       | otherwise = x * fact (x -1)

-- SumDown
sumDown :: Int -> Int
sumDown 0 = 0
sumDown x = x + sumDown (x - 1)

-- euclid
gcdx :: Int -> Int -> Int
gcdx x y | x == y = x
        | x > y = gcdx (x - y) y
        | y > x = gcdx x (y -x)

-- Are all logical values in a list True
andx :: [Bool] -> Bool
andx [] = True
andx (x:xs) = x && andx xs

-- Concat a list of lists
concatx :: [[a]] -> [a]
concatx [] = []
concatx (x:xs) = x ++ concatx xs

-- Select nth element of the list
selectn :: Int -> [a] -> a
selectn 0 (x:xs) = x
selectn n (x:xs) = selectn (n-1) xs

-- Merge
merge :: Ord a => [a] -> [a] -> [a]
merge x [] = x
merge [] y = y
merge (x:xs) (y:ys) | x <= y = x : merge xs (y:ys)
                    | otherwise = y : merge (x:xs) ys

-- Halve a list into 2 lists
fhalve :: [a] -> [a]
fhalve [] = []
fhalve xs = take (length(xs) `div` 2) xs

shalve :: [a] -> [a]
shalve [] = []
shalve xs = drop (length(xs) `div` 2) xs

-- Merge Sort
msort :: Ord a => [a] -> [a]
msort [] = []
msort (x:[]) = [x]
msort xs = merge (msort (fhalve xs))  (msort (shalve xs))
